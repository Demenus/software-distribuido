package game;

import context.Context;
import exceptions.ErrType;
import statemachine.StateMachine;

import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by aaron on 24/02/2015.
 */
public class GameContext extends Context {

    public GameContext(Socket socket, StateMachine stateMachine) {
        super(socket, stateMachine);
    }

    @Override
    public void onError(OutputStream stream, ErrType errType, String message) {

    }
}
