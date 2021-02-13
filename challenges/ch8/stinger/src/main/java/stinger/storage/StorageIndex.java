package stinger.storage;

import stinger.db.Database;
import stinger.db.DatabaseException;
import stinger.db.JdbcDatabase;
import stinger.logging.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class StorageIndex {

    private final Database mDatabase;
    private final Logger mLogger;

    public StorageIndex(Database database, Logger logger) throws StorageException {
        mDatabase = database;
        mLogger = logger;

        try {
            createTable();
        } catch (DatabaseException e) {
            throw new StorageException(e);
        }
    }

    public static StorageIndex inFile(Path dbFile, Logger logger) throws StorageException {
        try {
            Database database = JdbcDatabase.open(dbFile);
            return new StorageIndex(database, logger);
        } catch (ClassNotFoundException | SQLException e) {
            throw new StorageException(e);
        }
    }

    public void addProduct(PersistentStoredProduct product) throws StorageException {
        try {
            mDatabase.update("INSERT INTO pros (prid, type, path) VALUES (?,?,?)",
                    product.getId(),
                    product.getType().intValue(),
                    product.getStoredPath().toAbsolutePath().toString());
        } catch (DatabaseException e) {
            throw new StorageException(e);
        }
    }

    public Iterator<StoredProduct> storedProductsSnapshot() throws StorageException {
        try {
            List<Map<String, Object>> data = mDatabase.query("SELECT * FROM pros");
            List<PersistentStoredProduct> storedProducts = data.stream().map((map)-> {
                String id = (String) map.get("prid");
                int typeInt = (int) map.get("type");
                String pathStr = (String) map.get("path");
                return new PersistentStoredProduct(
                        id,
                        ProductType.fromInt(typeInt),
                        Paths.get(pathStr)
                );
            }).collect(Collectors.toList());

            return new Iterator<StoredProduct>() {
                int currentIndex = -1;

                @Override
                public boolean hasNext() {
                    return currentIndex < storedProducts.size();
                }

                @Override
                public StoredProduct next() {
                    currentIndex++;
                    if (currentIndex < 0 || currentIndex >= storedProducts.size()) {
                        throw new NoSuchElementException();
                    }

                    return storedProducts.get(currentIndex);
                }

                @Override
                public void remove() {
                    if (currentIndex < 0 || currentIndex >= storedProducts.size()) {
                        throw new NoSuchElementException();
                    }

                    StorageIndex.this.remove(storedProducts.get(currentIndex));
                }
            };
        } catch (DatabaseException e) {
            throw new StorageException(e);
        }
    }

    private void createTable() throws DatabaseException {
        mDatabase.update(String.format("CREATE TABLE IF NOT EXISTS pros (%s,%s)",
                "prid NVARCHAR UNIQUE NOT NULL",
                "path NVARCHAR NOT NULL"));
    }

    private void remove(PersistentStoredProduct product) {
        try {
            mDatabase.update("DELETE FROM pros WHERE prid=?", product.getId());
        } catch (DatabaseException e) {
            mLogger.error("Storage remove index error", e);
        }

        try {
            Files.deleteIfExists(product.getStoredPath());
        } catch (IOException e) {
            mLogger.error("Storage remove path error", e);
        }
    }
}
