package statemachine.game.states;

import statemachine.core.Controller;
import statemachine.core.Node;
import statemachine.core.ParseException;
import statemachine.game.States;

import java.io.InputStream;
import java.util.Map;

/**
 * Created by aaron on 24/02/2015.
 */
public class VoidState implements Node {


    @Override
    public String getState() {
        return States.VOID_STATE;
    }

    @Override
    public Map<String, Object> parseMessage(InputStream messageStream) throws ParseException {
        return null;
    }

    @Override
    public boolean checkPreviousState(String previousState) {
        return true;
    }

    @Override
    public String getNextState(String previousState, Controller controller, Map<String, Object> parsedMessage) {
        return null;
    }

    @Override
    public void onError(String previousState, InputStream messageStream) {

    }
}
