package tech.smartaps.folderWatcher.watcher;

import tech.smartaps.folderWatcher.folder.Folder;

public abstract class FolderWatcher implements Runnable {

    protected Folder baseFolder;

    public FolderWatcher(Folder baseFolder) {
        this.setBaseFolder(baseFolder);
    }

    public Folder getBaseFolder() {
        return baseFolder;
    }

    public void setBaseFolder(Folder baseFolder) {
        this.baseFolder = baseFolder;
    }

    public abstract void treatment() throws Exception;

    @Override
    public void run() {
        while (true) {
            try {
                treatment();
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace(System.out);
                break;
            }
        }
    }
}
