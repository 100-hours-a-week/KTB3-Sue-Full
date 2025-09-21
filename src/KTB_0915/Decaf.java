package KTB_0915;
import java.lang.String;

public class Decaf extends Coffee{
    private int decafPrice = 500;

    public Decaf(String menuName, int price){
        super(menuName, price);
    }

    public int getDecafPrice(){
        return this.decafPrice;
    }

}
