package statemachine;

import comutils.ComUtils;
import exceptions.connectionexceptions.ReadException;
import exceptions.connectionexceptions.WriteException;
import exceptions.protocolexceptions.CommandException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

/**
 * Created by aaron on 26/02/2015.
 */
public abstract class ProtocolParser {

    public ProtocolParser() {
    }

    protected void runWriteOperation(OutputStream outputStream, Writer operation) throws WriteException {
        try {
            ComUtils.Writer writer = new ComUtils.Writer(outputStream);
            operation.write(writer);
        } catch (IOException e) {
            throw new WriteException();
        } catch (RuntimeException e) {
            throw new WriteException();
        }
    }

    protected Object runReadOperation(InputStream inputStream, Reader operation) throws ReadException {
        try {
            ComUtils.Reader reader = new ComUtils.Reader(inputStream);
            return operation.read(reader);
        } catch (IOException e) {
            throw new ReadException();
        } catch (RuntimeException e) {
            throw new ReadException();
        }
    }

    public abstract String getStateFromCommand(InputStream stream, Map<String, String> mapCommands) throws CommandException, ReadException;

    public interface Reader {
        public Object read(ComUtils.Reader reader) throws IOException, IndexOutOfBoundsException;
    }

    public interface Writer {
        public void write(ComUtils.Writer writer) throws IOException;
    }
}
