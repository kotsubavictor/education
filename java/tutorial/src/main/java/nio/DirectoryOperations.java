package nio;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Set;

public class DirectoryOperations {
    public static void main(String[] args) throws IOException {
        Iterable<Path> dirs = FileSystems.getDefault().getRootDirectories();
        for (Path name: dirs) {
            System.err.println(name);
        }

        Path dir = Paths.get("./tutorial/src/main/resources/nio/dir");
        Path directories = Paths.get("./tutorial/src/main/resources/nio/a/b");
        Set<PosixFilePermission> set = PosixFilePermissions.fromString("rwxrwxrwx");
        Files.createDirectory(dir, PosixFilePermissions.asFileAttribute(set));
        Files.createDirectories(directories);

        dir = Paths.get("/");
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
            for (Path file: stream) {
                System.out.println(file.getFileName());
            }
        } catch (IOException | DirectoryIteratorException x) {
            // IOException can never be thrown by the iteration.
            // In this snippet, it can only be thrown by newDirectoryStream.
            System.err.println(x);
        }
        System.out.println("-----------");
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir, "*i*")) {
            for (Path file: stream) {
                System.out.println(file.getFileName());
            }
        } catch (IOException | DirectoryIteratorException x) {
            // IOException can never be thrown by the iteration.
            // In this snippet, it can only be thrown by newDirectoryStream.
            System.err.println(x);
        }
    }
}
