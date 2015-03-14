package io;

import exceptions.connectionexceptions.TimeOutException;
import exceptions.connectionexceptions.WriteException;

import java.io.IOException;

/**
 * Created by aaron on 05/03/2015.
 */
public interface WriterManager<W> {
    public void runWriteOperation(WriteOperation<W> operation) throws WriteException, TimeOutException;

    public static interface WriteOperation<W> {
        public void write(W writer) throws IOException;
    }

}
