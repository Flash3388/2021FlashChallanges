package stinger.commands;

import stinger.Module;
import stinger.StingerEnvironment;

import java.util.Collection;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;

public class CommandModule implements Module, CommandQueue {

    private final ExecutorService mExecutorService;
    private final BlockingQueue<Executable> mCommandQueue;

    private Future<?> mFuture;

    public CommandModule(ExecutorService executorService) {
        mExecutorService = executorService;
        mCommandQueue = new LinkedBlockingDeque<>();

        mFuture = null;
    }

    @Override
    public void start(StingerEnvironment environment) {
        mFuture = mExecutorService.submit(new Task(mCommandQueue, environment));
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
        mCommandQueue.add(executable);
    }

    @Override
    public void addCommands(Collection<? extends Executable> executables) {
        mCommandQueue.addAll(executables);
    }

    private static class Task implements Runnable {

        private final BlockingQueue<Executable> mCommandQueue;
        private final StingerEnvironment mStingerEnvironment;

        private Task(BlockingQueue<Executable> commandQueue, StingerEnvironment stingerEnvironment) {
            mCommandQueue = commandQueue;
            mStingerEnvironment = stingerEnvironment;
        }

        @Override
        public void run() {
            try {
                while (!Thread.interrupted()) {
                    try {
                        Executable command = mCommandQueue.take();
                        command.execute(mStingerEnvironment);
                    } catch (CommandException e) {

                    }
                }
            } catch (InterruptedException e) {}
        }
    }
}
