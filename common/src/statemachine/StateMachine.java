package statemachine;

import exceptions.applicationexceptions.ApplicationException;
import exceptions.connectionexceptions.ReadException;
import exceptions.connectionexceptions.TimeOutException;
import exceptions.connectionexceptions.WriteException;
import exceptions.protocolexceptions.CommandException;
import exceptions.protocolexceptions.ParseException;
import exceptions.protocolexceptions.StateException;
import io.ReaderManager;
import io.WriterManager;

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
    private HashMap<String, StateNode> mStateNodes;
    private HashMap<String, Object> mControllers;

    protected StateMachine(String initialState, ProtocolParser parser) {
        mCurrentState = initialState;
        mParser = parser;
        mPreviousState = null;
        mCurrentStateNode = null;
        mStateNodes = new HashMap<String, StateNode>();
        mControllers = new HashMap<String, Object>();
    }

    protected abstract void initializeControllers(final Map<String, Object> controllers);

    protected abstract void initializeStates(final Map<String, StateNode> states);

    public void initialize() {
        initializeControllers(mControllers);
        initializeStates(mStateNodes);
        mCurrentStateNode = getStateNode(mCurrentState);
    }

    public boolean isInFinalState() {
        return mCurrentStateNode.isFinalState();
    }

    public Object getControllerOf(String state) {
        return mControllers.get(state);
    }

    public StateNode getStateNode(String state) {
        return mStateNodes.get(state);
    }

    public StateNode getCurrentStateNode() {
        return mCurrentStateNode;
    }

    public String getNextCandidateState(ReaderManager readerManager) throws CommandException, ReadException, TimeOutException {
        return mParser.getStateFromCommand(readerManager);
    }

    public StateNode getNextCandidateStateNode(String candidateState) throws ReadException, CommandException {
        //Checks the command
        return getStateNode(candidateState);
    }

    public StateNode checkNextCandidateNode(StateNode nodeCandidate, String candidateState) throws StateException {
        //Checks the previous state, if it is incorrect throws an exception
        nodeCandidate.checkPreviousState(mCurrentState);
        //Parse the data from the node
        mCurrentStateNode = nodeCandidate;
        mPreviousState = mCurrentState;
        mCurrentState = candidateState;
        return mCurrentStateNode;
    }

    public void processCurrentNode(ReaderManager readerManager, WriterManager writerManager) throws ParseException, ApplicationException, ReadException, TimeOutException, WriteException {
        //Parse the data from the node
        Object parsed = mCurrentStateNode.parseRequestBody(readerManager);
        Object controller = getControllerOf(mCurrentState);
        mCurrentStateNode.process(writerManager, controller, parsed);
    }

}
