package sample;
import java.util.*;

/**
 * Created by tneilson on 1/8/2016.
 */
public class Player {

//    private boolean isTurn;
//    private boolean hasCards; //Arguably useless, since have numCards, but we'll see.
//    private boolean canAct; //Might be useless?
    private boolean isWinner;
    private int numCards;
    private boolean hasPlayed = false;

    private ArrayList<Card> hand;

    public Player(){
        this.hand = new ArrayList<>();
        this.isWinner = false;
        this.numCards = 0;
        //maybe have draw cards when created?  fill out hand, etc.
    }

    public Player(int numCards, ArrayList<Card> deck, ArrayList<Card> discard){
        this.hand = new ArrayList<>();
        this.isWinner = false;
        this.numCards = 0;
        draw(deck, discard, numCards);
    }

//    public boolean getTurn(){
//        return this.isTurn;
//    }
//
//    public void setTurn(boolean isTurn){
//        this.isTurn = isTurn;
//    }

//    public boolean getHasCards(){
//        return this.hasCards;
//    }

//    public void setHasCards(boolean hasCards){
//        this.hasCards = hasCards;
//    }

//    public boolean getAct(){
//        return this.canAct;
//    }
//
//    public void setAct(boolean canAct){
//        this.canAct = canAct;
//    }

//    public void setHasPlayed(boolean hasPlayed){this.hasPlayed = hasPlayed;}

    public int getNumCards(){
        return this.numCards;
    }

//    public void setNumCards(int numCards){
//        this.numCards = numCards;
//    }

    public void getHand(){
        String view = "";
        Iterator<Card> handIterator = hand.iterator();
        while(handIterator.hasNext()){
            Card cur = handIterator.next();
            //System.out.println(cur.getType() + " " + cur.getColor());
            view = (view + " " + cur.getType() + " " + cur.getColor() + ",");
        }
        System.out.println(view);
    }

    public void play(ArrayList<Card> deck, ArrayList<Card> discard){
        //remove card from hand, update current card to act on for next player, decrement number of cards in hand
        //want to refine logic so that there are roughly the same number of each color available in hand, ie: 2 red, 2 blue, etc.
        //further refine to include logic such that maximize number of unique numbers and colors
        int numBlue = 0;
        int numRed = 0;
        int numYellow = 0;
        int numGreen = 0;
        this.hasPlayed = false;
        //Iterator<Card> handIterator = this.hand.iterator();
        for(Card cur : this.hand){ //probably wanna rework this whole thing so that it doesn't mess with wilds until rest of hand has been searched and deemed invalid
            //Card cur = handIterator.next();
            if(/*(cur.getType().equals(discard.get(discard.size() - 1).getType()) && !cur.getType().equals(CardType.WILD) && !cur.getType().equals(CardType.DRAW4)) ||*/ cur.getType().getType().equals(Uno.curType)){
                discard.add(cur);
                Uno.curColor = cur.getColor().getColor();
                Uno.curType = cur.getType().getType();
                this.hand.remove(cur);
                this.numCards--;
                this.hasPlayed = true;

                break;
            }
            else if(cur.getColor().equals(discard.get(discard.size()-1).getColor()) || cur.getColor().getColor().equals(Uno.curColor)){
                discard.add(cur);
                Uno.curColor = cur.getColor().getColor();
                Uno.curType = cur.getType().getType();
                this.hand.remove(cur);
                this.numCards--;
                this.hasPlayed = true;

                break;
            }
        }

        if(!this.hasPlayed){
            Iterator<Card> handIteratorMKII = this.hand.iterator();
            while(handIteratorMKII.hasNext()){ //similar situation as above with reworking at later date
                Card cur = handIteratorMKII.next();
                if(cur.getColor().equals(CardColor.ALL)){ //IF HIT SCENARIO WHERE ONE PLAYER CHANGES COLOR WITH WILD AND NEXT PLAYER DOESN'T HAVE THAT COLOR AND IS
                    //FORCED TO USE WILD, SECOND PLAYER DOESN'T CHANGE COLOR PROPERLY??
                    //WORKS PROPERLY IF NOT CALLED MULTIPLE TIMES WITHIN SAME CYCLE OF TURNS?? weird.
                    discard.add(cur);
                    this.hand.remove(cur);
                    this.numCards--;

                    //Iterator<Card> handIteratorMKIII = this.hand.iterator();
                    for(Card curMKII : this.hand){
                    //while(handIteratorMKIII.hasNext()){
                        //Card curMKII = handIteratorMKIII.next();
                        if(curMKII.getColor().equals(CardColor.BLUE))
                            numBlue++;
                        else if(curMKII.getColor().equals(CardColor.RED))
                            numRed++;
                        else if(curMKII.getColor().equals(CardColor.YELLOW))
                            numYellow++;
                        else if(curMKII.getColor().equals(CardColor.GREEN))
                            numGreen++;
                    }
                    int mostFreq = Math.max(numBlue, (Math.max(numRed, Math.max(numYellow, numGreen))));

                    if(mostFreq == numBlue)
                        Uno.curColor = "blue";
                    else if(mostFreq == numRed)
                        Uno.curColor = "red";
                    else if(mostFreq == numYellow)
                        Uno.curColor = "yellow";
                    else
                        Uno.curColor = "green";

                    Uno.curType = cur.getType().getType();
                    this.hasPlayed = true;

                    break;
                }
            }
        }
        if(!this.hasPlayed){
            this.draw(deck,discard,1);
            this.play(deck,discard);
        }

    }

