package gamelayer.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aaron on 13/03/2015.
 */
public class ServerGame {
    private ArrayList<Card> mCards;
    private float mScore;

    public ServerGame() {
        mCards = new ArrayList<Card>();
        mScore = 0;
    }

    public void addCard(Card card) {
        mCards.add(card);
        mScore += card.getCardValue();
    }

    public Card getLastCard() {
        return mCards.get(mCards.size()-1);
    }

    public List<Card> getCards() {
        return mCards;
    }

    public float getScore() {
        return mScore;
    }

    public boolean hasLost() {
        return mScore > 7.5f;
    }
}
