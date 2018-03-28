package concurrency;

import java.util.concurrent.atomic.AtomicLong;

public class SynchronizedOp {
    public static void main(String[] args) throws InterruptedException {
        final int amount = 10000000;
        SynchronizedCounter counter = new SynchronizedCounter();
        NonSynchronizedCounter nonSynchronizedCounter = new NonSynchronizedCounter();
        VolatileCounter volatileCounter = new VolatileCounter();


        Thread a = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < amount; i++) {
                    counter.increment();
                }
            }
        });
        Thread b = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < amount; i++) {
                    counter.increment();
                }
            }
        });

        Thread c = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < amount; i++) {
                    nonSynchronizedCounter.increment();
                }
            }
        });
        Thread d = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < amount; i++) {
                    nonSynchronizedCounter.increment();
                }
            }
        });

        Thread e = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < amount; i++) {
                    volatileCounter.increment();
                }
            }
        });
        Thread f = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < amount; i++) {
                    volatileCounter.increment();
                }
            }
        });

        a.start();
        b.start();
        c.start();
        d.start();
        e.start();
        f.start();

        a.join();
        b.join();
        c.join();
        d.join();
        e.join();
        f.join();

        System.out.println(counter.value());
        System.out.println(nonSynchronizedCounter.value());
//        will not work, because only one thread is allowed to write value
        System.out.println("will not work, because only one thread is allowed to write value");
        System.out.println(volatileCounter.value());
    }
}

class SynchronizedCounter {
    private int c = 0;

    public synchronized void increment() {
        c++;
    }

    public synchronized void decrement() {
        c--;
    }

    public synchronized int value() {
        return c;
    }
}

class NonSynchronizedCounter {
    private int c = 0;

    public void increment() {
        c++;
    }

    public void decrement() {
        c--;
    }

    public int value() {
        return c;
    }
}


/**
 * volatile is Not Always Enough
 * Even if the volatile keyword guarantees that all reads of a volatile variable are read directly
 * from main memory, and all writes to a volatile variable are written directly to main memory,
 * there are still situations where it is not enough to declare a variable volatile.
 * <p>
 * In the situation explained earlier where only Thread 1 writes to the shared counter variable,
 * declaring the counter variable volatile is enough to make sure that Thread 2 always sees the
 * latest written value.
 * <p>
 * In fact, multiple threads could even be writing to a shared volatile variable, and still
 * have the correct value stored in main memory, if the new value written to the variable
 * does not depend on its previous value. In other words, if a thread writing a value to
 * the shared volatile variable does not first need to read its value to figure out its next value.
 * <p>
 * As soon as a thread needs to first read the value of a volatile variable, and based
 * on that value generate a new value for the shared volatile variable, a volatile variable
 * is no longer enough to guarantee correct visibility. The short time gap in between the reading
 * of the volatile variable and the writing of its new value, creates an race condition where multiple
 * threads might read the same value of the volatile variable, generate a new value for the variable,
 * and when writing the value back to main memory - overwrite each other's values.
 * <p>
 * The situation where multiple threads are incrementing the same counter is exactly such a
 * situation where a volatile variable is not enough. The following sections explain this case
 * in more detail.
 * <p>
 * Imagine if Thread 1 reads a shared counter variable with the value 0 into its CPU cache,
 * increment it to 1 and not write the changed value back into main memory. Thread 2 could then
 * read the same counter variable from main memory where the value of the variable is still 0,
 * into its own CPU cache. Thread 2 could then also increment the counter to 1, and also not write
 * it back to main memory.
 */
class VolatileCounter {
    private volatile AtomicLong c = new AtomicLong(0);

    public void increment() {
        c.incrementAndGet();
    }

    public void decrement() {
        c.decrementAndGet();
    }

    public long value() {
        return c.get();
    }
}