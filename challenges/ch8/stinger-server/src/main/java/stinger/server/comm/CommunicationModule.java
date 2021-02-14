package stinger.server.comm;

import stinger.server.Environment;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class CommunicationModule {

    private final ExecutorService mExecutorService;
    private final Communicator mCommunicator;

    private Future<?> mFuture;

    public CommunicationModule(ExecutorService executorService, Communicator communicator) {
        mExecutorService = executorService;
        mCommunicator = communicator;
    }

    public void start(Environment environment) {
        mFuture = mExecutorService.submit(new Task(mCommunicator, environment));
    }

    public void stop() {
        if (mFuture != null) {
            mFuture.cancel(true);
            mFuture = null;
        }

        try {
            mCommunicator.close();
        } catch (IOException e) {

        }
    }

    private static class Task implements Runnable {

        private final Communicator mCommunicator;
        private final Environment mEnvironment;

        private Task(Communicator communicator, Environment environment) {
            mCommunicator = communicator;
            mEnvironment = environment;
        }

        @Override
        public void run() {
            while (!Thread.interrupted()) {
                try {
                    mCommunicator.handleNextClient(mEnvironment);
                } catch (IOException e) {
                    mEnvironment.getLogger().error("error handling client", e);
                }
            }
        }
    }
}
