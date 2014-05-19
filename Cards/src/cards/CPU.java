/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cards;

/**
 *
 * @author ShaiG
 */
public class CPU {
    Card[] _hand;
    int _money;
    boolean raised=true;
    
    public CPU(Card[] hand, int money){
        _hand=hand;
        _money=money;
    }
    
}
