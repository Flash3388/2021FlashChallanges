package stinger;

import stinger.commands.CommandQueue;
import stinger.logging.Logger;
import stinger.storage.Storage;

public interface StingerEnvironment {

    Storage getStorage();
    CommandQueue getCommandQueue();
    Logger getLogger();
}
