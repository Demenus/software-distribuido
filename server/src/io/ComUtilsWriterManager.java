package io;

import comutils.ComUtils;
import constants.Commands;
import exceptions.ErrType;
import exceptions.connectionexceptions.TimeOutException;
import exceptions.connectionexceptions.WriteException;
import gamelayer.model.Card;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.List;
import java.util.Locale;


public class ComUtilsWriterManager implements WriterManager<ComUtils.Writer> {

    private ComUtils.Writer mWriter;

    public ComUtilsWriterManager(ComUtils.Writer writer) {
        mWriter = writer;
    }

    @Override
    public void runWriteOperation(WriteOperation<ComUtils.Writer> operation) throws WriteException, TimeOutException {
        try {
            operation.write(mWriter);
        } catch (SocketTimeoutException e) {
            throw new TimeOutException();
        } catch (IOException e) {
            throw new WriteException();
        } catch (RuntimeException e) {
            throw new WriteException();
        }
    }

    @Override
    public void writeStartBet(final int bet) throws WriteException, TimeOutException {
        runWriteOperation(new WriteOperation<ComUtils.Writer>() {
            @Override
            public void write(ComUtils.Writer writer) throws IOException {
                writer.write_string(Commands.START_BET);
                writer.write_char(' ');
                writer.write_int32(bet);
            }
        });
    }

    @Override
    public void writeError(final ErrType errCode, final String message) throws WriteException, TimeOutException {
        runWriteOperation(new WriteOperation<ComUtils.Writer>() {
            @Override
            public void write(ComUtils.Writer writer) throws IOException {
                writer.write_string(Commands.ERROR);
                writer.write_char(' ');
                String error = errCode.toString() + ' ' + message;
                if (error.length() < 10) {
                    writer.write_char('0');
                    writer.write_string(String.valueOf(error.length()));
                } else {
                    writer.write_string(String.valueOf(error.length()));
                }
                writer.write_string(error);
            }
        });
    }

    @Override
    public void writeExceededErrors() throws WriteException, TimeOutException {
        runWriteOperation(new WriteOperation<ComUtils.Writer>() {
            @Override
            public void write(ComUtils.Writer writer) throws IOException {
                writer.write_string(Commands.ERROR);
                writer.write_char(' ');
                String error = "Sorry your IQ is too low to play with me, GTFO!";
                if (error.length() < 10) {
                    writer.write_char('0');
                    writer.write_string(String.valueOf(error.length()));
                } else {
                    writer.write_string(String.valueOf(error.length()));
                }
                writer.write_string(error);
            }
        });
    }

    @Override
    public void writeCard(final Card card) throws WriteException, TimeOutException {
        runWriteOperation(new WriteOperation<ComUtils.Writer>() {
            @Override
            public void write(ComUtils.Writer writer) throws IOException {
                writer.write_string(Commands.CARD);
                writer.write_char(' ');
                writer.write_string(card.toString());
            }
        });
    }

    @Override
    public void writeBusting() throws TimeOutException, WriteException {
        runWriteOperation(new WriteOperation<ComUtils.Writer>() {
            @Override
            public void write(ComUtils.Writer writer) throws IOException {
                writer.write_string(Commands.BUSTING);
            }
        });
    }

    @Override
    public void writeBankScore(final List<Card> cards, final float score) throws TimeOutException, WriteException {
        runWriteOperation(new WriteOperation<ComUtils.Writer>() {
            @Override
            public void write(ComUtils.Writer writer) throws IOException {
                writer.write_string(Commands.BANK_SCORE);
                writer.write_char(' ');
                writer.write_int32(cards.size());
                for (Card card : cards) {
                    writer.write_string(card.toString());
                }
                writer.write_char(' ');
                //String scoreStr = String.format("%.1f", score);
                String scoreStr;
                if (score < 10) {
                    scoreStr = "0"+String.format(Locale.ENGLISH, "%.1f", score);
                } else {
                    scoreStr = String.format(Locale.ENGLISH, "%.1f", score);
                }
                writer.write_string(scoreStr);
            }
        });
    }

    @Override
    public void writeGain(final int gain) throws TimeOutException, WriteException {
        runWriteOperation(new WriteOperation<ComUtils.Writer>() {
            @Override
            public void write(ComUtils.Writer writer) throws IOException {
                writer.write_string(Commands.GAINS);
                writer.write_char(' ');
                writer.write_int32(gain);
            }
        });
    }
}
