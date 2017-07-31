package hemano.utils;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import junit.framework.Assert;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by hemantojha on 05/08/16.
 */
public class AppiumServer {

    //    String appiumInstallationDir = "C:/Program Files (x86)";// e.g. in Windows
    String appiumInstallationDir = "/Applications";// e.g. for Mac
    private AppiumDriverLocalService service = null;

    private static AppiumServer instance;

    public static AppiumServer getInstance()
    {
        if (instance == null)
        {
            try {
                instance = new AppiumServer();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return instance;
    }



    private AppiumServer() throws IOException {
        File classPathRoot = new File(System.getProperty("user.dir"));
        String osName = System.getProperty("os.name");

        File file = new File(new File(classPathRoot, File.separator + "log"), "androidLog.txt");
        file.createNewFile();
        OutputStream stream = new FileOutputStream(file);

        if (osName.contains("Windows")) {
            service = AppiumDriverLocalService.buildService(new AppiumServiceBuilder()
                    .usingDriverExecutable(new File(appiumInstallationDir + File.separator + "Appium" + File.separator + "node.exe"))
                    .withAppiumJS(new File(appiumInstallationDir + File.separator + "Appium" + File.separator
                            + "node_modules" + File.separator + "appium" + File.separator + "bin" + File.separator + "appium.js"))
                    .withLogFile(new File(new File(classPathRoot, File.separator + "log"), "appiumLog.txt")));

        } else if (osName.contains("Mac")) {
//            service = AppiumDriverLocalService.buildService(new AppiumServiceBuilder()
//                    .usingDriverExecutable(new File(appiumInstallationDir + "/Appium.app/Contents/Resources/node/bin/node"))
//                    .withAppiumJS(new File(
//                            appiumInstallationDir + "/Appium.app/Contents/Resources/node_modules/appium/bin/appium.js"))
//                    .withLogFile(new File(new File(classPathRoot, File.separator + "log"), "androidLog.txt")));
            service = AppiumDriverLocalService.buildService(new AppiumServiceBuilder()
//                    .withArgument(GeneralServerFlag.LOG_LEVEL, "warn")
                    .usingDriverExecutable(new File("/usr/local/bin/node"))
                    .withAppiumJS(new File(
                            "/usr/local/lib/node_modules/appium/build/lib/main.js")));

            service.addOutPutStream(stream);

//                    .withLogFile(new File(new File(classPathRoot, File.separator + "log"), "androidLog.txt"))).addOutPutStream();

        } else {
            // you can add for other OS, just to track added a fail message
            Assert.fail("Starting appium is not supporting the current OS.");
        }
    }


    /**
     * Starts appium server
     */
    public void startAppiumServer() {
        service.start();
    }

    /**
     * Stops appium server
     */
    public void stopAppiumServer() {
        service.stop();
    }


}
