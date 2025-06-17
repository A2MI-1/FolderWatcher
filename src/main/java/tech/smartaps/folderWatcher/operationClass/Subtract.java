package tech.smartaps.folderWatcher.operationClass;

import tech.smartaps.folderWatcher.ICalculator;

import java.util.List;

public class Subtract implements ICalculator {
    @Override
    public Double compute(List<Double> operationParameters) {
        Double result = operationParameters.get(0);
        for(int i = 1; i < operationParameters.size(); i++) {
            result -= operationParameters.get(i);
        }
        return result;
    }
}
