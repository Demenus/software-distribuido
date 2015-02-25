package game;

import game.states.StartNode;
import statemachine.StateMachine;
import statemachine.StateNode;

import java.util.Map;

/**
 * Created by aaron on 24/02/2015.
 */
public class GameStateMachine extends StateMachine{
    public GameStateMachine(String initialState) {
        super(initialState);
    }

    @Override
    protected void initializeControllers(Map<String, Object> controllers) {
        controllers.put(States.START_STATE, null);
    }

    @Override
    protected void initializeStates(Map<String, StateNode> states) {
        states.put(States.START_STATE, new StartNode());
    }

    @Override
    protected void mapCommands(Map<String, String> commands) {
        commands.put(States.START_STATE, Commands.START);
    }


    /*protected void initializeStates(Map<String, StateNode> states) {
        states.put(States.START_STATE, new StartNode());

    }

    protected void mapCommands(Map<String, String> commands) {
        commands.put(States.START_STATE, Commands.START);
    }*/
}
