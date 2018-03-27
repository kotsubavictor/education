package nio;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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




        System.out.println("");
    }
}
