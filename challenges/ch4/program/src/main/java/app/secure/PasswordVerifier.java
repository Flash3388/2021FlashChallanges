package app.secure;

import app.storage.Storage;
import app.storage.StorageException;

import java.util.Arrays;

public class PasswordVerifier {

    private final Storage mStorage;

    public PasswordVerifier(Storage storage) {
        mStorage = storage;
    }

    public boolean verify(char[] password) throws StorageException {
        char[] actualPassword = mStorage.getPassword();
        return Arrays.equals(password, actualPassword);
    }
}
