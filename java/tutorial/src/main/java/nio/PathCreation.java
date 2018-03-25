package nio;

import java.io.IOException;
import java.net.URI;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PathCreation {
    public static void main(String[] args) throws IOException{
        Path p1 = Paths.get("/tmp/foo");
        Path p2 = Paths.get("tmp/foo");
        Path p3 = Paths.get(URI.create("file:///Users/joe/FileTest.java"));
        Path p4 = FileSystems.getDefault().getPath("/users/sally");
        Path p5 = Paths.get(System.getProperty("user.home"),"logs", "foo.log");

        System.out.println(p1);
        System.out.println(p2);
        System.out.println(p2.toAbsolutePath());
        System.out.println(p3);
        System.out.println(p4);
        System.out.println(p5);

        System.out.println("\n-------------------\n");

        Path path = Paths.get("/home/joe/foo");
        System.out.format("toString: %s%n", path.toString());
        System.out.format("getFileName: %s%n", path.getFileName());
        System.out.format("getName(0): %s%n", path.getName(0));
        System.out.format("getNameCount: %d%n", path.getNameCount());
        System.out.format("subpath(0,2): %s%n", path.subpath(0,2));
        System.out.format("getParent: %s%n", path.getParent());
        System.out.format("getRoot: %s%n", path.getRoot());

        System.out.println("\n-------------------\n");

        path = Paths.get("sally/bar");
        System.out.format("toString: %s%n", path.toString());
        System.out.format("getFileName: %s%n", path.getFileName());
        System.out.format("getName(0): %s%n", path.getName(0));
        System.out.format("getNameCount: %d%n", path.getNameCount());
        System.out.format("subpath(0,2): %s%n", path.subpath(0,2));
        System.out.format("getParent: %s%n", path.getParent());
        System.out.format("getRoot: %s%n", path.getRoot());

        System.out.println("\n---------  Relative path ----------\n");

        p1 = Paths.get("home");
        p3 = Paths.get("home/sally/bar");
        System.out.println(p1.relativize(p3));
        System.out.println(p3.relativize(p1));


        System.out.println("\n---------  eQUALS path ----------\n");

        p1 = Paths.get("home/sally/bar/../bar");
        p3 = Paths.get("home/sally/bar");
        System.out.println(p1.equals(p3));
        System.out.println(p1.normalize().equals(p3));

        System.out.println("\n---------  iterator path ----------\n");

        p1 = Paths.get("home/sally/bar/../bar");
        p3 = Paths.get("home/sally/bar");

        p1.forEach(path1 -> System.out.println(path1));
        System.out.println("-");
        p3.forEach(path1 -> System.out.println(path1));

        System.out.println("\n--------- Normalizing path ----------\n");

        path = Paths.get("/home/test/./../sally/bar");
        System.out.println(path.normalize());

        System.out.println("\n--------- View of normalized path----------\n");

        path = Paths.get("/home/test/./../sally/bar");
        System.out.println(path.toAbsolutePath());
        System.out.println(path.toUri());
        System.out.println(path.normalize().toAbsolutePath());
        System.out.println(path.normalize().toUri());
        System.out.println(path.toRealPath());
    }
}
