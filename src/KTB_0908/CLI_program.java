package KTB_0908;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.List;

public class CLI_program {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);

        // 주문한 항목을 담아놓을 장바구니
        List<Beverage> orderMenus = new ArrayList<Beverage>();

        // 주문을 위한 클래스
        OrderManager order = new OrderManager();

        // 총 결제 금액
        int total = 0;

        boolean isOrdering = true;

        while(isOrdering){
            System.out.println("주문하시겠습니까? 네(1) 아니오(2) 결제(3)");

            int orderAnswer = scanner.nextInt();
            int menuNum = 0;
            int shotCount = 0;
            boolean isDecaf = false;
            int addCount = 0;

            if(orderAnswer == 2){
                // 주문을 그만할 때
                isOrdering = false;
                break;
            } else if(orderAnswer < 1 || orderAnswer > 3 ){
                // 예외 값 입력
                System.out.println("1번과 2번, 3번만 선택 가능합니다.");
                continue;
            } else if(orderAnswer == 3){
                // 결제할 때

                // 주문한 항목이 없을 때
                if(orderMenus.toArray().length == 0){
                    System.out.println("결제할 항목이 존재하지 않습니다.");
                    isOrdering = false;
                    break;
                } else {
                    // 주문한 항목이 있을 때
                    for(int i = 0; i < orderMenus.toArray().length; i++){
                        total += orderMenus.get(i).getPrice();
                    }
                    System.out.println("결제할 금액: " + total + "원입니다.");
                    return;
                }
            }


            System.out.println("주문할 메뉴의 번호를 입력해주세요.");
            menuNum = scanner.nextInt();

            // 존재하지 않는 메뉴 선택 시
            if(menuNum < 1 || menuNum > 6){
                System.out.println("존재하지 않는 메뉴입니다. 주문을 다시 진행해주세요.");
                continue;
            }

            // 커피 메뉴 선택 시
            if(menuNum <= 4){
                System.out.println("샷을 추가하시겠습니까? 네(1) 아니오(2)");

                // 샷 추가 여부
                int addShot = scanner.nextInt();

                if(addShot != 1 && addShot != 2){
                    System.out.println("1번과 2번만 선택 가능합니다.");
                    break;
                } else if(addShot == 1){
                    // 추가할 샷 수
                    System.out.println("샷 수량을 입력해주세요. 최대 4샷까지 가능합니다.");
                    shotCount = scanner.nextInt();
                    if(shotCount > 4){
                        System.out.println("최대로 추가 가능한 샷은 4샷입니다. 4샷 추가.");
                        shotCount = 4;
                    } else if(shotCount < 0){
                        System.out.println("샷을 추가하지 않습니다.");
                        shotCount = 0;
                    }

                }

                System.out.println("디카페인 변경하시겠습니까? 500원의 추가 요금이 발생합니다. 네(1) 아니오(2)");
                // 디카페인 변경 여부
                int decaf = scanner.nextInt();

                if(decaf > 2 || decaf < 0){
                    System.out.println("존재하지 않는 옵션입니다. 디카페인으로 변경되지 않습니다.");
                } else if (decaf == 1) {
                    System.out.println("디카페인으로 변경되셨습니다.");
                    isDecaf = true; // 디카페인 여부 확인 값
                }

                System.out.println("수량을 추가하시겠습니까? 네(1) 아니오(2)");
                // 수량 추가 여부
                int addAnswer = scanner.nextInt();

                if(addAnswer <= 0){
                    System.out.println("수량을 추가하지 않습니다.");
                } else if(addAnswer == 1){
                    System.out.println("추가하실 수량을 입력해주세요.");
                    // 추가할 수량
                    addCount = scanner.nextInt();
                } else {
                    // 추가 안 할 때
                    addCount = 0;
                }
            }

            // 커피 아닌 일반 메뉴
            if(menuNum == 5 || menuNum == 6) {
                System.out.println("수량을 추가하시겠습니까? 네(1) 아니오(2)");
                // 수량 추가 여부
                int addAnswer = scanner.nextInt();

                if(addAnswer <= 0){
                    System.out.println("수량을 추가하지 않습니다.");
                } else if(addAnswer == 1){
                    System.out.println("추가하실 수량을 입력해주세요.");
                    // 추가할 수량
                    addCount = scanner.nextInt();
                } else {
                    // 추가 안 할 때
                    addCount = 0;
                }

                // 일반 메뉴의 경우
                Beverage orderBeverage = order.getMenu(menuNum);
                for (int i = 0; i <= addCount; i++) {
                    orderMenus.add(orderBeverage);
                }
            } else {
                if(isDecaf) {
                    // 디카페인일 경우
                    Decaf orderDecaf = new Decaf(order.getMenu(menuNum).getMenuName(), order.getMenu(menuNum).getPrice());
                    // 샷 추가했을 경우
                    if(shotCount > 0){
                        orderDecaf.addShot(shotCount);
                    }

                    orderDecaf.setPrice(orderDecaf.getPrice() + orderDecaf.getShotPrice() + orderDecaf.getDecafPrice());
                    for (int i = 0; i <= addCount; i++) {
                        orderMenus.add(orderDecaf);
                    }

                } else {
                    // 커피 메뉴일 경우
                    Coffee orderCoffee = new Coffee(order.getMenu(menuNum).getMenuName(), order.getMenu(menuNum).getPrice());
                    // 샷 추가했을 경우
                    if(shotCount > 0){
                        orderCoffee.addShot(shotCount);
                    }
                    orderCoffee.setPrice(orderCoffee.getPrice() + orderCoffee.getShotPrice());
                    for (int i = 0; i <= addCount; i++) {
                        orderMenus.add(orderCoffee);
                    }
                }

            }

        }
    }
}
