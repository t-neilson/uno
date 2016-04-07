package sample;

/**
 * Created by tneilson on 1/7/2016.
 */
public enum CardType {
    ZERO("0"), ONE("1"), TWO("2"), THREE("3"), FOUR("4"), FIVE("5"), SIX("6"), SEVEN("7"), EIGHT("8"), NINE("9"), SKIP("skip"), REVERSE("reverse"), DRAW2("draw2"), DRAW4("draw4"),  WILD("wild");

    private String cardType;

    CardType(String cardType){
        this.cardType = cardType;
    }

    public String getType(){
        return this.cardType;
    }
}
