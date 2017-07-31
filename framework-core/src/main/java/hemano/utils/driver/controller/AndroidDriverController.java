package hemano.utils.driver.controller;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import org.apache.commons.lang3.NotImplementedException;
import org.openqa.selenium.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class WebDriverMethods.
 */
public class AndroidDriverController extends ControllerBase implements MobileController {

    /**
     * The Constant LOG.
     */
    private static final Logger WEBDRIVER_LOG = LoggerFactory.getLogger(AndroidDriverController.class);


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
    private static final String ACCESSIBILITY_ID = "accessibility id";

    /**
     * The Constant LINK.
     */
    private static final String LINK = "link";

    /**
     * The Constant ID.
     */
    private static final String ID = "id";


    /**
     * Sets the driver.
     *
     * @param driver the new driver
     */
    @Override
    public void setDriver(WebDriver driver) {

        this.driver = (AndroidDriver<WebElement>) driver;
    }

    /**
     * Gets the driver.
     *
     * @return the driver
     */
    public AppiumDriver getDriver() {
        return ((AppiumDriver) driver);
    }


    @Override
    public void highlight(String locator) {
        throw new NotImplementedException("");
    }

    @Override
    public void highlight(String locator, String color) {
        throw new NotImplementedException("");
    }

    /**
     * Determine locator.
     *
     * @param locator the locator
     * @return the by
     */
    // TODO: 27/03/17 update this method for Android Driver
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
        } else if (locator.startsWith(CLASS_NAME)) {
            return By.className(findLocatorSubstring(locator));
        } else if (locator.startsWith(ACCESSIBILITY_ID)) {
            return By.className(findLocatorSubstring(locator));
        } else if (locator.startsWith("for")) {
            return forFind(locator);
        } else {
            return By.id(locator);
        }
    }

    private By forFind(String value) {
        return By.xpath("//*[@content-desc=\"" + value + "\" or @resource-id=\"" + value +
                "\" or @text=\"" + value + "\"] | //*[contains(translate(@content-desc,\"" + value +
                "\",\"" + value + "\"), \"" + value + "\") or contains(translate(@text,\"" + value +
                "\",\"" + value + "\"), \"" + value + "\") or @resource-id=\"" + value + "\"]");

    }


    private MobileElement getElement(String locator) {
        return ((MobileElement) waitForElement(locator));
    }

    public void input(String locator, String value) {
        getElement(locator).setValue(value);
        hideKeyboard();
    }

    public void hideKeyboard() {
        getDriver().hideKeyboard();
    }

    // Generic scroll using send keys
    // Pass in values to be selected as a String array to the list parameter
    // Method will loop through looking for scroll wheels based on the number of
    // values you supply
    // For instance Month, Day, Year for a birthday would have this loop 3 times
    // dynamically selecting each scroll wheel
    // Code here shouldn't be modified
    public void scrollKeys(String[] list) {

        for (int i = list.length - 1; i >= 0; i--) {
//            android:id/numberpicker_input
//            android.widget.EditText
//
//            android:id/numberpicker_input
//            android.widget.EditText


            By meX = By.xpath("//android.widget.EditText[@resource-id='android:id/numberpicker_input']");
            MobileElement me = (MobileElement) driver.findElements(meX).get(i);

            TouchAction touchAction6 = new TouchAction(getDriver());
            touchAction6.longPress(me, 1000).release();
            (getDriver()).performTouchAction(touchAction6);

            (getDriver()).getKeyboard().pressKey(list[i] + "");
        }
        By meX = By.xpath("//android.widget.EditText[@resource-id='android:id/numberpicker_input']");
        MobileElement me = (MobileElement) driver.findElements(meX).get(list.length - 1);
        me.click();
    }

    public void getFocus(String locator) {
        WebElement element = waitForElement(locator);
        new TouchAction((MobileDriver) driver).moveTo(element).perform();
    }

    public void moveToElement(String locator) {
        int limit = 0;
        while (!elementExists(locator).isPresent() && limit < 5) {
            limit++;
            new TouchAction((MobileDriver) driver).longPress(0, 0).moveTo(0, 100).release().perform();
        }

        if (!elementExists(locator).isPresent()) {
            throw new ElementNotVisibleException("Unable to find element");
        }
    }

    public void scrollTillElementDisappear(WebElement webElement) {
        int limit = 0;
        while (elementExists(webElement).isPresent() && limit < 5) {
            limit++;
            new TouchAction((MobileDriver) driver).longPress(0, 0).moveTo(0, 100).release().perform();
        }
//
//        if (!elementExists(webElement).isPresent()) {
//            throw new ElementNotVisibleException("Unable to find element");
//        }
    }


    public void scrollDown(int pageDown) {
        int limit = 0;
        while (limit < pageDown) {
            limit++;
            new TouchAction((MobileDriver) driver).longPress(0, 0).moveTo(0, 100).release().perform();
        }
    }

    public void swipeUpFromTo(Point point1, Point point2) {
        new TouchAction((MobileDriver) driver).longPress(point1.getX(), point1.getY()).moveTo(point2.getX(), point2.getY()).release().perform();
        sleep(500);
    }


}