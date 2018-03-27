package concurrency;

public class JoinThreads {
    public static void main(String[] args) throws InterruptedException {
        HelloRunnable2 a = new HelloRunnable2(1000, "1");
        HelloRunnable2 b = new HelloRunnable2(3000, "2");
        a.setDaemon(true);
        b.setDaemon(true);
        a.start();
        b.start();
        a.join();
//        b.join();
    }
}

class HelloRunnable2 extends Thread {

    private int wait;
    private String message;

    public HelloRunnable2(int wait, String message) {
        this.wait = wait;
        this.message = "Hello from a thread!" + message;
    }

    public void run() {
        try {
            Thread.sleep(wait);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(message);
    }
}