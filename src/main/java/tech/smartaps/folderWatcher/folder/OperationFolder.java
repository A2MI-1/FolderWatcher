package tech.smartaps.folderWatcher.folder;

import tech.smartaps.folderWatcher.helper.WatcherHelper;

import java.util.regex.Matcher;

public class OperationFolder extends Folder {

    private String className, pattern;

    public String getClassName() {
        return className;
    }

    public String getPattern() {
        return pattern;
    }

    // evaluate operation
    public boolean isLikePattern(String operation) {
        // get operation matcher
        Matcher matcher = WatcherHelper.getMatcher(this.getPattern(), operation);
        // if we find a corresponding pattern, return true
        // else return false
        return matcher.find();
    }
}
