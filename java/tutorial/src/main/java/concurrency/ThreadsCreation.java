package concurrency;

public class ThreadsCreation {
    public static void main(String[] args) {
        new Thread(new HelloRunnable()).start();
        new HelloRunnable1().start();
    }
}

class HelloRunnable implements Runnable {

    public void run() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Hello from a thread!");
    }
}

class HelloRunnable1 extends Thread {
    @Override
    public void run() {
        super.run();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Hello from a thread1!");
    }
}