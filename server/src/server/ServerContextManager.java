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

    public static int PORT = 8000;
    public static int STARTING_BET = 100;
    public static String DECK_FILE = "deck.txt";
    private final HashMap<Socket, GameContext> mConnections;
    private boolean mRun;
    private ServerSocket mServerSocket;


    public ServerContextManager() {
        mRun = true;
        mConnections = new HashMap<Socket, GameContext>();
        try {
            mServerSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        //-p <port> -b <starting_bet> -f <deckfile>
        ServerContextManager manager = new ServerContextManager();
        manager.runServer();
    }

    @Override
    public void runServer() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (mRun) {
                    try {
                        Socket socket = mServerSocket.accept();
                        bindContextToSocket(socket);
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
        final GameContext context = new GameContext(socket, DECK_FILE, STARTING_BET);
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
