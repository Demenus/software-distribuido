/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import controlador.Controlador;

import java.util.Scanner;

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
        String cardinformation;
        String op;
        double betupAmount=0.0;
        int aux=0;
        this.controller.startNewGame_client();
        current_bet=this.controller.getCurrentBet();
        menu(current_bet, this.controller.getCurrentPoints());
        op=sc.next();
        while(!op.equals("4")){
            switch(op){
                case "1":
                        cardinformation=this.controller.newCard_client();
                        System.out.println(cardinformation);
                        if(cardinformation.length()>25 || cardinformation.equals("error")){
                            op="4";
                        }else{
                            current_bet=this.controller.getCurrentBet();
                            menu(current_bet,this.controller.getCurrentPoints());
                        }
                        
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
        int current_bet;
        this.controller.startNewGame_client();
        int random;
        String cardinformation;
        boolean state=true;
        boolean errorState=false;
        current_bet=this.controller.getCurrentBet();
        menuAutomaticMode(current_bet, this.controller.getCurrentPoints(),0);
        String score;
        //new card
        cardinformation=this.controller.newCard_client();
        System.out.println(cardinformation);
        if(cardinformation.equals("error")){
            System.out.println("Error on receiving new card!");
        }else{
            while(this.controller.getCurrentCardsNumber()>0 && this.controller.getCurrentPoints()<6.5 && state==true && errorState==false){
                random = (int)(Math.random() * 1);//It has to be changed. We want 0 has probability 0.8 and 0.2 to 1.
                if(random==0){
                    cardinformation=this.controller.newCard_client();
                    System.out.println(cardinformation);
                    if(cardinformation.length()>25){
                        state=false; //Change state to false if we got over 7.5 points.
                    }else{
                        if(cardinformation.equals("error")){//Verify if it received ERRO from server.
                            errorState=true;
                        }
                    }
                }else{
                    this.controller.betUp_client(100);//Bet up to 100.
                }
                current_bet=this.controller.getCurrentBet();
                menuAutomaticMode(current_bet,this.controller.getCurrentPoints(),random);
            }
            if(this.controller.getCurrentPoints()>=6.5 && this.controller.getCurrentPoints()<7.5){
                score=this.controller.pass();
                System.out.println(score);
                
            }
        }
        
       
        
    }
    private void menuAutomaticMode(double betamount, double currentpoint, int opcion){
        System.out.println("Current bet amount: "+betamount+ ". Current got points: "+currentpoint);
        if(opcion==0){
            System.out.println("I've asked to the server for a new card.");
        }else{
            if(opcion==1){
                System.out.println("I've bet 100.0 up.");
            }else{
                System.out.println("I've passed this turn.");
            }
        }
        System.out.println("------------------------------------------------------------");
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
