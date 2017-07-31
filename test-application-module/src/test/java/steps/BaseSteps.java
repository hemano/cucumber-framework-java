package steps;

import com.cucumber.listener.Reporter;
import hemano.utils.driver.DriverFactory;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import ru.yandex.qatools.allure.annotations.Attachment;
import ru.yandex.qatools.allure.annotations.Step;

/**
 * Created by hemantojha on 05/09/16.
 */
@ContextConfiguration("classpath:Cucumber.xml")
public class BaseSteps {

    private static final Logger logger = LoggerFactory.getLogger(BaseSteps.class);

    public final WebDriver getWrappedDriver() {
        return DriverFactory.getDriver();
    }

    @Step("{0}")
    public void logToReport(String message) {
        logger.info(message);
        Reporter.addStepLog(message);
    }

    @Attachment()
    public byte[] takeScreenShot() {
        return ((TakesScreenshot) getWrappedDriver()).getScreenshotAs(OutputType.BYTES);
    }


}
