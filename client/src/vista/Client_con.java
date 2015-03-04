/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import comutils.ComUtils;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

/**
 *
 * @author Huang
 */
public class Client_con {
    public static void main(String args[]) {
        String nomMaquina="", str,val;
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
          maquinaServidora = InetAddress.getByName(nomMaquina);
          /* Obrim una connexio amb el servidor */
          socket = new Socket(maquinaServidora, numPort);
          /* Obrim un flux d'entrada/sortida amb el servidor */
          comUtils = new ComUtils(socket); 

          /* Enviem el valor 10 al servidor */
         comUtils.sendMessage("STRT");

          /* Llegim la resposta del servidor */
         
 
          val = comUtils.receiveMessage(40);

          System.out.println("He enviat un 10, la resposta del servidor es " + val);

          /* Tornem a enviar un altre valor */
          comUtils.sendMessage("12");

          /* Ara hauriem de rebre un missatge d'error */
          val = comUtils.receiveMessage(10);

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
    }
}


        