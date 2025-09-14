package KTB_0908;

import java.util.ArrayList;
import java.util.List;
public class OrderManager {
    private List<Beverage> orderList = new ArrayList<Beverage>();

    public OrderManager(){
        add();
        printMenu();
    }

    public void add(){
        Beverage americano = new Coffee("아메리카노", 3000);
        Beverage latte = new Coffee("카페라떼", 3500);
        Beverage mocha = new Coffee("카페모카", 4000);
        Beverage cappuccino = new Coffee("카푸치노", 4500);
        Beverage matchLatte = new Beverage("말차라떼", 5000);
        Beverage chocoLatte = new Beverage("초코라떼", 5000);

        orderList.add(americano);
        orderList.add(latte);
        orderList.add(mocha);
        orderList.add(cappuccino);
        orderList.add(matchLatte);
        orderList.add(chocoLatte);
    }

    public void printMenu(){
        System.out.println("=====메뉴판=====");
        for(int i = 0; i < orderList.toArray().length; i++){
            System.out.println((i + 1) + ". 메뉴: " + orderList.get(i).getMenuName() + " " + orderList.get(i).getPrice() + "원.");
        }
        System.out.println("==========");
    }

    public Beverage getMenu(int menuNum){
        return new Beverage(orderList.get(menuNum - 1).getMenuName(), orderList.get(menuNum - 1).getPrice());
    }

}
