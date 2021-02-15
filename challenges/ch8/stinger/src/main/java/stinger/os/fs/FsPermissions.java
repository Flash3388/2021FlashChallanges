package stinger.os.fs;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FsPermissions {

    // permissions is a complicated topic. Especially in NTFS (windows file-system).
    // So the following solution is simplified and doesn't cover everything.
    // Read about Access Control Lists for more information.

    public static boolean canDelete(Path path) throws IOException {
        // the basics for delete is that you need to be able to edit the file (deleting it)
        // and update the parent directory (because we remove a file from it).
        return Files.isWritable(path.getParent()) && Files.isWritable(path);
    }

    public static boolean canRead(Path path) {
        return Files.isReadable(path);
    }

    public static boolean canCreateFileIn(Path path) {
        // to create a file in a directory we must have permission to write into
        // the parent directory.
        return Files.isWritable(path.getParent());
    }

    public static boolean canListContents(Path path) {
        // to list the contents of a directory we must have
        // execute permission for a directory.
        return Files.isExecutable(path.getParent());
    }
}
