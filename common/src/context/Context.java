package context;

import exceptions.ErrType;
import io.ReaderManager;
import io.WriterManager;
import statemachine.StateMachine;

import java.io.IOException;

/**
 * Created by aaron on 08/03/2015.
 */
public interface Context {
    StateMachine getStateMachine();

    ReaderManager getReader() throws IOException;

    WriterManager getWriter() throws IOException;

    void processInputData();

    void closeConnection();

    void onConnectionError();

    void onError(WriterManager writerManager, ErrType errType, String message);

    void onTimeOut();
}
