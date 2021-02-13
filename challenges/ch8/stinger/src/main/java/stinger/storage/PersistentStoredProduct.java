package stinger.storage;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

public class PersistentStoredProduct implements StoredProduct {

    private final String mId;
    private final ProductType mType;
    private final Path mStoredPath;

    public PersistentStoredProduct(String id, ProductType type, Path storedPath) {
        mId = id;
        mType = type;
        mStoredPath = storedPath;
    }

    @Override
    public String getId() {
        return mId;
    }

    @Override
    public ProductType getType() {
        return mType;
    }

    @Override
    public InputStream open() throws IOException {
        return new FileInputStream(mStoredPath.toFile());
    }

    public Path getStoredPath() {
        return mStoredPath;
    }
}
