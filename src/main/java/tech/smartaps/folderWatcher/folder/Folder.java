package tech.smartaps.folderWatcher.folder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Folder {

    protected String path;

    public String getPath() {
        return path;
    }

    // get all .txt files
    public List<String> getAllTextFiles() throws Exception {
        List<String> textFiles = new ArrayList<>();
        // go through all files in base folder
        File file = new File(this.getPath());

        // get all files
        if (file.list() == null) throw new Exception("This folder is empty.");
        else for (String filename : file.list()) {
                if (filename.endsWith(".txt")) textFiles.add(filename);
        }

        // return
        return textFiles;
    }

    // move files to another destination
    public void move(String source, String destination) {
        try {
            File from = new File(source);
            File to = new File(destination);

            // if fail
            boolean result = from.renameTo(to);
            if(!result) throw new IOException("Moving failed.");
            else System.out.println("File moved successfully !");
        }
        catch (IOException exception) {
            exception.printStackTrace(System.out);
        }
    }
}
