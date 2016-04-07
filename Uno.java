package sample;
import java.util.*;

/**
 * Created by tneilson on 1/6/2016.
 */
public class Uno {

    public static Deck deck;
    public static Deck discard;
    public static String curColor; //Might need to change these to be CardColor and CardType, respectively
    public static String curType;
    public static final int DEALHAND = 7;
    public static boolean gameOver;
    public static int shuffleCount = 0;
    public static int shufCount = 0;
    public static boolean needDraw = false;
    public static boolean isReversed = false;
    //public static CardColor curColor;
    //public static CardType curType;

    private static ArrayList players = new ArrayList<Player>();
    private static ArrayList reversePlayers = new ArrayList<Player>();
    private static Player player1, player2, player3, player4;
    private static Player curPlayer;

    public static void main(String[] args){
        gameOver = false;
        deck = new Deck();
        discard = new Deck(0);
        player1 = new Player(DEALHAND, deck.getDeck(), discard.getDeck());
        player2 = new Player(DEALHAND, deck.getDeck(), discard.getDeck());
        player3 = new Player(DEALHAND, deck.getDeck(), discard.getDeck());
        player4 = new Player(DEALHAND, deck.getDeck(), discard.getDeck());
        players.add(player1);
        players.add(player2);
        players.add(player3);
        players.add(player4);
        reversePlayers.add(player4);
        reversePlayers.add(player3);
        reversePlayers.add(player2);
        reversePlayers.add(player1);

        discard.getDeck().add(deck.getDeck().remove(0));
        curColor = discard.getDeck().get(0).getColor().getColor();
        curType = discard.getDeck().get(0).getType().getType();

        if(curColor.equals(CardColor.ALL.getColor())){
            setRandomColor();
        }

        Iterator<Player> iter = players.iterator();
        while(iter.hasNext()){
            Player cur = iter.next();
            System.out.println(cur.toString());
            cur.getHand();
        }

        /*System.out.println("");
        System.out.println(discard.getDeck().get(0).getColor() + " " + discard.getDeck().get(0).getType());
        System.out.println("");*/

        while(gameOver == false){
            //probably do something like, if not reversed, iterate over this whole chunk with normal iterator.
            //if reversed, basically replicate this chunk, but use iterator over reversed list.  just toggle between the two, but need make sure
            //starts from player before person who played the reverse card
            //Alternatively, rework structure of players into circular array list
            //if(!isReversed){
                Iterator<Player> playerIterator = players.iterator();
                while(playerIterator.hasNext()){
                    Player cur = playerIterator.next();
                    /*System.out.println("");
                    System.out.println(discard.getDeck().get(discard.getDeck().size()-1).getColor() + " " + discard.getDeck().get(discard.getDeck().size()-1).getType());
                    System.out.println("");*/
                    if(!needDraw) {
                        cur.play(deck.getDeck(), discard.getDeck());
                        if(curType.equals(CardType.DRAW2.getType()) || curType.equals(CardType.DRAW4.getType()) || curType.equals(CardType.SKIP.getType()) /*|| curType.equals(CardType.REVERSE.getType())*/){
                            needDraw = true;
                        }
                    }
                    else if(needDraw){
                        if(curType.equals(CardType.DRAW2.getType())) {
                            cur.draw(deck.getDeck(), discard.getDeck(), 2);
                            System.out.println("PLAYER DREW TWO CARDS");
                        }
                        else if(curType.equals(CardType.DRAW4.getType())) {
                            cur.draw(deck.getDeck(), discard.getDeck(), 4);
                            System.out.println("PLAYER DREW FOUR CARDS");
                        }
                        else if(curType.equals(CardType.SKIP.getType())){ //Doesn't quite have as much to do with needing to draw, but it's still an effect card
                            System.out.println("PLAYER SKIPPED");
                        }
                        /*else if(curType.equals(CardType.REVERSE.getType())){
                            System.out.println("REVERSED (if it were actually implemented properly)");
                            //curPlayer = cur;
                            /*isReversed = !isReversed;
                            if(isReversed) {
                                players.clear();
                                for (int i = 0; i < reversePlayers.size() - 1; i++) {
                                    players.add(i, reversePlayers.get(i));
                                }
                                Iterator<Player> reverseIterator = players.iterator();
                                while(!reverseIterator.next().equals(curPlayer)){
                                    reverseIterator.next();
                                    System.out.println("Finding correct player");
                                }

                            }
                            else if(!isReversed){

                            }
                        }*/
                        needDraw = false;
                    }
                    System.out.println(cur.toString());
                    cur.getHand();
                    System.out.println("\n" + curColor + " " + curType + "\n");
                    if(curColor.equals(CardColor.ALL.getColor())) { //SORTA FIXES BUG WHERE AI WOULDN'T SET NEW COLOR AFTER PLAYING WILD IN CASE OF NOT BEING ABLE TO PLAY
                        setRandomColor();                           //AFTER WILD WAS PREVIOUSLY PLAYED AND DIDN'T HAVE COLOR, BUT DID HAVE A WILD
                        System.out.println("Changed color to: " + curColor);
                    }
                    gameOver = gameOver(players);

                    //if(curType.equals(CardType.REVERSE.getType())){

                    //}
                }
            //}
            //gameOver = gameOver(players);
        }

        //System.out.println(shuffleCount);
        // System.out.println(shufCount);
        //deck.viewDeck();
    }

    public static void setRandomColor(){
        double color = Math.random();
        if(0 < color && color <= .25)
            curColor = "red";
        else if(.25 < color && color <=.50)
            curColor = "blue";
        else if(.50 < color && color <= .75)
            curColor = "yellow";
        else
            curColor = "green";
    }

    public static boolean gameOver(ArrayList<Player> players){
        boolean over = false;
        Iterator<Player> playerIterator = players.iterator();
        while(playerIterator.hasNext()){
            Player cur = playerIterator.next();
            if(cur.getIsWinner())
                over = true;
        }
        return over;
    }

}
