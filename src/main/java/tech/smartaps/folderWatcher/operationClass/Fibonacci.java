package tech.smartaps.folderWatcher.operationClass;

import tech.smartaps.folderWatcher.ICalculator;

import java.util.List;

public class Fibonacci implements ICalculator {

    // fibonacci
    public Double fibonacci(Double n) {
        if (n <= 1) return n;
        return fibonacci(n - 1) + fibonacci(n - 2);
    }

    @Override
    public Double compute(List<Double> operationParameters) {
        return fibonacci(operationParameters.get(0));
    }
}
