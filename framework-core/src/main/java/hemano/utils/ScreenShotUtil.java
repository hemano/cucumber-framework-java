package hemano.utils;

import com.cucumber.listener.Reporter;
import hemano.utils.driver.DriverFactory;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by hemantojha on 16/04/17.
 */
public class ScreenShotUtil {

    private static final Logger logger = LoggerFactory.getLogger( RestUtil.class );

    private static WebDriver driver;
    public static void takeScreenShot()  {
        driver = DriverFactory.getDriver();

        try {
            File scrFile = null;
            File file = null;
            try {
                scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            } catch (Exception e) {
                logger.error("Failed to generate screenshot, problem with driver: {} ", e.getMessage());
            }

            if (scrFile != null) {
                file = createScreenshotFile();
                FileUtils.copyFile(scrFile, file);
            }

            Reporter.addScreenCaptureFromPath(makeRelativePath(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * make a filename within the reportng folders so we can use relative paths.
     *
     * @return the file
     */
    protected static File createScreenshotFile() {

        if (System.getProperty("screenShotDirectory") != null) {

            File outputDir = new File(System.getProperty("screenShotDirectory"));
            try {
                outputDir.mkdirs();
            } catch (SecurityException e) {
                logger.error(e.getMessage());
                return null;
            }

            return new File(outputDir, "screenshot-" + System.nanoTime() + ".png");
        }else {
            try {
                File outputDir = new File(getPath("Screenshot"));
                return outputDir;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }



        return new File("screenshot-" + System.nanoTime() + ".png");
    }


    private static String getPath(String nameTest) throws IOException {
        File directory = new File(".");
        String newFileNamePath = directory.getCanonicalPath() + "/target/screenShots/" + getFileName(nameTest);
//        return newFileNamePath;
        return makeRelativePath(new File(newFileNamePath));
    }

    private static String getFileName(String nameTest) throws IOException {
        DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy_hh.mm.ss");
        Date date = new Date();
        return dateFormat.format(date) + "_" + nameTest + ".png";
    }

    /**
     * Make relative path.
     *
     * @param file
     *            the file instance
     * @return the file path
     */
    public static String makeRelativePath(File file) {
        String outputDir = new File(System.getProperty("outputDirectory")).getParent();
        if (outputDir != null) {
            String absolute = file.getAbsolutePath();
            int beginIndex = absolute.indexOf(outputDir) + outputDir.length();
            String relative = absolute.substring(beginIndex);
            return ".." + relative.replace('\\', '/');
        }
        return file.getPath();
    }
}
