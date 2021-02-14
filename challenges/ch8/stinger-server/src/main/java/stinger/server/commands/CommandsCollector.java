package stinger.server.commands;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import stingerlib.commands.CommandDefinition;
import stingerlib.logging.Logger;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class CommandsCollector {

    private final Path mCommandsDir;
    private final CommandQueue mCommandQueue;
    private final Logger mLogger;
    private final Gson mGson;

    public CommandsCollector(Path commandsDir, CommandQueue commandQueue, Logger logger) {
        mCommandsDir = commandsDir;
        mCommandQueue = commandQueue;
        mLogger = logger;
        mGson = new Gson();
    }

    public void collectAll() throws IOException {
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(mCommandsDir)) {
            for (Path path : stream) {
                try {
                    collect(path);
                } catch (Throwable t) {
                    mLogger.error("Error with file", path.toAbsolutePath().toString());
                    mLogger.error("stacktrace", t);
                }
            }
        }
    }

    private void collect(Path path) throws IOException {
        if (!Files.isRegularFile(path)) {
            return;
        }

        try (Reader reader = Files.newBufferedReader(path)) {
            CommandDefinition[] commandDefinitions = mGson.fromJson(reader, CommandDefinition[].class);
            mCommandQueue.addCommands(Arrays.asList(commandDefinitions));
        }
    }
}
