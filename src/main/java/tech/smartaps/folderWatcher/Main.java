package tech.smartaps.folderWatcher;

import com.google.gson.Gson;
import tech.smartaps.folderWatcher.configuration.ConfigurationModel;
import tech.smartaps.folderWatcher.folder.OperationFolder;
import tech.smartaps.folderWatcher.helper.WatcherHelper;
import tech.smartaps.folderWatcher.watcher.BaseFolderWatcher;
import tech.smartaps.folderWatcher.watcher.FolderWatcher;
import tech.smartaps.folderWatcher.watcher.OperationFolderWatcher;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        // parsing configuration file
        Gson gson = new Gson();
        ConfigurationModel configurationModel = WatcherHelper.parseConfigurationModel(gson, "configuration.json");

        // Create watchers for all operation folders
        List<Thread> watchers = new ArrayList<>();
        for(OperationFolder of : configurationModel.getOperationFolders()) {
            FolderWatcher watcher = new OperationFolderWatcher(configurationModel.getBaseFolder(), of);
            watchers.add(new Thread(watcher));
        }
        
        // Add thread for base folder
        FolderWatcher watcher = new BaseFolderWatcher(configurationModel.getBaseFolder(), configurationModel.getOperationFolders());
        watchers.add(new Thread(watcher));
        
        // start all watchers
        for(Thread t : watchers) {
            t.start();
        }
    }
}