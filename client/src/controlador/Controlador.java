/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import comutils.ComUtils;
import java.net.Socket;
import java.util.ArrayList;
import model.Card;
import model.Listcard;

/**
 *
 * @author Huang
 */
public class Controlador {
    ComUtils comUtils;
    Socket socket;
 

    public Controlador(Socket socket) {
        this.socket=socket;
        comUtils=new ComUtils(socket);
    }
    public Listcard getDeck(){
        Listcard listcard=new Listcard();
        listcard.setdeck();
        return listcard;
    }
    public void addNewCard(Listcard listcard,Card card){
        listcard.addCard(card);
    } 
    public void removeCard(Listcard listcard,Card card){
        listcard.removeCard(card);
    }
    public int getCardValue(Card card){
        return card.getValue();
    }

    public boolean verify_estate(Listcard listcard) {
        boolean estate;
        estate=listcard.verify_estate();
        return estate;
    }

    public Listcard getListCard() {
        Listcard listcard=new Listcard();
        return listcard;
    }

    public void passCard(String ans) {
        if(ans.equals("y")){
            this.comUtils.sendMessage("PASS");
        }
    }

    public void betUp(String inputValue) {
         this.comUtils.sendMessage("ANTE"+" "+inputValue);
    }



    
}
