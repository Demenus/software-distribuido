package connectionlayer;

import context.Context;
import exceptions.ErrType;
import statemachine.StateMachine;

import java.io.*;

/**
 * Created by aaron on 24/02/2015.
 */
    public class TestGameContext extends Context {

    private InputStream mInputStream;
    private OutputStream mOutputStream;

    public TestGameContext(File input, File output, StateMachine stateMachine) {
        super(null, stateMachine);
        try {
            mInputStream = new FileInputStream(input);
            mOutputStream = new FileOutputStream(output);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public InputStream getSocketInputStream() throws IOException {
        return mInputStream;
    }

    @Override
    public OutputStream getSocketOutputStream() throws IOException {
        return mOutputStream;
    }

    @Override
    public void onError(OutputStream stream, ErrType errType, String message) {
        //TODO: Fill this
    }
}
