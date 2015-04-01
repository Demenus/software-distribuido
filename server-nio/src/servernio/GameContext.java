package servernio;

import comutils.ChannelUtils;
import constants.Commands;
import exceptions.ErrType;
import exceptions.applicationexceptions.ApplicationException;
import exceptions.connectionexceptions.ReadException;
import exceptions.connectionexceptions.TimeOutException;
import exceptions.connectionexceptions.WriteException;
import exceptions.protocolexceptions.CommandException;
import exceptions.protocolexceptions.ParseException;
import exceptions.protocolexceptions.StateException;
import nio.ChannelReaderManager;
import nio.ChannelWriterManager;
import servershared.GameStateMachine;
import servershared.ServerLogger;
import servershared.context.Context;
import servershared.gamelayer.GameController;
import servershared.io.ReaderManager;
import servershared.io.WriterManager;
import servershared.statemachine.StateMachine;

import java.io.File;
import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.util.logging.Level;
import java.util.logging.Logger;


public class GameContext implements Context {

    private static final int sTimeOut = 500;
    private static final int sMaxConnectionErrors = 600;
    private static final int sMaxErrors = 15;
    private Logger log = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private ServerLogger mLogger;
    private SocketChannel mSocketChannel;
    private GameStateMachine mStateMachine;
    private GameController mGameController;
    private int mGameId;
    private String mDeckFile;
    private int mConnectionErrCount = 0;
    private int mErrCount = 0;

    public GameContext(int gameId, SocketChannel socketChannel, String fileDeck, int bet) {
        mGameId = gameId;
        mDeckFile = fileDeck;
        mSocketChannel = socketChannel;
        mStateMachine = new GameStateMachine();
        mGameController = new GameController(fileDeck, bet);
        mStateMachine.setGameController(mGameController);
        mStateMachine.initialize();
    }

    @Override
    public StateMachine getStateMachine() {
        return mStateMachine;
    }

    public void setSocketChannel(SocketChannel channel) {
        mSocketChannel = channel;
    }

    @Override
    public ReaderManager getReader() throws IOException {
        ChannelUtils.Reader reader = new ChannelUtils.Reader(mSocketChannel);
        ChannelReaderManager manager = new ChannelReaderManager(reader);
        return manager;
    }

    @Override
    public WriterManager getWriter() throws IOException {
        ChannelUtils.Writer writer = new ChannelUtils.Writer(mSocketChannel);
        ChannelWriterManager manager = new ChannelWriterManager(writer);
        return manager;
    }

    private void createLogger() {
        mLogger = new ServerLogger() {
            @Override
            public String getFileName() {
                String folder = mDeckFile.replace('.','_');
                File folderFile = new File(folder);
                folderFile.mkdir();
                return "ServerGame-"+mGameId+".log";
            }
        };
    }

    @Override
    public void initContext() {
        createLogger();
    }

    @Override
    public void processInputData() {
        try {
            innerProcessInputData(getReader(), getWriter());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void innerProcessInputData(ReaderManager readerManager, WriterManager writerManager) {
        if (!mStateMachine.isInFinalState() && isValidContext()) {
            try {
                mStateMachine.processNext(mLogger, readerManager, writerManager);
                mErrCount = 0;
                mConnectionErrCount = 0;
            } catch (ReadException e) {
                onError(writerManager, e.getErrType(), e.getMessage());
            } catch (TimeOutException e) {
                onError(writerManager, e.getErrType(), e.getMessage());
            } catch (CommandException e) {
                onError(writerManager, e.getErrType(), e.getMessage());
            } catch (ParseException e) {
                onError(writerManager, e.getErrType(), e.getMessage());
            } catch (StateException e) {
                onError(writerManager, e.getErrType(), e.getMessage());
            } catch (WriteException e) {
                onError(writerManager, e.getErrType(), e.getMessage());
            } catch (ApplicationException e) {
                onError(writerManager, e.getErrType(), e.getMessage());
            }
        } else {
            disposeContext();
        }
    }

    @Override
    public void closeConnection() {
        log.log(Level.OFF, "Clossing connection of: "+Thread.currentThread().getName());
        try {
            mSocketChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isValidContext() {
        return mSocketChannel.isConnected() && mConnectionErrCount <= sMaxConnectionErrors && mErrCount <= sMaxErrors;
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
                mErrCount++;
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
    }
}
