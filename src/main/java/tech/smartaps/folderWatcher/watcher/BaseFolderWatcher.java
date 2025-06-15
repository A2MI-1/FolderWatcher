package tech.smartaps.folderWatcher.watcher;

import tech.smartaps.folderWatcher.helper.WatcherHelper;
import tech.smartaps.folderWatcher.model.BaseFolder;
import tech.smartaps.folderWatcher.model.OperationFolder;

import java.io.File;
import java.util.List;

public class BaseFolderWatcher extends FolderWatcher {

    private List<OperationFolder> operationFolders;

    public BaseFolderWatcher(BaseFolder baseFolder, List<OperationFolder> operationFolders) {
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
    public void treatment(List<OperationFolder> operationFolders) throws Exception {
        // go through all files in base folder
        File file = new File(this.getFolder().getPath());

        if(file.list() == null) throw new Exception( "This folder is empty.");
        else for(String filename : file.list()) {
            // filter by extension .txt
            if(filename.endsWith(".txt")) {

                // get operation
                String operation = WatcherHelper.readLines(filename);
                for(OperationFolder of : this.getOperationFolders()) {
                    // if operation pattern without whitespaces == folder pattern, move to folder
                    if(evaluateOperation(of.getPattern(), operation.replaceAll(" ", "").trim())) {
                        String source = this.getFolder().getPath() + "/" + filename;
                        String destination = of.getPath() + "/" + filename;
                        move(source, destination);
                        // get out of loop
                        break;
                    }
                }
            }
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                // execute treatment
                treatment(operationFolders);
            } catch (Exception exception) {
                exception.printStackTrace(System.out);
                break;
            }
        }
    }
}
