package statemachine;

import comutils.ComUtils;
import exceptions.ErrType;
import exceptions.connectionexceptions.ReadException;
import exceptions.protocolexceptions.CommandException;
import exceptions.applicationexceptions.ApplicationException;
import exceptions.protocolexceptions.ParseException;
import exceptions.protocolexceptions.StateException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * Created by aaron on 24/02/2015.
 */
public abstract class StateMachine {

    private StateNode mCurrentStateNode;
    private String mCurrentState;
    private String mPreviousState;
    private HashMap<String, String> mCommands;
    private HashMap<String, StateNode> mStateNodes;
    private HashMap<String, Object> mControllers;

    protected StateMachine(String initialState) {
        mCurrentState = initialState;
        mPreviousState = null;
        mCurrentStateNode = null;
        mStateNodes = new HashMap<String, StateNode>();
        mCommands = new HashMap<String, String>();
        mControllers = new HashMap<String, Object>();
        mapCommands(mCommands);
        initializeControllers(mControllers);
        initializeStates(mStateNodes);
    }

    protected abstract void initializeControllers(final Map<String, Object> controllers);

    protected abstract void initializeStates(final Map<String, StateNode> states);

    protected abstract void mapCommands(final Map<String, String> commands);



    public Object getControllerOf(String state) {
        return mControllers.get(state);
    }

    public StateNode getStateNode(String state) {
        return mStateNodes.get(state);
    }

    public String getCommandOf(String state) {
        return mCommands.get(state);
    }


    public StateNode getNextStateNode(InputStream stream) throws ReadException, CommandException, StateException {
        //We read the command
        ComUtils.Reader reader = new ComUtils.Reader(stream);
        String command;
        try {
            command = reader.read_string(4);
        } catch (IOException e) {
            e.printStackTrace();
            throw new ReadException();
        }
        //Check if the command is in the available command list
        String state = null;
        for (Map.Entry<String, String> stateCommandPair : mCommands.entrySet()) {
            if (command.equalsIgnoreCase(stateCommandPair.getValue())) {
                state = stateCommandPair.getKey();
            }
        }
        if (state == null)
            throw new CommandException(command);

        //We get the current state-node from state
        mPreviousState = mCurrentState;
        mCurrentState = state;
        StateNode nodeCandidate = getStateNode(state);

        //Check if the candidate node is a valid state
        if (!nodeCandidate.checkPreviousState(mPreviousState)) {
            throw new StateException(mPreviousState, mCurrentState);
        }
        //Parse the data from the node
        mCurrentStateNode = nodeCandidate;
        return mCurrentStateNode;
    }

    public Object getResponseData(InputStream stream) throws ParseException, ApplicationException, ReadException {
        //Parse the data from the node
        Object parsed = mCurrentStateNode.parseRequestBody(stream);
        Object controller = getControllerOf(mCurrentState);
        return mCurrentStateNode.process(mPreviousState, controller, parsed);
    }


    public Object stepState(InputStream stream) throws ReadException, CommandException, StateException, ParseException, ApplicationException {
        //We read the command
        ComUtils.Reader reader = new ComUtils.Reader(stream);
        String command;
        try {
            command = reader.read_string(4);
        } catch (IOException e) {
            e.printStackTrace();
            throw new ReadException();
        }
        //Check if the command is in the available command list
        String state = null;
        for (Map.Entry<String, String> stateCommandPair : mCommands.entrySet()) {
            if (command.equalsIgnoreCase(stateCommandPair.getValue())) {
                state = stateCommandPair.getKey();
            }
        }
        if (state == null)
            throw new CommandException(command);

        //We get the current state-node from state
        mPreviousState = mCurrentState;
        mCurrentState = state;
        StateNode nodeCandidate = getStateNode(state);

        //Check if the candidate node is a valid state
        if (!nodeCandidate.checkPreviousState(mPreviousState)) {
            throw new StateException(mPreviousState, mCurrentState);
        }
        //Parse the data from the node
        mCurrentStateNode = nodeCandidate;
        Object parsed = mCurrentStateNode.parseRequestBody(stream);
        Object controller = getControllerOf(mCurrentState);
        return mCurrentStateNode.process(mPreviousState, controller, parsed);
    }

}
