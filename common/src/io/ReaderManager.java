package io;

import exceptions.connectionexceptions.ReadException;
import exceptions.connectionexceptions.TimeOutException;

import java.io.IOException;

/**
 * Created by aaron on 08/03/2015.
 */
public interface ReaderManager<R> {
    public Object runReadOperation(ReadOperation<R> operation) throws ReadException, TimeOutException;

    String readCommand() throws ReadException, TimeOutException;

    int readBet() throws ReadException, TimeOutException;

    public static interface ReadOperation<R> {
        public Object read(R reader) throws IOException, IndexOutOfBoundsException;
    }

}
