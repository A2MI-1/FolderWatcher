package tech.smartaps.folderWatcher.operationClass;

import tech.smartaps.folderWatcher.ICalculator;

import java.util.List;

public class Divide implements ICalculator {

    @Override
    public Double compute(List<Double> operationParameters) {
        Double result = operationParameters.get(0);
        for(int i = 1; i < operationParameters.size(); i++) {
            Double param = operationParameters.get(i);
            if(Double.compare(param, 0.0) == 0) throw new ArithmeticException("Operation parameter at index " + i + " is zero. Unable to divide.");
            result /= param;
        }
        return result;
    }
}
