package io;

import exceptions.connectionexceptions.ReadException;

import java.io.IOException;

/**
 * Created by aaron on 08/03/2015.
 */
public interface Reader<R> {
    public Object runReadOperation(ReadOperation<R> operation) throws ReadException;

    public static interface ReadOperation<R> {
        public Object read(R reader) throws IOException, IndexOutOfBoundsException;
    }

}
