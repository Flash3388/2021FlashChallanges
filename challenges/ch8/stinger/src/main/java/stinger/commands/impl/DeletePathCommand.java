package stinger.commands.impl;

import stinger.StingerEnvironment;
import stinger.commands.Command;
import stinger.os.fs.FsPermissions;
import stingerlib.commands.CommandException;
import stingerlib.commands.Parameters;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

public class DeletePathCommand implements Command {

    @Override
    public void execute(StingerEnvironment environment, Parameters parameters) throws CommandException {
        // TODO: IMPLEMENT
        // This command should receive file information in the parameters.
        // and delete the file in the path.
        // If the file doesn't exist, do nothing.
        // If path is a file, delete file. If path is a directory, delete directory.
        // parameters:
        // - path: path to the file to delete

        // using the new java.nio API. Another solution can use the older java.io.File
        Path path = Paths.get(parameters.getString("path"));

        try {
            if (!Files.exists(path)) {
                throw new CommandException("Path doesn't exist");
            }
            // For bonus. This class was made as part of the solution.
            if (!FsPermissions.canDelete(path)) {
                throw new CommandException("Cannot delete " + path.toString());
            }

            if (Files.isDirectory(path)) {
                // In the case of a directory, we can't simply delete it, we must first delete
                // all the files and sub-directories within it.
                recursiveDelete(path);
            } else {
                Files.delete(path);
            }
        } catch (IOException e) {
            throw new CommandException(e);
        }
    }

    private void recursiveDelete(Path path) throws IOException {
        // Using the new API we can easily traverse the file tree and delete everything.
        // This will go over all the directories and files under our directory, and delete them.
        // It will first delete all the files, and only later delete the directories.
        Files.walkFileTree(path,
                new SimpleFileVisitor<Path>() {
                    @Override
                    public FileVisitResult postVisitDirectory(
                            Path dir, IOException exc) throws IOException {
                        Files.delete(dir);
                        return FileVisitResult.CONTINUE;
                    }

                    @Override
                    public FileVisitResult visitFile(
                            Path file, BasicFileAttributes attrs)
                            throws IOException {
                        Files.delete(file);
                        return FileVisitResult.CONTINUE;
                    }
                });
    }
}
