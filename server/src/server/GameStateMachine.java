package server;

import constants.States;
import gamelayer.GameController;
import statemachine.StateMachine;
import statemachine.StateNode;
import states.*;

import java.util.Map;


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
        controllers.put(States.ANTE_STATE, mGameController);
        controllers.put(States.PASS_STATE, mGameController);
    }

    @Override
    protected void initializeStates(Map<String, StateNode> states) {
        states.put(States.VOID_STATE, new VoidNode());
        states.put(States.START_STATE, new StartNode());
        states.put(States.DRAW_STATE, new DrawState());
        states.put(States.ANTE_STATE, new AnteState());
        states.put(States.PASS_STATE, new PassState());
    }

}
