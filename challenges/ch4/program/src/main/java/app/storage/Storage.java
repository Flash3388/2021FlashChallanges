package app.storage;

import java.io.Closeable;
import java.util.Map;

public interface Storage extends Closeable {

    Map<String, String> getStateSecrets() throws StorageException;
    void addStateSecret(String key, String data) throws StorageException;

    char[] getPassword() throws StorageException;
}
