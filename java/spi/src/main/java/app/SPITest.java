package app;

import spi.SPInterface;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * Created by Viktor on 20.09.2015.
 */
public class SPITest {

    public static void main(String[] args) {
        ServiceLoader<SPInterface> loader = ServiceLoader.load(SPInterface.class);
        Iterator<SPInterface> it = loader.iterator();
        while (it.hasNext()) {
            System.out.println(it.next().saySomething());
        }
    }
}
