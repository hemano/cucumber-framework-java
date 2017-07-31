package hemano.utils.driver.controller;


import hemano.utils.JavaScriptHelper;
import hemano.utils.SessionContextManager;
import hemano.utils.driver.controller.commonApi.KeyInfo;
import hemano.utils.listeners.ReportingWebDriverEventListner;
import org.hamcrest.Matchers;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.HttpCookie;
import java.util.*;

import static hemano.utils.misc.UpdateAssert.assertThat;

/**
 * The Class WebDriverMethods.
 */
public class WebDriverWebController extends ControllerBase{

    /**
     * The Constant LOG.
     */
    private static final Logger WEBDRIVER_LOG = LoggerFactory.getLogger(WebDriverWebController.class);

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

    /*
     * waitForPageLoading(java.lang.String)
     */
    
    public void waitForCondition(String jscondition) {
        waitForCondition(jscondition, SessionContextManager.getInstance().getWaitForElement());
    }

    /*
     * waitForCondition(java.lang.String, long)
     */
    
    public void waitForCondition(String jscondition, long waitSeconds) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        boolean conditionResult;
        long startTime = System.currentTimeMillis();
        do {
            conditionResult = ((Boolean) js.executeScript(jscondition)).booleanValue();
            try {
                Thread.sleep(THREAD_SLEEP);
            } catch (InterruptedException e) {
                WEBDRIVER_LOG.error(e.getMessage());
            }
        } while (!conditionResult && System.currentTimeMillis() - startTime <= waitSeconds * TO_MILLIS);
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

//    /**
//     * Determine locator.
//     *
//     * @param locator the locator
//     * @return the by
//     */
//    public By determineLocator(String locator) {
//        if (locator.startsWith(XPATH)) {
//            return By.xpath(findLocatorSubstring(locator));
//        } else if (locator.startsWith("//")) {
//            return By.xpath(locator);
//        } else if (locator.startsWith(CSS)) {
//            return ByExtended.cssSelector(findLocatorSubstring(locator));
//        } else if (locator.startsWith(NAME)) {
//            return By.name(findLocatorSubstring(locator));
//        } else if (locator.startsWith(LINK)) {
//            return By.linkText(findLocatorSubstring(locator));
//        } else if (locator.startsWith(ID)) {
//            return By.id(findLocatorSubstring(locator));
//        } else {
//            return By.id(locator);
//        }
//    }

    /**
     * Wait for element till the default period
     *
     * @param locator
     */
    
    public WebElement waitForElement(String locator) {
        return waitForElement(locator, SessionContextManager.getInstance().getWaitForElement());
    }

    public WebElement waitForElementClickable(String locator) {
        WebDriverWait wait = new WebDriverWait(driver, SessionContextManager.getInstance().getWaitForElement(), THREAD_SLEEP);
        return wait.until(ExpectedConditions.elementToBeClickable(determineLocator(locator)));

    }

    
    public WebElement waitForElement(WebElement webElement) {
        return waitForElement(getFullXpath(webElement), SessionContextManager.getInstance().getWaitForElement());
    }

    /**
     *
     */
    
