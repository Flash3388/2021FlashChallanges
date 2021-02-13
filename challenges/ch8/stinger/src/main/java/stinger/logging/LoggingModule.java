package stinger.logging;

import stinger.Module;
import stinger.StingerEnvironment;
import stinger.storage.Product;
import stinger.storage.StorageException;

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
            try {
                while (!Thread.interrupted()) {
                    Thread.sleep(60000);

                    if (mLoggerControl.getRecordCount() == 1000) {
                        try {
                            mLogger.info("Doing rotation");
                            Product product = mLoggerControl.rotate();
                            mStingerEnvironment.getStorage().store(product);
                        } catch (IOException | StorageException e) {
                            mLogger.error("LoggerModule Rotation", e);
                        }
                    }
                }
            } catch (InterruptedException e) {}
        }
    }
}
