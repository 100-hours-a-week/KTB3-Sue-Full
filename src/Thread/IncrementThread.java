package Thread;

public class IncrementThread extends Thread{
    private final Counter counter;

    public IncrementThread(Counter counter){
        this.counter = counter;
    }
    @Override
    public void run(){
        for(int i = 0; i < 1000; i++){
            counter.increment();
        }
    }
}
