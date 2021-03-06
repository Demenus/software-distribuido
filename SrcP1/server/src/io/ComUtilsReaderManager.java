package io;

import comutils.ComUtils;
import exceptions.connectionexceptions.ReadException;
import exceptions.connectionexceptions.TimeOutException;
import servershared.io.ReaderManager;

import java.io.IOException;
import java.net.SocketTimeoutException;

/**
 * Created by aaron on 08/03/2015.
 */
public class ComUtilsReaderManager implements ReaderManager<ComUtils.Reader> {

    private ComUtils.Reader mReader;

    public ComUtilsReaderManager(ComUtils.Reader reader) {
        mReader = reader;
    }

    @Override
    public Object runReadOperation(ReadOperation<ComUtils.Reader> operation) throws ReadException, TimeOutException {
        try {
            return operation.read(mReader);
        } catch (SocketTimeoutException e) {
            throw new TimeOutException();
        } catch (IOException e) {
            throw new ReadException();
        } catch (IndexOutOfBoundsException e) {
            throw new ReadException();
        }
    }

    @Override
    public String readCommand() throws ReadException, TimeOutException {
        Object command = runReadOperation(new ReadOperation<ComUtils.Reader>() {
            @Override
            public Object read(ComUtils.Reader reader) throws IOException, IndexOutOfBoundsException {
                return reader.read_string(4);
            }
        });
        return (String) command;
    }

    @Override
    public char readChar() throws ReadException, TimeOutException {
        Object ch = runReadOperation(new ReadOperation<ComUtils.Reader>() {
            @Override
            public Object read(ComUtils.Reader reader) throws IOException, IndexOutOfBoundsException {
                return reader.read_char();
            }
        });
        return Character.toLowerCase((Character) ch);
    }

    @Override
    public int readInt32() throws ReadException, TimeOutException {
        Object i = runReadOperation(new ReadOperation<ComUtils.Reader>() {
            @Override
            public Object read(ComUtils.Reader reader) throws IOException, IndexOutOfBoundsException {
                return reader.read_int32();
            }
        });
        return (Integer) i;
    }

    @Override
    public int readBet() throws ReadException, TimeOutException {
        Object bet = runReadOperation(new ReadOperation<ComUtils.Reader>() {
            @Override
            public Object read(ComUtils.Reader reader) throws IOException, IndexOutOfBoundsException {
                reader.read_char();
                return reader.read_int32();
            }
        });
        return (Integer) bet;
    }
}
