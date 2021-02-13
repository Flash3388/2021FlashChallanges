package stinger.storage;

import java.util.Iterator;

public interface Storage {

    void store(Product product) throws StorageException;
    Iterator<StoredProduct> storedProducts() throws StorageException;
}
