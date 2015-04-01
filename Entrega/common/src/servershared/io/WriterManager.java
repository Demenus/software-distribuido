package servershared.io;

import exceptions.ErrType;
import exceptions.connectionexceptions.TimeOutException;
import exceptions.connectionexceptions.WriteException;
import servershared.gamelayer.model.Card;

import java.io.IOException;
import java.util.List;

/**
 * Created by aaron on 05/03/2015.
 */
public interface WriterManager<W> {
    public void runWriteOperation(WriteOperation<W> operation) throws WriteException, TimeOutException;

    void writeStartBet(int bet) throws WriteException, TimeOutException;

    void writeError(ErrType errCode, String message) throws WriteException, TimeOutException;

    void writeExceededErrors() throws WriteException, TimeOutException;

    void writeCard(Card card) throws WriteException, TimeOutException;

    void writeBusting() throws TimeOutException, WriteException;

    void writeBankScore(List<Card> cards, float score) throws TimeOutException, WriteException;

    void writeGain(int gain) throws TimeOutException, WriteException;

    public static interface WriteOperation<W> {
        public void write(W writer) throws IOException;
    }
}
