package gamelayer;

import gamelayer.model.Deck;

/**
 * Created by aaron on 08/03/2015.
 */
public class GameController {

    private Deck mDeck;

    public GameController() {

    }

    public GameController(Deck deck) {
        mDeck = deck;
    }

    public String getNextCard() {
        return "7o";
        //return mDeck.getNextCard();
    }
}
