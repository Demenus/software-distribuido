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
    private ServerSocketChannel mServerSocketChannel;
    private Logger log = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public ServerContextManager(int port, int bet, String deck) {
        mPort = port;
        mStartingBet = bet;
        mDeckFile = deck;
        mRun = true;
    }

    @Override
    public void runServer() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                innerRunServer();
            }
        });
        thread.start();
    }


    public void innerRunServer() {
        Selector selector;
        SocketChannel client;
        SelectionKey key, readKey;
        try {
            mServerSocketChannel = ServerSocketChannel.open();
            mServerSocketChannel.configureBlocking(false);
            mServerSocketChannel.socket().bind(new InetSocketAddress(mPort));
            log.log(Level.INFO, "Nio-Server listening on port " + mPort);
            selector = Selector.open();
            mServerSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

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
                        client = mServerSocketChannel.accept();
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
    public synchronized void stopServer() {
        mRun = false;
        try {
            mServerSocketChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
