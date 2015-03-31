package server;

import servershared.context.ContextManager;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.logging.Logger;

/**
 * Created by aaron on 31/03/2015.
 */
public class ServerContextManager implements ContextManager {

    private static int sConnections = 0;
    private int mPort;
    private int mStartingBet;
    private String mDeckFile;
    private boolean mRun;
    private Logger log = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public ServerContextManager(int port, int bet, String deck) {

    }

    @Override
    public void runServer() {
        ServerSocketChannel server;
        Selector selector;
        try {
            server = ServerSocketChannel.open();
            server.configureBlocking(false);
            server.socket().bind(new InetSocketAddress(9000));
            selector = Selector.open();
            server.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }



    }

    @Override
    public void stopServer() {

    }
}
