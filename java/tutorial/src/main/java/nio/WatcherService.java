package nio;

import java.io.IOException;
import java.nio.file.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class WatcherService {
    public static void main(String[] args) {
        Path dir = Paths.get("./tutorial/src/main/resources");;
        try {
            WatchService watcher = FileSystems.getDefault().newWatchService();
            WatchKey key = dir.register(watcher,
                    StandardWatchEventKinds.ENTRY_CREATE,
                    StandardWatchEventKinds.ENTRY_DELETE,
                    StandardWatchEventKinds.ENTRY_MODIFY);
            for(;;) {
                key.pollEvents().forEach(new Consumer<WatchEvent<?>>() {
                    @Override
                    public void accept(WatchEvent<?> watchEvent) {
                        System.out.println(watchEvent);
                    }
                });
            }
        } catch (IOException x) {
            System.err.println(x);
        }
    }
}
