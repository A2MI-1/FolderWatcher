# Folder watcher program
This program was written as a test for a company after (re)learning the basics of Java, 
Richard Raposa's Java in 60 minutes a day.

The program works as follows: a thread monitors a folder defined as the base folder 
in which text files containing operations will be placed. The thread analyzes the 
list of .txt files in the folder, and according to each operation in a file, 
the file is directed to an appropriate folder.

In each appropriate folder, a thread analyzes the operation and adds the result to 
the end of the file.

If a user places a file in the wrong folder, the file is returned to the main folder, 
where it is switched to the appropriate folder.

Each of these folders can be configured using a JSON configuration file.

## Features
* Reading a JSON file and parsing a JSON file into POJO.
* Continuous monitoring of all operation files in addition to the main file.
* Analyze the operations in each file, move them to the appropriate folder and add the result of the calculation.
* Dynamic instantiation of the operation class adapted to each operation folder at runtime.
* Closing the program to the code and extensibility of operations from the outside.

## JSON configuration file structure
The structure of the configuration.json file is as follows. Keep in mind that 
it's a sample.
```json
{
  "baseFolder": {
    "path": "/path/to/base/folder"
  },
  "operationFolders": [
    {
      "path": "/path/to/addition/folder",
      "className": "com.example.addition",
      "pattern": "(-?\\d+(?:\\.\\d+)?)[+](-?\\d+(?:\\.\\d+)?)"
    },
    {
      "path": "/path/to/division/folder",
      "className": "com.example.division",
      "pattern": "(-?\\d+(?:\\.\\d+)?)[/](-?\\d+(?:\\.\\d+)?)"
    },
    {
      "path": "/path/to/multiplication/folder",
      "className": "com.example.multiplication",
      "pattern": "(-?\\d+(?:\\.\\d+)?)[*](-?\\d+(?:\\.\\d+)?)"
    },
    {
      "path": "/path/to/subtraction/folder",
      "className": "com.example.subtraction",
      "pattern": "(-?\\d+(?:\\.\\d+)?)[-](-?\\d+(?:\\.\\d+)?)"
    },
    {
      "path": "/path/to/fibonacci/folder",
      "className": "com.example.fibonacci",
      "pattern": "fibonacci\\(\\d+\\)"
    },
    {
      "etc": "etc..."
    }
  ]
}
```
and so on...

## Extension
As mentioned above, the program is closed to code, but can be extended from the 
outside. Each operation class must implement an interface containing a single method. 
This interface contains a single method with a list of Double parameters to match the 
number of parameters of any operation. Like the two parameters of an operation or the 
single parameter of a fibonacci calculation.

Thus, we have :

```java
import java.util.List;

public interface ICalculator {
    Double compute(List<Double> parameters);
}

// for an addition operation
public class Addition implements ICalculator {
    @Override
    public Double compute(List<Double> parameters) {
        Double result = 0.0;
        for(Double d : parameters) { result += d; }
        return result;
    }
}

// for a cosinus operation
public class Cosinus implements ICalculator {
    @Override
    public Double compute(List<Double> parameters) {
        return Math.cos(parameters.getFirst());
    }
}
```

In this way, all operation classes will be instantiated as ICalculator objects 
at runtime. 
```java
// remainder of class
Class<?> operationClass = Class.forName(className);
Object instance = operationClass.getDeclaredConstructor().newInstance();
if(instance instanceof ICalculator) return ((ICalculator)instance).compute(parameters);
// remainder of class
```

Thanks for reading and hope it will be a good help for any new or other developer.