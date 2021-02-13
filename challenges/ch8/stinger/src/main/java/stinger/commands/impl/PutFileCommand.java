package stinger.commands.impl;

import stinger.StingerEnvironment;
import stinger.commands.Command;
import stinger.commands.CommandException;
import stinger.commands.Parameters;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

public class PutFileCommand implements Command {

    @Override
    public void execute(StingerEnvironment environment, Parameters parameters) throws CommandException {
        try {
            String pathStr = parameters.getString("path");
            Path path = Paths.get(pathStr);

            String dataEncoded = parameters.getString("data");
            byte[] data = Base64.getDecoder().decode(dataEncoded);

            Files.write(path, data);
        } catch (IOException e) {
            throw new CommandException(e);
        }
    }
}
