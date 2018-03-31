package concurrency;

import java.util.Random;
import java.util.concurrent.*;

public class ExecutorsS {
    public static void main(String[] args) throws Exception {
        Executors.newCachedThreadPool();
        ScheduledExecutorService service = Executors.newScheduledThreadPool(10);
        Executors.newSingleThreadExecutor();
        Executors.newFixedThreadPool(10);
        Executors.newWorkStealingPool();
        Random rand = new Random();


        final ScheduledFuture<Integer> future = service.schedule(new Callable<Integer>() {
            private int i = 0;
            @Override
            public Integer call() throws Exception {
                System.out.println("push value = " + i);
                return i++;
            }
        }, 5, TimeUnit.SECONDS);

        System.out.println("gets value");
        System.out.println("pop value =" + future.get());
        service.shutdown();
    }
}
