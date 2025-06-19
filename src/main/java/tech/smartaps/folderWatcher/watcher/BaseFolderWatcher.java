package tech.smartaps.folderWatcher.watcher;

import tech.smartaps.folderWatcher.folder.Folder;
import tech.smartaps.folderWatcher.folder.OperationFolder;
import tech.smartaps.folderWatcher.helper.WatcherHelper;

import java.io.File;
import java.util.List;

public class BaseFolderWatcher extends FolderWatcher{

    private List<OperationFolder> operationFolders;

    public BaseFolderWatcher(Folder baseFolder, List<OperationFolder> operationFolders) {
        super(baseFolder);
        this.setOperationFolders(operationFolders);
    }

    public List<OperationFolder> getOperationFolders() {
        return operationFolders;
    }

    public void setOperationFolders(List<OperationFolder> operationFolders) {
        this.operationFolders = operationFolders;
    }

    // base treatment
    @Override
    public void treatment() throws Exception {
        // get base folder
        for(String s : this.getBaseFolder().getAllTextFiles()) {
            String filename = this.getBaseFolder().getPath() + File.separator + s;
            // get operation
            String operation = WatcherHelper.readLines(filename);
            for(OperationFolder of : this.getOperationFolders()) {
                // if operation pattern without whitespaces == folder pattern, move to folder
                if(of.isLikePattern(operation.replaceAll(" ","").trim())) {
                    this.getBaseFolder().move(filename, of.getPath() + File.separator + s);
                    // get out of loop
                    break;
                }
            }
        }
    }
}
