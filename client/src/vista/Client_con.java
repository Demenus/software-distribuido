/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import comutils.ComUtils;
import controlador.Controlador;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Huang
 */
public class Client_con {
    ComUtils comUtils;
    private static void menu(){
        System.out.println("Menu");
        System.out.println("---------------------------------------------------");
        System.out.println("1.Player mode - Start new game");
        System.out.println("2.Automatic mode - Start new game");
        System.out.println("3.Exit");
        System.out.println("---------------------------------------------------");
        System.out.println("Please enter a integer number to choosse one of the above options:");
    }
    public static void main(String args[]) {
        int op;
        Socket socket =null;
        Controlador controller=new Controlador(socket);
        try {
            socket = new Socket("127.0.0.1", 1212);
        } catch (UnknownHostException ex) {
            Logger.getLogger(Client_con.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Client_con.class.getName()).log(Level.SEVERE, null, ex);
        }
        ComUtils comUtils=new ComUtils(socket);
        Scanner sc = new Scanner(System.in);
        menu();
        op=sc.nextInt();
        while(op<1 || op>3){
            System.out.println("Input ERROR! ");
            menu();
        }
        switch(op){
            case 1:
                Game game=new Game(socket,comUtils,50,controller);
                break;
            case 2:
                break;
            case 3:
                break;
        }
      
        
        
        /*String nomMaquina="", str,val;
        int numPort=0, value,i=0;
        Socket socket=null;
        InetAddress maquinaServidora;
        ComUtils comUtils;
        
        while (i<=args.length ){
            switch(args[i]){
                    case "-h":
                        
                        System.out.println("Us: java Client <maquina_servidora> <port>");
                        //System.exit(1);
                        break;
                    case "-s":
                        nomMaquina = args[0];//get ip
                        break;
                    case "-p":
                        numPort    = Integer.parseInt(args[1]); //get port
                        break;
            }     
            i+=2;
        }
        
        
        try{
          /* Obtenim la IP de la maquina servidora */
      //    maquinaServidora = InetAddress.getByName(nomMaquina);
          /* Obrim una connexio amb el servidor */
       //   socket = new Socket(maquinaServidora, numPort);
          /* Obrim un flux d'entrada/sortida amb el servidor */
       //   comUtils = new ComUtils(socket); 

          /* Enviem el valor 10 al servidor */
      //   comUtils.sendMessage("STRT");

          /* Llegim la resposta del servidor */
   /*      
 
          val = comUtils.receiveMessage(40);

          System.out.println("He enviat un 10, la resposta del servidor es " + val);

          /* Tornem a enviar un altre valor */
    //      comUtils.sendMessage("12");

          /* Ara hauriem de rebre un missatge d'error */
      /*    val = comUtils.receiveMessage(10);

          if (!val.equals("12")){
            System.out.println("No he rebut un 99 de resposta, sino un " + val);
            System.exit(1);
          }else{
            str = comUtils.receiveMessage(10);
            System.out.println("He enviat un 12, la resposta és el següent missatge d'error: " + str);
          }

        }
        catch (IOException e)
        {
          System.out.println("Els errors han de ser tractats correctament en el vostre programa.");
        }
        finally {
            try {
                    if(socket != null) socket.close();
                }
                catch (IOException ex) {
                        System.out.println("Els errors han de ser tractats correctament pel vostre programa");
                } // fi del catch    
        }
    }*/
    }
    
    
}


        