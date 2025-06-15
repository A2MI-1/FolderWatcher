package tech.smartaps.folderWatcher.model;

public class OperationFolder extends Folder {

    private String pattern, className;

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
