package server;

import context.Context;
import context.ContextManager;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashMap;

/**
 * Created by aaron on 24/02/2015.
 */
//TODO: Check exceptions
public class ServerContextManager implements ContextManager {

    private final HashMap<Socket, GameContext> mConnections = new HashMap<Socket, GameContext>();
    private int mPort;
    private int mStartingBet;
    private String mDeckFile;
    private boolean mRun;
    private ServerSocket mServerSocket;


    public ServerContextManager(int port, int startingBet, String deckFile) {
        mPort = port;
        mStartingBet = startingBet;
        mDeckFile = deckFile;
        mRun = true;
        try {
            mServerSocket = new ServerSocket(mPort);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void runServer() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (mRun) {
                    try {
                        Socket socket = mServerSocket.accept();
                        bindContextToSocket(socket);//Start new game.
                    } catch (SocketException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();
    }

    @Override
    public synchronized void stopServer() {
        mRun = false;
        for (Context context : mConnections.values()) {
            context.disposeContext();
        }
    }

    private void bindContextToSocket(Socket socket) throws SocketException {
        final GameContext context = new GameContext(socket, mDeckFile, mStartingBet);
        context.initContext();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                context.processInputData();
            }
        });
        thread.start();
        System.out.println(Thread.activeCount());
    }
}
