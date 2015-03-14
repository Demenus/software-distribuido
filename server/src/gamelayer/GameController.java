package gamelayer;

import exceptions.applicationexceptions.ApplicationException;
import gamelayer.model.Card;
import gamelayer.model.Deck;
import gamelayer.model.ServerGame;
import gamelayer.model.UserGame;

import java.util.List;

/**
 * Created by aaron on 08/03/2015.
 */
public class GameController {

    private int mBet;
    private Deck mDeck;
    private ServerGame mServerGame;
    private UserGame mUserGame;

    public GameController(String fileDeck, int bet) {
        mDeck = Deck.parseDeckFile(fileDeck);
        mBet = bet;
        mServerGame = new ServerGame();
        mUserGame = new UserGame();
    }

    //TODO: Check overflow
    public void increaseBet(int bet) throws ApplicationException {
        if (bet < 0) {
            throw new ApplicationException("The bet must be greater than 0");
        }
        int aux = mBet + bet;
        if (aux < 0) {
            throw new ApplicationException("Please select a lower bet");
        }
        mBet += bet;
    }

    public int getBet() {
        return mBet;
    }

    public int getGain() {
        float userScore = mUserGame.getScore();
        float serverScore = mServerGame.getScore();
        if (userScore < serverScore) {
            return -mBet;
        } else if (userScore > serverScore) {
            return mBet;
        } else {
            return 0;
        }
    }


    //User
    public boolean userHasLost() {
        return mUserGame.hasLost();
    }

    public Card getUserNextCard() {
        Card card = mDeck.getNextCard();
        mUserGame.addCard(card);
        return card;
    }

    //Server
    public void playServer() {
        while (mServerGame.getScore() < 7) {
            mServerGame.addCard(mDeck.getNextCard());
        }
    }

    public List<Card> getServerCards() {
        return mServerGame.getCards();
    }

    public float getServerScore() {
        return mServerGame.getScore();
    }

}
