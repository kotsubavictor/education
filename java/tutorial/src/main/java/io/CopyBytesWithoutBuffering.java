package io;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @see FileInputStream
 */
public class CopyBytesWithoutBuffering {
    public static void main(String[] args) throws IOException {
        copy1();
        copy2();
    }

    private static void copy1() throws IOException {
        FileInputStream in = null;
        FileOutputStream out = null;
        try {
            in = new FileInputStream("./tutorial/src/main/resources/io/xanadu.txt");
            out = new FileOutputStream("./tutorial/src/main/resources/io/outagain1.txt");
            int c;

            while ((c = in.read()) != -1) {
                out.write(c);
            }
        } finally {
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
        }
    }

    private static void copy2() throws IOException {
        try (
                FileInputStream in = new FileInputStream("./tutorial/src/main/resources/io/xanadu.txt");
                FileOutputStream out = new FileOutputStream("./tutorial/src/main/resources/io/outagain2.txt");
        ) {
            int c;
            while ((c = in.read()) != -1) {
                out.write(c);
            }
        } finally {
            // nothing to close
        }
    }
}
