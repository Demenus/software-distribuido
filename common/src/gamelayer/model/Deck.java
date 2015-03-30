package gamelayer.model;

import exceptions.DeckException;

import java.io.*;

/**
 * Created by aaron on 08/03/2015.
 */
public class Deck {

    private BufferedReader mBufferedReader;
    private FileReader mFileReader;

    private Deck() {

    }

    public static Deck parseDeckFile(String fileName) throws DeckException {
        try {
            Deck deck = new Deck();
            File file = new File(fileName);
            deck.mFileReader = new FileReader(file);
            deck.mBufferedReader = new BufferedReader(deck.mFileReader);
            return deck;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new DeckException("Can't find the deck file");
        }
    }

    public Card getNextCard() throws DeckException {
        try {
            String line = mBufferedReader.readLine();
            if (line == null) {
                throw new DeckException("No more cards");
            }
            return Card.parseCard(line);
        } catch (IOException e) {
            throw new DeckException("Can't get the next card");
        }
    }

    public void disposeDeck() throws DeckException {
        try {
            mBufferedReader.close();
            mFileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new DeckException("Can't dispose the deck");
        }
    }

}
