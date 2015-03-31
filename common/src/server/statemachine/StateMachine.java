package server.statemachine;

import exceptions.applicationexceptions.ApplicationException;
import exceptions.connectionexceptions.ReadException;
import exceptions.connectionexceptions.TimeOutException;
import exceptions.connectionexceptions.WriteException;
import exceptions.protocolexceptions.CommandException;
import exceptions.protocolexceptions.ParseException;
import exceptions.protocolexceptions.StateException;
import server.ServerLogger;
import server.io.ReaderManager;
import server.io.WriterManager;

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

    /*public StateNode getCurrentStateNode() {
        return mCurrentStateNode;
    }

    public String getNextCandidateState(ReaderManager readerManager) throws CommandException, ReadException, TimeOutException {
        return mParser.getStateFromCommand(readerManager);
    }

    public StateNode getNextCandidateStateNode(String candidateState) throws ReadException, CommandException {
        //Checks the command
        return mStateNodes.get(candidateState);
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
    }*/

    private void setCurrentNode(StateNode newNode, String newState) {
        mCurrentStateNode = newNode;
        mPreviousState = mCurrentState;
        mCurrentState = newState;
    }

    public void processNext(ServerLogger logger, ReaderManager readerManager, WriterManager writerManager) throws ReadException, TimeOutException, CommandException, ParseException, StateException, WriteException, ApplicationException {
        String candidateState = mParser.getStateFromCommand(readerManager);
        StateNode candidateNode = mStateNodes.get(candidateState);
        Object parsed = candidateNode.parseRequestBody(readerManager);

        String request = candidateNode.getLastRequest();
        if (request != null) {
            logger.writeClient(request);
        }

        candidateNode.checkPreviousState(mCurrentState);
        setCurrentNode(candidateNode, candidateState);

        Object controller = getControllerOf(mCurrentState);
        mCurrentStateNode.process(writerManager, controller, parsed);
        String response = candidateNode.getLastResponse();
        if (response != null) {
            for (String res : response.split("\n")) {
                logger.writeServer(res);
            }
        }
    }

}
