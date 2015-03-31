package server;

import constants.Commands;
import constants.States;
import exceptions.connectionexceptions.ReadException;
import exceptions.connectionexceptions.TimeOutException;
import exceptions.protocolexceptions.CommandException;
import server.io.ReaderManager;
import server.statemachine.ProtocolParser;


public class GameProtocolParser implements ProtocolParser {

    @Override
    public String getStateFromCommand(ReaderManager readerManager) throws CommandException, ReadException, TimeOutException {

        char c0 = readerManager.readChar();
        char c1 = ' ';
        int i = 0;
        while (i<4) {
            switch (c0) {
                case 's':
                    c1 = readerManager.readChar();
                    if (c1 != 't') break;
                    c1 = readerManager.readChar();
                    if (c1 != 'r') break;
                    c1 = readerManager.readChar();
                    if (c1 != 't') break;
                    return States.START_STATE;
                case 'd':
                    c1 = readerManager.readChar();
                    if (c1 != 'r') break;
                    c1 = readerManager.readChar();
                    if (c1 != 'a') break;
                    c1 = readerManager.readChar();
                    if (c1 != 'w') break;
                    return States.DRAW_STATE;
                case 'a':
                    c1 = readerManager.readChar();
                    if (c1 != 'n') break;
                    c1 = readerManager.readChar();
                    if (c1 != 't') break;
                    c1 = readerManager.readChar();
                    if (c1 != 'e') break;
                    return States.ANTE_STATE;
                case 'p':
                    c1 = readerManager.readChar();
                    if (c1 != 'a') break;
                    c1 = readerManager.readChar();
                    if (c1 != 's') break;
                    c1 = readerManager.readChar();
                    if (c1 != 's') break;
                    return States.PASS_STATE;
            }
            c0 = c1;
            i++;
        }
        throw new ReadException();
    }

    public String getStateFromCommand2(ReaderManager readerManager) throws CommandException, ReadException, TimeOutException {
        //We read the command
        String command = readerManager.readCommand();

        if (command.equalsIgnoreCase(Commands.START)) {
            return States.START_STATE;
        } else if (command.equalsIgnoreCase(Commands.DRAW)) {
            return States.DRAW_STATE;
        } else if (command.equalsIgnoreCase(Commands.ANTE)) {
            return States.ANTE_STATE;
        } else if (command.equalsIgnoreCase(Commands.PASS)) {
            return States.PASS_STATE;
        } else {
            throw new CommandException(command);
        }
    }
}
