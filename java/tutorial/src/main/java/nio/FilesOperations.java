package nio;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;

public class FilesOperations {
    public static void main(String[] args) throws IOException{
        Path file = Paths.get("./tutorial/src/main/resources/nio/usnumbers.txt");
        Path file1 = Paths.get("./tutorial/src/main/resources/nio/../nio/usnumbers.txt");
        Path fileToBeDeleted = Paths.get("./tutorial/src/main/resources/nio/tobedeleted.txt");


        System.out.println("file " + file);
        System.out.println("file1 " + file1);

        System.out.println("Files.isRegularFile(file) " + Files.isRegularFile(file));
        System.out.println("Files.isReadable(file) " + Files.isReadable(file));
        System.out.println("Files.isExecutable(file) " + Files.isExecutable(file));
        System.out.println("Files.isSameFile(file, file1) " + Files.isSameFile(file, file1));
        System.out.println("file.equals(file1) " + file.equals(file1));
        System.out.println("Files.exists(file) " + Files.exists(file));


        try {
            Files.delete(fileToBeDeleted);
        } catch (NoSuchFileException x) {
            System.err.format("%s: no such" + " file or directory%n", fileToBeDeleted);
        } catch (DirectoryNotEmptyException x) {
            System.err.format("%s not empty%n", fileToBeDeleted);
        } catch (IOException x) {
            // File permission problems are caught here.
            System.err.println(x);
        }

        Files.copy(file, fileToBeDeleted, StandardCopyOption.REPLACE_EXISTING);

        System.out.println("");
    }
}
