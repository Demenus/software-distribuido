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
    private double value;
    private String figure;
    private Palo palo;

    public Card(String figure, Palo palo) {
        this.figure=figure;
        if(!figure.equals("s") && !figure.equals("c") && !figure.equals("r")){
            this.value=Integer.parseInt(figure);
        }else{
            this.value=0.5;
        }
        
        this.palo = palo;
    }
    public void setFigureValue(String v){
        this.figure=v;
        if(!v.equals("s") && !v.equals("c") && !v.equals("r")){
            this.value=(double)Integer.parseInt(v);
        }else{
            this.value=0.5;
        }
    }
    public String getFigure(){
        return this.figure;
    }
    public void setPalo(Palo p){
        this.palo=p;
    }
    public double getValue() {
        return value;
    }

    public Palo getPalo() {
        return palo;
    }

    
    public static Card parseCard(String cardStr) {
        Card car=null;
        double va=0;
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
                va=0.5;
                break;
            case 'c':
                va=0.5;
                break;
            case 'r':
                va=0.5;
                break;  
        }
        
        if(va>=0.5 && va<=7.0){
            switch (palo){
                case 'o':
                    car=new Card(Character.toString(value),Palo.O);
                    break;
                case 'b':
                    car=new Card(Character.toString(value),Palo.B);
                    break;
                case 'e':
                    car=new Card(Character.toString(value),Palo.E);
                    break;
                case 'c':
                    car=new Card(Character.toString(value),Palo.C);
                    break;
            }          
        }
        return car;
    }

    @Override
    public String toString() {
        return "Card{" + "value=" + figure + ", palo=" + palo + '}';
    }
    
    
 
}
