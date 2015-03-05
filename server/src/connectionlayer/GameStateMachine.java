package connectionlayer;

import connectionlayer.states.DrawState;
import connectionlayer.states.StartNode;
import server.ServerProtocolParser;
import statemachine.StateMachine;
import statemachine.StateNode;

import java.util.Map;

/**
 * Created by aaron on 24/02/2015.
 */
public class GameStateMachine extends StateMachine{
    public GameStateMachine(String initialState) {
        super(initialState, new ServerProtocolParser());
    }

    @Override
    protected void initializeControllers(Map<String, Object> controllers) {
        controllers.put(States.START_STATE, null);
        controllers.put(States.DRAW_STATE, null);
    }

    @Override
    protected void initializeStates(Map<String, StateNode> states) {
        states.put(States.START_STATE, new StartNode());
        states.put(States.DRAW_STATE, new DrawState());
    }

    @Override
    protected void mapCommands(Map<String, String> commands) {
        commands.put(States.START_STATE, Commands.START);
        commands.put(States.DRAW_STATE, Commands.DRAW);
    }
}
