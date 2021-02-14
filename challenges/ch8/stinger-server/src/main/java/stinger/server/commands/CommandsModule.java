package stinger.server.commands;

import stinger.server.Environment;
import stingerlib.logging.Logger;

import java.io.IOException;
import java.nio.file.Path;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class CommandsModule {

    private final ExecutorService mExecutorService;
    private final Path mCommandsDir;

    private Future<?> mFuture;

    public CommandsModule(ExecutorService executorService, Path commandsDir) {
        mExecutorService = executorService;
        mCommandsDir = commandsDir;
    }

    public void start(Environment environment) {
        mFuture = mExecutorService.submit(new Task(mCommandsDir, environment));
    }

    public void stop() {
        if (mFuture != null) {
            mFuture.cancel(true);
            mFuture = null;
        }
    }

    private static class Task implements Runnable {

        private final Logger mLogger;
        private final CommandsCollector mCommandsCollector;

        private Task(Path commandsDir, Environment environment) {
            mCommandsCollector = new CommandsCollector(commandsDir, environment.getCommandQueue(),
                    environment.getLogger());
            mLogger = environment.getLogger();
        }

        @Override
        public void run() {
            try {
                while (!Thread.interrupted()) {
                    Thread.sleep(30 * 1000);

                    try {
                        mCommandsCollector.collectAll();
                    } catch (IOException e) {
                        mLogger.error("error collecting commands", e);
                    }
                }
            } catch (InterruptedException e) {}
        }
    }
}
