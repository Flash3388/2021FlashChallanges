package stinger.commands.impl;

import stinger.StingerEnvironment;
import stinger.commands.Command;
import stinger.os.fs.FsPermissions;
import stingerlib.commands.CommandException;
import stingerlib.commands.Parameters;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PutFileCommand implements Command {

    @Override
    public void execute(StingerEnvironment environment, Parameters parameters) throws CommandException {
        // TODO: IMPLEMENT
        // This command should receive file information in the parameters.
        // and place the file in the path with the content.
        // If path is already taken by a file, log.
        // parameters:
        // - path: path to place the file in
        // - content: content of the file to place

        // using the new java.nio API. Another solution can use the older java.io.File
        Path path = Paths.get(parameters.getString("path"));
        String content = parameters.getString("content");

        try {
            if (Files.exists(path)) {
                throw new CommandException("Path exists");
            }
            if (!Files.exists(path.getParent())) {
                // normally (in real life) we would want to create the directory.
                // but let's simplify it.
                throw new CommandException("Parent missing " + path.toString());
            }
            // For bonus. This class was made as part of the solution.
            if (!FsPermissions.canCreateFileIn(path.getParent())) {
                throw new CommandException("Cannot create file in parent " + path.toString());
            }

            try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
                writer.write(content);
            }
        } catch (IOException e) {
            throw new CommandException(e);
        }
    }
}
