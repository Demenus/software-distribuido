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
public class Game {
    Controlador controller;
    Scanner sc;
    /**
     * Contructor.
     * @param controller
     * @param i  the option to start a new game with player mode or automatic mode.
     */
    public Game(Controlador controller, int i) {
        this.controller=controller;
        this.sc=new Scanner(System.in);
        if(i==1){
            player_mode();
        }else{
            automatic_mode();
        }
    }
    /**
     * To start a new game with player mode.
     */
    private void player_mode() {
        int current_bet;
        String cardinformation;
        String op;
        int betUp_amount;
        this.controller.startNewGame_client();
        current_bet=this.controller.getCurrentBet();
        menu(current_bet, this.controller.getCurrentPoints());
        op=sc.next();
        while(!op.equals("1") && !op.equals("2") && !op.equals("3") && !op.equals("4")){
            System.out.println("Please enter a integer value between 1 to 4:");
            op=sc.next(); 
        }
        while(!op.equals("5")){
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
                        String aux=sc.next();
                        try{
                            betUp_amount=Integer.parseInt(aux); 
                        }catch (NumberFormatException e){
                            betUp_amount=-5;
                        }
                        while(betUp_amount<=0 ){
                            System.out.println("Introduce the amount of bet(Integer Value):");
                            aux=sc.next();
                            try{
                                betUp_amount=Integer.parseInt(aux); 
                            }catch (NumberFormatException e){
                                betUp_amount=-5;
                            }
                        }
                        this.controller.betUp_client(betUp_amount);
                        current_bet=this.controller.getCurrentBet();
                        menu(current_bet,this.controller.getCurrentPoints());
                        break;
                case "3":
                        String result=this.controller.pass();
                        if(result.equals("error")){
                            System.out.println("This option is not enable now, because you've just bet up the amount before. Please try it after asking for new card.");
                            current_bet=this.controller.getCurrentBet();
                            menu(current_bet,this.controller.getCurrentPoints());
                        }else{
                            System.out.println(result);
                            op="5";
                        } 
                        break;
                case "4":
                        System.out.println("Are you sure to leave game right now?\n Enter 's' to leave game and  'n' to stay at game.");
                        String res=sc.next();
                        while(!res.toLowerCase().equals("s") && !res.toLowerCase().equals("n")){
                            System.out.println("Are you sure to leave game right now?\n Enter 's' to leave game and  'n' to stay at game.");
                            res=sc.next();
                        }
                        if(res.equals("s")){
                            op="5";
                        }else{
                            current_bet=this.controller.getCurrentBet();
                            menu(current_bet,this.controller.getCurrentPoints());
                        }
            }
            if(!op.equals("5")){
                op=sc.next(); 
                while(!op.equals("1") && !op.equals("2") && !op.equals("3") && !op.equals("4")){
                    System.out.println("Please enter a integer value between 1 to 4:");
                    op=sc.next(); 
                }
            }   
        }
    }
    /**
     * To start a new game with automatic mode. It's plays by machine and plays game until it gets 6.5 points.
     */
    private void automatic_mode() {
        int current_bet;
        this.controller.startNewGame_client();
        int random;
        String cardinformation;
        boolean state=true;
        boolean errorState=false;
        current_bet=this.controller.getCurrentBet();
        menuAutomaticMode(current_bet, this.controller.getCurrentPoints(),0,"");
        String score;
        //new card
        cardinformation=this.controller.newCard_client();
        System.out.println(cardinformation);
        if(cardinformation.equals("error")){
            System.out.println("Error on receiving new card!");
        }else{
            while(this.controller.getCurrentCardsNumber()>0 && this.controller.getCurrentPoints()<6.5 && state==true && errorState==false){
                random =(Math.random()<0.8)?0:1;//It has to be changed. We want 0 has probability 0.8 and 0.2 to 1.
                if(random==0){
                    cardinformation=this.controller.newCard_client();
                    if(cardinformation.length()>25){
                        state=false; //Change state to false if we got over 7.5 points.
                    }else{
                        if(cardinformation.equals("error")){//Verify if it received ERRO from server.
                            errorState=true;
                        }
                    }
                }else{
                    this.controller.betUp_client(100);//Bet up to 100.
                    cardinformation="";
                }
                current_bet=this.controller.getCurrentBet();
                menuAutomaticMode(current_bet,this.controller.getCurrentPoints(),random, cardinformation);
            }
            if(this.controller.getCurrentPoints()>=6.5 && this.controller.getCurrentPoints()<7.5){
                score=this.controller.pass();
                current_bet=this.controller.getCurrentBet();
                menuAutomaticMode(current_bet,this.controller.getCurrentPoints(),2,"");
                System.out.println(score);
                
            }
        }
    }
    /**
     * Menu for automatic mode.
     * @param betamount
     * @param currentpoint
     * @param opcion
     * @param cardinformation 
     */
    private void menuAutomaticMode(double betamount, double currentpoint, int opcion, String cardinformation){
        System.out.println("------------------------------------------------------------");
        System.out.println("Current bet amount: "+betamount+ ". Current got points: "+currentpoint);
        if(opcion==0){
            System.out.println("I've asked to the server for a new card, which is:");
            System.out.println(cardinformation);
        }else{
            if(opcion==1){
                System.out.println("I've bet 100.0 up.");
            }else{
                System.out.println("I've passed this turn.");
            }
        }
        System.out.println("------------------------------------------------------------");
    }
    /**
     * Menu for player mode.
     * @param betamount
     * @param currentpoint 
     */
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
