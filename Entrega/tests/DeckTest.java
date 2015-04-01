import exceptions.DeckException;
import org.junit.Test;
import servershared.gamelayer.model.Card;
import servershared.gamelayer.model.Deck;

import static org.junit.Assert.assertEquals;

/**
 * Created by aaron on 12/03/2015.
 */
public class DeckTest {
    @Test
    public void testDeckCreation() throws Exception {
        Deck deck = Deck.parseDeckFile("deck.txt");
    }

    @Test
    public void testDeckGetCards() throws Exception {
        Deck deck = Deck.parseDeckFile("deck.txt");
        Card card = deck.getNextCard();
        assertEquals("3o", card.toString());
        card = deck.getNextCard();
        assertEquals("cc", card.toString());
        card = deck.getNextCard();
        assertEquals("rc", card.toString());
    }

    @Test(expected = DeckException.class)
    public void testAllCardsTraversal() throws Exception {
        Deck deck = Deck.parseDeckFile("deck.txt");
        Card card = null;
        for (int i = 0; i < 40; i++) {
            card = deck.getNextCard();
        }
        assertEquals("5e", card.toString());
        card = deck.getNextCard();
    }

    @Test(expected = DeckException.class)
    public void testDisposeDeck() throws Exception {
        Deck deck = Deck.parseDeckFile("deck.txt");
        Card card = deck.getNextCard();
        deck.disposeDeck();
        card = deck.getNextCard();
    }
}
