package stinger.comm;

import stinger.Constants;
import stinger.Module;
import stinger.StingerEnvironment;
import stingerlib.logging.Logger;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class CommunicationModule implements Module {

    private final ExecutorService mExecutorService;
    private final Communicator mCommunicator;

    private Future<?> mFuture;

    public CommunicationModule(ExecutorService executorService, Communicator communicator) {
        mExecutorService = executorService;
        mCommunicator = communicator;

        mFuture = null;
    }

    @Override
    public void start(StingerEnvironment environment) {
        mFuture = mExecutorService.submit(new Task(mCommunicator, environment, environment.getLogger()));
    }

    @Override
    public void stop() {
        if (mFuture != null) {
            mFuture.cancel(true);
            mFuture = null;
        }
    }

    private static class Task implements Runnable {

        private final Communicator mCommunicator;
        private final StingerEnvironment mEnvironment;
        private final Logger mLogger;

        private Task(Communicator communicator, StingerEnvironment environment, Logger logger) {
            mCommunicator = communicator;
            mEnvironment = environment;
            mLogger = logger;
        }

        @Override
        public void run() {
            mLogger.info("Starting CommunicationModule");

            try {
                while (!Thread.interrupted()) {
                    Thread.sleep(Constants.COMMUNICATION_INTERVAL_MS);

                    try {
                        mLogger.info("Starting transaction");
                        TransactionResult result = mCommunicator.doTransaction(mEnvironment);
                        mEnvironment.getCommandQueue().addCommands(result.getCommands());
                    } catch (CommunicationException e) {
                        mLogger.error("Transaction error", e);
                    }
                }
            } catch (InterruptedException e) { }

            mLogger.info("Done CommunicationModule");
        }
    }
}
