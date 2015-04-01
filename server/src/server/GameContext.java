package server;

import comutils.ComUtils;
import constants.Commands;
import exceptions.ErrType;
import exceptions.applicationexceptions.ApplicationException;
import exceptions.connectionexceptions.ReadException;
import exceptions.connectionexceptions.TimeOutException;
import exceptions.connectionexceptions.WriteException;
import exceptions.protocolexceptions.CommandException;
import exceptions.protocolexceptions.ParseException;
import exceptions.protocolexceptions.StateException;
import io.ComUtilsReaderManager;
import io.ComUtilsWriterManager;
import servershared.GameStateMachine;
import servershared.ServerLogger;
import servershared.context.Context;
import servershared.gamelayer.GameController;
import servershared.io.ReaderManager;
import servershared.io.WriterManager;
import servershared.statemachine.StateMachine;
import servershared.statemachine.StateNode;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by aaron on 24/02/2015.
 */
public class GameContext implements Context {

    private static final int sTimeOut = 500;
    private static final int sMaxConnectionErrors = 600;
    private static final int sMaxErrors = 15;
    private Logger log = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private ServerLogger mLogger;
    private Socket mSocket;
    private GameStateMachine mStateMachine;
    private GameController mGameController;
    private int mConnectionErrCount = 0;
    private int mErrCount = 0;
    private String mDeckFile;

    public GameContext(Socket socket, String fileDeck, int bet) {
        mSocket = socket;
        mDeckFile = fileDeck;
        mStateMachine = new GameStateMachine();
        mGameController = new GameController(mDeckFile, bet);
        mStateMachine.setGameController(mGameController);
        mStateMachine.initialize();
    }

    @Override
    public void initContext() throws SocketException {
        mSocket.setSoTimeout(sTimeOut);
    }

    @Override
    public StateMachine getStateMachine() {
        return mStateMachine;
    }


    @Override
    public ReaderManager getReader() throws IOException {
        InputStream stream = mSocket.getInputStream();
        ComUtils.Reader reader = new ComUtils.Reader(stream);
        return new ComUtilsReaderManager(reader);
    }

    @Override
    public WriterManager getWriter() throws IOException {
        OutputStream stream = mSocket.getOutputStream();
        ComUtils.Writer writer = new ComUtils.Writer(stream);
        return new ComUtilsWriterManager(writer);
    }

    private void createLogger() {
        mLogger = new ServerLogger() {
            @Override
            public String getFileName() {
                String folder = mDeckFile.replace('.','_');
                File folderFile = new File(folder);
                folderFile.mkdir();
                return folder + "/" + Thread.currentThread().getName()+".log";
            }
        };
    }

    @Override
    public void processInputData() {
        createLogger();
        log.log(Level.INFO, "New connection from " + mSocket.getInetAddress());
        try {
            innerProcessInputData(getReader(), getWriter());
        } catch (IOException e) {
            disposeContext();
        }
    }

    private void innerProcessInputData(ReaderManager readerManager, WriterManager writerManager) {
        StateNode node = null;
        String candidateState;
        //TODO: Ojo!! si hay un fallo de escritura no vuelves a intentarlo o que majete?!
        while (!mStateMachine.isInFinalState() && isValidContext()) {
            try {
                /*candidateState = mStateMachine.getNextCandidateState(readerManager);
                node = mStateMachine.getNextCandidateStateNode(candidateState);
                mStateMachine.checkNextCandidateNode(node,candidateState);
                mStateMachine.processCurrentNode(readerManager, writerManager);
                log.log(Level.INFO, "Thread: " + Thread.currentThread().getName() + " Current State: " + candidateState);*/
                mStateMachine.processNext(mLogger, readerManager, writerManager);
                mErrCount = 0;
                mConnectionErrCount = 0;
            }  catch (ApplicationException e) {
                onError(writerManager, e.getErrType(), e.getMessage());
            } catch (StateException e) {
                onError(writerManager, e.getErrType(), e.getMessage());
            } catch (ParseException e) {
                onError(writerManager, e.getErrType(), e.getMessage());
            }

            catch (CommandException e) {
                onError(writerManager, e.getErrType(), e.getMessage());
            }

            //Connection error
            catch (ReadException e) {
                onError(writerManager, e.getErrType(), e.getMessage());
            } catch (WriteException e) {
                onError(writerManager, e.getErrType(), e.getMessage());
            } catch (TimeOutException e) {
                onError(writerManager, e.getErrType(), e.getMessage());
            }
        }
        disposeContext();
    }

