package concurrency;

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

class VolatileCounter {
    private volatile int c = 0;

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