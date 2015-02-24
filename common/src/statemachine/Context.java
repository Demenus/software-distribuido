package statemachine;

import statemachine.applicationexceptions.ApplicationException;
import statemachine.connectionexceptions.ReadException;
import statemachine.protocolexceptions.CommandException;
import statemachine.protocolexceptions.ParseException;
import statemachine.protocolexceptions.StateException;

import java.io.IOException;
import java.net.Socket;

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
        try {
            Object response = mStateMachine.stepAndProcessState(mSocket.getInputStream());
            mStateMachine.sendSuccessResponse(mSocket.getOutputStream(), response);
        } catch (CommandException e) {
            e.printStackTrace();
        } catch (ApplicationException e) {
            e.printStackTrace();
        } catch (StateException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (ReadException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeConnection() {
        try {
            mSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onTimeOut() {
        try {
            mSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
