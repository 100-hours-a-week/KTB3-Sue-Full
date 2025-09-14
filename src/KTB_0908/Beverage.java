package KTB_0908;

import java.lang.String;

public class Beverage {
    protected String menuName;
    protected int price;

    public Beverage(String menuName, int price){
        this.menuName = menuName;
        this.price = price;
    }

    public String getMenuName(){
        return this.menuName;
    }

    public int getPrice(){
        return this.price;
    }

    public void setPrice(int price){
        this.price = price;
    }
}
