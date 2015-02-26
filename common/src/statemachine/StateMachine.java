package statemachine;

import exceptions.applicationexceptions.ApplicationException;
import exceptions.connectionexceptions.ReadException;
import exceptions.protocolexceptions.CommandException;
import exceptions.protocolexceptions.ParseException;
import exceptions.protocolexceptions.StateException;

import java.io.InputStream;
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
    private ProtocolParser mParser;
    private HashMap<String, String> mCommands;
    private HashMap<String, StateNode> mStateNodes;
    private HashMap<String, Object> mControllers;

    protected StateMachine(String initialState, ProtocolParser parser) {
        mCurrentState = initialState;
        mParser = parser;
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


    public StateNode getNextStateNode(InputStream stream) throws ReadException, CommandException, StateException {
        String candidateState = mParser.getStateFromCommand(stream, mCommands);
        StateNode nodeCandidate = getStateNode(candidateState);
        //Check if the candidate node is a valid state
        if (!nodeCandidate.checkPreviousState(mCurrentState)) {
            throw new StateException(mCurrentState, candidateState);
        }
        //Parse the data from the node
        mCurrentStateNode = nodeCandidate;
        mPreviousState = mCurrentState;
        mCurrentState = candidateState;
        return mCurrentStateNode;
    }

    public Object getResponseData(InputStream stream) throws ParseException, ApplicationException, ReadException {
        //Parse the data from the node
        Object parsed = mCurrentStateNode.parseRequestBody(stream);
        Object controller = getControllerOf(mCurrentState);
        return mCurrentStateNode.process(mPreviousState, controller, parsed);
    }

}