    public void draw(ArrayList<Card> deck, ArrayList<Card> discard, int numCards){
        //update this player's hand with new card from pile, increment number of cards in hand
        //probably want to call this until can play, basically do while loop until hand has valid card
        //MIGHT BE AN ISSUE HERE IF REACT TO DRAW 2/4 AND LESS THAN 2/4 CARDS LEFT IN DECK
        if(deck.size()!=0) {
//            for (int i = 0; i < numCards; i++) {
//                this.hand.add(deck.remove(0));
//                this.numCards++;
//            }
            try{ //Try to draw as many cards as specified
                while(numCards > 0){
                    this.hand.add(deck.remove(0));
                    this.numCards++;
                    numCards--;
                }
            }
            catch(Exception E){ //If we run into an issue with not having enough cards to draw being left in the deck, shuffle the discard first then draw
                System.out.println("~~~~Shuffling~~~~");
                Card temp = discard.remove(discard.size()-1);
                Collections.shuffle(discard);
                ArrayList<Card> tempDeck = new ArrayList<>(discard);
                ArrayList<Card> tempDiscard = new ArrayList<>();
                tempDiscard.add(temp);

                for(Card c : tempDeck){
                    Uno.deck.getDeck().add(c);
                }

                for(Card c : tempDiscard){
                    Uno.discard.getDeck().add(c);
                }

                Uno.shufCount++;
                this.draw(Uno.deck.getDeck(), Uno.discard.getDeck(), numCards);
            }
        }
        else{ //If no cards in deck, shuffle discard and draw from new deck
            System.out.println("...Shuffling...");
            Card temp = discard.remove(discard.size()-1);
            Collections.shuffle(discard);
            ArrayList<Card> tempDeck = new ArrayList<>(discard);
            ArrayList<Card> tempDiscard = new ArrayList<>();
            tempDiscard.add(temp);

            for(Card c : tempDeck){
                Uno.deck.getDeck().add(c);
            }

            for(Card c : tempDiscard){
                Uno.discard.getDeck().add(c);
            }

            Uno.shuffleCount++;
            this.draw(Uno.deck.getDeck(), Uno.discard.getDeck(), numCards);
        }
    }

    public boolean getIsWinner(){
        if(this.numCards == 0)
            this.isWinner = true;
        return this.isWinner;
    }
}
