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
            e.printStackTrace();
            onConnectionError();
        }
        innerProcessInputData(inputStream, outputStream);
    }

    public void innerProcessInputData(InputStream inputStream, OutputStream outputStream) {
        StateNode node = null;
        ArrayList<BaseException> exceptions = new ArrayList<BaseException>();
        try {
            //Object response = mStateMachine.stepState(mSocket.getInputStream());
            //mStateMachine.sendSuccessResponse(mSocket.getOutputStream(), response);
            node = mStateMachine.getNextStateNode(inputStream);
            Object responseData = mStateMachine.getResponseData(inputStream);
            node.onSuccess(outputStream, responseData);
        } catch (CommandException e) {
            e.printStackTrace();
            exceptions.add(e);
        } catch (ApplicationException e) {
            e.printStackTrace();
            exceptions.add(e);
        } catch (StateException e) {
            e.printStackTrace();
            exceptions.add(e);
        } catch (ParseException e) {
            e.printStackTrace();
            exceptions.add(e);
        } catch (ReadException e) {
            e.printStackTrace();
            onError(outputStream, e.getErrType(), e.getMessage());
        } catch (WriteException e) {
            e.printStackTrace();
            onError(outputStream, e.getErrType(), e.getMessage());
        }

        for (BaseException e : exceptions) {
            try {
                if (node != null) {
                    node.onError(outputStream, e.getErrType(), e.getMessage());
                }
            } catch (WriteException e1) {
                e1.printStackTrace();
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
