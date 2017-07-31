package hemano.utils.driver.controller;


import com.cucumber.listener.Reporter;
import com.google.common.base.Optional;
import hemano.utils.SessionContextManager;
import hemano.utils.driver.controller.commonApi.KeyInfo;
import hemano.utils.driver.controller.webdriverApi.ByExtended;
import hemano.utils.listeners.ReportingWebDriverEventListner;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The Class WebDriverMethods.
 */
public abstract class ControllerBase extends WebControllerBase {

    /**
     * The Constant LOG.
     */
    private static final Logger WEBDRIVER_LOG = LoggerFactory.getLogger(ControllerBase.class);

    /**
     * The driver.
     */
    protected WebDriver driver;

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
     * The Constant CSS.
     */
    private static final String CSS = "css";

    /**
     * The Constant NAME.
     */
    private static final String NAME = "name";

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
        this.driver = driver;
    }

    /*
     * enableActionsLogging()
     */

    public void enableActionsLogging() {
        this.setDriver(new EventFiringWebDriver(driver).register(new ReportingWebDriverEventListner()));
    }

    /*
     * disableActionsLogging()
     */
    public void disableActionsLogging() {
        this.setDriver(new EventFiringWebDriver(driver).unregister(new ReportingWebDriverEventListner()));
    }


    /**
     * Find locator substring.
     *
     * @param locator the element locator
     * @return the string after the character '='
     */
    protected String findLocatorSubstring(String locator) {
        return locator.substring(locator.indexOf('=') + 1);
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
        } else if (locator.startsWith(CSS)) {
            return ByExtended.cssSelector(findLocatorSubstring(locator));
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

    public Optional<WebElement> elementExists(String locator) {
        try {
            return Optional.of(getDriver().findElement(determineLocator(locator)));
//            if (isComponentPresent(locator)) {
//                return Optional.of(findElements(locator).get(0));
//            } else {
//                return Optional.absent();
//            }

        } catch (NoSuchElementException e) {
            return Optional.absent();
        }
    }

    public Optional<WebElement> elementExists(WebElement element) {
        try {
            if (element.isDisplayed()) {
                return Optional.of(element);
            } else {
                return Optional.absent();
            }
        } catch (NoSuchElementException e) {
            return Optional.absent();
        }
    }

    /**
     * Wait for element till the default period
     *
     * @param locator
     */

    public WebElement waitForElement(String locator) {
        return waitForElement(locator, SessionContextManager.getInstance().getWaitForElement());
    }

    /**
     *
     */

    public WebElement waitForElement(String locator, long waitSeconds) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, waitSeconds, THREAD_SLEEP);
            return wait.until(ExpectedConditions.visibilityOfElementLocated(determineLocator(locator)));

        } catch (TimeoutException e) {
            takeScreenShot();
            throw new TimeoutException("Exception has been thrown", e);
        }
    }

    /**
     * waitForElementInvisibility(java.lang.String)
     */

    public void waitForElementInvisibility(String locator) {
        waitForElementInvisibility(locator, SessionContextManager.getInstance().getWaitForElementInvisibility());
    }

    /**
     * waitForElementInvisibility(java.lang.String, long)
     */

    public void waitForElementInvisibility(String locator, long waitSeconds) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, waitSeconds);
            wait.until(ExpectedConditions.invisibilityOfElementLocated(determineLocator(locator)));
        } catch (Exception e) {
            takeScreenShot();
            throw new TimeoutException("Exception has been thrown", e);
        }

    }

    /**
     * waitForElementPresence(java.lang.String)
     */
    public WebElement waitForElementPresence(String locator) {
        return waitForElementPresence(locator, SessionContextManager.getInstance().getWaitForElement());
    }

	/*
     *
	 * (java.lang.String)
	 */

    public WebElement waitForElementPresence(String locator, long waitSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, waitSeconds, THREAD_SLEEP);
        return wait.until(ExpectedConditions.presenceOfElementLocated(determineLocator(locator)));
    }

    /*
     * 
     * (org.openqa.selenium.String)
     */

    public List<WebElement> findElements(String locator) {
        WebDriverWait wait = new WebDriverWait(getDriver(), SessionContextManager.getInstance().getWaitForElement(), THREAD_SLEEP);
        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(determineLocator(locator)));
    }

    public WebElement findChildElement(String locator, int child) {
        return findElements(locator).get(child - 1);
    }

    /*
     * (non-Javadoc)
     * org.openqa.selenium.String, java.lang.String)
     */

    public void input(String locator, String value) {
        WebElement element = waitForElement(locator);
        element.sendKeys(value);
    }

    /*
     * (non-Javadoc)
     *
     * org.openqa.selenium.String)
     */

    public void press(String locator) {
        waitForElement(locator).click();
    }

    /*
     * (non-Javadoc)
     * pressAndWaitForPageToLoad(java.lang.String)
     */

    public void pressAndWaitForPageToLoad(String locator) {
        press(locator);
    }

    /*
     * (non-Javadoc)
     * pressAndWaitForElement(java.lang.String, java.lang.String, long)
     */

    public void pressAndWaitForElement(String pressLocator, String elementToWaitLocator, long waitSeconds) {
        press(pressLocator);
        waitForElement(elementToWaitLocator, waitSeconds);
    }

    /**
     * @see WebController
     * pressAndWaitForElement(java.lang.String, java.lang.String)
     */

    public void pressAndWaitForElement(String pressLocator, String elementToWaitLocator) {
        pressAndWaitForElement(pressLocator, elementToWaitLocator, SessionContextManager.getInstance().getWaitForElement());
    }

    /*
     * (non-Javadoc)
     *
     * @see WebController#
     * pressAndClickOkInAlert(java.lang.String)
     */

    public void pressAndClickOkInAlert(String locator) {
        press(locator);
        clickOkInAlert();
    }

    /*
     * (non-Javadoc)
     *
     * @see WebController#
     * pressAndClickOkInAlertNoPageLoad(java.lang.String)
     */

    public void pressAndClickOkInAlertNoPageLoad(String locator) {
        pressAndClickOkInAlert(locator);
    }

    /*
     * (non-Javadoc)
     *
     * @see WebController#
     * pressAndClickCancelInAlert(java.lang.String)
     */

    public void pressAndClickCancelInAlert(String locator) {
        press(locator);
        clickCancelInAlert();
    }

    /**
     * Gets the select object.
     *
     * @param locator the locator
     * @return the select object
     */
    public Select getSelectObject(String locator) {
        return new Select(waitForElementPresence(locator));

    }

    /*
     * (non-Javadoc)
     *
     * @see
     * WebController#select
     * (org.openqa.selenium.String, java.lang.String)
     */

    public void select(String locator, String option) {
        getSelectObject(locator).selectByVisibleText(option);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * WebController#selectByValue
     * (java.lang.String, java.lang.String)
     */

    public void selectByValue(String locator, String value) {
        getSelectObject(locator).selectByValue(value);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * WebController#multiSelectAdd
     * (java.lang.String, java.lang.String)
     */

    public void multiSelectAdd(String locator, String option) {

    }

    /*
     * (non-Javadoc)
     *
     * @see
     * WebController#clear(
     * org.openqa.selenium.String, java.lang.String)
     */

    public void clear(String locator) {
        WebElement element = waitForElement(locator);
        element.clear();

    }

    /*
     * (non-Javadoc)
     *
     * @see
     * WebController#getBuilder
     * ()
     */

    public Actions getBuilder() {
        return new Actions(driver);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * WebController#hover(
     * org.openqa.selenium.String)
     */

    public void mouseOver(String locator) {
        getBuilder().moveToElement(waitForElement(locator)).perform();
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * WebController#mouseUp
     * (java.lang.String)
     */

    public void mouseUp(String locator) {
        getBuilder().release(waitForElement(locator)).perform();
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * WebController#mouseDown
     * (java.lang.String)
     */

    public void mouseDown(String locator) {
        getBuilder().clickAndHold(waitForElement(locator)).perform();
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * WebController#doubleClick
     * (org.openqa.selenium.String)
     */

    public void doubleClick(String locator) {
        getBuilder().doubleClick(waitForElement(locator)).perform();
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * WebController#click(
     * java.lang.String)
     */
    public void click(String locator) {
        waitForElement(locator).click();
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * WebController#typeKeys
     * (java.lang.String, java.lang.String)
     */

    public void typeKeys(String locator, String value) {
        waitForElement(locator).sendKeys(value);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * WebController#keyDown
     * (java.lang.String, org.openqa.selenium.Keys)
     */

    public void keyDown(String locator, KeyInfo thekey) {
        getBuilder().keyDown(waitForElement(locator), thekey.getKey()).perform();

    }

    /*
     * (non-Javadoc)
     *
     * @see
     * WebController#keyUp(
     * java.lang.String, org.openqa.selenium.Keys)
     */

    public void keyUp(String locator, KeyInfo thekey) {
        getBuilder().keyUp(waitForElement(locator), thekey.getKey()).perform();
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * WebController#keyDown
     * (org.openqa.selenium.Keys)
     */

    public void keyDown(KeyInfo thekey) {
        getBuilder().keyDown(thekey.getKey()).perform();
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * WebController#keyUp(
     * org.openqa.selenium.Keys)
     */

    public void keyUp(KeyInfo thekey) {
        getBuilder().keyUp(thekey.getKey()).perform();
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * WebController#keyPress
     * (org.openqa.selenium.Keys)
     */

    public void keyPress(KeyInfo thekey) {
        getBuilder().sendKeys(thekey.getKey()).perform();
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * WebController#keyPress
     * (java.lang.String, org.openqa.selenium.Keys)
     */

    public void keyPress(String locator, KeyInfo thekey) {
        waitForElement(locator).sendKeys(thekey.getKey());

    }

    /*
     * (non-Javadoc)
     *
     * @see
     * WebController#getText
     * (org.openqa.selenium.String)
     */

    public String getText(String locator) {
        return waitForElement(locator).getText();
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * WebController#getFocus
     * (java.lang.String)
     */

    public void getFocus(String locator) {
        WebElement element = waitForElement(locator);
        if ("input".equals(element.getTagName())) {
            element.sendKeys("");
        } else {
            new Actions(driver).moveToElement(element).perform();

        }
    }

    /*
     * (non-Javadoc)
     *
     * @see WebController#
     * getSelectedLabel(org.openqa.selenium.String)
     */

    public String getSelectedOption(String locator) {
        return getSelectObject(locator).getFirstSelectedOption().getText();
    }

    /*
     * (non-Javadoc)
     *
     * @see WebController#
     * getSelectOptions(org.openqa.selenium.String)
     */

    public List<String> getSelectedOptions(String locator) {
        List<String> optionValues = new ArrayList<String>();
        Select menuList = getSelectObject(locator);
        List<WebElement> options = menuList.getAllSelectedOptions();
        for (WebElement option : options) {
            optionValues.add(option.getText());
        }
        return optionValues;

    }

    /*
     * (non-Javadoc)
     *
     * @see
     * WebController#getInputValue
     * (org.openqa.selenium.String)
     */

    public String getInputValue(String locator) {
        return waitForElement(locator).getAttribute("value");
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * WebController#isAlertPresent
     * ()
     */

    public boolean isAlertPresent() {
        WebDriverWait wait = new WebDriverWait(driver, 0);
        try {
            wait.until(ExpectedConditions.alertIsPresent());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * WebController#isTextPresent
     * (java.lang.String)
     */

    public boolean isTextPresent(String value) {
        return driver.getPageSource().contains(value);
    }

    /*
     * (non-Javadoc)
     *
     * @see WebController#
     * isTextNotPresent(java.lang.String)
     */

    public boolean isTextNotPresent(String value) {
        return !driver.getPageSource().contains(value);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * WebController#refresh()
     */

    public String getPageSource() {
        return driver.getPageSource();
    }

    /*
     * (non-Javadoc)
     *
     * @see WebController#
     * isComponentEditable(org.openqa.selenium.String)
     */

    public boolean isComponentEditable(String locator) {
        return waitForElement(locator).isEnabled();
    }

    /*
     * (non-Javadoc)
     *
     * @see WebController#
     * isComponentDisabled(org.openqa.selenium.String)
     */

    public boolean isComponentDisabled(String locator) {
        return !waitForElement(locator).isEnabled();
    }

    /*
     * (non-Javadoc)
     *
     * @see WebController#
     * isComponentPresent(org.openqa.selenium.String)
     */

    public boolean isComponentPresent(String locator) {
        return getDriver().findElements(determineLocator(locator)).size() != 0;
    }

    /*
     * (non-Javadoc)
     *
     * @see WebController#
     * isComponentPresent(java.lang.String, long)
     */

    public boolean isComponentPresent(String locator, long seconds) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, seconds);
            wait.until(ExpectedConditions.presenceOfElementLocated(determineLocator(locator)));
            return true;
        } catch (Exception e) {
            return false;

        }
    }

    /*
     * (non-Javadoc)
     *
     * @see WebController#
     * isComponentNotPresent(org.openqa.selenium.String)
     */

    public boolean isComponentNotPresent(String locator) {
        return driver.findElements(determineLocator(locator)).size() == 0;
    }

    /*
     * (non-Javadoc)
     *
     * @see WebController#
     * isComponentVisible(org.openqa.selenium.String)
     */

    public boolean isComponentVisible(String locator) {
        return isComponentPresent(locator) && driver.findElement(determineLocator(locator)).isDisplayed();
    }

    /*
     * (non-Javadoc)
     *
     * @see WebController#
     * isComponentVisible(java.lang.String, long)
     */

    public boolean isComponentVisible(String locator, long seconds) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, seconds);
            wait.until(ExpectedConditions.visibilityOfElementLocated(determineLocator(locator)));
            return true;
        } catch (TimeoutException e) {
            return false;

        }
    }


    /*
     * (non-Javadoc)
     *
     * @see WebController#
     * isComponentVisible(java.lang.String, long)
     */
    public boolean isComponentVisibleWithTime(String locator) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, SessionContextManager.getInstance().getWaitForElement());
            wait.until(ExpectedConditions.visibilityOfElementLocated(determineLocator(locator)));
            return true;
        } catch (TimeoutException e) {
            return false;

        }
    }

    /*
     * (non-Javadoc)
     *
     * @see WebController#
     * isComponentNotVisible(org.openqa.selenium.String)
     */

    public boolean isComponentNotVisible(final String locator) {
        return !isComponentVisible(locator);
    }

    /*
     * (non-Javadoc)
     *
     * @see WebController#
     * isComponentNotVisible(java.lang.String, long)
     */

    public boolean isComponentNotVisible(String locator, long seconds) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, seconds);
            wait.until(ExpectedConditions.invisibilityOfElementLocated(determineLocator(locator)));
            return true;
        } catch (TimeoutException e) {
            return false;

        }
    }

    /*
     * (non-Javadoc)
     *
     * @see WebController#
     * isComponentSelected(org.openqa.selenium.String)
     */

    public boolean isComponentSelected(String locator) {
        return waitForElement(locator).isSelected();
    }

    /*
     * (non-Javadoc)
     *
     * @see WebController#
     * isComponentNotSelected(org.openqa.selenium.String)
     */

    public boolean isComponentNotSelected(String locator) {
        return !waitForElement(locator).isSelected();
    }

    /**
     * Wait for alert.
     *
     * @return the alert
     */
    public Alert waitForAlert() {
        WebDriverWait wait = new WebDriverWait(driver, SessionContextManager.getInstance().getWaitForElement());
        return wait.until(ExpectedConditions.alertIsPresent());
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * WebController#clickOkInAlert
     * ()
     */
    public void clickOkInAlert() {

        waitForAlert().accept();
    }

    /*
     * (non-Javadoc)
     *
     * @see WebController#
     * clickCancelInAlert()
     */
    public void clickCancelInAlert() {

        waitForAlert().dismiss();
    }

    public void close() {
        driver.close();
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * WebController#quit()
     */

    public void quit() {
        driver.quit();
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * WebController#pressLinkName
     * (java.lang.String)
     */

    public void pressLinkName(String linkName) {
        (new WebDriverWait(driver, SessionContextManager.getInstance().getWaitForElement())).until(ExpectedConditions.visibilityOfElementLocated((By.linkText(linkName)))).click();

    }

    /*
     * (non-Javadoc)
     *
     * @see WebController#
     * pressLinkNameAndWaitForPageToLoad(java.lang.String)
     */

    public void pressLinkNameAndWaitForPageToLoad(String linkName) {
        pressLinkName(linkName);
    }

    /*
     * (non-Javadoc)
     *
     * @see WebController#
     * pressLinkNameAndClickOkInAlert(java.lang.String)
     */

    public void pressLinkNameAndClickOkInAlert(String linkName) {
        pressLinkName(linkName);
        clickOkInAlert();
    }

    /*
     * (non-Javadoc)
     *
     * @see WebController#
     * pressLinkNameAndClickOkInAlertNoPageLoad(java.lang.String)
     */

    public void pressLinkNameAndClickOkInAlertNoPageLoad(String linkName) {
        pressLinkNameAndClickOkInAlert(linkName);
    }

    /*
     * (non-Javadoc)
     *
     * @see WebController#
     * pressLinkNameAndClickCancelInAlert(java.lang.String)
     */

    public void pressLinkNameAndClickCancelInAlert(String linkName) {
        pressLinkName(linkName);
        clickCancelInAlert();

    }


    /*
     * (non-Javadoc)
     *
     * @see WebController#
     * getAttributeValue(java.lang.String, java.lang.String)
     */

    public String getAttributeValue(String locator, String attribute) {
        return waitForElement(locator).getAttribute(attribute);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * WebController#dragAndDrop
     * (java.lang.String, java.lang.String)
     */

    public void dragAndDrop(String locatorFrom, String locatorTo) {
        getBuilder().dragAndDrop(waitForElement(locatorFrom), waitForElement(locatorTo)).perform();
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * WebController#dragAndDrop
     * (java.lang.String, int, int)
     */

    public void dragAndDrop(String locatorFrom, int xOffset, int yOffset) {
        getBuilder().dragAndDropBy(waitForElement(locatorFrom), xOffset, yOffset).perform();
    }

    /*
     * (non-Javadoc)
     *
     * @see WebController#
     * switchToNewWindow()
     */

    /*
     * (non-Javadoc)
     *
     * @see WebController#
     * getAllListOptions(java.lang.String)
     */

    public List<String> getAllListOptions(String locator) {
        List<String> optionValues = new ArrayList<String>();
        Select menuList = getSelectObject(locator);
        List<WebElement> options = menuList.getOptions();
        for (WebElement option : options) {
            optionValues.add(option.getText());
        }
        return optionValues;
    }


    /*
     * (non-Javadoc)
     *
     * @see WebController#
     * getNumberOfElementsMatchLocator(java.lang.String)
     */

    public int getNumberOfElementsMatchLocator(String locator) {
        return driver.findElements(determineLocator(locator)).size();
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * WebController#moveToElement
     * (java.lang.String, int, int)
     */

    public void moveToElement(String locator, int x, int y) {
        getBuilder().moveToElement(waitForElement(locator), x, y).perform();
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * WebController#moveToElement
     * (java.lang.String)
     */

    public void moveToElement(String locator) {
        getBuilder().moveToElement(waitForElement(locator)).perform();
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * WebController#moveByOffset
     * (int, int)
     */

    public void moveByOffset(int xOffset, int yOffset) {
        getBuilder().moveByOffset(xOffset, yOffset).perform();
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * WebController#getAlertText
     * ()
     */

    public String getAlertText() {
        return waitForAlert().getText();

    }

    /*
     * (non-Javadoc)
     *
     * @see WebController#
     * promptInputPressOK(java.lang.String)
     */

    public void promptInputPressOK(String inputMessage) {
        Alert alert = waitForAlert();
        alert.sendKeys(inputMessage);
        alert.accept();

    }

    /*
     * (non-Javadoc)
     *
     * @see WebController#
     * promptInputPressCancel(java.lang.String)
     */

    public void promptInputPressCancel(String inputMessage) {
        Alert alert = waitForAlert();
        alert.sendKeys(inputMessage);
        alert.dismiss();

    }

    /*
     * (non-Javadoc)
     *
     * @see WebController#
     * getElementPosition(java.lang.String)
     */

    public Point getElementPosition(String locator) {
        return waitForElement(locator).getLocation();
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * WebController#refresh()
     */

    public void refresh() {
        driver.navigate().refresh();
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * WebController#getRowsNumber
     * (java.lang.String)
     */

    public int getNumberOfTotalRows(String locator) {
        return waitForElement(locator).findElements(By.cssSelector("tbody tr")).size();
    }

    /*
     * (non-Javadoc)
     *
     * @see WebController#
     * getColumnsNumber(java.lang.String)
     */

    public int getNumberOfTotalColumns(String locator) {
        return waitForElement(locator).findElements(By.cssSelector("tbody tr:nth-child(1) td")).size();
    }


        /*
     * (non-Javadoc)
     *
     * @see
     * WebController#takeScreenShot
     * (java.io.File, java.lang.String)
     */

    public void takeScreenShot() {

        try {
            File scrFile = null;
            File file = null;
            try {
                scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            } catch (Exception e) {
                WEBDRIVER_LOG.error("Failed to generate screenshot, problem with driver: {} ", e.getMessage());
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

}