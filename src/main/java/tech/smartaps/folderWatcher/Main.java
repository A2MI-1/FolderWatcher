package tech.smartaps.folderWatcher;

import com.google.gson.Gson;
import tech.smartaps.folderWatcher.configuration.ConfigurationModel;
import tech.smartaps.folderWatcher.helper.WatcherHelper;
import tech.smartaps.folderWatcher.model.OperationFolder;
import tech.smartaps.folderWatcher.watcher.BaseFolderWatcher;
import tech.smartaps.folderWatcher.watcher.OperationFolderWatcher;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        // Retrieving configuration file
        Scanner sc = new Scanner(System.in);
        System.out.println("Entrez le chemin du fichier de configuration : ");
        String configurationFilePath = sc.nextLine();

        // parsing configuration file
        Gson gson = new Gson();
        ConfigurationModel configurationModel = WatcherHelper.parseConfigurationModel(gson, configurationFilePath);

        // operation folder watchers
        List<Thread> threads = new ArrayList<>();
        for(OperationFolder of : configurationModel.getOperationFolders()) {
            threads.add(new Thread(new OperationFolderWatcher(of, configurationModel.getBaseFolder().getPath())));
        }

        // add to thread list, base folder watcher
        threads.add(new Thread(new BaseFolderWatcher(configurationModel.getBaseFolder(), configurationModel.getOperationFolders())));

        // start all threads
        for(Thread t : threads) {
            System.out.println("Starting watchers...");
            t.start();
        }
    }
}