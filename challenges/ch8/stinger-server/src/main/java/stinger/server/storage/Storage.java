package stinger.server.storage;

import stingerlib.logging.Logger;
import stingerlib.storage.StoredProduct;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class Storage {

    private final Path mStorageDir;
    private final Logger mLogger;

    public Storage(Path storageDir, Logger logger) {
        mStorageDir = storageDir;
        mLogger = logger;
    }

    public void save(StoredProduct product) {
        try(InputStream inputStream = product.open()) {
            Files.copy(inputStream, mStorageDir.resolve(String.format("%s.%s",
                    product.getType(), product.getId())));
        } catch (IOException e) {
            mLogger.error("Storage error", e);
        }
    }
}
