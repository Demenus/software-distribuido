package server;

import gamelayer.GameController;
import states.DrawState;
import states.StartNode;
import constants.States;
import statemachine.StateMachine;
import statemachine.StateNode;

import java.util.Map;

/**
 * Created by aaron on 24/02/2015.
 */
public class GameStateMachine extends StateMachine{
    public GameStateMachine() {
        super(States.VOID_STATE, new GameProtocolParser());
    }

    @Override
    protected void initializeControllers(Map<String, Object> controllers) {
        controllers.put(States.START_STATE, null);
        controllers.put(States.DRAW_STATE, new GameController());
    }

    @Override
    protected void initializeStates(Map<String, StateNode> states) {
        states.put(States.START_STATE, new StartNode());
        states.put(States.DRAW_STATE, new DrawState());
    }

}