    @Override
    public void closeConnection() {
        log.log(Level.OFF, "Clossing connection of: "+Thread.currentThread().getName());
        try {
            mSocket.getOutputStream().flush();
            mSocket.getInputStream().close();
            mSocket.getOutputStream().close();
            mSocket.close();
        } catch (IOException e) {

        }
    }

    @Override
    public boolean isValidContext() {
        return !mSocket.isClosed() && mConnectionErrCount <= sMaxConnectionErrors && mErrCount <= sMaxErrors;
    }

    private void writeErrorToLog(ErrType errType, String message) {
        String errLog = Commands.ERROR + " ";
        String error = errType.toString() + ' ' + message;
        if (error.length() < 10) {
            errLog += "0" + String.valueOf(error.length());
        } else {
            errLog += String.valueOf(error.length());
        }
        errLog += error;
        mLogger.writeServer(errLog);
    }

    private void writeExceededErrorToLog() {
        String errLog = Commands.ERROR + " ";
        String error = "Sorry your IQ is too low to play with me, GTFO!";
        if (error.length() < 10) {
            errLog += "0" + error.length();
        } else {
            errLog += error.length();
        }
        errLog += error;
        mLogger.writeServer(errLog);
    }

    @Override
    public void onError(WriterManager writerManager, ErrType errType, String message) {
        try {
            if (errType == ErrType.TIMEOUT_ERROR) {
                if (!isValidContext()) {
                    log.log(Level.SEVERE, "Thread: "+Thread.currentThread().getName()+" & Error found: "+errType.toString()+" & message: "+message);
                    writerManager.writeError(errType, message);
                    writeErrorToLog(errType, message);
                    disposeContext();
                }
                mConnectionErrCount++;
            }
            else if (errType == ErrType.WRITE_ERROR || errType == ErrType.READ_ERROR) {
                if (!isValidContext()) {
                    disposeContext();
                }
            } else if (errType == ErrType.COMMAND_ERROR) {
                log.log(Level.SEVERE, "Thread: "+Thread.currentThread().getName()+" & Error found: "+errType.toString()+" & message: "+message);
                if (isValidContext()) {
                    //writerManager.writeError(errType, message);
                    //writeErrorToLog(errType, message);
                    //mErrCount++;
                } else {
                    writerManager.writeExceededErrors();
                    writeExceededErrorToLog();
                    disposeContext();
                }
            } else if (errType == ErrType.PARSE_ERROR) {
                log.log(Level.SEVERE, "Thread: "+Thread.currentThread().getName()+" & Error found: "+errType.toString()+" & message: "+message);
                if (isValidContext()) {
                    writerManager.writeError(errType, message);
                    writeErrorToLog(errType, message);
                    mErrCount++;
                } else {
                    writerManager.writeExceededErrors();
                    writeExceededErrorToLog();
                    disposeContext();
                }
            } else {
                log.log(Level.SEVERE, "Thread: "+Thread.currentThread().getName()+" & Error found: "+errType.toString()+" & message: "+message);
                if (isValidContext()) {
                    //mStateMachine.getCurrentStateNode().onError(writerManager, errType, message);
                    writerManager.writeError(errType, message);
                    writeErrorToLog(errType, message);
                    mErrCount++;
                } else {
                    writerManager.writeExceededErrors();
                    writeExceededErrorToLog();
                    disposeContext();
                }
            }
        } catch (WriteException e) {
            disposeContext();
        } catch (TimeOutException e) {
            disposeContext();
        }
    }

    @Override
    public void disposeContext() {
        closeConnection();
        mGameController.disposeGameController();
        mLogger.disposeLogger();
        //TODO: Dispose controller and state machine
    }
}
