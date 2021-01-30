package app;

import java.io.Console;

public class Main {

    public static final boolean DEBUG = false;

    public static void main(String[] args) {
        Console console = System.console();
        if (console == null) {
            System.err.println("No console");
            return;
        }

        Application application = null;
        try {
            application = new Application(console);
            application.run();
        } catch (Throwable t) {
            if (DEBUG) {
                t.printStackTrace();
            } else {
                console.printf("Unable to start program: %s\n", t.getMessage());
            }
        } finally {
            if (application != null) {
                application.close();
            }

            System.out.print("\nPress any key to continue...");
            console.readLine();
        }
    }
}
