/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Huang
 */
public class Card {


    public enum Palo {
        O,
        B,
        E, 
        C
    }
    private int value;
    private Palo palo;

    public Card(int value, Palo palo) {
        this.value = value;
        this.palo = palo;
    }
    public void setValue(String v){
        if(v!="s" && v!="c" && v!="r"){
            this.value=Integer.parseInt(v);
        }else{
            switch (v){
                case "s":
                    this.value=8;
                    break;
                case "c":
                    this.value=9;
                    break;
                case "r":
                    this.value=10;
                    break;
            }
        }
    }
    public void setPalo(Palo p){
        this.palo=p;
    }
    public int getValue() {
        return value;
    }

    public Palo getPalo() {
        return palo;
    }

    
    public static Card parseCard(String cardStr) {
        Card car=null;
        int va=0;
        char value = cardStr.charAt(0);
        char palo = cardStr.toLowerCase().charAt(1);
        switch(value){
            case '1':
                va=1;
                break;
            case '2':
                va=2;
                break;
            case '3':
                va=3;
                break;
            case '4':
                va=4;
                break;
            case '5':
                va=5;
                break;
            case '6':
                va=6;
                break;
            case '7':
                va=7;
                break;
            case 's':
                va=8;
                break;
            case 'c':
                va=9;
                break;
            case 'r':
                va=10;
                break;  
        }
        if(va>=1 && va<=10){
            switch (palo){
                case 'o':
                    car=new Card(va,Palo.O);
                    break;
                case 'b':
                    car=new Card(va,Palo.B);
                    break;
                case 'e':
                    car=new Card(va,Palo.E);
                    break;
                case 'c':
                    car=new Card(va,Palo.C);
                    break;
            }          
        }
        return car;
    }

    @Override
    public String toString() {
        return "Card{" + "value=" + value + ", palo=" + palo + '}';
    }
    
    
 
}
