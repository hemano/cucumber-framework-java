package hemano.utils;

import hemano.utils.io.FileUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by hemantojha on 21/07/16.
 */
public class MyPropertyReader {

    protected Properties properties = new Properties();
    protected InputStream inputStream = null;

    public MyPropertyReader(String propertiesFileName) {
        loadProperties(propertiesFileName);
    }

    private void loadProperties(String fileName) {
        try {
            String propertiesFilePath;

            if (fileName.equals("preferences.properties")) {
//                inputStream = new FileInputStream(System.getProperty("user.dir") + "/" +"preferences.properties");

                Path path = Paths.get(System.getProperty("user.dir"), "preferences.properties");
                boolean fileOptional = Files.exists(path);

                if (!fileOptional) {
                    Path userDirPath = Paths.get(System.getProperty("user.dir")).getParent();
                    path = Paths.get(userDirPath.toString(), "preferences.properties");
                }
                inputStream = new FileInputStream(path.toFile());

            } else {
                propertiesFilePath = "config/" + fileName;
                URL url = FileUtils.locateFromClasspath(propertiesFilePath);
                inputStream = new FileInputStream(url.getPath());
            }

            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String readProperty(String key) {
        return properties.getProperty(key);
    }

    public Map<String, String> getAllParams(){
        Map properties = this.properties;
        return new HashMap<String, String>(properties);
    }
}
