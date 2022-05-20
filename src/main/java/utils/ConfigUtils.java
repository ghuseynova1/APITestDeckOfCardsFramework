package utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigUtils {
    public static Properties readConfigProperties(String fileName)  {
        fileName = fileName.trim();

        Properties configProperties = new Properties();
        try {
            InputStream inputStream = new FileInputStream(fileName);
            configProperties.load(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return configProperties;

    }
}
