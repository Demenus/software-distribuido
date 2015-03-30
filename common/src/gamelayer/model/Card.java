package gamelayer.model;

/**
 * Created by aaron on 08/03/2015.
 */
public class Card {
    private CardNumber mCardNumber;
    private Palo mPalo;

    public Card(Palo palo, CardNumber cardNumber) {
        mCardNumber = cardNumber;
        mPalo = palo;
    }

    //TODO: throw exception
    public static Card parseCard(String cardStr) {
        String aux = cardStr.toLowerCase();
        char n = aux.charAt(0);
        char p = aux.charAt(1);
        Palo palo = null;
        switch (p) {
            case 'o':
                palo = Palo.ORO;
                break;
            case 'e':
                palo = Palo.ESPADAS;
                break;
            case 'c':
                palo = Palo.COPAS;
                break;
            case 'b':
                palo = Palo.BASTOS;
                break;
            default:
                break;
        }
        CardNumber cardNumber = null;
        switch (n) {
            case '1':
                cardNumber = CardNumber.UNO;
                break;
            case '2':
                cardNumber = CardNumber.DOS;
                break;
            case '3':
                cardNumber = CardNumber.TRES;
                break;
            case '4':
                cardNumber = CardNumber.CUATRO;
                break;
            case '5':
                cardNumber = CardNumber.CINCO;
                break;
            case '6':
                cardNumber = CardNumber.SEIS;
                break;
            case '7':
                cardNumber = CardNumber.SIETE;
                break;
            case 's':
                cardNumber = CardNumber.SOTA;
                break;
            case 'c':
                cardNumber = CardNumber.CABALLO;
                break;
            case 'r':
                cardNumber = CardNumber.REY;
                break;
            default:
                break;
        }
        return new Card(palo, cardNumber);
    }

    public float getCardValue() {
        return mCardNumber.getValue();
    }

    public Palo getPalo() {
        return mPalo;
    }

    @Override
    public String toString() {
        return mCardNumber.toString()+mPalo.toString();
    }

    public enum Palo {
        ORO, BASTOS, COPAS, ESPADAS;

        @Override
        public String toString() {
            switch (this) {
                case ORO:
                    return "o";
                case BASTOS:
                    return "b";
                case COPAS:
                    return "c";
                case ESPADAS:
                    return "e";
            }
            return "";
        }
    }

    public enum CardNumber {
        UNO(1), DOS(2), TRES(3), CUATRO(4), CINCO(5), SEIS(6), SIETE(7), SOTA(0.5f), CABALLO(0.5f), REY(0.5f);

        private float mValue;

        CardNumber(float value) {
            mValue = value;
        }

        public float getValue() {
            return mValue;
        }

        @Override
        public String toString() {
            if (mValue >= 1) {
                return String.valueOf((int)mValue);
            } else {
                switch (this) {
                    case SOTA:
                        return "s";
                    case CABALLO:
                        return "c";
                    case REY:
                        return "r";
                }
            }
            return "";
        }
    }

}
