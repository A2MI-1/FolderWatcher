package tech.smartaps.folderWatcher.helper;

import com.google.gson.Gson;
import tech.smartaps.folderWatcher.configuration.ConfigurationModel;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WatcherHelper {

    // parse configuration file
    public static ConfigurationModel parseConfigurationModel(Gson gson, String filename) {
        // read configuration.json file
        String configurationFileAsString = readLines(filename);

        // return parsed file in POJO
        return gson.fromJson(configurationFileAsString, ConfigurationModel.class);
    }

    // read all lines in a file
    public static String readLines(String filename) {
        StringBuilder lines = new StringBuilder();
        try {
            // read filename
            BufferedReader reader = new BufferedReader(new FileReader(filename));

            // read all lines in file
            String line;
            while((line = reader.readLine()) != null) { lines.append(line); }
            reader.close();
        }
        catch (IOException exception) {
            exception.printStackTrace(System.out);
        }

        // return
        return lines.toString();
    }

    // get pattern matcher
    public static Matcher getMatcher(String regex, String text) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(text);
    }
}
