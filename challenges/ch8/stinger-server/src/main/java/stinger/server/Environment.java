package stinger.server;

import stinger.server.commands.CommandQueue;
import stinger.server.commands.GenericCommandType;
import stinger.server.storage.GenericProductType;
import stinger.server.storage.Storage;
import stinger.server.util.KnownTypes;
import stingerlib.logging.Logger;

public class Environment {

    private final KnownTypes<GenericCommandType, Integer> mKnownCommandTypes;
    private final KnownTypes<GenericProductType, Integer> mKnownProductTypes;
    private final CommandQueue mCommandQueue;
    private final Storage mStorage;
    private final Logger mLogger;

    public Environment(KnownTypes<GenericCommandType, Integer> knownCommandTypes,
                       KnownTypes<GenericProductType, Integer> knownProductTypes,
                       CommandQueue commandQueue, Storage storage, Logger logger) {
        mKnownCommandTypes = knownCommandTypes;
        mKnownProductTypes = knownProductTypes;
        mCommandQueue = commandQueue;
        mStorage = storage;
        mLogger = logger;
    }

    public KnownTypes<GenericCommandType, Integer> getCommandTypes() {
        return mKnownCommandTypes;
    }

    public KnownTypes<GenericProductType, Integer> getProductTypes() {
        return mKnownProductTypes;
    }

    public CommandQueue getCommandQueue() {
        return mCommandQueue;
    }

    public Storage getStorage() {
        return mStorage;
    }

    public Logger getLogger() {
        return mLogger;
    }
}
