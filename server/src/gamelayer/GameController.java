package gamelayer;

import exceptions.applicationexceptions.ApplicationException;
import gamelayer.model.Card;
import gamelayer.model.Deck;
import gamelayer.model.ServerGame;
import gamelayer.model.UserGame;

import java.util.List;

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

    public int getGain() throws ApplicationException {
        //Empate 0 OK
        //Ambos pierden OK
        //Si usuario gana con 7.5 y server pierde +2*bet OK
        //Si usuario gana y server pierde +bet OK
        //Si usuario pierde -bet OK
        if (mUserGame.hasLost() && mServerGame.hasLost()) {
            return 0;
        }
        if (mUserGame.hasLost() && !mServerGame.hasLost()) {
            return -mBet;
        }
        float userScore = mUserGame.getScore();
        float serverScore = mServerGame.getScore();
        if (!mUserGame.hasLost() && mServerGame.hasLost()) {
            if (Math.abs(userScore-7.5f) < 0.0001) {
                return 2*mBet;
            } else {
                return mBet;
            }
        }
        if (Math.abs(userScore-serverScore) < 0.0001) {
            return 0;
        }
        if (!mUserGame.hasLost() && !mServerGame.hasLost()) {
            if (userScore > serverScore) {
                return mBet;
            }
            if (userScore < serverScore) {
                return -mBet;
            }
            if (Math.abs(userScore-serverScore) < 0.0001) {
                return 0;
            }
        }
        throw new ApplicationException("Error getting the gain");
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
