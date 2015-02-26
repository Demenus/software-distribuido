package server;

import comutils.ComUtils;
import exceptions.connectionexceptions.ReadException;
import exceptions.connectionexceptions.WriteException;
import exceptions.protocolexceptions.CommandException;
import statemachine.ProtocolParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

/**
 * Created by aaron on 26/02/2015.
 */
public class ServerProtocolParser extends ProtocolParser {
    @Override
    public String getStateFromCommand(InputStream stream, Map<String, String> mapCommands) throws CommandException, ReadException {
        //We read the command
        ComUtils.Reader reader = new ComUtils.Reader(stream);
        String command;
        try {
            command = reader.read_string(4);
        } catch (IOException e) {
            throw new ReadException();
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
            throw new CommandException();
        }
        //Check if the command is in the available command list
        String state = null;
        for (Map.Entry<String, String> stateCommandPair : mapCommands.entrySet()) {
            if (command.equalsIgnoreCase(stateCommandPair.getValue())) {
                state = stateCommandPair.getKey();
            }
        }
        if (state == null)
            throw new CommandException(command);
        return state;
    }

    public void writeStartBet(OutputStream outputStream, final int bet) throws WriteException {
        runWriteOperation(outputStream, new ProtocolParser.WriteOperation() {
            @Override
            public void write(ComUtils.Writer writer) throws IOException{
                writer.write_string("stbt ");
                writer.write_int32(bet);
            }
        });
    }
}
