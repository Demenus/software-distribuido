package statemachine;

import statemachine.comutils.ComUtils;
import statemachine.connectionexceptions.ReadException;
import statemachine.protocolexceptions.CommandException;
import statemachine.applicationexceptions.ApplicationException;
import statemachine.protocolexceptions.ParseException;
import statemachine.protocolexceptions.StateException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by aaron on 24/02/2015.
 */
public abstract class StateMachine {

    private StateNode mCurrentStateNode;
    private String mCurrentState;
    private String mPreviousState;
    private HashMap<String, String> mCommands;
    private HashMap<String, StateNode> mStates;
    private HashMap<String, Object> mControllers;

    protected StateMachine(String initialState) {
        mCurrentState = initialState;
        mPreviousState = null;
        mCurrentStateNode = null;
        mStates = new HashMap<String, StateNode>();
        mCommands = new HashMap<String, String>();
        mControllers = new HashMap<String, Object>();
        mapCommands(mCommands);
        initializeControllers(mControllers);
        initializeStates(mStates);
    }

    protected abstract void initializeControllers(final Map<String, Object> controllers);

    protected abstract void initializeStates(final Map<String, StateNode> states);

    protected abstract void mapCommands(final Map<String, String> commands);



    public Object getControllerOf(String state) {
        return mControllers.get(state);
    }

    public StateNode getStateNode(String state) {
        return mStates.get(state);
    }

    public String getCommandOf(String state) {
        return mCommands.get(state);
    }




    private String readCommand(InputStream stream) throws ReadException {
        ComUtils.Reader reader = new ComUtils.Reader(stream);
        try {
            return reader.read_string(4);
        } catch (IOException e) {
            e.printStackTrace();
            throw new ReadException();
        }
    }

    private String detectState(String command) throws CommandException {
        for (Map.Entry<String, String> stateCommandPair : mCommands.entrySet()) {
            if (command.equalsIgnoreCase(stateCommandPair.getValue())) {
                return stateCommandPair.getKey();
            }
        }
        throw new CommandException(command);
    }

    private StateNode getNextState(InputStream stream) throws CommandException, ReadException {
        String command = readCommand(stream);
        String state = detectState(command);
        mPreviousState = mCurrentState;
        mCurrentState = state;
        return getStateNode(state);
    }

    private Object processNextState(InputStream inputStream, StateNode node) throws StateException, ParseException, ApplicationException {
        if (!node.checkPreviousState(mPreviousState)) {
            throw new StateException(mPreviousState, mCurrentState);
        }
        Object parsed = node.parseRequestBody(inputStream);
        Object controller = getControllerOf(mCurrentState);
        return node.process(mPreviousState, controller, parsed);
    }

    public Object stepAndProcessState(InputStream stream) throws CommandException, StateException, ParseException, ApplicationException, ReadException {
        mCurrentStateNode = getNextState(stream);
        Object response = processNextState(stream, mCurrentStateNode);
        return response;
    }

    public void sendSuccessResponse(OutputStream outputStream, Object response) throws IOException {
        mCurrentStateNode.onSuccess(outputStream, response);
    }

    public void sendErrorResponse(OutputStream outputStream, int errCode, String message) throws IOException {
        mCurrentStateNode.onError(outputStream, errCode, message);
    }
}
