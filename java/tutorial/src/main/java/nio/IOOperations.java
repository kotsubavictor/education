package nio;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.*;
import java.util.Set;

public class IOOperations {
    public static void main(String[] args) throws IOException{
        Path file = Paths.get("./tutorial/src/main/resources/nio/usnumbers.txt");
        Path file_new = Paths.get("./tutorial/src/main/resources/nio/usnumbers_new.txt");
        Path file_a = Paths.get("./tutorial/src/main/resources/nio/usnumbers_a.txt");
        Path file_b = Paths.get("./tutorial/src/main/resources/nio/usnumbers_b.txt");
        System.out.println("file " + file);

        byte[] fileArray;
        fileArray = Files.readAllBytes(file);
        System.out.println(fileArray);

        Files.write(file_new, fileArray);

        try (BufferedReader reader = Files.newBufferedReader(file)) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }

        String s = "xzvxvxcdavx dadf adfds ad \n FA \n sdfds sdaf daf \n asf dasf \n";
        try (BufferedWriter writer = Files.newBufferedWriter(file_a)) {
            writer.write(s, 0, s.length());
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }

        try (InputStream in = Files.newInputStream(file);
             BufferedReader reader =
                     new BufferedReader(new InputStreamReader(in))) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException x) {
            System.err.println(x);
        }

        byte[] data = s.getBytes();
        try (OutputStream out = new BufferedOutputStream(
                Files.newOutputStream(file_b))) {
            out.write(data, 0, data.length);
        } catch (IOException x) {
            System.err.println(x);
        }

        // Defaults to READ
        try (SeekableByteChannel sbc = Files.newByteChannel(file)) {
            ByteBuffer buf = ByteBuffer.allocate(12);

            // Read the bytes with the proper encoding for this platform.  If
            // you skip this step, you might see something that looks like
            // Chinese characters when you expect Latin-style characters.
            String encoding = System.getProperty("file.encoding");
            Charset charset = Charset.forName(encoding);
            int readed = 0;
            while ((readed = sbc.read(buf)) > 0) {
                buf.rewind().limit(readed);
                System.out.print(charset.decode(buf));
                System.out.print("||");
                buf.flip();
            }
        } catch (IOException x) {
            System.out.println("caught exception: " + x);
        }

        try {
            Path tempFile = Files.createTempFile(null, ".myapp");
            System.out.format("The temporary file" +
                    " has been created: %s%n", tempFile)
            ;
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }

        System.out.println("---");


    }
}
