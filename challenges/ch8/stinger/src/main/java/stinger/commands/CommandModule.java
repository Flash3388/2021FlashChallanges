package stinger.commands;

import stinger.Module;
import stinger.StingerEnvironment;
import stingerlib.commands.CommandException;
import stingerlib.logging.Logger;

import java.util.Collection;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;

public class CommandModule implements Module, CommandQueue {

    private final ExecutorService mExecutorService;
    private final Logger mLogger;

    private final BlockingQueue<Executable> mCommandQueue;
    private Future<?> mFuture;

    public CommandModule(ExecutorService executorService, Logger logger) {
        mExecutorService = executorService;
        mLogger = logger;

        mCommandQueue = new LinkedBlockingDeque<>();
        mFuture = null;
    }

    @Override
    public void start(StingerEnvironment environment) {
        mFuture = mExecutorService.submit(new Task(mCommandQueue, environment, environment.getLogger()));
    }

    @Override
    public void stop() {
        if (mFuture != null) {
            mFuture.cancel(true);
            mFuture = null;
        }
    }

    @Override
    public void addCommand(Executable executable) {
        mLogger.info("Adding command %s", executable.toString());
        mCommandQueue.add(executable);
    }

    @Override
    public void addCommands(Collection<? extends Executable> executables) {
        mLogger.info("Adding commands %s", executables.toString());
        mCommandQueue.addAll(executables);
    }

    private static class Task implements Runnable {

        private final BlockingQueue<Executable> mCommandQueue;
        private final StingerEnvironment mStingerEnvironment;
        private final Logger mLogger;

        private Task(BlockingQueue<Executable> commandQueue, StingerEnvironment stingerEnvironment, Logger logger) {
            mCommandQueue = commandQueue;
            mStingerEnvironment = stingerEnvironment;
            mLogger = logger;
        }

        @Override
        public void run() {
            mLogger.info("Starting CommandModule");

            try {
                while (!Thread.interrupted()) {
                    try {
                        Executable command = mCommandQueue.take();
                        mLogger.info("Executing command %s", command);
                        command.execute(mStingerEnvironment);
                    } catch (CommandException e) {
                        mLogger.error("CommandModule exec error", e);
                    }
                }
            } catch (InterruptedException e) {
            } catch (Throwable t) {
                mLogger.error("Unexpected error in CommandModule", t);
            }

            mLogger.info("Done CommandModule");
        }
    }
}
