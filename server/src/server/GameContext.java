package server;

import comutils.ComUtils;
import io.ComUtilsReaderManager;
import io.ComUtilsWriterManager;
import context.Context;
import exceptions.BaseException;
import exceptions.ErrType;
import exceptions.applicationexceptions.ApplicationException;
import exceptions.connectionexceptions.ReadException;
import exceptions.connectionexceptions.WriteException;
import exceptions.protocolexceptions.CommandException;
import exceptions.protocolexceptions.ParseException;
import exceptions.protocolexceptions.StateException;
import io.ReaderManager;
import io.WriterManager;
import statemachine.StateMachine;
import statemachine.StateNode;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by aaron on 24/02/2015.
 */
public class GameContext implements Context {

    private Socket mSocket;
    private StateMachine mStateMachine;

    public GameContext(Socket socket, StateMachine stateMachine) {
        mSocket = socket;
        mStateMachine = stateMachine;
    }

    @Override
    public StateMachine getStateMachine() {
        return mStateMachine;
    }

    public Socket getSocket() {
        return mSocket;
    }

    public void setSocket(Socket socket) {
        mSocket = socket;
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
        ArrayList<BaseException> exceptions = new ArrayList<BaseException>();
        try {
            candateState = mStateMachine.getNextCandidateState(readerManager);
            node = mStateMachine.getNextCandidateStateNode(candateState);
            mStateMachine.checkNextCandidateNode(node,candateState);
            Object responseData = mStateMachine.getResponseData(readerManager);
            node.onSuccess(writerManager, responseData);
        } catch (CommandException e) {
            exceptions.add(e);
        } catch (ApplicationException e) {
            exceptions.add(e);
        } catch (StateException e) {
            exceptions.add(e);
        } catch (ParseException e) {
            exceptions.add(e);


        } catch (ReadException e) {
            onError(writerManager, e.getErrType(), e.getMessage());
        } catch (WriteException e) {
            onError(writerManager, e.getErrType(), e.getMessage());
        }

        for (BaseException e : exceptions) {
            try {
                if (node != null) {
                    node.onError(writerManager, e.getErrType(), e.getMessage());
                }
            } catch (WriteException e1) {
                //e1.printStackTrace();
            }
        }
    }

    @Override
    public void closeConnection() {
        try {
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
    public void onError(WriterManager writerManager, ErrType errType, String message) {

    }

    @Override
    public void onTimeOut() {
        try {
            mSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
