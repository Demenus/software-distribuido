package server.context;

import exceptions.ErrType;
import server.io.ReaderManager;
import server.io.WriterManager;
import server.statemachine.StateMachine;

import java.io.IOException;
import java.net.SocketException;

/**
 * Created by aaron on 08/03/2015.
 */
public interface Context {
    StateMachine getStateMachine();

    ReaderManager getReader() throws IOException;

    WriterManager getWriter() throws IOException;

    void initContext() throws SocketException;

    void processInputData();

    void closeConnection();

    boolean isValidContext();

    void onError(WriterManager writerManager, ErrType errType, String message);

    void disposeContext();
}
