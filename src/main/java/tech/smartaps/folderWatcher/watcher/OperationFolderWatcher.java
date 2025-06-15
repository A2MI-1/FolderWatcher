package tech.smartaps.folderWatcher.watcher;

import tech.smartaps.folderWatcher.ICalculator;
import tech.smartaps.folderWatcher.helper.WatcherHelper;
import tech.smartaps.folderWatcher.model.OperationFolder;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

public class OperationFolderWatcher extends FolderWatcher {

    private List<String> checkedFiled;

    private String baseFolderPath;

    public OperationFolderWatcher(OperationFolder operationFolder, String baseFolderPath) {
        super(operationFolder);
        this.setBaseFolderPath(baseFolderPath);
        this.setCheckedFiled(new ArrayList<>());
    }

    public String getBaseFolderPath() {
        return baseFolderPath;
    }

    public void setBaseFolderPath(String baseFolderPath) {
        this.baseFolderPath = baseFolderPath;
    }

    public List<String> getCheckedFiled() {
        return checkedFiled;
    }

    public void setCheckedFiled(List<String> checkedFiled) {
        this.checkedFiled = checkedFiled;
    }

    // treatment
    public void treatment(String baseFolderPath) throws Exception {
        for(String filename : getAllTextFiles()) {
            // get operation
            String operation = WatcherHelper.readLines(filename);
            OperationFolder of = (OperationFolder) this.getFolder();

            // if operation pattern == folder pattern, compute operation
            if(evaluateOperation(of.getPattern(), operation)) {
                String result = getResult(operation, of.getClassName());
                appendResultInFile(filename, result);
            }
            // else move to base folder
            else {
                String source = of.getPath() + "/" + filename;
                String destination = this.getBaseFolderPath() + "/" + filename;
                move(source, destination);
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

    @Override
    public void run() {
        while (true) {
            try {
                treatment(this.getBaseFolderPath());
            } catch (Exception exception) {
                exception.printStackTrace(System.out);
                break;
            }
        }
    }
}
