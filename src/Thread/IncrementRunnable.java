package Thread;

public class IncrementRunnable implements Runnable{
    private final Counter counter;

    public IncrementRunnable(Counter counter){
        this.counter = counter;
    }
    @Override
    public void run(){
        for(int i = 0; i < 1000; i++){
            // counter.increment();
            counter.atomicIncrement();
        }
    }
}
