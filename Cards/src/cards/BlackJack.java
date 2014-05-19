/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cards;
import java.util.Scanner;
import java.util.Random;
        
/**
 *
 * @author ShaiG
 */
public class BlackJack {
    
    static int decksize=52; //in case later you want a double deck or something
    static int money=100; 
    static int bet=0;
    static int value=0; //total value of users hand
    static int x=0; //used for various counters
    static int cardHold;//used for deck creator 
    static int cardsmissing=0; //tells deck if there are any cards missing     
    
    static boolean add=true; //will continue to let you get hit
    static boolean split=false, dd=false; 
    //to make suits easier to read\\
    static int SPADES=1; 
    static int HEARTS=2;        
    static int DIAMONDS=3;
    static int CLUBS=4;
    
    static Card[] deck = new Card [decksize]; //the deck array
    static Card[] hand = new Card [15]; //users hand
    static int dealerVal; //dealers total
    static Card dealerone; //used to represent one of dealer's actual deck
    
    static Scanner scan = new Scanner(System.in); //defines scanner
    static Random random = new Random(); //defines random generator
    
    public static void main(String args[]){
      
        while(money>0) {      
        
            if(money>3000){
                System.out.println("obviously, you've been counting cards. "
                + "get out of this casino!");
                money=0; 
            }//ends if  
            if(money==0){ System.out.println("out of cash!");}//end if
            else{
                //redifine variables for each play through\\
                x=0;  

                
           if(deck[15]==null){

               cardsmissing=0;deckcreator();System.out.println("shuffling deck...");}//end if
                //only refresh deck if less than 15 cards left
                value=0;
                add=true;
                hand[2]=null; //so system knows you can double down
                System.out.println("money total= "+money);
                bet(); //starts game by bet method
            }//end else
        }//end while
    }//end main
    public static void deckcreator(){//creates deck         
        for(int n=2; n<=14; n++){//for card value 
            for(int s=1;s<=4;s++){//for suit value               
                deck[x]=new Card(n, s);//creates card in counter spot
                x++;//ups counter
            }//end for
        }//end for                          
    }//end deck creator
    public static void valuer(){//turns all royals to 10, aces to 11
        for (int i=0; i<decksize; i++){//will go through whole deck
            if((deck[i]._value>10)){
                if(deck[i]._value==14){//for aces
                    deck[i]._value=11;
                }//ends if
                else{//for royals
                    deck[i]._value=10;
                }//ends else
            }//ends if
        }//ends for
    }//ends valuer
    
    public static void bet(){//for betting, initiates game
        if(cardsmissing==0){valuer();}//see above}
        boolean flg=true;//continues betting, for if you bet to little or to much        
       
        while(flg){
            System.out.println("How much would you like to bet?");
            bet = scan.nextInt();//user defines bet
            if(bet<money/10){//less than 10%
                System.out.println("please bet more...");                
            }//ends if
            else if(bet>money){//bet too much
                bet=money;//caps bet off at money total
                System.out.println("all in?");
                String ii=scan.next();//user defines ii
                if (ii.contains("y")){                  
                    flg=false;//ends loop
                }//ends if
            }//ends elif
            else{flg=false;}//ends loop
        }//end while
        money=money-bet; game(true);//initiates actual game
    }//end bet
    
