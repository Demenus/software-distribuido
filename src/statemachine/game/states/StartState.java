package statemachine.game.states;

import statemachine.core.*;
import statemachine.game.States;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * Created by aaron on 24/02/2015.
 */
public class StartState implements Node {
    @Override
    public String getState() {
        return States.START_STATE;
    }

    @Override
    public Map<String, Object> parseMessage(InputStream messageStream) throws ParseException {
        ComUtilsReader reader = new ComUtilsReader(messageStream);
        try {
            String head = reader.read_string_variable(4);

        } catch (IOException e) {
            throw new ParseException(e.getMessage());
        }
        return null;
    }

    @Override
    public boolean checkPreviousState(String previousState) {
        return previousState.equalsIgnoreCase(States.VOID_STATE);
    }

    @Override
    public String getNextState(String previousState, Controller controller, Map<String, Object> parsedMessage) {
        return null;
    }

    @Override
    public void onError(String previousState, InputStream messageStream) {

    }
}
