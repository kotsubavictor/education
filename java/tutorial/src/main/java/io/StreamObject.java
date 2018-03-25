package io;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;

public class StreamObject {
    static final String dataFile = "./tutorial/src/main/resources/io/objectstream";

    public static void main(String[] args) throws IOException, URISyntaxException, ClassNotFoundException {
        try (ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(
                new FileOutputStream(dataFile)));) {
            out.writeObject(new URI("https://localhost:12345"));
        }

        try (ObjectInputStream in = new ObjectInputStream(new
                BufferedInputStream(new FileInputStream(dataFile)));) {
            boolean read = true;
            Object data = null;
            while (read) {
                data = in.readObject();
                read = data != null;
                System.out.println("Object: " + data);
            }
        } catch (EOFException e) {
//            end of file
        }
    }
}
