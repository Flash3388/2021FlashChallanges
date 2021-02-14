package stinger.server;

import stinger.server.comm.CommunicationModule;
import stinger.server.comm.Communicator;
import stinger.server.commands.CommandQueue;
import stinger.server.commands.CommandsModule;
import stinger.server.commands.GenericCommandType;
import stinger.server.storage.GenericProductType;
import stinger.server.storage.Storage;
import stinger.server.util.GenericTypesParser;
import stinger.server.util.KnownTypes;
import stingerlib.logging.FileLogger;

import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerMain {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newScheduledThreadPool(3);
        try {
            ServerFiles files = new ServerFiles();
            FileLogger logger = new FileLogger(files.getLogFile());


            KnownTypes<GenericCommandType, Integer> knownCommandTypes =
                    new GenericTypesParser<GenericCommandType, Integer>(GenericCommandType.class)
                        .parseFromFile(files.getCommandTypesFile());
            KnownTypes<GenericProductType, Integer> knownProductTypes =
                    new GenericTypesParser<GenericProductType, Integer>(GenericProductType.class)
                            .parseFromFile(files.getProductTypesFile());

            CommandQueue commandQueue = new CommandQueue();
            Storage storage = new Storage();

            CommunicationModule communicationModule = new CommunicationModule(
                    executorService, new Communicator(new InetSocketAddress(10000)));
            CommandsModule commandsModule = new CommandsModule(executorService, files.getCommandsDirectory());

            Environment environment = new Environment(knownCommandTypes, knownProductTypes,
                    commandQueue, storage, logger);

            try {
                communicationModule.start(environment);
                commandsModule.start(environment);

                while (true) {
                    Thread.sleep(1000);
                }
            } finally {
                communicationModule.stop();
                commandsModule.stop();
            }
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            executorService.shutdownNow();
        }
    }
}
