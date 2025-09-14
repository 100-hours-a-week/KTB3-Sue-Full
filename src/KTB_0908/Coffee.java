package KTB_0908;

import java.lang.String;
public class Coffee extends Beverage{
    protected int shot = 0;
    protected int shotPrice = 0;
    public Coffee(String menuName, int price){
        super(menuName, price);
    }

    public void addShot(int shot){
        this.shot = shot;
        this.shotPrice = 500 * this.shot;
    }

    public int getShotPrice(){
        return this.shotPrice;
    }
}