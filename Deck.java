package sample;
import java.util.*;

/**
 * Created by tneilson on 1/7/2016.
 */
public class Deck {

    private ArrayList deck;

    public Deck(){

        this.deck = new ArrayList<Card>();
        for(int i = 0; i < 13; i++){
            CardType type = CardType.values()[i];

            for(int j = 0; j<4; j++) {
                CardColor color = CardColor.values()[j];
                Card card = new Card(type, color);
                this.deck.add(card);
                if(i > 0){
                    Card secondSet = new Card(type, color);
                    this.deck.add(secondSet);
                }
                if(i > 8 && j > 2){
                    this.deck.add(new Card(CardType.WILD, CardColor.ALL));
                    this.deck.add(new Card(CardType.DRAW4, CardColor.ALL));
                }
            }
        }

        Collections.shuffle(deck);

    }

    public Deck(int size){
        this.deck = new ArrayList<Card>(size);
    }

   /*public void viewDeck(){
        int numWild = 0;
        Iterator<Card> cardIterator = deck.iterator();
        while(cardIterator.hasNext()){
            Card cur = cardIterator.next();
            if(cur.getType().equals(CardType.NINE))
                numWild++;
            System.out.println(cur.getType() + " " + cur.getColor());
        }
        System.out.println(deck.size());
        System.out.println(numWild);
    }*/

    public ArrayList<Card> getDeck(){
        return this.deck;
    }
}
