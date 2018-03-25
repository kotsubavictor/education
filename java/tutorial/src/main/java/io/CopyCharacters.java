package io;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @see FileReader
 */
public class CopyCharacters {
    public static void main(String[] args) throws IOException {

        FileReader inputStream = null;
        FileWriter outputStream = null;

        try {
            inputStream = new FileReader("./tutorial/src/main/resources/io/xanadu.txt");
            outputStream = new FileWriter("./tutorial/src/main/resources/io/characteroutput.txt");

            int c;
            while ((c = inputStream.read()) != -1) {
                outputStream.write(c);
            }
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            if (outputStream != null) {
                outputStream.close();
            }
        }
    }
}
