package servernio;

import servershared.context.ContextManager;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerContextManager implements ContextManager {

    private static int sConnections = 0;
    private int mPort;
    private int mStartingBet;
    private String mDeckFile;
    private boolean mRun;
    private Logger log = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public ServerContextManager(int port, int bet, String deck) {
        mPort = port;
        mStartingBet = bet;
        mDeckFile = deck;
        mRun = true;
    }

    @Override
    public void runServer() {
        ServerSocketChannel server;
        Selector selector;
        SocketChannel client;
        SelectionKey key, readKey;
        try {
            server = ServerSocketChannel.open();
            server.configureBlocking(false);
            server.socket().bind(new InetSocketAddress(mPort));
            log.log(Level.INFO, "Server listening on port " + mPort);
            selector = Selector.open();
            server.register(selector, SelectionKey.OP_ACCEPT);

            while (mRun) {
                selector.selectNow();
                Set readyKeys = selector.selectedKeys();
                Iterator iterator = readyKeys.iterator();
                while (iterator.hasNext()) {
                    key = (SelectionKey) iterator.next();
                    iterator.remove();
                    if (!key.isValid()) {
                        key.channel().close();
                        continue;
                    }
                    if (key.isAcceptable()) {
                        client = server.accept();
                        //System.out.println("Accepted connection from " + client);
                        client.configureBlocking(false);
                        //ByteBuffer source = ByteBuffer.wrap(data);
                        readKey = client.register(selector, SelectionKey.OP_READ);
                        bindContextToChannel(client, readKey);
                    } else if (key.isReadable()) {
                        client = (SocketChannel) key.channel();
                        GameContext context = (GameContext) key.attachment();
                        context.setSocketChannel(client);
                        context.processInputData();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void bindContextToChannel(SocketChannel channel, SelectionKey key) {
        GameContext context = new GameContext(sConnections, channel, mDeckFile, mStartingBet);
        sConnections++;
        context.initContext();
        key.attach(context);
    }

    @Override
    public void stopServer() {

    }
}
