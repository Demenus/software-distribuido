package game;

import statemachine.Context;
import statemachine.StateMachine;

import java.net.Socket;

/**
 * Created by aaron on 24/02/2015.
 */
public class GameContext extends Context {

    public GameContext(Socket socket, StateMachine stateMachine) {
        super(socket, stateMachine);
    }
}
