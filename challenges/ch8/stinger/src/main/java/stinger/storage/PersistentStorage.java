package stinger.storage;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.UUID;

public class PersistentStorage implements Storage {

    private final Path mRoot;
    private final StorageIndex mStorageIndex;

    public PersistentStorage(Path root, StorageIndex storageIndex) {
        mRoot = root;
        mStorageIndex = storageIndex;
    }

    @Override
    public void store(Product product) throws StorageException {
        try {
            StoredProduct storedProduct = storeNew(product);
            mStorageIndex.addProduct(storedProduct);
        } catch (IOException e) {
            throw new StorageException(e);
        }
    }

    @Override
    public Iterator<Product> storedProducts() throws StorageException {
        return null;
    }

    private StoredProduct storeNew(Product product) throws IOException {
        String id = generateId();
        Path path = saveProductData(product, id);

        return new StoredProduct(id, path);
    }

    private String generateId() {
        return UUID.randomUUID().toString();
    }

    private Path saveProductData(Product product, String id) throws IOException {
        Path file = mRoot.resolve(id);
        try (InputStream inputStream = product.open()) {
            Files.copy(inputStream, file);
        }

        return file;
    }
}
