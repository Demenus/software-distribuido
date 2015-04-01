package servershared.statemachine;

import exceptions.applicationexceptions.ApplicationException;
import exceptions.connectionexceptions.ReadException;
import exceptions.connectionexceptions.TimeOutException;
import exceptions.connectionexceptions.WriteException;
import exceptions.protocolexceptions.CommandException;
import exceptions.protocolexceptions.ParseException;
import exceptions.protocolexceptions.StateException;
import servershared.ServerLogger;
import servershared.io.ReaderManager;
import servershared.io.WriterManager;

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
    private boolean mParseError;

    protected StateMachine(String initialState, ProtocolParser parser) {
        mCurrentState = initialState;
        mParser = parser;
        mPreviousState = null;
        mCurrentStateNode = null;
        mStateNodes = new HashMap<String, StateNode>();
        mControllers = new HashMap<String, Object>();
        mParseError = false;
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

    private void setCurrentNode(StateNode newNode, String newState) {
        mCurrentStateNode = newNode;
        mPreviousState = mCurrentState;
        mCurrentState = newState;
    }

    public void processNext(ServerLogger logger, ReaderManager readerManager, WriterManager writerManager) throws ReadException, TimeOutException, CommandException,  StateException, WriteException, ApplicationException, ParseException {
        StateNode candidateNode = mCurrentStateNode;
        String candidateState = mCurrentState;
        if (!mParseError) {
            candidateState = mParser.getStateFromCommand(readerManager);
            candidateNode = mStateNodes.get(candidateState);
        }
        Object parsed = null;
        try {
            parsed = candidateNode.parseRequestBody(readerManager);
            mParseError = false;
        } catch (ParseException e) {
            mParseError = true;
        }

        String request = candidateNode.getLastRequest();
        if (request != null) {
            logger.writeClient(request);
        }

        candidateNode.checkPreviousState(mCurrentState);
        setCurrentNode(candidateNode, candidateState);

        if (!mParseError) {
            Object controller = getControllerOf(mCurrentState);
            mCurrentStateNode.process(writerManager, controller, parsed);
            String response = candidateNode.getLastResponse();
            if (response != null) {
                for (String res : response.split("\n")) {
                    logger.writeServer(res);
                }
            }
        } else {
            throw new TimeOutException();
        }
    }

}
