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

        Runnable task1 = new IncrementRunnable(counter);
        Runnable task2 = new IncrementRunnable(counter);
        Thread thread3 = new Thread(task1);
        Thread thread4 = new Thread(task2);

        try {
            thread1.start();
            thread2.start();

            thread3.start();
            thread4.start();

            thread1.join();
            thread2.join();
            thread3.join();
            thread4.join();
        }catch(InterruptedException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("value: " + counter.getValue());
    }
}