    public static void game(boolean twice){        
        if(twice){x=0;
            dealerone=truePlus(dealerone);//see truePlus method  
            System.out.println("dealers face up is a "+dealerone._name+" of "+dealerone._suit+".");}
        else{System.out.println("You have a "+hand[0]._name);}
        System.out.println("You drew:"); 
        addCard(); 
        if(twice){System.out.print(", and"); 
            addCard(); System.out.println(".");}
        else{System.out.println();}
        
        while(add&&value!=21){
            dd=false; 
            System.out.print("total is "+value+". hit");
            if(hand[2]==null&&money>=2*bet){System.out.print(", double down");dd=true;}
            if(money>bet&&hand[1]!=null&&hand[2]==null&&(hand[1]._value==hand[0]._value||hand[1]._name.equals(hand[0]._name))){System.out.print(", split");split=true;}
            else{split=false;}
            System.out.println(" or stand?");
            String ii=scan.next();
            if (ii.contains("y")||ii.contains("h")){
                System.out.print("You got"); addCard(); System.out.println("."); 
            }             
            else if(ii.contains("d")&&dd&&!ii.contains("s")){
                doubleDown();
            }//end elif
            else if(ii.contains("sp")&&split){split();}
            else{add=false;}//end else/end loop 
            if(value>=21){add=false;}//end if/end loop
        }//end while
        if(!split||(value>dealerVal)){
            while(dealerVal<15){//dealer hiting
                int card=(int)(random.nextDouble()*(decksize-cardsmissing-1));                
                dealerone = deck[card]; 
                dealerVal=dealerVal+dealerone._value;
            }//end while
            if (value>21){System.out.println("bust!");}//end if       
            else if (value==21){endGame("blackjack!", 1);}//end elif
            else if (value>dealerVal){endGame("dealer's total: "+dealerVal+".",1);}//end elif
            else if (dealerVal>21){endGame("dealer's total: "+dealerVal+". dealer busted.",1);}//end elif
            else if (dealerVal==value){ endGame("dealer's total: "+dealerVal,2 );}//end elif
            else{endGame("dealer's total: "+dealerVal,900); }//end elif        
        }
    }
    public static Card truePlus(Card Card){//actually creates random card
        int card=(int)(random.nextDouble()*(decksize-cardsmissing-1));//random number btwn 0 and 51-(amount of cards missing)
        //card=52-cardsmissing-1;//for testing
        Card = deck[card];//takes card from deck in slot defined above 
        deck[card]=null; cardsmissing++; //deletes chosen card from actual deck
        for(int xhold=card+1; xhold<decksize-cardsmissing+1; xhold++){ //moves cards
            deck[xhold-1]=deck[xhold];
            deck [xhold]=null;
        }//end for
        return Card;
    }//end trueplus
    public static void addCard(){
        boolean flg=true;
        hand[x]=truePlus(hand[x]);
        System.out.print(" a "+hand[x]._name+" of "+hand[x]._suit);
        value=value+hand[x]._value;
        while((value>21)&&(flg)){//if you busted and hit program hasn't yet been turned off
            for(int xhold=x;xhold>0; xhold--){
               if(hand[xhold]._value==11){//changes any possble aces back to one in case of bust
                   hand[xhold].setAce(1);
                   xhold=0;
                   value=value-10;
                }//end if                   
            }//end for
            if(value>21){//even after changing all aces
               flg=false;//end loop                  
            }//end if
        }//end while                          
        x++;
    }//end addCard     
    public static void doubleDown(){
        //will double the bet amount but only give one more card for a total of 3 cards
        money=money-bet;//subtracts bet again from money
        bet=bet*2;//doubles bet        
        add=false;//no more hits allowed
         
        System.out.print("You got"); 
        addCard();
        System.out.println(".");         
    }
    public static void split(){
        money=money-bet;hand[1]=null;value=hand[0]._value;x=1;
        System.out.println("round one");
        game(false);
        Card c=hand[0];hand=new Card[15];hand[0]=c;value=hand[0]._value;add=true;split=false;x=1;
        System.out.println("round two");
        game(false);
    }  
    public static void endGame(String a, int b){
        value=0;
        double h;String ww;
        if(b==1){ww="Winner!";}else if(b==2){ww="Tie.";}else{ww="Loser...";}
        System.out.println(a+" "+ww);
        if(value==21){h=2.5;}else if(b==1){h=2;}else if(b==2){h=1;}else{h=0;}
        int y=(int)Math.ceil(h*bet);
        money=money+(y);
    }     
}