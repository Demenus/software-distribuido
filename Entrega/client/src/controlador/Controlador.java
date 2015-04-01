/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import comutils.ComUtils;
import model.Card;
import model.Card.Palo;
import model.Listcard;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *Controller class
 * @author Huang
 */
public class Controlador {
    ComUtils comUtils;
    Socket socket=null;
    String answer;
    Listcard listcard;
    char charact;
    int current_bet;
    boolean betUP;
 
    /**
     * Constructor, it's the method that in charge to establish the connection to the server.
     * @param ip  it's String value which is the server IP adress
     * @param port  
     */
    public Controlador(String ip, int port) {
        try {
            this.socket=new Socket(ip, port);
        } catch (UnknownHostException ex) {
            Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.comUtils=new ComUtils(socket);
        this.answer="";
        this.charact=' ';
        this.listcard=new Listcard();
        current_bet=0;
        betUP=false;
    }
    /**
     * This method verifies if the socket is connected.
     * @return 
     */
    public boolean isConnected(){
        return this.socket.isConnected();
    }
    /**
     * To add the new card to the list of cards.
     * @param card 
     */
    public void addNewCard(Card card){
        this.listcard.addCard(card);
    } 
    /**
     * To remove one card from our list of cards.
     * @param listcard
     * @param card 
     */
    public void removeCard(Listcard listcard,Card card){
        listcard.removeCard(card);
    }
    /**
     * To get the card value, which is a integer value from 1 to 7 or a double value 0.5 .
     * @param card
     * @return Card's value
     */
    public double getCardValue(Card card){
        return card.getValue();
    }
    /**
     * Get current cards number
     * @return int
     */
    public int getCurrentCardsNumber(){
        return this.listcard.currentCardsNumber();
    }
    /**
     * It calls the method of verify_estate() of the class ListCard to check if player got over then 7.5 points.
     * @return 
     */
    public boolean verify_estate() {
        boolean estate;
        estate=this.listcard.verify_estate();
        return estate;
    }
    /**
     * To get the list of cards.
     * @return 
     */
    public Listcard getListCard() {
        return this.listcard;
    }
    /**
     * To disconnect the connection.
     */
    public void disconnect() {
        try {
            this.socket.close();
        } catch (IOException ex) {
            Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * It's the method to send command START to server and to get the answer from server, then to verify if it's a correct state.
     */
    public void startNewGame_client() {
        comUtils.sendMessageString("STRT");
        /* Read an answer from the server */
        answer = comUtils.receiveMessageString(4);
        charact=comUtils.receiveMessageChar();
        this.current_bet=comUtils.receiveMessageInt();//To get the initial bet amount.
    
        answer=answer.toLowerCase();
        if (!answer.equals("stbt") || this.answer.equals("erro") || charact!=' '){
             System.out.println("We've received " + this.answer +", which is not expected answer.");
             System.exit(1);
        }
        this.betUP=false;
    }
    
    /**
     * It's the method to ask server for a new card. It returns a String value. 
     * @return 
     */
    public String newCard_client() {
        Card carta=null;
        boolean estate;
        Palo palo;
        String d;
        String p;
        comUtils.sendMessageString("DRAW");
        answer = comUtils.receiveMessageString(4); 
        charact=comUtils.receiveMessageChar();
        d=comUtils.receiveMessageString(1);
        p=comUtils.receiveMessageString(1);
        this.answer=answer.toLowerCase();
        if (!this.answer.equals("card") || charact!=' ' || this.answer.equals("erro")){
             System.out.println("We've received " + this.answer +", which is not expected answer.");
        }else{
            carta=Card.parseCard(d+""+p);
        }
        if(carta!=null){//We verify the estate of the game, we have to see if the sum of all the value passes over 7.5 o no.
            addNewCard(carta);
            estate=verify_estate();
            this.betUP=false;
            if(estate){
                //In case not passing over 7.5, we add this new card to the card list.
                return carta.toString();
            }else{
                //In case it passes over 7.5, we have to receive the message 'BUSTING' from the server.
               answer = comUtils.receiveMessageString(4);
               this.answer=answer.toLowerCase();
               if (!this.answer.equals("bstg")){
                    System.out.println("We've received " + this.answer +", which is not expected answer.");
                    return "error";
               }else{
                   return carta.toString()+"\n"+getScore();
               }
                
            }
        }else{
            return "";
        }
        
    }
    /**
     * The method to pass the round.
     * @return if the previous round, player bet up the amount then it returns a String value 'error'. Else, it returns score.
     * 
     */
    public String pass() {
        if(this.betUP==false){
            this.comUtils.sendMessageString("PASS");
            return getScore();
        }else{
            return "error";
        }
    }

    /**
     * To bet up with amount got from the parameter.
     * @param betupAmount 
     */
    public void betUp_client(int betupAmount) {
        String ans=this.answer.toLowerCase();
        if(!ans.equals("card") || this.betUP){
            System.out.println("This option is not enable now. Please try it after asking for new card.");
        }else{
            this.comUtils.sendMessageString("ANTE");
            this.comUtils.sendMessageChar(' ');
            this.comUtils.sendMessageInt(betupAmount);
            this.current_bet+=betupAmount;
            this.betUP=true;
        }
    }
    /**
     * Get current bet amount.
     * @return int
     */
    public int getCurrentBet(){     
        return this.current_bet;
    }
    /**
     * Get current got points.
     * @return 
     */
    public double getCurrentPoints(){
        return this.listcard.getCurrentPoints();
    }
    /**
     * To get score from server.
     * @return 
     */
    private String getScore() {
        String res="";
        int cardnumber;
        String cards="";
        String score="";
        char point;
        String scr;
        int gain;
        answer = comUtils.receiveMessageString(4);
        charact=comUtils.receiveMessageChar();
        cardnumber=comUtils.receiveMessageInt();
        cards=comUtils.receiveMessageString(cardnumber*2);
        charact=comUtils.receiveMessageChar();
        score=comUtils.receiveMessageString(4);
        
        this.answer=answer.toLowerCase();
        if(!answer.equals("bksc") || this.answer.equals("erro") || this.charact!=' '){
            System.out.println("We've received " + this.answer +", which is not expected answer.");
        }else{
            res=res+this.listcard.getGamenInformation()+"\n";
            res=res+"The server has got "+cardnumber+" cards which are: "+cards+".\n The server score is: "+score+"\n";
            answer=comUtils.receiveMessageString(4);
            this.answer=answer.toLowerCase();
            charact=comUtils.receiveMessageChar();
            gain=comUtils.receiveMessageInt();
            if(!this.answer.equals("gain") || this.answer.equals("erro") || charact!=' '){
                System.out.println("We've received " + this.answer +", which is not expected answer.");
            }else{
                if(gain>=0){
                    res=res+" You win the game. Your gain is: "+gain;
                }else{
                    res=res+" You lose the game. Your gain is: "+gain;
                }
            }
        }
        return res;
    }
    
   



    
}
