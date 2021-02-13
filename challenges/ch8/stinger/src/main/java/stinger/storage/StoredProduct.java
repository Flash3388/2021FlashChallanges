package stinger.storage;

import java.nio.file.Path;

public class StoredProduct {

    private final String mId;
    private final Path mStoredPath;

    public StoredProduct(String id, Path storedPath) {
        mId = id;
        mStoredPath = storedPath;
    }

    public String getId() {
        return mId;
    }

    public Path getStoredPath() {
        return mStoredPath;
    }
}
