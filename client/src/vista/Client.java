/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import comutils.ComUtils;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Huang
 */
public class Client extends javax.swing.JFrame {
    Socket socket;
    ComUtils comUtils;
    /**
     * Creates new form Client
     */
    public Client() {
        initComponents();
    }

    private Client(String[] args) {
        initComponents();
        this.conect(args);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bt_startnewgame = new javax.swing.JButton();
        bt_exit = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        bt_startnewgame.setText("Start New Game");
        bt_startnewgame.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_startnewgameActionPerformed(evt);
            }
        });

        bt_exit.setText("Exit");
        bt_exit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_exitActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(bt_startnewgame)
                .addGap(68, 68, 68)
                .addComponent(bt_exit)
                .addContainerGap(120, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(127, 127, 127)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bt_startnewgame)
                    .addComponent(bt_exit))
                .addContainerGap(150, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void bt_startnewgameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_startnewgameActionPerformed
        // TODO add your handling code here:
        String val;
        String [] answer;
        int minim_bet_amount;
        boolean error=true;
        if(this.socket.isConnected()){
            /* Ask new card from server*/
            comUtils.sendMessage("STRT");

            /* Read an answer from the server */
            val = comUtils.receiveMessage(40);
            System.out.println("He enviat un 10, la resposta del servidor es " + val);

            answer=(val.toLowerCase()).split(" ");
            if (!answer[0].equals("stbt")){
                 System.out.println("No he rebut una bona resposta, sino un " + val);
                 System.exit(1);
            }else{
                minim_bet_amount=Integer.parseInt(answer[1]);
                System.out.println("El minim aposta es, " + minim_bet_amount);
                Game newgame=new Game(socket,comUtils,minim_bet_amount);
                newgame.setVisible(true); 
            }
        }
           

    }//GEN-LAST:event_bt_startnewgameActionPerformed

    private void bt_exitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_exitActionPerformed
        // TODO add your handling code here:
        if(this.socket.isConnected()){
            try {
                this.socket.close();
            } catch (IOException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        this.dispose();
        
    }//GEN-LAST:event_bt_exitActionPerformed
   private void conect(String [] args){
        String nomMaquina="", str,val;
        int numPort=0,i=0;
        InetAddress maquinaServidora;        
        while (i<=args.length ){
            switch(args[i]){
                    case "-h":        
                        System.out.println("Us: java Client <maquina_servidora> <port>");
                        //System.exit(1);
                        break;
                    case "-s":
                        nomMaquina = args[0];//get IP
                        break;
                    case "-p":
                        numPort  = Integer.parseInt(args[1]); //get port
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
    /**
     * @param args the command line arguments
     */
    public static void main(final String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Client.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Client.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Client.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Client.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Client(args).setVisible(true);

            }
        });
       
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bt_exit;
    private javax.swing.JButton bt_startnewgame;
    // End of variables declaration//GEN-END:variables
}
