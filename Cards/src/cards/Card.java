                    /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cards;

/**
 *
 * @author goldman family
 */
public class Card {
   boolean _used;
   String _name;
   String _suit;
   int _value;
   int _suit_number;
   
   
   public Card(int value, int sNumber){
       _value=value;
       _suit_number=sNumber;
       _used=false;
       
       //------card name------\\
       
       if(value==11){_name="jack";}
       else if(value==12){_name="queen";}
       else if(value==13){_name="king";}  
       else if((value==14)||(value==1)){_name="ace";}
       else{_name=String.valueOf(value);}
       //converts actual name into card number
      
       //------suit name------\\
       if(sNumber==1){_suit="spades";}
       if(sNumber==2){_suit="hearts";}
       if(sNumber==3){_suit="diamonds";}
       if(sNumber==4){_suit="clubs";}     
     
       /*  rank of suits:
        * spades 
        * hearts
        * diamonds
        * clubs    */       
   }
   
   public void setAce(int value){
       _value=value;
   }
   
   
    
}
