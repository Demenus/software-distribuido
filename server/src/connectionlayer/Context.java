package connectionlayer;

import comutils.ComUtils;
import connectionlayer.io.ComutilsReader;
import connectionlayer.io.ComutilsWriter;
import exceptions.BaseException;
import exceptions.ErrType;
import exceptions.applicationexceptions.ApplicationException;
import exceptions.connectionexceptions.ReadException;
import exceptions.connectionexceptions.WriteException;
import exceptions.protocolexceptions.CommandException;
import exceptions.protocolexceptions.ParseException;
import exceptions.protocolexceptions.StateException;
import io.Reader;
import io.Writer;
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
public abstract class Context {

    private Socket mSocket;
    private StateMachine mStateMachine;

    public Context(Socket socket, StateMachine stateMachine) {
        mSocket = socket;
        mStateMachine = stateMachine;
    }

    public StateMachine getStateMachine() {
        return mStateMachine;
    }

    public void setStateMachine(StateMachine stateMachine) {
        mStateMachine = stateMachine;
    }

    public Socket getSocket() {
        return mSocket;
    }

    public void setSocket(Socket socket) {
        mSocket = socket;
    }

    public InputStream getSocketInputStream() throws IOException {
        return mSocket.getInputStream();
    }

    public OutputStream getSocketOutputStream() throws IOException {
        return mSocket.getOutputStream();
    }

    public void processInputData() {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            inputStream = getSocketInputStream();
            outputStream = getSocketOutputStream();
        } catch (IOException e) {
            onConnectionError();
        }
        ComutilsReader reader = new ComutilsReader(new ComUtils.Reader(inputStream));
        ComutilsWriter writer = new ComutilsWriter(new ComUtils.Writer(outputStream));
        innerProcessInputData(reader, writer);
    }

    private void innerProcessInputData(Reader reader, Writer writer) {
        StateNode node = null;
        String candateState;
        ArrayList<BaseException> exceptions = new ArrayList<BaseException>();
        try {
            candateState = mStateMachine.getNextCandidateState(reader);
            node = mStateMachine.getNextCandidateStateNode(candateState);
            mStateMachine.checkNextCandidateNode(node,candateState);
            Object responseData = mStateMachine.getResponseData(reader);
            node.onSuccess(writer, responseData);
        } catch (CommandException e) {
            exceptions.add(e);
        } catch (ApplicationException e) {
            exceptions.add(e);
        } catch (StateException e) {
            exceptions.add(e);
        } catch (ParseException e) {
            exceptions.add(e);


        } catch (ReadException e) {
            onError(writer, e.getErrType(), e.getMessage());
        } catch (WriteException e) {
            onError(writer, e.getErrType(), e.getMessage());
        }

        for (BaseException e : exceptions) {
            try {
                if (node != null) {
                    node.onError(writer, e.getErrType(), e.getMessage());
                }
            } catch (WriteException e1) {
                //e1.printStackTrace();
            }
        }
    }

    public void closeConnection() {
        try {
            mSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onConnectionError() {
        try {
            mSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public abstract void onError(Writer writer, ErrType errType, String message);

    public void onTimeOut() {
        try {
            mSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
