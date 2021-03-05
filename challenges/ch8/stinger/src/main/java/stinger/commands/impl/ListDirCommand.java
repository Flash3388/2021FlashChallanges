package stinger.commands.impl;

import stinger.StingerEnvironment;
import stinger.commands.Command;
import stinger.os.fs.FsPermissions;
import stinger.storage.StandardProductType;
import stinger.storage.Storage;
import stinger.storage.impl.BinaryProduct;
import stingerlib.commands.CommandException;
import stingerlib.commands.Parameters;
import stingerlib.storage.StorageException;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.EnumSet;

public class ListDirCommand implements Command {

    @Override
    public void execute(StingerEnvironment environment, Parameters parameters) throws CommandException {
        // TODO: IMPLEMENT
        // This command should receive directory information in the parameters.
        // and list all the files in the directory until a certain depth.
        // If the directory doesn't exist, log.
        // If the path is not a directory, log.
        // parameters:
        // - path: path to the directory to list.
        // - depth: depth of subdirectories to go into.
        //          1 = only the files under the given directory
        //          2 = also the files under subdirectories.
        //          so on....
        // result:
        // - product containing information about the list. Use BinaryProduct with a string.
        //      Remember to pass StandardProductType.LIST_DIR to it.

        Path path = Paths.get(parameters.getString("path"));
        int depth = parameters.getInt("depth");

        try {
            if (!Files.exists(path)) {
                throw new CommandException("Path doesn't exist");
            }
            // For bonus. This class was made as part of the solution.
            if (!FsPermissions.canListContents(path)) {
                throw new CommandException("Cannot list contents " + path.toString());
            }

            StringBuilder builder = new StringBuilder();
            listDir(path, depth, builder);

            Storage storage = environment.getStorage();
            storage.store(new BinaryProduct(builder.toString(), StandardProductType.LIST_DIR));
        } catch (IOException | StorageException e) {
            throw new CommandException(e);
        }
    }

    private void listDir(Path path, int depth, StringBuilder builder) throws IOException {
        // We'll use the new java API to walk on the file tree, which will allow us to reach
        // each file and subdirectory until a given depth.
        // For each we'll update the stringbuilder, making a huge string that will look
        // like this:
        // dir1:
        //      file1
        //      file2
        //      dir2
        //          file3
        //      file4
        Files.walkFileTree(path, EnumSet.noneOf(FileVisitOption.class), depth,
                new SimpleFileVisitor<Path>() {
                    long indentation = 0;
                    @Override
                    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs)
                            throws IOException {
                        makeIndentation();
                        builder.append(dir.toString());
                        builder.append(':');
                        builder.append('\n');
                        indentation++;
                        return FileVisitResult.CONTINUE;
                    }

                    @Override
                    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
                            throws IOException {
                        makeIndentation();
                        builder.append(file.toString());
                        builder.append('\n');
                        return FileVisitResult.CONTINUE;
                    }

                    @Override
                    public FileVisitResult postVisitDirectory(Path dir, IOException exc)
                            throws IOException {
                        indentation--;
                        return FileVisitResult.CONTINUE;
                    }

                    private void makeIndentation() {
                        for (int i = 0; i < indentation; i++) {
                            builder.append('\t');
                        }
                    }
                });
    }
}
