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

public class Application implements AutoCloseable {

    private final Console mConsole;
    private final Storage mStorage;
    private final PasswordVerifier mPasswordVerifier;

    public Application(Console console) throws SQLException, ClassNotFoundException, CryptoException {
        mConsole = console;

        Database database = JdbcDatabase.open(Paths.get("mdn.db"));
        AesCipher cipher = new AesCipher("keyyyyyyyyyyyyyyyyyyyyyyyyyyy");
        mStorage = new SecureStorage(database, cipher);

        mPasswordVerifier = new PasswordVerifier(mStorage);
    }

    public void run() throws StorageException {
        if (!verify()) {
            mConsole.printf("Authentication failed\n");
            return;
        }

        runMenu();
    }

    private boolean verify() throws StorageException {
        int attempts = 0;
        do {
            attempts++;
            char[] password = mConsole.readPassword("Enter password to enter: ");
            if (mPasswordVerifier.verify(password)) {
                return true;
            }

            mConsole.printf("Try again.\n");
        } while (attempts < 3);

        return false;
    }

    private void runMenu() {
        while (true) {
            mConsole.printf("\nWelcome!\n\t1. Enter New\n\t2. Read All\n\t3. Exit\n");
            String request = mConsole.readLine("Enter request: ");

            try {
                int menuItem = Integer.parseInt(request);
                switch (menuItem) {
                    case 1:
                        String key = mConsole.readLine("Key: ");
                        String data = mConsole.readLine("Data: ");

                        mStorage.addStateSecret(key, data);
                        break;
                    case 2:
                        Map<String, String> secrets = mStorage.getStateSecrets();
                        for (Map.Entry<String, String> entry : secrets.entrySet()) {
                            mConsole.printf("%s:\t%s\n", entry.getKey(), entry.getValue());
                        }
                        break;
                    case 3:
                        mConsole.printf("Bye");
                        return;
                }
            } catch (StorageException e) {
                mConsole.printf("Unknown error\n");
            } catch (NumberFormatException e) {
                mConsole.printf("Bad input: %s\n", request);
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
