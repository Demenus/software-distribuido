/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import comutils.ComUtils;
import controlador.Controlador;
import vista.Game;

import java.util.HashMap;


/**
 *
 * @author Huang
 */


//COMENTS:
//Problem 1: After client ask for BETUP, if he ask for PASS, then server dosen't know this problem. We have to think some method to trate this problem.
//Problem 2: In case of BUSTING, server always send client the same game(the same cards and the same score).
//Problem 3: Client ask for SCORE, the value of gain is alway strange LIKE  "-541665165"(big value);
//Problem 4: If client doesn't ask anything to Server for 3 seconds, Server authomatically close the conexion.
public class Client {
    public static final String DEFAULT_PORT = "1212";
    public static final String DEFAULT_IP="127.0.0.1";
    ComUtils comUtils;

    private static void menuGame(){
        System.out.println("Menu");
        System.out.println("---------------------------------------------------");
        System.out.println("1.Player mode - Start new game");
        System.out.println("2.Automatic mode - Start new game");
        System.out.println("3.Exit");
        System.out.println("---------------------------------------------------");
        System.out.println("Please enter a integer number to choosse one of the above options:");
    }
    private static void menuStartNewGame(){
        System.out.println("Menu");
        System.out.println("---------------------------------------------------");
        System.out.println("1.Start new game");
        System.out.println("2.Exit");
        System.out.println("---------------------------------------------------");
        System.out.println("Please enter a integer number to choosse one of the above options:");
    }
    /*
    public static void main(String args[]) {
        String op;
        Scanner sc = new Scanner(System.in);
        menuStartNewGame();
        op=sc.next();
        while(!op.equals("1") && !op.equals("2")){
            System.out.println("Input ERROR! Please try again.");
            menuStartNewGame();
            op=sc.next();
        }        
        switch(op){
            case "1":
                Controlador controller=new Controlador("127.0.0.1",1212);
                if(controller.isConnected()){
                    System.out.println("We have just connected to the server.");
                    menuGame();
                    op=sc.next();
                    while(!op.equals("1") && !op.equals("2") && !op.equals("3")){
                        System.out.println("Input ERROR! Please try again.");
                        menuGame();
                        op=sc.next();
                    }      
                    if(op.equals("3")){
                        controller.disconnect();
                    }else{
                        if(op.equals("1")){
                            Game game=new Game(controller,1,sc);
                        }else{
                            Game game=new Game(controller,2,sc);
                        }
                    }
                }else{
                    System.out.println("It is not possible to connect to the server!");
                }
                break;
            case "2":
                break;
        }*/
    public static void main(String args[]) {
        boolean automaticMode=false;
        if (args.length%2 != 0) {
            System.exit(-1);
        }
        HashMap<String, String> arguments = new HashMap<String, String>();
        arguments.put("-p",DEFAULT_PORT);
        arguments.put("-s",DEFAULT_IP);
        for (int i = 0; i < args.length; i=i+2) {
            arguments.put(args[i], args[i+1]);
            if(args[i].equals("-a")){
                automaticMode=true;
            }
        }
        int port = Integer.valueOf(arguments.get("-p"));
        String ip = arguments.get("-s");
        Controlador controller=new Controlador(ip,port);
        if(controller.isConnected()){
            System.out.println("We have just connected to the server.");
            if(automaticMode){
                Game game=new Game(controller,2);
            }else{
                Game game=new Game(controller,1);
            }                  
        }else{
            System.out.println("Error! We didn't connect to the server.");
        }
        

        //case "h": System.out.println("Us: java Client <maquina_servidora> <port>");


    }
    
    
}


        