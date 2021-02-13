package stinger.logging;

import stinger.storage.Product;

import java.io.IOException;
import java.nio.file.Path;

public interface LoggerControl {

    long getRecordCount();
    Product rotate() throws IOException;
}
