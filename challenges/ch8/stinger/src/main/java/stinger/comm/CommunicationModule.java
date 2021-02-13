package stinger.comm;

import stinger.Module;
import stinger.StingerEnvironment;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class CommunicationModule implements Module {

    private static final long COMMUNICATION_INTERVAL = 3600;

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
        mFuture = mExecutorService.submit(new Task(mCommunicator, environment));
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

        private Task(Communicator communicator, StingerEnvironment environment) {
            mCommunicator = communicator;
            mEnvironment = environment;
        }

        @Override
        public void run() {
            try {
                while (!Thread.interrupted()) {
                    Thread.sleep(COMMUNICATION_INTERVAL);

                    try {
                        TransactionResult result = mCommunicator.doTransaction(mEnvironment);
                        mEnvironment.getCommandQueue().addCommands(result.getCommands());
                    } catch (CommunicationException e) {
                    }
                }
            } catch (InterruptedException e) { }
        }
    }
}
