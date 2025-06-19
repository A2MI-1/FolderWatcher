package tech.smartaps.folderWatcher.watcher;

import tech.smartaps.folderWatcher.ICalculator;
import tech.smartaps.folderWatcher.folder.Folder;
import tech.smartaps.folderWatcher.folder.OperationFolder;
import tech.smartaps.folderWatcher.helper.WatcherHelper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

public class OperationFolderWatcher extends FolderWatcher {

    private OperationFolder folder;

    private List<String> checkedFiles;

    public OperationFolderWatcher(Folder baseFolder, OperationFolder folder) {
        super(baseFolder);
        this.setFolder(folder);
        this.checkedFiles = new ArrayList<>();
    }

    public OperationFolder getFolder() {
        return folder;
    }

    public void setFolder(OperationFolder folder) {
        this.folder = folder;
    }

    public List<String> getCheckedFiles() {
        return checkedFiles;
    }

    @Override
    public void treatment() throws Exception {

        for(String s : this.getFolder().getAllTextFiles()) {

            String filename = this.getFolder().getPath() + File.separator + s;

            // checking if file has not already been checked and computed
            if(!this.getCheckedFiles().contains(filename)) {
                // get operation
                String operation = WatcherHelper.readLines(filename);

                // if operation pattern == folder pattern, compute operation
                if(this.getFolder().isLikePattern(operation.replaceAll(" ", "").trim())) {
                    String result = getResult(operation, this.getFolder().getClassName());
                    appendResultInFile(filename, result);
                    // added filename into checked files
                    this.getCheckedFiles().add(filename);
                }
                // else move to base folder
                else {
                    String destination = this.getBaseFolder().getPath() + "/" + s;
                    this.getFolder().move(filename, destination);
                }
            }
        }
    }

    // get result of operation
    public String getResult(String operation, String className) throws Exception {
        // get all parameters of the operation
        List<Double> operationParameters = new ArrayList<>();

        // regular expression for signed number with optional float part
        String regex = "(-?\\d+(?:\\.\\d+)?)";

        // get all elements matching in this regex
        Matcher matcher = WatcherHelper.getMatcher(regex, operation.replaceAll(" ", "").trim());
        while (matcher.find()) {
            operationParameters.add(Double.parseDouble(matcher.group()));
        }

        // check if at least one number is found
        if (operationParameters.isEmpty()) throw new Exception("No valid numbers found in operation: " + operation);

        // get computing class by dynamic instantiation
        try {
            Class<?> operationClass = Class.forName(className);
            Object instance = operationClass.getDeclaredConstructor().newInstance();
            if(instance instanceof ICalculator) return ((ICalculator)instance).compute(operationParameters).toString();
            else throw new IllegalArgumentException(className + "doesn't implement ICalculator interface.");
        }
        catch (Exception exception) {
            throw new Exception(exception.getMessage());
        }
    }

    // append result
    public void appendResultInFile(String filename, String result) {
        try {
            // open a writer on a file
            FileWriter fileWriter = new FileWriter(filename, true);
            BufferedWriter writer = new BufferedWriter(fileWriter);
            writer.write(" = " + result);
            writer.close();

        } catch(IOException exception) {
            exception.printStackTrace(System.out);
        }
    }

}
