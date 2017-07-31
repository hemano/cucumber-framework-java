package hemano.utils.driver.controller;

import io.appium.java_client.MobileElement;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class WebDriverMethods.
 */
public class IOSDriverController extends ControllerBase implements MobileController {

    /**
     * The Constant LOG.
     */
    private static final Logger WEBDRIVER_LOG = LoggerFactory.getLogger(IOSDriverController.class);

    /**
     * The driver.
     */
//    private IOSDriver<WebElement> driver;

    /**
     * The Constant TO_MILLIS.
     */
    private static final int TO_MILLIS = 1000;

    /**
     * The Constant THREAD_SLEEP.
     */
    private static final long THREAD_SLEEP = 100;

    /**
     * The Constant XPATH.
     */
    private static final String XPATH = "xpath";

    /**
     * The Constant NAME.
     */
    private static final String NAME = "name";

    /**
     * The Constant CLASS_NAME.
     */
    private static final String CLASS_NAME = "className";

    /**
     * The Constant UI_AUTOMATOR.
     */
    private static final String UI_AUTOMATOR = "uiAutomator";

    /**
     * The Constant UI_AUTOMATOR.
     */
    private static final String ACCESSIBILITY_ID = "accessibilityID";

    /**
     * The Constant LINK.
     */
    private static final String LINK = "link";

    /**
     * The Constant ID.
     */
    private static final String ID = "id";

    /**
     * Gets the driver.
     *
     * @return the driver
     */
    public WebDriver getDriver() {
        return driver;
    }

    /**
     * Sets the driver.
     *
     * @param driver the new driver
     */
    public void setDriver(WebDriver driver) {
        this.driver = (IOSDriver<WebElement>) driver;
    }

    @Override
    public void highlight(String locator) {

    }

    @Override
    public void highlight(String locator, String color) {

    }

    @Override
    public void scrollKeys(String[] list) {

    }

    @Override
    public void scrollDown(int pageDown) {

    }

    @Override
    public void scrollTillElementDisappear(WebElement webElement) {

    }

    @Override
    public void swipeUpFromTo(Point point1, Point point2) {

    }

    private MobileElement getElement(String locator) {
        return ((MobileElement) waitForElement(locator));
    }


    public void input(String locator, String value) {
        getElement(locator).setValue(value);
    }

    @Override
    public void hideKeyboard() {

    }

    /**
     * Determine locator.
     *
     * @param locator the locator
     * @return the by
     */
    public By determineLocator(String locator) {
        if (locator.startsWith(XPATH)) {
            return By.xpath(findLocatorSubstring(locator));
        } else if (locator.startsWith("//")) {
            return By.xpath(locator);
        } else if (locator.startsWith(NAME)) {
            return By.name(findLocatorSubstring(locator));
        } else if (locator.startsWith(LINK)) {
            return By.linkText(findLocatorSubstring(locator));
        } else if (locator.startsWith(ID)) {
            return By.id(findLocatorSubstring(locator));
        } else {
            return By.id(locator);
        }
    }

}