    public WebElement waitForElement(String locator, long waitSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, waitSeconds, THREAD_SLEEP);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(determineLocator(locator)));
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
        WebDriverWait wait = new WebDriverWait(driver, waitSeconds);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(determineLocator(locator)));

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
     * (non-Javadoc)
     * org.openqa.selenium.String, java.lang.String)
     */
    
    public void input(String locator, String value) {
        WebElement element = waitForElement(locator);
        element.clear();
        element.sendKeys(value);
    }

    /*
     * (non-Javadoc)
     *
     * org.openqa.selenium.String)
     */
    
    public void press(String locator) {
        waitForElementClickable(locator).click();
    }

    /*
     * (non-Javadoc)
     * pressAndWaitForPageToLoad(java.lang.String)
     */
    
    public void pressAndWaitForPageToLoad(String locator) {
        press(locator);
        waitForPageToLoad();
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
        waitForElementClickable(locator);
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

    /**
     * Highlight.
     *
     * @param element the element
     */
    public void highlight(WebElement element) {
        executeJavascript("arguments[0].style.backgroundColor = 'rgb(255, 255, 0)'", element);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * WebController#highlight
     * (org.openqa.selenium.String)
     */
    
    public void highlight(String locator) {
        executeJavascript("arguments[0].style.backgroundColor = 'rgb(255, 255, 0)'", waitForElement(locator));
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * WebController#highlight
     * (java.lang.String, java.lang.String)
     */
    
    public void highlight(String locator, String color) {
        executeJavascript("arguments[0].style.backgroundColor = arguments[1]", waitForElement(locator), color);
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
        return driver.findElements(determineLocator(locator)).size() != 0;
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

    /**
     * Gets the full xpath.
     *
     * @param locator the locator
     * @return the full xpath
     */
    public String getFullXpath(String locator) {
        WebElement element = waitForElement(locator);
        String js = "gPt=function(c){if(c.id!==''){return'id(\"'+c.id+'\")'}if(c===document.body){return c.tagName}var a=0;var e=c.parentNode.childNodes;for(var b=0;b<e.length;b++){var d=e[b];if(d===c){return gPt(c.parentNode)+'/'+c.tagName+'['+(a+1)+']'}if(d.nodeType===1&&d.tagName===c.tagName){a++}}};return gPt(arguments[0]).toLowerCase()";
        return "//" + executeJavascript(js, element);
    }

    /**
     * Gets the full xpath.
     *
     * @param element the Webelement
     * @return the full xpath
     */
    public String getFullXpath(WebElement element) {
        String js = "gPt=function(c){if(c.id!==''){return'id(\"'+c.id+'\")'}if(c===document.body){return c.tagName}var a=0;var e=c.parentNode.childNodes;for(var b=0;b<e.length;b++){var d=e[b];if(d===c){return gPt(c.parentNode)+'/'+c.tagName+'['+(a+1)+']'}if(d.nodeType===1&&d.tagName===c.tagName){a++}}};return gPt(arguments[0]).toLowerCase()";
        return "//" + executeJavascript(js, element);
    }

    /**
     * Gets the table header position.
     *
     * @param locator    the locator
     * @param headerName the header name
     * @return the table header position
     */
    public String getTableHeaderPosition(String locator, String headerName) {
        List<WebElement> columnHeaders = null;

        WebElement element = waitForElement(locator);

        columnHeaders = element.findElements(By.cssSelector("th"));

        int position = 1;
        for (WebElement record : columnHeaders) {
            if (record.getText().equals(headerName)) {
                return String.valueOf(position);
            }
            position++;
        }

        throw new WebDriverException("Header name not Found");
    }

    /* (non-Javadoc)
     * @see WebController#getTableElementColumnPosition(java.lang.String, java.lang.String)
     */
    
    public String getTableElementColumnPosition(String locator, String elementName) {
        List<WebElement> tableRows = null;
        List<WebElement> tableColumnsPerRow = null;
        WebElement element = waitForElement(locator);
        tableRows = element.findElements(By.cssSelector("tbody tr"));

        int position = 1;
        for (WebElement row : tableRows) {
            tableColumnsPerRow = row.findElements(By.cssSelector("td"));
            for (WebElement column : tableColumnsPerRow) {
                if (column.getText().equals(elementName)) {
                    return String.valueOf(position);
                }
                position++;
            }
            position = 1;
        }
        throw new WebDriverException("Column name not Found");
    }

    /*
     * (non-Javadoc)
     *
     * @see WebController#
     * getTableElementRowPosition(java.lang.String, java.lang.String)
     */
    public String getTableElementRowPosition(String locator, String elementName) {

        List<WebElement> tableRows = null;
        List<WebElement> tableColumnsPerRow = null;
        WebElement element = waitForElement(locator);
        tableRows = element.findElements(By.cssSelector("tbody tr"));

        int position = 1;
        for (WebElement row : tableRows) {
            tableColumnsPerRow = row.findElements(By.cssSelector("td"));
            for (WebElement column : tableColumnsPerRow) {
                if (column.getText().equals(elementName)) {
                    return String.valueOf(position);
                }
            }
            position++;
        }
        throw new WebDriverException("Element not Found");
    }

    /*
     * (non-Javadoc)
     *
     * @see WebController#
     * getTableElementTextForSpecificHeader(java.lang.String, java.lang.String,
     * java.lang.String)
     */
    public String getTableElementTextUnderHeader(String locator, String elementName, String headerName) {
        WebElement element = waitForElement(locator);
        return element.findElement(By.cssSelector("tbody tr:nth-child(" + getTableElementRowPosition(locator, elementName) + ") td:nth-child(" + getTableHeaderPosition(locator, headerName) + ")"))
                .getText();

    }

    /* (non-Javadoc)
     * @see WebController#getTableRecordsUnderHeader(java.lang.String, java.lang.String)
     */
    public List<String> getTableRecordsUnderHeader(String locator, String headerName) {
        List<String> records = new ArrayList<String>();
        WebElement element = waitForElement(locator);
        String headerPosition = getTableHeaderPosition(locator, headerName);
        List<WebElement> rows = element.findElements(By.cssSelector("tbody tr"));
        for (WebElement row : rows) {
            records.add(row.findElement(By.cssSelector("th:nth-child(" + headerPosition + "),td:nth-child(" + headerPosition + ")")).getText());
        }
        return records;

    }

    /*
     * (non-Javadoc)
     *
     * @see WebController#
     * getTableElementTextForRowAndColumn(java.lang.String, java.lang.String,
     * java.lang.String)
     */
    
    public String getTableElementTextForRowAndColumn(String locator, String row, String column) {
        WebElement element = waitForElement(locator);
        return element.findElement(By.cssSelector("tr:nth-child(" + row + ") td:nth-child(" + column + ")")).getText();
    }

    /*
     * (non-Javadoc)
     *
     * @see WebController#
     * getTableElements2DArray(java.lang.String)
     */
    
    public String[][] getTableElements2DArray(String locator) {

        WebElement element = waitForElement(locator);
        List<WebElement> tableRows = element.findElements(By.cssSelector("tbody tr"));
        int numberOrRows = tableRows.size();
        int numberOfColumns = element.findElements(By.cssSelector("tbody tr:nth-child(1) td")).size();
        String[][] table = new String[numberOrRows][numberOfColumns];

        for (int i = 0; i < numberOrRows; i++) {
            List<WebElement> tableColumnsPerRow = tableRows.get(i).findElements(By.cssSelector("td"));
            for (int j = 0; j < tableColumnsPerRow.size(); j++) {
                table[i][j] = tableColumnsPerRow.get(j).getText();
            }
        }

        return table;
    }

    /* (non-Javadoc)
     * @see WebController#getTableInfoAsList(java.lang.String)
     */
    
    public List<List<String>> getTableInfoAsList(String locator) {
        WebElement table = waitForElement(locator);
        List<List<String>> tableInfo = new ArrayList<List<String>>();
        List<WebElement> tableRows = table.findElements(By.cssSelector("tbody tr"));
        for (WebElement row : tableRows) {
            List<String> rowText = new ArrayList<String>();
            List<WebElement> columnsPerRow = row.findElements(By.cssSelector("td"));
            for (WebElement column : columnsPerRow) {
                rowText.add(column.getText());
            }
            tableInfo.add(rowText);
        }
        return tableInfo;
    }


    /*
     * (non-Javadoc)
     *
     * @see WebController#
     * getTableElementSpecificHeaderLocator(java.lang.String, java.lang.String,
     * java.lang.String)
     */
    
    public String getTableElementSpecificHeaderLocator(String locator, String elementName, String headerName) {
        if (locator.startsWith(XPATH)) {
            return "//" + findLocatorSubstring(locator) + "//tr[" + getTableElementRowPosition(locator, elementName) + "]//td[" + getTableHeaderPosition(locator, headerName) + "]";
        } else if (locator.startsWith("//")) {
            return locator + "//tr[" + getTableElementRowPosition(locator, elementName) + "]//td[" + getTableHeaderPosition(locator, headerName) + "]";
        } else if (locator.startsWith(CSS)) {
            return locator + " tr:nth-child(" + getTableElementRowPosition(locator, elementName) + ") td:nth-child(" + getTableHeaderPosition(locator, headerName) + ")";
        } else {
            return "css=#" + locator + " tr:nth-child(" + getTableElementRowPosition(locator, elementName) + ") td:nth-child(" + getTableHeaderPosition(locator, headerName) + ")";
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see WebController#
     * getTableElementSpecificRowAndColumnLocator(java.lang.String,
     * java.lang.String, java.lang.String)
     */
    
    public String getTableElementSpecificRowAndColumnLocator(String locator, String row, String column) {
        if (locator.startsWith(XPATH)) {
            return "//" + findLocatorSubstring(locator) + "//tr[" + row + "]//td[" + column + "]";
        } else if (locator.startsWith("//")) {
            return locator + "//tr[" + row + "]//td[" + column + "]";
        } else if (locator.startsWith(CSS)) {
            return locator + " tr:nth-child(" + row + ") td:nth-child(" + column + ")";
        } else {
            return "css=#" + locator + " tr:nth-child(" + row + ") td:nth-child(" + column + ")";
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * WebController#navigate
     * (java.lang.String)
     */
    public void navigate(String url) {
        assertThat(url, Matchers.not(Matchers.isEmptyOrNullString()));
        driver.navigate().to(url);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * WebController#getCurrentUrl
     * ()
     */
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * WebController#close()
     */
    
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
     * executeJavascript(java.lang.String, java.lang.Object[])
     */
    
    public Object executeJavascript(String js, Object... args) {
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        return executor.executeScript(js, args);

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
    
    public void switchToLatestWindow() {
        Iterator<String> itr = driver.getWindowHandles().iterator();
        String lastElement = null;
        while (itr.hasNext()) {
            lastElement = itr.next();
        }
        driver.switchTo().window(lastElement);
    }

    public Set<String> getWindowHandles() {
        return driver.getWindowHandles();
    }

    public String getParentWindowHandle() {
        return driver.getWindowHandle();
    }

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
     * @see
     * WebController#selectFrame
     * (java.lang.String)
     */
    
    public void selectFrame(String frameID) {
        selectFrameMain();
        driver.switchTo().frame(frameID);
    }

    /*
     * (non-Javadoc)
     *
     * @see WebController#
     * selectFrameMain()
     */
    
    public void selectFrameMain() {
        driver.switchTo().defaultContent();
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * WebController#maximizeWindow
     * ()
     */
    public void maximizeWindow() {
        driver.manage().window().maximize();
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
     * waitForAjaxComplete(long)
     */
    
    public void waitForAjaxComplete(long milliseconds) {
        long endTime;
        boolean ajaxComplete = false;
        long startTime = System.currentTimeMillis();
        do {
            try {
                ajaxComplete = ((Boolean) executeJavascript("return jQuery.active == 0")).booleanValue();
                Thread.sleep(THREAD_SLEEP);
            } catch (InterruptedException e) {
                error(e.getMessage());
            }
            endTime = System.currentTimeMillis();
        } while (!ajaxComplete && endTime - startTime <= milliseconds);

        if (!ajaxComplete) {
            warn("The AJAX call was not completed with in " + milliseconds + " ms");
        }
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


    public Map<String, Map<String, String>> getTableInfo(String locator, int numberOfColumns) {
        return null;
    }

    /* (non-Javadoc)
     * @see WebController#getCookieByName(java.lang.String)
     */
    
    public HttpCookie getCookieByName(String name) {
        return new HttpCookie(name, driver.manage().getCookieNamed(name).getValue());
    }

    /* (non-Javadoc)
     * @see WebController#getAllCookies()
     */
    
    public List<HttpCookie> getAllCookies() {
        List<HttpCookie> allCookies = new ArrayList<HttpCookie>();
        Iterator<Cookie> it = driver.manage().getCookies().iterator();
        while (it.hasNext()) {
            Cookie c = it.next();
            allCookies.add(new HttpCookie(c.getName(), c.getValue()));
        }
        return allCookies;
    }


    public void scrollToElement(String locator) {

        JavaScriptHelper js = new JavaScriptHelper();
        js.executeScript("arguments[0].scrollIntoView(true);", determineLocator(locator));
    }

    public void scrollToElement(WebElement element) {
        JavaScriptHelper js = new JavaScriptHelper();
        js.executeScript("arguments[0].scrollIntoView(true);", element);
        sleep(500);
    }

    public void waitForPageToLoad()
    {
        JavaScriptHelper js = new JavaScriptHelper();
        ExpectedCondition expectedCondition = new ExpectedCondition<Boolean>(){
            public Boolean apply(WebDriver driver){
                return js.executeScript( "return document.readyState" ).equals( "complete" );
            }
        };

        WebDriverWait wait = new WebDriverWait(driver, SessionContextManager.getWaitForPageToLoad(), THREAD_SLEEP);
        wait.until(expectedCondition);

        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.tagName("div")));
    }
}