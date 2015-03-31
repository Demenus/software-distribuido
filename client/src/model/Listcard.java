/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Huang
 */
public class Listcard {
    private ArrayList<Card> cards;

    public Listcard( ) {
        this.cards = new ArrayList();
    }
    public void addCard(Card newcard){
        this.cards.add(newcard);
    }
    public void removeCard(Card card){
        this.cards.remove(card);
    }
    /*
    public void setdeck(){
        Card card;
        int i;
        for(i=1; i<=10; i++){
            card=new Card(i,Card.Palo.O);
            this.cards.add(card);
            card=new Card(i,Card.Palo.B);
            this.cards.add(card);
            card=new Card(i,Card.Palo.E);
            this.cards.add(card);
            card=new Card(i,Card.Palo.C);
            this.cards.add(card);
        }
    }
*/
    public boolean verify_estate() {
        Iterator it=this.cards.iterator();
        Card card;
        double value=0;
        boolean estate=true;
        while(it.hasNext()){
            card=(Card) it.next();
            value=value+card.getValue();
        }
        if(value>=7.5){
            estate=false;
        }
        return estate;
    }
    public double getCurrentPoints(){
        Iterator it=this.cards.iterator();
        Card card;
        double value=0;
         while(it.hasNext()){
            card=(Card)it.next();
            value+=card.getValue();
         }
         return value;
    }
    public String getGamenInformation() {
        Iterator it=this.cards.iterator();
        Card card;
        int count=0;
        String information="";
        String result;
        double value=0;
        while(it.hasNext()){
            card=(Card)it.next();
            count++;
            information=information+card.getFigure()+card.getPalo()+" ";
            value+=card.getValue();
        }
        result="You got "+count+" card,which are: "+information+".You got "+value+" points.";
        return result;
    }

    public int currentCardsNumber() {
       Iterator it=this.cards.iterator();
       int count=0;
       Card card;
       while(it.hasNext()){
           count++;
           card=(Card)it.next();
       }
       return count;
    }
    
     
}
