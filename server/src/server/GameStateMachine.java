package server;

import constants.States;
import gamelayer.GameController;
import statemachine.StateMachine;
import statemachine.StateNode;
import states.DrawState;
import states.StartNode;
import states.VoidNode;

import java.util.Map;

/**
 * Created by aaron on 24/02/2015.
 */
public class GameStateMachine extends StateMachine{

    private GameController mGameController;

    public GameStateMachine() {
        super(States.VOID_STATE, new GameProtocolParser());
    }

    public void setGameController(GameController controller) {
        mGameController = controller;
    }

    @Override
    protected void initializeControllers(Map<String, Object> controllers) {
        controllers.put(States.VOID_STATE, mGameController);
        controllers.put(States.START_STATE, mGameController);
        controllers.put(States.DRAW_STATE, mGameController);
    }

    @Override
    protected void initializeStates(Map<String, StateNode> states) {
        states.put(States.VOID_STATE, new VoidNode());
        states.put(States.START_STATE, new StartNode());
        states.put(States.DRAW_STATE, new DrawState());
    }

}
