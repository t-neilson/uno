package sample;

/**
 * Created by tneilson on 1/7/2016.
 */
public enum CardColor {
    BLUE("blue"), RED("red"), YELLOW("yellow"), GREEN("green"), ALL("all");

    private String cardColor;

    CardColor(String cardColor){
        this.cardColor = cardColor;
    }

    public String getColor(){
        return this.cardColor;
    }
}
