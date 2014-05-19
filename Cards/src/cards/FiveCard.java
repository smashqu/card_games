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
public class FiveCard {
    static int money=100, decksize=52, draw=5, bet, pool, poolbet;
    static int cardsmissing, x=0;
    static Card[] deck=new Card[decksize], userhand=new Card[draw];
    static CPU[]CPUplayers; static int plymiss=0;
    static Scanner scan=new Scanner(System.in);
    static Random random=new Random();
    static boolean raise= true,playing=true;
    
    public static void main(String args[]){
        System.out.println("how many players would you like to play with?");
        int choice=scan.nextInt();CPUplayers=new CPU[choice];
        for(int i=0;i<choice;i++){
            CPUplayers[i]=new CPU(new Card[draw], money);
        }
        bet=0;pool=0;poolbet=0;cardsmissing=0;
        boolean flg=true;
        while(flg){
        if(deck[15]==null){
               cardsmissing=0;x=0;deckcreator();System.out.println("shuffling deck...");
        }//end if
        game();
        }
    }
    public static void bet(){//for betting, initiates game
        boolean flg=true;//continues betting, for if you bet to little or to much        
       
        while(flg){
            System.out.println("How much would you like to bet?");
            bet = scan.nextInt();//user defines bet
            if(bet<money/10||bet<poolbet){//less than 10%
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
        money=money-bet;pool=pool+bet;poolbet=bet;
    }//end bet
    public static void game (){
        
        System.out.println("your hand:");
        for (CPU CPUplayer : CPUplayers) {
            for (int j = 0; j < CPUplayer._hand.length; j++) {
                CPUplayer._hand[j] = truePlus();
            }
        }
        for (int i=0;i<draw;i++){
            userhand[i]=truePlus();
            System.out.print(userhand[i]._name+" "+userhand[i]._suit+", ");
        }
        System.out.println("your hand value: "+handValue(userhand)._rankname);
        while(raise){
            if(playing){raise=betCheck(poolbet);}
            if (raise){x=1;
                for (CPU CPUplayer : CPUplayers){ 
                    x++;
                    System.out.println("player "+x+" ");
                    CPUplayer.raised=CPUbet(CPUplayer);
                }
                for (CPU CPUplayer : CPUplayers){
                    int xy=0;
                    if(!CPUplayer.raised){
                        xy++;
                    }
                    if (xy==CPUplayers.length){
                        raise = false;
                    }
                }
            }
        }
        
    }
    public static boolean CPUbet(CPU cpu){
        int rand=(int)(random.nextDouble()*6);int mraised;
        if(poolbet>cpu._money/2){rand--;} if(poolbet>cpu._money*.75){rand--;}
        if(poolbet<cpu._money/4){rand++;}if(poolbet<cpu._money/8){rand++;}
        if (handValue(cpu._hand)._value==0){
            if(rand<=1){System.out.println("folded.");for(int i=0;i<draw;i++){cpu._hand[i]=null;}}
            else if(cpu._money<poolbet){System.out.println("cannot bet.");}
            else{System.out.println("called.");cpu._money=cpu._money-poolbet;pool=pool+poolbet;}
            return false;
        }
        else if(handValue(cpu._hand)._value<3){
            if(rand<=1){System.out.println("folded.");for(int i=0;i<draw;i++){cpu._hand[i]=null;}return false;}
            else if(cpu._money<poolbet){System.out.println("cannot bet.");return false;}
            else if(rand<=3){System.out.println("called.");cpu._money=cpu._money-poolbet;pool=pool+poolbet;return false;}
            else {
                mraised=poolbet/10;
                if(mraised==0){mraised=5;}
                poolbet=poolbet+(mraised);System.out.println("mraisedd by "+mraised+" total is "+poolbet);cpu._money=cpu._money-(poolbet+mraised);pool=pool+(poolbet+mraised);
                return true;
            }
        }
        else if(handValue(cpu._hand)._value<5){
            if(rand<=2){System.out.println("called.");cpu._money=cpu._money-poolbet;pool=pool+poolbet;return false;}
            else if(cpu._money<poolbet){System.out.println("cannot bet.");return false;}
            else if (rand<=3){
                mraised=poolbet/10; if(mraised==0){mraised=5;}
                poolbet=poolbet+(mraised);System.out.println("mraisedd by "+mraised+" total is "+poolbet);cpu._money=cpu._money-(poolbet+mraised);pool=pool+(poolbet+mraised);
                return true;
            }
            else {
                mraised=poolbet/5; if(mraised==0){mraised=10;}
                poolbet=poolbet+mraised;System.out.println("mraisedd by "+mraised+" total is "+poolbet);cpu._money=cpu._money-(poolbet+mraised);pool=pool+(poolbet+mraised);
                return true;
            }
        }
        else{
            if(rand<=1){System.out.println("called.");cpu._money=cpu._money-poolbet;pool=pool+poolbet;}
            else if(cpu._money<poolbet){System.out.println("cannot bet.");return false;}
            else {
                mraised=poolbet/4; if(mraised==0){mraised=25;}
                poolbet=poolbet+(mraised);System.out.println("mraisedd by "+mraised+" total is "+poolbet);cpu._money=cpu._money-(poolbet+mraised);pool=pool+(poolbet+mraised);
                return true;
            }
        }
        return false;
    }
    public static boolean betCheck(int x){
        boolean check=false;
        if(x>0){
            System.out.println("poolbet is "+ x + " call, raise, or fold?");
        }
        else{
            System.out.println("bet or check?"); check=true;
        }
        String choice=scan.next();  
        if(check){
            if(choice.contains("b")){bet();}
            return true;
        }
        else if(!check) {
            if(choice.contains("r")){bet();return true;} 
            if(choice.contains("c")){money=money-poolbet;pool=pool+poolbet;return false;} 
            if(choice.contains("f")){playing = false;return true;}
        }
        System.out.println("Command not recognized");raise=betCheck(poolbet);return raise;
    }
    public static void deckcreator(){//creates deck         
        for(int i=2; i<=14; i++){//for card value 
            for(int j=1;j<=4;j++){//for suit value               
                deck[x]=new Card(i, j);//creates card in counter spot
                x++;//ups counter
            }//end for
        }//end for                          
    }//end deck creator
    public static Card truePlus(){//actually creates random card
        int card=(int)(random.nextDouble()*(decksize-cardsmissing-1));//random number btwn 0 and 51-(amount of cards missing)
        //card=52-cardsmissing-1;//for testing
        Card Card= deck[card];//takes card from deck in slot defined above 
        deck[card]=null; cardsmissing++; //deletes chosen card from actual deck
        for(int xhold=card+1; xhold<decksize-cardsmissing+1; xhold++){ //moves cards
            deck[xhold-1]=deck[xhold];
            deck [xhold]=null;
        }//end for
        return Card;
    }//end trueplus
    public static Value handValue(Card [] hand){
        int sameCrd1=1, sameCrd2=1, amntSmCrd=0, amntToFlush=1, amntToStrght=1, rank=0, y=0;
        boolean straight=false, flush=false, plus=false, minus=false;
        Card holder=new Card(0,0);
        for(Card handCard : hand){
            if(!handCard._used){
                boolean match=false;int x=0;
                for(Card testCard : hand){
                    if (!testCard.equals(handCard)&&!testCard._used){
                        if (handCard._value==testCard._value){
                            if(amntSmCrd==0){sameCrd1++;}else{sameCrd2++;}
                            match=true;hand[x+1]._used=true;
                        }
                        if(!handCard._used){
                            if(testCard._value==handCard._value+1||testCard._value==handCard._value-1){
                                boolean flg=false;
                                if(testCard._value==handCard._value+1&&!minus){plus=true;flg=true;}
                                if(testCard._value==handCard._value-1&&!plus){minus=true;flg=true;}
                                if(flg){amntToStrght++; hand[y]._used=true; if(amntToStrght==5){straight=true;}}
                            }
                            if(testCard._suit_number==handCard._suit_number){
                                amntToFlush++;  hand[y]._used=true; if(amntToFlush==5){flush=true;}
                            }
                        }
                        x++;
                    }
                }
                y++;
                if(match){
                    amntSmCrd++;
                }
                if(handCard._value>holder._value){holder=handCard;}
            }
        }    
        
        if(sameCrd1==2){rank=1;}//one pair
        if(sameCrd1==2&&sameCrd2==2){rank=2;}//two pair
        if(sameCrd1==3){rank=3;}//three of a kind
        if(straight){rank=4;}if(flush){rank=5;}
        if((sameCrd1==3&&sameCrd2==2)||(sameCrd1==2&&sameCrd2==3)){rank=6;}//full house
        if(sameCrd1==4){rank=7;}//four of a kind
        if(flush&&straight){rank=8;}//flush and straight and straightflush
        Value value=new Value(rank, holder);return value;
    }
}
