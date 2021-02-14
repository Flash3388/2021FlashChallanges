package stinger.storage.impl;


import stinger.storage.StandardProductType;
import stingerlib.storage.Product;
import stingerlib.storage.ProductType;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

public class FileProduct implements Product {

    private final Path mPath;

    public FileProduct(Path path) {
        mPath = path;
    }

    @Override
    public ProductType getType() {
        return StandardProductType.FILE;
    }

    @Override
    public InputStream open() throws IOException {
        return new FileInputStream(mPath.toFile());
    }
}
