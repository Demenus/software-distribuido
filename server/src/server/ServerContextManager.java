package server;

import context.ContextManager;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

/**
 * Created by aaron on 24/02/2015.
 */
//TODO: Check exceptions
public class ServerContextManager implements ContextManager {

    public static final int PORT = 8000;

    private boolean mRun;
    private ServerSocket mServerSocket;
    private HashMap<Socket, GameContext> mConnections;

    public ServerContextManager() {
        mRun = true;
        mConnections = new HashMap<Socket, GameContext>();
        try {
            mServerSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void runServer() {
        while (mRun) {
            try {
                Socket socket = mServerSocket.accept();
                bindContextToSocket(socket);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void bindContextToSocket(Socket socket) {
        mConnections.put(socket, new GameContext(socket, new GameStateMachine()));
    }

    public static void main(String[] args) {
        ServerContextManager manager = new ServerContextManager();
        manager.runServer();
    }
}
