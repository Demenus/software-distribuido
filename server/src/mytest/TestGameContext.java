package mytest;

import comutils.ComUtils;
import io.ComUtilsReaderManager;
import io.ComUtilsWriterManager;
import io.ReaderManager;
import io.WriterManager;
import server.GameContext;
import statemachine.StateMachine;

import java.io.*;

/**
 * Created by aaron on 24/02/2015.
 */
public class TestGameContext extends GameContext {

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

    public ReaderManager getReader() throws IOException {
        ComUtils.Reader reader = new ComUtils.Reader(mInputStream);
        return new ComUtilsReaderManager(reader);
    }

    public WriterManager getWriter() throws IOException {
        ComUtils.Writer writer = new ComUtils.Writer(mOutputStream);
        return new ComUtilsWriterManager(writer);
    }
}
