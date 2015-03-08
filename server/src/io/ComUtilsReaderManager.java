package io;

import comutils.ComUtils;
import exceptions.connectionexceptions.ReadException;
import io.ReaderManager;

import java.io.IOException;

/**
 * Created by aaron on 08/03/2015.
 */
public class ComUtilsReaderManager implements ReaderManager<ComUtils.Reader> {

    private ComUtils.Reader mReader;

    public ComUtilsReaderManager(ComUtils.Reader reader) {
        mReader = reader;
    }

    @Override
    public Object runReadOperation(ReadOperation<ComUtils.Reader> operation) throws ReadException {
        try {
            return operation.read(mReader);
        } catch (IOException e) {
            throw new ReadException();
        } catch (IndexOutOfBoundsException e) {
            throw new ReadException();
        }
    }

    public String readCommand() throws ReadException {
        Object command = runReadOperation(new ReadOperation<ComUtils.Reader>() {
            @Override
            public Object read(ComUtils.Reader reader) throws IOException, IndexOutOfBoundsException {
                return reader.read_string(4);
            }
        });
        return (String) command;
    }
}
