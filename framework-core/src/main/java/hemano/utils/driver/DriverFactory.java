package hemano.utils.driver;

import hemano.utils.WebDriverThread;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.yandex.qatools.allure.annotations.Step;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by hemantojha on 21/07/16.
 */
public class DriverFactory {

    private static final Logger logger = LoggerFactory.getLogger(DriverFactory.class);

    private static List<WebDriverThread> webDriverThreadPool =
            Collections.synchronizedList(new ArrayList<WebDriverThread>());

    private static ThreadLocal<WebDriverThread> driverThread;

//    private static String driver_id;

    public static void instantiateDriverObject() {

        driverThread = new ThreadLocal<WebDriverThread>() {

            @Override
            public WebDriverThread initialValue() {
                WebDriverThread webDriverThread = new WebDriverThread();
                webDriverThreadPool.add(webDriverThread);
                return webDriverThread;
            }
        };

    }

    public static <E> WebDriver getDriver() {
        try {
            return driverThread.get().getDriver();

        } catch (Exception e) {
            throw new WebDriverException("Could not start the Driver", e);
        }

    }


    public static void closeDriverObjects() {
        for (WebDriverThread webDriverThread : webDriverThreadPool) {

            webDriverThread.quitDriver();
        }

    }

    @Step("{0}")
    public void logToReport(String message) {
        logger.info(message); //or System.out.println(message);
    }

}
