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
public class Value {
    int _value;
    double _high;
    String _rankname;
    
    public Value(int value, Card high){
        _value=value;
        _high=high._value+(high._suit_number*.25);    
        if(1==value){_rankname="Two of a Kind";}
        if(value==2){_rankname="Two Pair";}
        if(3==value){_rankname="Three of a Kind";}
        if(4==value){_rankname="Straight";}
        if(5==value){_rankname="Flush";}
        if(6==value){_rankname="Full House";}
        if(7==value){_rankname="Four of a Kind";}
        if(8==value){_rankname="Straight Flush";}
        if(0==value){_rankname="High Card "+high._name;}
    }
    public void setValue(int value){
        _value=value;
    }
    public void setHigh(Card high){
        _high=high._value+(high._suit_number*.25);  
    }
}
