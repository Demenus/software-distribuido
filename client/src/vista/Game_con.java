/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import controlador.Controlador;
import java.util.Scanner;
import model.Card;

/**
 *
 * @author Huang
 */
public class Game_con {
    Controlador controller;
    Scanner sc;
    public Game_con(Controlador controller, int i, Scanner sc) {
        this.controller=controller;
        this.sc=sc;
        if(i==1){
            player_mode();
        }else{
            automatic_mode();
        }
    }

    private void player_mode() {
        int current_bet;
        Card card;
        String op;
        double betupAmount=0.0;
        double aux=0.0;
        this.controller.startNewGame_client();
        current_bet=this.controller.getCurrentBet();
        menu(current_bet, this.controller.getCurrentPoints());
        op=sc.next();
        while(!op.equals("4")){
            switch(op){
                case "1":
                        card=this.controller.newCard_client();
                        System.out.println(card.toString());
                        current_bet=this.controller.getCurrentBet();
                        menu(current_bet,this.controller.getCurrentPoints());
                        break;
                case "2":
                        System.out.println("Introduce the amount of bet(Integer Value):");
                        aux=sc.nextInt();
                        this.controller.betUp_client(aux);
                        current_bet=this.controller.getCurrentBet();
                        menu(current_bet,this.controller.getCurrentPoints());
                        break;
                case "3":
                        String result=this.controller.pass();
                        System.out.println(result);
                        op="4";
                        break;
            }
            if(!op.equals("4")){
                op=sc.next(); 
            }   
        }
    }

    private void automatic_mode() {
        this.controller.startNewGame_client();
    }

    private void menu(double betamount,double currentpoint) {
        System.out.println("Current bet amount: "+betamount+ ". Current got points: "+currentpoint);
        System.out.println("------------------------------------------------------------");
        System.out.println("Choose one of the below options introducing integer number: ");
        System.out.println("1. New card");
        System.out.println("2. Bet up");
        System.out.println("3. Pass");
        System.out.println("4. Exit");
        System.out.println("------------------------------------------------------------");
    }


    
}
