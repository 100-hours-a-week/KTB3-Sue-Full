package KTB_0915;

import java.lang.String;
public class Coffee extends Beverage{
    protected int shot = 0;
    protected int shotPrice = 0;
    private boolean isDecaf = false;
    public Coffee(String menuName, int price){
        super(menuName, price);
    }

    public void addShot(int shot){
        this.shot = shot;
        this.shotPrice = 500 * this.shot;
    }

    public void setDecaf(boolean isDecaf){
        this.isDecaf = isDecaf;
    }

    public int getShotPrice(){
        if(isDecaf) return this.shotPrice;
        return 0;
    }

    public int getDecafPrice() { return 500; }
}