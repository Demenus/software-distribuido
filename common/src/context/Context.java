package context;

import exceptions.BaseException;
import exceptions.ErrType;
import exceptions.applicationexceptions.ApplicationException;
import exceptions.connectionexceptions.ReadException;
import exceptions.connectionexceptions.WriteException;
import exceptions.protocolexceptions.CommandException;
import exceptions.protocolexceptions.ParseException;
import exceptions.protocolexceptions.StateException;
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

    public void processInputData() {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            inputStream = mSocket.getInputStream();
            outputStream = mSocket.getOutputStream();
        } catch (IOException e) {
            onConnectionError();
        }
        innerProcessInputData(inputStream, outputStream);
    }

    public void innerProcessInputData(InputStream inputStream, OutputStream outputStream) {
        StateNode node = null;
        ArrayList<BaseException> exceptions = new ArrayList<BaseException>();
        try {
            node = mStateMachine.getNextStateNode(inputStream);
            Object responseData = mStateMachine.getResponseData(inputStream);
            node.onSuccess(outputStream, responseData);
        } catch (CommandException e) {
            exceptions.add(e);
        } catch (ApplicationException e) {
            exceptions.add(e);
        } catch (StateException e) {
            exceptions.add(e);
        } catch (ParseException e) {
            exceptions.add(e);


        } catch (ReadException e) {
            onError(outputStream, e.getErrType(), e.getMessage());
        } catch (WriteException e) {
            onError(outputStream, e.getErrType(), e.getMessage());
        }

        for (BaseException e : exceptions) {
            try {
                if (node != null) {
                    node.onError(outputStream, e.getErrType(), e.getMessage());
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

    public abstract void onError(OutputStream stream, ErrType errType, String message);

    public void onTimeOut() {
        try {
            mSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
