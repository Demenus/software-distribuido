package gamelayer;

import gamelayer.model.Card;
import gamelayer.model.Deck;

/**
 * Created by aaron on 08/03/2015.
 */
public class GameController {

    private int mBet;
    private Deck mDeck;

    public GameController(String fileDeck, int bet) {
        mDeck = Deck.parseDeckFile(fileDeck);
        mBet = bet;
    }

    //TODO: Check overflow
    public void increaseBet(int bet) {
        mBet += bet;
    }

    public int getBet() {
        return mBet;
    }

    public Card getNextCard() {
        return mDeck.getNextCard();
    }
}
