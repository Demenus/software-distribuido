package servershared;

import constants.States;
import exceptions.connectionexceptions.ReadException;
import exceptions.connectionexceptions.TimeOutException;
import exceptions.protocolexceptions.CommandException;
import servershared.io.ReaderManager;
import servershared.statemachine.ProtocolParser;


public class GameProtocolParser implements ProtocolParser {

    private State mCurrentState = State.OO;

    private String stepState(char c) throws CommandException {
        switch (mCurrentState) {
            case ST_S:
                if (c == 't') mCurrentState = State.ST_T1;
                else mCurrentState = State.OO;
                break;
            case ST_T1:
                if (c == 'r') mCurrentState = State.ST_R;
                else mCurrentState = State.OO;
                break;
            case ST_R:
                if (c == 't') {
                    mCurrentState = State.OO;
                    return States.START_STATE;
                }
                break;

            case AN_A:
                if (c == 'n') mCurrentState = State.AN_N;
                else mCurrentState = State.OO;
                break;
            case AN_N:
                if (c == 't') mCurrentState = State.AN_T;
                else mCurrentState = State.OO;
                break;
            case AN_T:
                if (c == 'e') {
                    mCurrentState = State.OO;
                    return States.ANTE_STATE;
                }
                break;


            case DR_D:
                if (c == 'r') mCurrentState = State.DR_R;
                else  mCurrentState = State.OO;
                break;
            case DR_R:
                if (c == 'a') mCurrentState = State.DR_A;
                else mCurrentState = State.OO;
                break;
            case DR_A:
                if (c == 'w') {
                    mCurrentState = State.OO;
                    return States.DRAW_STATE;
                }
                break;


            case PS_P:
                if (c == 'a') mCurrentState = State.PS_A;
                else mCurrentState = State.OO;
                break;
            case PS_A:
                if (c == 's') mCurrentState = State.PS_S1;
                else mCurrentState = State.OO;
                break;
            case PS_S1:
                if (c =='s') {
                    mCurrentState = State.OO;
                    return States.PASS_STATE;
                }
                break;

        }
        if (mCurrentState == State.OO) {
            switch (c) {
                case 's':
                    mCurrentState = State.ST_S;
                    break;
                case 'a':
                    mCurrentState = State.AN_A;
                    break;
                case 'd':
                    mCurrentState = State.DR_D;
                    break;
                case 'p':
                    mCurrentState = State.PS_P;
                    break;
            }
        }
        return null;
    }

    @Override
    public String getStateFromCommand(ReaderManager readerManager) throws CommandException, ReadException, TimeOutException {
        int i = 0;
        while (i < 4) {
            char c = readerManager.readChar();
            String state = stepState(c);
            if (state != null) {
                return state;
            }
            i++;
        }
        throw new CommandException();
        /*char c0 = readerManager.readChar();
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
        throw new ReadException();*/
    }

    private enum State {
        OO,
        ST_S, ST_T1, ST_R,
        AN_A, AN_N, AN_T,
        DR_D, DR_R, DR_A,
        PS_P, PS_A, PS_S1,
    }


}
