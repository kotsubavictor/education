package nio;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.*;
import java.util.Set;

public class FilesMetadata {
    public static void main(String[] args) throws IOException{
        Path file = Paths.get("./tutorial/src/main/resources/nio/usnumbers.txt");
        System.out.println("file " + file);

        System.out.println("Files.isDirectory(file) " + Files.isDirectory(file));
        System.out.println("Files.isSymbolicLink(file) " + Files.isSymbolicLink(file));
        System.out.println("Files.isHidden(file) " + Files.isHidden(file));
        System.out.println("Files.getOwner(file) " + Files.getOwner(file));
        System.out.println("Files.getPosixFilePermissions(file) " + Files.getPosixFilePermissions(file));
        System.out.println("Files.readAttributes(file, BasicFileAttributes.class) " + Files.readAttributes(file, BasicFileAttributes.class));
        BasicFileAttributes attr = Files.readAttributes(file, BasicFileAttributes.class);

        System.out.println("creationTime: " + attr.creationTime());
        System.out.println("lastAccessTime: " + attr.lastAccessTime());
        System.out.println("lastModifiedTime: " + attr.lastModifiedTime());

        System.out.println("isDirectory: " + attr.isDirectory());
        System.out.println("isOther: " + attr.isOther());
        System.out.println("isRegularFile: " + attr.isRegularFile());
        System.out.println("isSymbolicLink: " + attr.isSymbolicLink());
        System.out.println("size: " + attr.size());

        PosixFileAttributes attr1 =
                Files.readAttributes(file, PosixFileAttributes.class);
        System.out.format("%s %s %s%n",
                attr1.owner().getName(),
                attr1.group().getName(),
                PosixFilePermissions.toString(attr1.permissions()));

        Set<PosixFilePermission> perms =
                PosixFilePermissions.fromString("rwxrwxrwx");
        FileAttribute<Set<PosixFilePermission>> attr2 =
                PosixFilePermissions.asFileAttribute(perms);
        Files.setPosixFilePermissions(file, perms);


        UserPrincipal owner = file.getFileSystem().getUserPrincipalLookupService()
                .lookupPrincipalByName("root");
        Files.setOwner(file, owner);

        GroupPrincipal group =
                file.getFileSystem().getUserPrincipalLookupService()
                        .lookupPrincipalByGroupName("root");
        Files.getFileAttributeView(file, PosixFileAttributeView.class)
                .setGroup(group);
    }
}
