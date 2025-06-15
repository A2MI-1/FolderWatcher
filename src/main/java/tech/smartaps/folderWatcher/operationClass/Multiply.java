package tech.smartaps.folderWatcher.operationClass;

import tech.smartaps.folderWatcher.ICalculator;

import java.util.List;

public class Multiply implements ICalculator {

    @Override
    public Double compute(List<Double> operationParameters) {
        Double result = 1.0;
        for(Double d : operationParameters) {
            result *= d;
        }
        return result;
    }
}
