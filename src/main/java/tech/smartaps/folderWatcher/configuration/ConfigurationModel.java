package tech.smartaps.folderWatcher.configuration;

import tech.smartaps.folderWatcher.model.BaseFolder;
import tech.smartaps.folderWatcher.model.OperationFolder;

import java.util.List;

public class ConfigurationModel {

    private BaseFolder baseFolder;

    private List<OperationFolder> operationFolders;

    public BaseFolder getBaseFolder() {
        return baseFolder;
    }

    public void setBaseFolder(BaseFolder baseFolder) {
        this.baseFolder = baseFolder;
    }

    public List<OperationFolder> getOperationFolders() {
        return operationFolders;
    }

    public void setOperationFolders(OperationFolder operationFolder) {
        this.operationFolders.add(operationFolder);
    }
}
