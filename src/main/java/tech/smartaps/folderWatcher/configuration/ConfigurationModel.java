package tech.smartaps.folderWatcher.configuration;

import tech.smartaps.folderWatcher.folder.Folder;
import tech.smartaps.folderWatcher.folder.OperationFolder;

import java.util.List;

public class ConfigurationModel {

    private Folder baseFolder;

    private List<OperationFolder> operationFolders;

    public Folder getBaseFolder() {
        return baseFolder;
    }

    public List<OperationFolder> getOperationFolders() {
        return operationFolders;
    }
}
