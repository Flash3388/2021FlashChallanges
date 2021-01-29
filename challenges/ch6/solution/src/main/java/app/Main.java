package app;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Main {

    private static final byte[] INFECTED_MAGIC = {
            70, 108, 97, 115, 104, 51, 51, 56, 56
    };

    public static void main(String[] args) throws IOException {
        File folder = new File(args[0]);
        for (File file : folder.listFiles()) {
            if (doesContainSequence(file, INFECTED_MAGIC)) {
                System.out.println(file.getName());
            }
        }
    }

    private static boolean doesContainSequence(File file, byte[] sequence) throws IOException {
        int position = 0;
        boolean found = false;

        try (InputStream inputStream = new FileInputStream(file)) {
            int ch;
            while ((ch = inputStream.read()) != -1) {
                if (ch == sequence[position]) {
                    position++;
                    if (position == sequence.length) {
                        found = true;
                        break;
                    }
                } else {
                    position = 0;
                }
            }
        }

        return found;
    }
}
