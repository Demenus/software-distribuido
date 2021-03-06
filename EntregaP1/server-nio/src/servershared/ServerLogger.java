package servershared;

import constants.Commands;
import servershared.gamelayer.model.Card;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

/**
 * Created by aaron on 30/03/2015.
 */
public abstract class ServerLogger {

    private PrintWriter mWriter;

    public ServerLogger() {
        try {
            mWriter = new PrintWriter(getFileName(), "UTF-8");
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    public abstract String getFileName();

    public void writeServer(String m) {
        mWriter.println("S: "+m);
        mWriter.flush();
    }

    public void writeClient(String m) {
        mWriter.println("C: "+m);
        mWriter.flush();
    }

    //Client logs
    private void writeClient() {
        mWriter.print("C: ");
    }

    public void writeStart() {
        writeClient();
        mWriter.println(Commands.START);
    }

    public void writeAnte(int bet) {
        writeClient();
        mWriter.println(Commands.ANTE+" "+bet);
    }

    public void writeDraw() {
        writeClient();
        mWriter.println(Commands.DRAW);
    }

    public void writePass() {
        writeClient();
        mWriter.println(Commands.PASS);
    }

    //Server logs
    private void writeServer() {
        mWriter.print("S: ");
    }

    public void writeStartBet(int bet) {
        writeServer();
        mWriter.println(Commands.START_BET+ " " + bet);
    }

    public void writeCard(Card card) {
        writeServer();
        mWriter.println(Commands.CARD + " "+ card.toString());
    }

    public void disposeLogger() {
        mWriter.flush();
        mWriter.close();
    }
}
