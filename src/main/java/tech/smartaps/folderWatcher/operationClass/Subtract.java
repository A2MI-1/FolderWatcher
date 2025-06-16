package tech.smartaps.folderWatcher.operationClass;

import tech.smartaps.folderWatcher.ICalculator;

import java.util.List;

public class Subtract implements ICalculator {
    @Override
    public Double compute(List<Double> operationParameters) {
        // get first element and remove it from parameters
        /* Double result = operationParameters.get(0);
        operationParameters.remove(0);
        for(Double d : operationParameters) {
            result -= d;
        }
        return result;
        */
        return 0.0;
    }
}
