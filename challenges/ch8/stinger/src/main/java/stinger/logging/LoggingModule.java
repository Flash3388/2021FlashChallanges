package stinger.logging;

import stinger.Constants;
import stinger.Module;
import stinger.StingerEnvironment;
import stingerlib.logging.Logger;
import stingerlib.logging.LoggerControl;
import stingerlib.storage.Product;
import stingerlib.storage.StorageException;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class LoggingModule implements Module {

    private final ExecutorService mExecutorService;
    private final LoggerControl mLoggerControl;

    private Future<?> mFuture;

    public LoggingModule(ExecutorService executorService, LoggerControl loggerControl) {
        mExecutorService = executorService;
        mLoggerControl = loggerControl;

        mFuture = null;
    }

    @Override
    public void start(StingerEnvironment environment) {
        mFuture = mExecutorService.submit(new Task(mLoggerControl, environment, environment.getLogger()));
    }

    @Override
    public void stop() {
        if (mFuture != null) {
            mFuture.cancel(true);
            mFuture = null;
        }
    }

    private static class Task implements Runnable {

        private final LoggerControl mLoggerControl;
        private final StingerEnvironment mStingerEnvironment;
        private final Logger mLogger;

        private Task(LoggerControl loggerControl, StingerEnvironment stingerEnvironment, Logger logger) {
            mLoggerControl = loggerControl;
            mStingerEnvironment = stingerEnvironment;
            mLogger = logger;
        }

        @Override
        public void run() {
            mLogger.info("Starting LoggingModule");

            try {
                long lastRotateMs = System.currentTimeMillis();
                while (!Thread.interrupted()) {
                    Thread.sleep(Constants.LOGGING_CHECK_INTERVAL_MS);

                    if (mLoggerControl.getRecordCount() == Constants.LOGGING_RECORDS_ROTATE ||
                        System.currentTimeMillis() - lastRotateMs >= Constants.LOGGING_TIME_ROTATE_MS) {
                        try {
                            mLogger.info("Doing rotation");
                            Product product = mLoggerControl.rotate();
                            lastRotateMs = System.currentTimeMillis();
                            mStingerEnvironment.getStorage().store(product);
                        } catch (IOException | StorageException e) {
                            mLogger.error("LoggerModule Rotation", e);
                        }
                    }
                }
            } catch (InterruptedException e) {}

            mLogger.info("Done LoggingModule");
        }
    }
}
