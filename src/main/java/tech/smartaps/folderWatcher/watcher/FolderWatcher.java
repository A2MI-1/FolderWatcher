package tech.smartaps.folderWatcher.watcher;

import tech.smartaps.folderWatcher.helper.WatcherHelper;
import tech.smartaps.folderWatcher.model.Folder;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;

public abstract class FolderWatcher implements Runnable {

    protected Folder folder;

    public FolderWatcher(Folder folder) {
        this.setFolder(folder);
    }

    public Folder getFolder() {
        return folder;
    }

    public void setFolder(Folder folder) {
        this.folder = folder;
    }

    // evaluate operation
    public boolean evaluateOperation(String pattern, String operation) {
        // get operation matcher
        Matcher matcher = WatcherHelper.getMatcher(pattern, operation);
        // if we find a corresponding pattern, return true
        // else return false
        return matcher.find();
    }

    // move files to another destination
    // move from a folder to another
    public static void move(String src, String dest) {
        try {
            File source = new File(src);
            File destination = new File(dest);

            // if fail
            boolean result = source.renameTo(destination);
            if(!result) throw new IOException("Moving failed.");
            else System.out.println("File " + src + " moved successfully !");
        }
        catch (IOException exception) {
            exception.printStackTrace(System.out);
        }
    }
}
