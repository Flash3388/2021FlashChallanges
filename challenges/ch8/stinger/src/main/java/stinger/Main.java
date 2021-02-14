package stinger;

import stinger.comm.CommunicationModule;
import stinger.comm.StandardCommunicator;
import stinger.commands.CommandModule;
import stinger.logging.FileLogger;
import stinger.logging.LoggingModule;
import stinger.storage.PersistentStorage;
import stinger.storage.Storage;
import stinger.storage.StorageIndex;

import java.util.Arrays;
import java.util.HashSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        try {
            StingerFiles files = new StingerFiles();

            StingerControl stingerControl = new StringerControlImpl(Thread.currentThread());

            FileLogger logger = new FileLogger(files.getLogFile());
            Storage storage = new PersistentStorage(
                    files.getStorageRoot(),
                    StorageIndex.inFile(files.getStorageIndexDbPath(), logger)
            );
            CommandModule commandModule = new CommandModule(executorService, logger);
            CommunicationModule communicationModule = new CommunicationModule(
                    executorService, new StandardCommunicator(Constants.COMMUNICATION_END_POINT)
            );
            LoggingModule loggingModule = new LoggingModule(executorService, logger);

            Stinger stinger = new Stinger(
                    new HashSet<>(Arrays.<Module>asList(
                            commandModule,
                            communicationModule,
                            loggingModule
                    )),
                    new StingerEnvironmentImpl(storage, commandModule, logger, stingerControl));
            try {
                logger.info("Stinger start");
                stinger.start();
            } finally {
                stinger.stop();
                logger.info("Stinger done");
                logger.close();
            }
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            executorService.shutdownNow();
        }
    }
}
