package Thread;

public class Main {
    public static void main(String[] args){
//        DrivingCar car = new DrivingCar();
//        DrivingCar car2 = new DrivingCar();
//        car.start();
//        car2.start();

        Counter counter = new Counter();
        IncrementThread thread1 = new IncrementThread(counter);
        IncrementThread thread2 = new IncrementThread(counter);

        try {
            thread1.start();
            thread2.start();

            thread1.join();
            thread2.join();
        }catch(InterruptedException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("value: " + counter.getValue());
    }
}
