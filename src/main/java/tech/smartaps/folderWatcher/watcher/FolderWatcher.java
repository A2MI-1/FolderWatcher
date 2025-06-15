package tech.smartaps.folderWatcher.watcher;

import tech.smartaps.folderWatcher.helper.WatcherHelper;
import tech.smartaps.folderWatcher.model.Folder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
    public void move(String src, String dest) {
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

    // get all .txt files
    public List<String> getAllTextFiles() throws Exception {
        List<String> textFiles = new ArrayList<>();
        // go through all files in base folder
        File file = new File(this.getFolder().getPath());
        // if folder is empty
        if(file.list() == null) throw new Exception("This folder is empty.");
        // else
        else {
            for(String filename : file.list()) {
                if(filename.endsWith(".txt")) textFiles.add(filename);
            }
        }

        // check if empty
        if(textFiles.isEmpty()) throw new Exception("No text files in this folder.");
        return textFiles;
    }
}
