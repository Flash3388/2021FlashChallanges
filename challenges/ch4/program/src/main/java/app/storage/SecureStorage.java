package app.storage;

import app.db.Database;
import app.db.DatabaseException;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SecureStorage implements Storage {

    private final Database mDatabase;
    private final AesCipher mCipher;

    public SecureStorage(Database database, AesCipher cipher) {
        mDatabase = database;
        mCipher = cipher;
    }

    @Override
    public Map<String, String> getStateSecrets() throws StorageException {
        try {
            List<Map<String, Object>> rows = mDatabase.query("SELECT key,data FROM secrets");

            Map<String, String> result = new HashMap<>();
            for (Map<String, Object> row : rows) {
                String key = (String) row.get("key");
                String data = (String) row.get("data");

                key = mCipher.decrypt(key);
                data = mCipher.decrypt(data);

                result.put(key, data);
            }

            return result;
        } catch (DatabaseException | CryptoException e) {
            throw new StorageException(e);
        }
    }

    @Override
    public void addStateSecret(String key, String data) throws StorageException {
        try {
            key = mCipher.encrypt(key);
            data = mCipher.encrypt(data);

            mDatabase.update("INSERT INTO secrets (key,data) VALUES (?,?)", key, data);
        } catch (CryptoException | DatabaseException e) {
            throw new StorageException(e);
        }
    }

    @Override
    public char[] getPassword() throws StorageException {
        try {
            List<Map<String, Object>> rows = mDatabase.query("SELECT data FROM password LIMIT 1");
            String password = (String) rows.get(0).get("data");
            return mCipher.decrypt(password).toCharArray();
        } catch (CryptoException | DatabaseException e) {
            throw new StorageException(e);
        }
    }

    @Override
    public void close() throws IOException {
        mDatabase.close();
    }
}
