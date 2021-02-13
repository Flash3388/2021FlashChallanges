package stinger.storage;

import java.util.Iterator;

public interface Storage {

    void store(Product product) throws StorageException;
    Iterator<Product> storedProducts() throws StorageException;
}
