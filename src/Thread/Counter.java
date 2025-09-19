package Thread;

import java.util.concurrent.atomic.AtomicInteger;

public class Counter {
    private int value;
    private AtomicInteger atomicValue = new AtomicInteger(0);

    synchronized public void increment(){
        value++;
    }

    public void atomicIncrement(){
        int newValue = atomicValue.incrementAndGet();
    }

    public int getValue(){
        return value;
    }
}
