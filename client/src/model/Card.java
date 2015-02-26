/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;

/**
 *
 * @author Huang
 */
public class Card {
    public enum Palo {
        ORO,
        BASTOS,
        ESPADAS, 
        COPAS
    }
    private int value;
    private Palo palo;

    public Card(int value, Palo palo) {
        this.value = value;
        this.palo = palo;
    }

    public int getValue() {
        return value;
    }

    public Palo getPalo() {
        return palo;
    }
    
    
 
}
