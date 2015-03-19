package server;

import comutils.ComUtils;
import context.Context;
import exceptions.ErrType;
import exceptions.applicationexceptions.ApplicationException;
import exceptions.connectionexceptions.ReadException;
import exceptions.connectionexceptions.TimeOutException;
import exceptions.connectionexceptions.WriteException;
import exceptions.protocolexceptions.CommandException;
import exceptions.protocolexceptions.ParseException;
import exceptions.protocolexceptions.StateException;
import gamelayer.GameController;
import io.ComUtilsReaderManager;
import io.ComUtilsWriterManager;
import io.ReaderManager;
import io.WriterManager;
import statemachine.StateMachine;
import statemachine.StateNode;

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
    private static final int sMaxConnectionErrors = 5;
    private static final int sMaxErrors = 15;
    private Logger log = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private Socket mSocket;
    private GameStateMachine mStateMachine;
    private GameController mGameController;
    private int mConnectionErrCount = 0;
    private int mErrCount = 0;

    public GameContext(Socket socket, String fileDeck, int bet) {
        mSocket = socket;
        mStateMachine = new GameStateMachine();
        mGameController = new GameController(fileDeck, bet);
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


    @Override
    public void processInputData() {
        try {
            innerProcessInputData(getReader(), getWriter());
        } catch (IOException e) {
            onConnectionError();
        }
    }

    private void innerProcessInputData(ReaderManager readerManager, WriterManager writerManager) {
        StateNode node = null;
        String candateState;
        //ArrayList<BaseException> exceptions = new ArrayList<BaseException>();

        log.log(Level.INFO, "Processing from "+Thread.currentThread().getName());
        //TODO: Ojo!! si hay un fallo de escritura no vuelves a intentarlo o que majete?!
        while (!mStateMachine.isInFinalState() && isValidContext()) {
            try {
                candateState = mStateMachine.getNextCandidateState(readerManager);
                node = mStateMachine.getNextCandidateStateNode(candateState);
                mStateMachine.checkNextCandidateNode(node,candateState);
                mStateMachine.processCurrentNode(readerManager, writerManager);
                log.log(Level.INFO,"Thread: "+Thread.currentThread().getName()+ "Current State: "+candateState);
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
        closeConnection();
    }

    @Override
    public void closeConnection() {
        log.log(Level.INFO, "Clossing connection of: "+Thread.currentThread().getName());
        try {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(GameContext.class.getName()).log(Level.SEVERE, null, ex);
            }
            mSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onConnectionError() {
        try {
            mSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isValidContext() {
        return !mSocket.isClosed() && mConnectionErrCount <= sMaxConnectionErrors && mErrCount <= sMaxErrors;
    }

    @Override
    public void onError(WriterManager writerManager, ErrType errType, String message) {
        try {
            ComUtilsWriterManager w = (ComUtilsWriterManager) writerManager;
            if (errType == ErrType.TIMEOUT_ERROR) {
                //log.log(Level.INFO, "Thread: "+Thread.currentThread().getName()+" & Error found: "+errType.toString()+" & message: "+message);
                if (!isValidContext()) {
                    closeConnection();
                }
                mConnectionErrCount++;
                //w.writeError(errType, message);
                //closeConnection();
            }
            else if (errType == ErrType.WRITE_ERROR || errType == ErrType.READ_ERROR) {
                if (!isValidContext()) {
                    closeConnection();
                }
                mConnectionErrCount++;
            } else if (errType == ErrType.COMMAND_ERROR) {
                log.log(Level.INFO, "Thread: "+Thread.currentThread().getName()+" & Error found: "+errType.toString()+" & message: "+message);
                if (isValidContext()) {
                    w.writeError(errType, message);
                    mErrCount++;
                } else {
                    w.writeExceededErrors();
                    closeConnection();
                }
            } else {
                log.log(Level.INFO, "Thread: "+Thread.currentThread().getName()+" & Error found: "+errType.toString()+" & message: "+message);
                if (isValidContext()) {
                    mStateMachine.getCurrentStateNode().onError(writerManager, errType, message);
                    mErrCount++;
                } else {
                    w.writeExceededErrors();
                    closeConnection();
                }
            }
        } catch (WriteException e) {
            closeConnection();
        } catch (TimeOutException e) {
            closeConnection();
        }
    }

    @Override
    public void disposeContext() {
        closeConnection();
        //TODO: Dispose controller and state machine
    }
}
