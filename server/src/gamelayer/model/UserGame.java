package gamelayer.model;


/**
 * Created by aaron on 13/03/2015.
 */
public class UserGame {
    private float mScore;

    public UserGame() {
        mScore = 0;
    }

    public void addCard(Card card) {
        mScore += card.getCardValue();
    }

    public float getScore() {
        return mScore;
    }

    public boolean hasLost() {
        return mScore > 7.5f;
    }
}
