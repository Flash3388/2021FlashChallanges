package stinger;

import stinger.commands.CommandQueue;
import stinger.logging.Logger;
import stinger.storage.Storage;

public class StingerEnvironmentImpl implements StingerEnvironment {

    private final Storage mStorage;
    private final CommandQueue mCommandQueue;
    private final Logger mLogger;

    public StingerEnvironmentImpl(Storage storage, CommandQueue commandQueue, Logger logger) {
        mStorage = storage;
        mCommandQueue = commandQueue;
        mLogger = logger;
    }

    @Override
    public Storage getStorage() {
        return mStorage;
    }

    @Override
    public CommandQueue getCommandQueue() {
        return mCommandQueue;
    }

    @Override
    public Logger getLogger() {
        return mLogger;
    }
}
