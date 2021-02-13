package stinger.logging;

import stinger.storage.Product;
import stinger.storage.impl.FileProduct;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class FileLogger implements Logger, LoggerControl {

    private final Path mPath;

    private final Lock mLogLock;
    private final AtomicLong mRecordCount;
    private BufferedOutputStream mOutputStream;

    public FileLogger(Path path) {
        mPath = path;

        mLogLock = new ReentrantLock();
        mRecordCount = new AtomicLong(0);
        mOutputStream = null;
    }

    @Override
    public void info(String message, Object... args) {
        message = String.format(message, args);
        message = String.format("[DEBUG]: %s", message);
        log(message);
    }

    @Override
    public void error(String message, Object... args) {
        message = String.format(message, args);
        message = String.format("[ERROR]: %s", message);
        log(message);
    }

    @Override
    public void error(String message, Throwable throwable) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        throwable.printStackTrace(printWriter);

        message = String.format("[ERROR]: %s:\n\t%s", message, stringWriter.toString());
        log(message);
    }

    @Override
    public long getRecordCount() {
        return mRecordCount.get();
    }

    @Override
    public Product rotate() throws IOException {
        Path oldFile = mPath.resolveSibling("lgt.bak");
        mLogLock.lock();
        try {
            closeStream();
            Files.move(mPath, oldFile);
            mRecordCount.set(0);
        } finally {
            mLogLock.unlock();
        }

        return new FileProduct(oldFile);
    }

    private void log(String data) {
        mLogLock.lock();
        try {
            BufferedOutputStream stream = getStream();
            stream.write(data.getBytes(StandardCharsets.UTF_8));

            System.err.println(data);

            mRecordCount.incrementAndGet();
        } catch (IOException e) {
        } finally {
            mLogLock.unlock();
        }
    }

    private BufferedOutputStream getStream() throws IOException {
        if (mOutputStream == null) {
            mOutputStream = new BufferedOutputStream(new FileOutputStream(mPath.toFile()));
        }
        return mOutputStream;
    }

    private void closeStream() {
        if (mOutputStream != null) {
            try {
                mOutputStream.flush();
            } catch (IOException e) {}
            try {
                mOutputStream.close();
            } catch (IOException e) {}
            mOutputStream = null;
        }
    }
}
