package hemano.utils.listeners;

import hemano.utils.SessionContextManager;
import hemano.utils.driver.controller.WebDriverWebController;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class LogDriver.
 */
public class ReportingWebDriverEventListner implements WebDriverEventListener {

    /**
     * The log.
     */
    private static final Logger EVENTS_LOGGER = LoggerFactory.getLogger("WebDriverEventListener");

    /**
     * Instantiates a new log driver.
     */
    public ReportingWebDriverEventListner() {
        System.setProperty("org.uncommons.reportng.escape-output", "false");
    }


    public void afterNavigateRefresh(WebDriver driver) {
    }

    public void beforeNavigateRefresh(WebDriver driver) {
    }


    /**
     * Info.
     *
     * @param message the message
     */
    public static void info(String message) {
        EVENTS_LOGGER.info(message);
    }

    @Override
    public void beforeAlertAccept(WebDriver driver) {

    }

    @Override
    public void afterAlertAccept(WebDriver driver) {

    }

    @Override
    public void afterAlertDismiss(WebDriver driver) {

    }

    @Override
    public void beforeAlertDismiss(WebDriver driver) {

    }

    /* (non-Javadoc)
         * @see org.openqa.selenium.support.events.WebDriverEventListener#beforeNavigateTo(java.lang.String, org.openqa.selenium.WebDriver)
         */
    public void beforeNavigateTo(String url, WebDriver driver) {

    }

    /* (non-Javadoc)
     * @see org.openqa.selenium.support.events.WebDriverEventListener#afterNavigateTo(java.lang.String, org.openqa.selenium.WebDriver)
     */
    public void afterNavigateTo(String url, WebDriver driver) {
        info("Navigate to " + url);
    }

    /* (non-Javadoc)
     * @see org.openqa.selenium.support.events.WebDriverEventListener#beforeNavigateBack(org.openqa.selenium.WebDriver)
     */
    public void beforeNavigateBack(WebDriver driver) {

    }


    /* (non-Javadoc)
     * @see org.openqa.selenium.support.events.WebDriverEventListener#afterNavigateBack(org.openqa.selenium.WebDriver)
     */
    public void afterNavigateBack(WebDriver driver) {

    }

    /* (non-Javadoc)
     * @see org.openqa.selenium.support.events.WebDriverEventListener#beforeNavigateForward(org.openqa.selenium.WebDriver)
     */
    public void beforeNavigateForward(WebDriver driver) {

    }


    /* (non-Javadoc)
     * @see org.openqa.selenium.support.events.WebDriverEventListener#afterNavigateForward(org.openqa.selenium.WebDriver)
     */
    public void afterNavigateForward(WebDriver driver) {

    }


    /* (non-Javadoc)
     * @see org.openqa.selenium.support.events.WebDriverEventListener#beforeFindBy(org.openqa.selenium.By, org.openqa.selenium.WebElement, org.openqa.selenium.WebDriver)
     */
    public void beforeFindBy(By by, WebElement element, WebDriver driver) {

    }

    /* (non-Javadoc)
     * @see org.openqa.selenium.support.events.WebDriverEventListener#afterFindBy(org.openqa.selenium.By, org.openqa.selenium.WebElement, org.openqa.selenium.WebDriver)
     */
    public void afterFindBy(By by, WebElement element, WebDriver driver) {
    }

    /* (non-Javadoc)
     * @see org.openqa.selenium.support.events.WebDriverEventListener#beforeClickOn(org.openqa.selenium.WebElement, org.openqa.selenium.WebDriver)
     */
    public void beforeClickOn(WebElement element, WebDriver driver) {
        ((WebDriverWebController) SessionContextManager.getController()).highlight(element);
    }


    /* (non-Javadoc)
     * @see org.openqa.selenium.support.events.WebDriverEventListener#afterClickOn(org.openqa.selenium.WebElement, org.openqa.selenium.WebDriver)
     */
    public void afterClickOn(WebElement element, WebDriver driver) {

        String locator = element.toString().substring(element.toString().indexOf(">") + 2, element.toString().lastIndexOf("]"));
        info("The element with locator '" + locator + "' was clicked");
    }

    @Override
    public void beforeChangeValueOf(WebElement element, WebDriver driver, CharSequence[] keysToSend) {

    }

    @Override
    public void afterChangeValueOf(WebElement element, WebDriver driver, CharSequence[] keysToSend) {

    }


    /* (non-Javadoc)
     * @see org.openqa.selenium.support.events.WebDriverEventListener#beforeChangeValueOf(org.openqa.selenium.WebElement, org.openqa.selenium.WebDriver)
     */
    public void beforeChangeValueOf(WebElement element, WebDriver driver) {
        ((WebDriverWebController) SessionContextManager.getController()).highlight(element);
    }

    /* (non-Javadoc)
     * @see org.openqa.selenium.support.events.WebDriverEventListener#afterChangeValueOf(org.openqa.selenium.WebElement, org.openqa.selenium.WebDriver)
     */
    public void afterChangeValueOf(WebElement element, WebDriver driver) {

        String value = element.getAttribute("value");
        String locator = element.toString().substring(element.toString().indexOf(">") + 2, element.toString().lastIndexOf("]"));

        if (!value.isEmpty()) {
            info("Value '" + value + "' was typed in element with locator '" + locator + "'");
        }
    }


    /* (non-Javadoc)
     * @see org.openqa.selenium.support.events.WebDriverEventListener#beforeScript(java.lang.String, org.openqa.selenium.WebDriver)
     */
    public void beforeScript(String script, WebDriver driver) {

    }

    /* (non-Javadoc)
     * @see org.openqa.selenium.support.events.WebDriverEventListener#afterScript(java.lang.String, org.openqa.selenium.WebDriver)
     */
    public void afterScript(String script, WebDriver driver) {

    }


    /* (non-Javadoc)
     * @see org.openqa.selenium.support.events.WebDriverEventListener#onException(java.lang.Throwable, org.openqa.selenium.WebDriver)
     */
    public void onException(Throwable throwable, WebDriver driver) {

    }
}