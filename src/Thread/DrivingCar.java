package Thread;

public class DrivingCar extends Thread{
    @Override
    public void run(){
        System.out.println("자동차 주행 스레드 실행 중: " + Thread.currentThread().getName());
    }
}
