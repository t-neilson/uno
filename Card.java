package sample;

import java.util.*;

/**
 * Created by tneilson on 1/7/2016.
 */
public class Card {

    private CardType type;
    private CardColor color;

    public Card(CardType type, CardColor color){
        this.type = type;
        this.color = color;
    }

    public CardType getType(){
        return this.type;
    }

    public void setType(CardType type){
        this.type = type;
    }

    public CardColor getColor(){
        return this.color;
    }

    public void setColor(CardColor color){
        this.color = color;
    }
}
