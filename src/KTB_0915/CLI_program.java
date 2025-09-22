package KTB_0915;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CLI_program {
    public static final Scanner scanner = new Scanner(System.in);
    public static final OrderManager orderManager = new OrderManager();
    public static final List<Beverage> orderMenus = new ArrayList<Beverage>();
    public static int total = 0;

    enum OrderOption { YES(1), NO(2), PAY(3);
        private final int value;
        OrderOption(int value){ this.value = value; }
        public int getValue(){ return value; }
    }

    public static void main(String[] args) {
        // 두 명의 손님이 동시에 주문하는 시뮬레이션
        Thread kiosk = new Thread(() -> processOrderSim("키오스크"));
        Thread pos = new Thread(() -> processOrderSim("메인POS"));

        kiosk.start();
        pos.start();

        try {
            kiosk.join();
            pos.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        processPayment();
    }

    // 스레드용 주문 시뮬레이션
    private static void processOrderSim(String source) {
        System.out.println(source + " 주문 시작");
        // 간단하게 랜덤 주문 시나리오 (여기서 실제 scanner 없이 자동 주문)
        int menuNum = (int)(Math.random()*6)+1; // 1~6 메뉴 랜덤 선택
        int addCount = (int)(Math.random()*2);  // 0~1 추가 수량

        Beverage beverage = orderManager.getMenu(menuNum);
        addToOrder(beverage, addCount);

        // 예: 커피라면 샷 랜덤, 디카페인 랜덤
        if(menuNum < 5) {
            Coffee coffee = new Coffee(beverage.getMenuName(), beverage.getPrice());
            if(Math.random()>0.5) coffee.addShot(1);
            if(Math.random()>0.5) coffee.setDecaf(true);
            coffee.setPrice(coffee.getPrice() + coffee.getShotPrice() + coffee.getDecafPrice());
            addToOrder(coffee, addCount);
        }

        System.out.println(source + " 주문 완료: " + beverage.getMenuName() + ", 수량 " + (addCount+1));
    }

    // synchronized로 스레드 안전하게 주문 추가
    private static synchronized void addToOrder(Beverage beverage, int addCount) {
        for (int i = 0; i <= addCount; i++) {
            orderMenus.add(beverage);
        }
    }

    private static synchronized void processPayment() {
        if (orderMenus.isEmpty()) {
            System.out.println("결제할 항목이 존재하지 않습니다.");
            return;
        }
        total = 0;
        for (Beverage b : orderMenus) {
            total += b.getPrice();
        }
        System.out.println("총 결제 금액: " + total + "원");
    }

//    public static void main(String[] args){
//        processOrder();
//    }
//
//    private static void processOrder(){
//        boolean isOrdering = true;
//        while(isOrdering){
//            System.out.println("주문하시겠습니까? 네(1) 아니오(2) 결제(3)");
//
//            int orderAnswer = scanner.nextInt();
//
//            switch (orderAnswer){
//                case 1:
//                    int menuNum = processMenuSelcet();
//                    if(menuNum == -1) continue;
//                    if(menuNum < 5){
//                        processCoffeeOrder(menuNum);
//                    } else {
//                        processBeverageOrder(menuNum);
//                    }
//                    break;
//                case 2:
//                    isOrdering = false;
//                    break;
//                case 3:
//                    processPayment();
//                    isOrdering = false;
//                    break;
//                default:
//                    System.out.println("1번, 2번, 3번만 선택 가능합니다.");
//            }
//        }
//    }
//
//    public static int processMenuSelcet() {
//        System.out.println("주문할 메뉴의 번호를 입력해주세요.");
//        int menuNum = scanner.nextInt();
//
//        if (menuNum < 1 || menuNum > 6) {
//            System.out.println("존재하지 않는 메뉴입니다. 주문을 다시 진행해주세요.");
//            return -1;
//        }
//        return menuNum;
//    }
//
//    private static void processPayment() {
//        if (orderMenus.isEmpty()) {
//            System.out.println("결제할 항목이 존재하지 않습니다.");
//            return;
//        }
//
//        for (Beverage b : orderMenus) {
//            total += b.getPrice();
//        }
//        System.out.println("결제할 금액: " + total + "원입니다.");
//    }
//
//    public static void processCoffeeOrder(int menuNum){
//        int shotCount = 0;
//        boolean isDecaf = false;
//        int addCount = 0;
//
//
//        System.out.println("샷을 추가하시겠습니까? 네(1) 아니오(2)");
//        int addShot = scanner.nextInt();
//
//        if (addShot == 1) {
//            while (true) {
//                System.out.println("샷 수량을 입력해주세요. 최대 4샷까지 가능합니다.");
//                shotCount = scanner.nextInt();
//
//                if (shotCount >= 1 && shotCount <= 4) {
//                    break; // 올바른 입력 → 반복 종료
//                } else {
//                    System.out.println("잘못된 입력입니다. 1~4 사이의 값을 입력해주세요.");
//                }
//            }
//        }
//
//
//        // 디카페인 여부
//        System.out.println("디카페인 변경하시겠습니까? 500원의 추가 요금이 발생합니다. 네(1) 아니오(2)");
//        int decaf = scanner.nextInt();
//        if (decaf == 1) {
//            System.out.println("디카페인으로 변경되셨습니다.");
//            isDecaf = true;
//        }
//
//        // 수량 추가
//        addCount = askAdditionalCount();
//
//        // 객체 생성 및 장바구니 담기
//        if (isDecaf) {
//            Decaf orderDecaf = new Decaf(orderManager.getMenu(menuNum).getMenuName(), orderManager.getMenu(menuNum).getPrice());
//            if (shotCount > 0) orderDecaf.addShot(shotCount);
//            orderDecaf.setPrice(orderDecaf.getPrice() + orderDecaf.getShotPrice() + orderDecaf.getDecafPrice());
//
//            addToOrder(orderDecaf, addCount);
//        } else {
//            Coffee orderCoffee = new Coffee(orderManager.getMenu(menuNum).getMenuName(),
//                    orderManager.getMenu(menuNum).getPrice());
//            if (shotCount > 0) orderCoffee.addShot(shotCount);
//            orderCoffee.setPrice(orderCoffee.getPrice() + orderCoffee.getShotPrice());
//
//            addToOrder(orderCoffee, addCount);
//        }
//    }
//
//    private static void processBeverageOrder(int menuNum) {
//        int addCount = askAdditionalCount();
//        Beverage orderBeverage = orderManager.getMenu(menuNum);
//        addToOrder(orderBeverage, addCount);
//    }
//
//    private static int askAdditionalCount() {
//        System.out.println("수량을 추가하시겠습니까? 네(1) 아니오(2)");
//        int addAnswer = scanner.nextInt();
//        if (addAnswer == 1) {
//            System.out.println("추가하실 수량을 입력해주세요.");
//            return scanner.nextInt();
//        }
//        return 0;
//    }
//
//    private static void addToOrder(Beverage beverage, int addCount) {
//        for (int i = 0; i <= addCount; i++) {
//            orderMenus.add(beverage);
//        }
//    }
}
