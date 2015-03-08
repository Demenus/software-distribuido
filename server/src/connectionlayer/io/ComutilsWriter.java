package connectionlayer.io;

import comutils.ComUtils;
import connectionlayer.Commands;
import exceptions.ErrType;
import exceptions.connectionexceptions.WriteException;
import io.Writer;

import java.io.IOException;

/**
 * Created by aaron on 08/03/2015.
 */
public class ComutilsWriter implements Writer<ComUtils.Writer> {

    private ComUtils.Writer mWriter;

    public ComutilsWriter(ComUtils.Writer writer) {
        mWriter = writer;
    }

    @Override
    public void runWriteOperation(WriteOperation<ComUtils.Writer> operation) throws WriteException {
        try {
            operation.write(mWriter);
        } catch (IOException e) {
            throw new WriteException();
        } catch (RuntimeException e) {
            throw new WriteException();
        }
    }

    public void writeStartBet(final int bet) throws WriteException {
        runWriteOperation(new WriteOperation<ComUtils.Writer>() {
            @Override
            public void write(ComUtils.Writer writer) throws IOException {
                writer.write_string(Commands.START_BET);
                writer.write_char(' ');
                writer.write_int32(bet);
            }
        });
    }

    public void writeError(final ErrType errCode, final String message) throws WriteException {
        runWriteOperation(new WriteOperation<ComUtils.Writer>() {
            @Override
            public void write(ComUtils.Writer writer) throws IOException {
                writer.write_string(Commands.ERROR);
                writer.write_char(' ');
                writer.write_string(errCode.toString());
                writer.write_char(' ');
                writer.write_string(message);
            }
        });
    }
}
