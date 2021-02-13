package stinger.commands.impl;

import stinger.StingerEnvironment;
import stinger.commands.Command;
import stinger.commands.CommandException;
import stinger.commands.Parameters;
import stinger.storage.Product;
import stinger.storage.StorageException;
import stinger.storage.impl.FileProduct;

import java.nio.file.Path;
import java.nio.file.Paths;

public class GetFileCommand implements Command {

    @Override
    public void execute(StingerEnvironment environment, Parameters parameters) throws CommandException {
        try {
            String pathStr = parameters.getString("path");
            Path path = Paths.get(pathStr);

            Product product = new FileProduct(path);
            environment.getStorage().store(product);
        } catch (StorageException e) {
            throw new CommandException(e);
        }
    }
}
