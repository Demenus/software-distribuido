/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import comutils.ComUtils;
import controlador.Controlador;
import java.util.Scanner;

/**
 *
 * @author Huang
 */
public class Client_con {
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
    
    public static void main(String args[]) {
        String op;
        Scanner sc = new Scanner(System.in);
        menuStartNewGame();
        op=sc.next();
        while(!op.equals("1") && !op.equals("2")){
            System.out.println("Input ERROR! ");
            menuStartNewGame();
            op=sc.next();
        }        
        switch(op){
            case "1":
               
                Controlador controller=new Controlador("127.0.0.1",8000);
                if(controller.isConnected()){
                    System.out.println("We have just connected to the server.");
                    menuGame();
                    op=sc.next();
                    while(!op.equals("1") && !op.equals("2") && !op.equals("3")){
                        System.out.println("Input ERROR! ");
                        menuGame();
                        op=sc.next();
                    }      
                    if(op.equals("3")){
                        controller.disconnect();
                    }else{
                        if(op.equals("1")){
                            Game_con game=new Game_con(controller,1,sc);
                        }else{
                            Game_con game=new Game_con(controller,2,sc);
                        }
                    }
                }else{
                    System.out.println("It is not possible to connect to the server!");
                }
                break;
            case "2":
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


        