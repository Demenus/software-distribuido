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
 *
 * @author Huang
 */
public class Controlador {
    ComUtils comUtils;
    Socket socket=null;
    String answer;
    Listcard listcard;
    char charact;
    int current_bet;
 

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
        
    }
    public boolean isConnected(){
        return this.socket.isConnected();
    }
    /*public Listcard getDeck(){
        Listcard listcards=new Listcard();
        listcards.setdeck();
        return listcards;
    }*/
    public void addNewCard(Card card){
        this.listcard.addCard(card);
    } 
    public void removeCard(Listcard listcard,Card card){
        listcard.removeCard(card);
    }
    public double getCardValue(Card card){
        return card.getValue();
    }
    public int getCurrentCardsNumber(){
        return this.listcard.currentCardsNumber();
    }

    public boolean verify_estate() {
        boolean estate;
        estate=this.listcard.verify_estate();
        return estate;
    }

    public Listcard getListCard() {
        return this.listcard;
    }
    public void disconnect() {
        try {
            this.socket.close();
        } catch (IOException ex) {
            Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void startNewGame_client() {

        comUtils.sendMessageString("STRT");

        /* Read an answer from the server */
        answer = comUtils.receiveMessageString(4);
        charact=comUtils.receiveMessageChar();
        this.current_bet=comUtils.receiveMessageInt();
        
        answer=answer.toLowerCase();
        if (!answer.equals("stbt") || this.answer.equals("erro") || charact!=' '){
             System.out.println("No he rebut una bona resposta, sino un " + answer);
             System.exit(1);
        }
    }
    

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
             System.out.println("No he rebut una bona resposta, sino un " + this.answer);
        }else{
            carta=Card.parseCard(d+""+p);
        }
        if(carta!=null){//We verify the estate of the game, we have to see if the sum of all the value passes over 7.5 o no.
            addNewCard(carta);
            estate=verify_estate();
            if(estate){
                //In case not passing over 7.5, we add this new card to the card list.
                return carta.toString();
            }else{
                //In case it passes over 7.5, we have to receive the message 'BUSTING' from the server.
               answer = comUtils.receiveMessageString(4);
               this.answer=answer.toLowerCase();
               if (!this.answer.equals("bstg")){
                    System.out.println("This answer is: "+this.answer);
                    System.out.println("¡ERROR!Something has gone wrong. We are expecting the answer 'BUSTING'!");
                    return "error";
               }else{
                   return carta.toString()+"\n"+getScore();
               }
                
            }
        }else{
            return "";
        }
        
    }

    public String pass() {
        this.comUtils.sendMessageString("PASS");
        return getScore();
        
    }

    public void betUp_client(double betupAmount) {
        String ans=this.answer.toLowerCase();
        if(!ans.equals("card")){
            System.out.println("This option is not enable now. Please try it after asking for new card.");
        }else{
            this.comUtils.sendMessageString("ANTE"+" "+betupAmount);
            this.current_bet+=betupAmount;
        }
    }
    public int getCurrentBet(){     
        return this.current_bet;
    }
    public double getCurrentPoints(){
        return this.listcard.getCurrentPoints();
    }

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
            System.out.println("Error! The server hasn't sent us a correct protocol.");
        }else{
            res=res+this.listcard.getGamenInformation()+"\n";
            res=res+"The server has got "+cardnumber+" cards which are: "+cards+".\n The server score is: "+score+"\n";
            answer=comUtils.receiveMessageString(4);
            this.answer=answer.toLowerCase();
            charact=comUtils.receiveMessageChar();
            gain=comUtils.receiveMessageInt();
            if(!this.answer.equals("gain") || this.answer.equals("erro") || charact!=' '){
                System.out.println("Error! The server hasn't sent to us a correct protocol.");
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
