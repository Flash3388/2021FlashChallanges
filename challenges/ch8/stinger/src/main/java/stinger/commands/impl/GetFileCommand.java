package stinger.commands.impl;

import stinger.StingerEnvironment;
import stinger.commands.Command;
import stinger.os.fs.FsPermissions;
import stinger.storage.Storage;
import stinger.storage.impl.FileProduct;
import stingerlib.commands.CommandException;
import stingerlib.commands.Parameters;
import stingerlib.storage.StorageException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class GetFileCommand implements Command {

    @Override
    public void execute(StingerEnvironment environment, Parameters parameters) throws CommandException {
        // TODO: IMPLEMENT
        // This command should receive file information in the parameters.
        // and return a product containing the file data.
        // If the file doesn't exist, log.
        // If path is not a file, log.
        // parameters:
        // - path: the path to the file to return
        // result:
        // - creates and stores product containing the file content. Use FileProduct.

        // using the new java.nio API. Another solution can use the older java.io.File
        Path path = Paths.get(parameters.getString("path"));

        try {
            if (!Files.exists(path)) {
                throw new CommandException("Path doesn't exist");
            }
            if (Files.isDirectory(path)) {
                throw new CommandException("Path is directory " + path.toString());
            }
            // For bonus. This class was made as part of the solution.
            if (!FsPermissions.canRead(path)) {
                throw new CommandException("Cannot read " + path.toString());
            }

            // store a FileProduct with the file path. It will read it by itself.
            Storage storage = environment.getStorage();
            storage.store(new FileProduct(path));
        } catch (StorageException e) {
            throw new CommandException(e);
        }
    }
}
