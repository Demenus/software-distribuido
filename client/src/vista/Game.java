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
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import model.Card;
import model.Card.Palo;
import model.Listcard;

/**
 *
 * @author Huang
 */
public class Game extends javax.swing.JFrame {
    Socket socket;
    ComUtils comUtils;
    String [] answer;
    int minim_bet_amount;
    int item=0;
    int current_card_value;
    Controlador controller;
    Listcard listcard;
    Scanner sc;
    /**
     * Creates new form Game
     */
   public Game(Socket socket,ComUtils comUtils, int minim_bet_amount, Controlador controller) {
        
        initComponents();
        this.socket=socket;
        this.comUtils=comUtils;
        this.tx_currentbet.setText(""+minim_bet_amount);
        this.answer[0]="";
        this.current_card_value=0;
        this.controller=controller;
        listcard=this.controller.getListCard();
        this.sc=new Scanner(System.in);
    }



    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bt_newcard = new javax.swing.JButton();
        bt_betup = new javax.swing.JButton();
        bt_pass = new javax.swing.JButton();
        bt_exit = new javax.swing.JButton();
        pn_listcard = new javax.swing.JScrollPane();
        ls_card = new javax.swing.JList();
        lb_listcard = new javax.swing.JLabel();
        lb_currentbet = new javax.swing.JLabel();
        tx_currentbet = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        bt_newcard.setText("New card");
        bt_newcard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_newcardActionPerformed(evt);
            }
        });

        bt_betup.setText("Bet up");
        bt_betup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_betupActionPerformed(evt);
            }
        });

        bt_pass.setText("Pass");
        bt_pass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_passActionPerformed(evt);
            }
        });

        bt_exit.setText("Exit");
        bt_exit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_exitActionPerformed(evt);
            }
        });

        ls_card.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        pn_listcard.setViewportView(ls_card);

        lb_listcard.setText("List of cards");

        lb_currentbet.setText("Current bet");

        tx_currentbet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tx_currentbetActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(bt_newcard, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(pn_listcard, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(lb_listcard)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(bt_betup, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(bt_exit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(bt_pass, javax.swing.GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(lb_currentbet)
                                .addGap(97, 97, 97))
                            .addComponent(tx_currentbet, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(28, 28, 28))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(46, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lb_currentbet, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lb_listcard, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pn_listcard, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tx_currentbet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bt_newcard)
                    .addComponent(bt_betup)
                    .addComponent(bt_pass))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bt_exit)
                .addGap(21, 21, 21))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void bt_newcardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_newcardActionPerformed
        // TODO add your handling code here:
            String ans;
            Card carta=null;
            boolean estate;
            Palo palo;
            comUtils.sendMessage("DRAW");

            /* Read an answer from the server */
            ans = comUtils.receiveMessage(7);
            System.out.println("He enviat un 10, la resposta del servidor es " + ans);

            this.answer=(ans.toLowerCase()).split(" ");
            if (!this.answer[0].equals("card")){
                 System.out.println("No he rebut una bona resposta, sino un " + this.answer[0]);
            }else{
                carta=Card.parseCard(this.answer[1]);
            }
            if(carta!=null){//We verify the estate of the game, we have to see if the sum of all the value passes over 7.5 o no.
                estate=controller.verify_estate(this.listcard);
                if(estate){
                    //In case not passing over 7.5, we add this new card to the card list.
                    controller.addNewCard(listcard, carta);
                }else{
                    //In case it passes over 7.5, we have to receive the message 'BUSTING' from the server.
                   ans = comUtils.receiveMessage(40);
                   this.answer=(ans.toLowerCase()).split(" ");
                   if (!this.answer[0].equals("bstg")){
                        System.out.println("¡ERROR!Something has gone wrong. We are expecting the answer 'BUSTING'!");
                   }else{
                       this.bt_passActionPerformed(evt);
                   }
                }
            }
        
    }//GEN-LAST:event_bt_newcardActionPerformed

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

    private void tx_currentbetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tx_currentbetActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tx_currentbetActionPerformed

    private void bt_betupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_betupActionPerformed
        // TODO add your handling code here:
        String ans=this.answer[0].toLowerCase();
        if(!ans.equals("card")){
            System.out.println("This option is not enable now. Please try it after asking for new card.");
        }else{
            String inputValue = JOptionPane.showInputDialog("Please input a value of bet:");
            while(Integer.parseInt(inputValue)<=0){
                inputValue = JOptionPane.showInputDialog("Please input a value of bet:");
            }
            this.controller.betUp(inputValue);
            int currentbet=Integer.parseInt(this.tx_currentbet.getText());
            inputValue=""+(Integer.parseInt(inputValue)+currentbet);
            this.tx_currentbet.setText(inputValue);
        }
    }//GEN-LAST:event_bt_betupActionPerformed

    private void bt_passActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_passActionPerformed
        // TODO add your handling code here:
        System.out.println("Are you sure to pass the card and finish the game?");
        System.out.println("Enter the character 'Y' to pass the card. Otherwise enter the 'N'.");
        String ans=sc.next(); 
        ans=ans.toLowerCase();
        while(!ans.equals("y") && !ans.equals("n")){
            System.out.println("Enter the character 'Y' to pass the card. Otherwise enter the 'N'.");
            ans=sc.next(); 
            ans=ans.toLowerCase();
        }
        this.controller.passCard(ans);
        
        
    }//GEN-LAST:event_bt_passActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bt_betup;
    private javax.swing.JButton bt_exit;
    private javax.swing.JButton bt_newcard;
    private javax.swing.JButton bt_pass;
    private javax.swing.JLabel lb_currentbet;
    private javax.swing.JLabel lb_listcard;
    private javax.swing.JList ls_card;
    private javax.swing.JScrollPane pn_listcard;
    private javax.swing.JTextField tx_currentbet;
    // End of variables declaration//GEN-END:variables
}
