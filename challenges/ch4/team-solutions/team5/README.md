```java
package app;

import app.db.Database;
import app.db.JdbcDatabase;
import app.secure.PasswordVerifier;
import app.storage.AesCipher;
import app.storage.CryptoException;
import app.storage.SecureStorage;
import app.storage.Storage;
import app.storage.StorageException;

import java.io.Console;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.Map;
import java.util.Scanner;

public class Application implements AutoCloseable {

    private final Storage mStorage;
    private final PasswordVerifier mPasswordVerifier;
    Scanner in;

    public Application() throws SQLException, ClassNotFoundException, CryptoException {

        Database database = JdbcDatabase.open(Paths.get("mdn.db"));
        AesCipher cipher = new AesCipher("keyyyyyyyyyyyyyyyyyyyyyyyyyyy");
        mStorage = new SecureStorage(database, cipher);
        in = new Scanner(System.in);
        mPasswordVerifier = new PasswordVerifier(mStorage);
    }

    public void run() throws StorageException {
        runMenu();
    }



    private void runMenu() {
        while (true) {
            System.out.printf("\nWelcome!\n\t1. Enter New\n\t2. Read All\n\t3. Exit\n");
            System.out.println("Enter request: ");
            String request = in.nextLine();

            try {
                int menuItem = Integer.parseInt(request);
                switch (menuItem) {
                    case 1:
                        System.out.println("Key: ");
                        String key = in.nextLine();;
                        System.out.println("Data: ");
                        String data = in.nextLine();
                        mStorage.addStateSecret(key, data);
                        break;
                    case 2:
                        Map<String, String> secrets = mStorage.getStateSecrets();
                        for (Map.Entry<String, String> entry : secrets.entrySet()) {
                            System.out.printf("%s:\t%s\n", entry.getKey(), entry.getValue());
                        }
                        break;
                    case 3:
                        System.out.printf("Bye");
                        return;
                }
            } catch (StorageException e) {
                System.out.printf("Unknown error\n");
            } catch (NumberFormatException e) {
                System.out.printf("Bad input: %s\n", request);
            }
        }
    }

    @Override
    public void close() {
        try {
            mStorage.close();
        } catch (IOException e) { }
    }
}


```
