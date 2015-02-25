package context;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by aaron on 24/02/2015.
 */
//TODO: Check exceptions
public abstract class ContextManager {

    public static final int PORT = 8000;

    private boolean mRun;
    private ServerSocket mServerSocket;

    public ContextManager() {
        mRun = true;
        try {
            mServerSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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

    protected abstract void bindContextToSocket(Socket socket);
}
