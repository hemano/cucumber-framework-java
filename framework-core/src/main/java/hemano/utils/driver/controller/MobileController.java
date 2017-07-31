package hemano.utils.driver.controller;

// TODO: Auto-generated Javadoc

import com.google.common.base.Optional;
import hemano.utils.driver.controller.commonApi.KeyInfo;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.io.IOException;
import java.util.List;

/**
 * The Interface WebController.
 */
public interface MobileController {


    WebDriver getDriver();

    void setDriver(WebDriver driver);

    /**
     * Close the current window, quitting the browser if it's the last window currently open.
     */
    void close();

    /**
     * Quits the controller, closing every associated window.
     */
    void quit();

    /**
     * Wait for an element to become visible, with maximum time 10 seconds.If the
     * time expires an Exception is thrown
     *
     * @param locator an element locator
     * @return the web element in case of WebDriver or null in case of Selenium
     */
    WebElement waitForElement(String locator);

    /**
     * Wait for an element to become visible with maximum time in seconds given as parameter.
     * If the time expires an Exception is thrown
     *
     * @param locator     an element locator
     * @param waitSeconds the number of seconds to wait for element visibility
     * @return the web element in case of WebDriver or null in case of Selenium
     */
    WebElement waitForElement(String locator, long waitSeconds);


    /**
     * Wait for element invisibility.
     *
     * @param locator the element locator
     */
    void waitForElementInvisibility(String locator);

    /**
     * Wait for element invisibility.
     *
     * @param locator     the element locator
     * @param waitSeconds time to wait in seconds, for element to become invisible
     */
    void waitForElementInvisibility(String locator, long waitSeconds);


    /**
     * Wait for element presence.
     *
     * @param locator the element locator
     * @return the web element
     */
    WebElement waitForElementPresence(String locator);

    /**
     * Wait for element presence.
     *
     * @param locator     the locator
     * @param waitSeconds time to wait in seconds, for element to become present
     * @return the web element
     */
    WebElement waitForElementPresence(String locator, long waitSeconds);

    /**
     * Find elements.
     *
     * @param locator the element locator
     * @return the list
     */
    List<WebElement> findElements(String locator);

    /**
     * Find elements.
     *
     * @param locator the element locator
     * @return the list
     */
    WebElement findChildElement(String locator, int child);

    /**
     * Sets the value of an input field, as you typed it in.
     *
     * @param locator the element locator
     * @param value   the value you want to type in
     */
    void input(String locator, String value);

    /**
     * Press on a link, button, check box or radio button.
     *
     * @param locator an element locator
     */
    void press(String locator);


    /**
     * Press on a link, button, check box or radio button wait for page to load.
     *
     * @param locator an element locator
     */
    void pressAndWaitForPageToLoad(String locator);


    /**
     * Press and wait for element.
     *
     * @param pressLocator         the locator of the element you want to perform the press action
     * @param elementToWaitLocator the locator of the element you wait to appear
     * @param waitSeconds          the time seconds to wait
     */
    void pressAndWaitForElement(String pressLocator, String elementToWaitLocator, long waitSeconds);


    /**
     * Press and wait for element.
     *
     * @param pressLocator         the locator of the element you want to perform the press action
     * @param elementToWaitLocator the locator of the element you wait to appear
     */
    void pressAndWaitForElement(String pressLocator, String elementToWaitLocator);

    /**
     * Press on a link, button, check box or radio button that generates an alert,
     * click OK in alert and wait for page to load.
     *
     * @param locator an element locator
     */
    void pressAndClickOkInAlert(String locator);


    /**
     * Press on a link, button, check box or radio button that generates an alert and
     * click OK in alert.This action does not cause a new page to load
     *
     * @param locator the locator of the element you want to press
     */
    void pressAndClickOkInAlertNoPageLoad(String locator);

    /**
     * Press on a link, button, check box or radio button that generates an alert and
     * click cancel in alert.This action does not cause a new page to load
     *
     * @param locator the locator of the element you want to press
     */
    void pressAndClickCancelInAlert(String locator);

    /**
     * Select an option from a drop-down list.
     *
     * @param locator the drop-down list locator
     * @param option  the option
     */
    void select(String locator, String option);


    /**
     * Select by value.
     *
     * @param locator the select locator
     * @param value   the value
     */
    void selectByValue(String locator, String value);


    /**
     * Clear the value of an input field.
     *
     * @param locator the locator of the input field you want to clear
     */
    void clear(String locator);

    /**
     * Gets the builder.
     *
     * @return an instance of an Actions driver
     */
    Actions getBuilder();


    /**
     * Clicks on a link, button, check box or radio button.
     *
     * @param locator the locator of the element(i.e link, button, etc) to perform the click action
     */
    void click(String locator);

    void hideKeyboard();
    /**
     * Double click. Simulates the double click action
     *
     * @param locator locator of the element where double click is performed
     */
    void doubleClick(String locator);

    /**
     * Highlight. Changes (highlights) the background color of the element
     *
     * @param locator the locator of the element to highlight
     */
    void highlight(String locator);


    /**
     * Highlight. Changes (highlights) the current background color of the element to the one you specify
     *
     * @param locator the locator of the element to highlight
     * @param color   the color you want to give to the element background
     */
    void highlight(String locator, String color);


    /**
     * Take screen shot.
     *
     * @throws IOException Signals that an I/O exception has occurred.
     */
    void takeScreenShot() throws IOException;


    /**
     * Gets the text. This works for any element that contains text.
     *
     * @param locator of the element
     * @return the text contained in specified element
     */
    String getText(String locator);


    /**
     * Gets the focus. Move the focus to the specified element
     *
     * @param locator the locator of the element to focus
     * @return the focus
     */
    void getFocus(String locator);

    public void scrollKeys(String[] list);

    /**
     * Gets the selected label.
     *
     * @param locator of the element
     * @return the selected label
     */
    String getSelectedOption(String locator);

    /**
     * Gets the selected options (multiselect element).
     *
     * @param locator the locator of the element
     * @return the select options
     */
    List<String> getSelectedOptions(String locator);

    /**
     * Gets the value of an input field.
     *
     * @param locator of the input field
     * @return the value of an input field
     */
    String getInputValue(String locator);


    /**
     * Checks if is alert present.
     *
     * @return true, if is alert present
     */
    boolean isAlertPresent();

    /**
     * Checks if text is present.
     *
     * @param value the text value you want to check for presence
     * @return true, if text is present
     */
    boolean isTextPresent(String value);

    /**
     * Checks if text is not present.
     *
     * @param value the text value you want to check
     * @return true, if text is not present
     */
    boolean isTextNotPresent(String value);

    /**
     * Checks if the specified input element is editable.
     *
     * @param locator the locator of the element
     * @return true, if is component editable
     */
    boolean isComponentEditable(String locator);

    /**
     * Checks if a component disabled. This means that no write/edit actions are allowed
     *
     * @param locator the locator of the element you want to chek
     * @return true, if the component is disabled
     */
    boolean isComponentDisabled(String locator);

    /**
     * Checks if a component present in the page (meaning somewhere in the page).
     *
     * @param locator the locator of the element you want to verify presence
     * @return true, if is component present
     */
    boolean isComponentPresent(String locator);


    /**
     * Checks if a component present in the page (meaning somewhere in the page) for at least a specified time.
     *
     * @param locator the locator of the element
     * @param seconds the time in seconds that element needs to be present
     * @return true, if the component is present for the specified time
     */
    boolean isComponentPresent(String locator, long seconds);

    /**
     * Checks if the component is not present in the page (meaning anywhere in the page).
     *
     * @param locator the locator of the element
     * @return true, if the component is not present
     */
    boolean isComponentNotPresent(String locator);


    /**
     * Determines if the specified element is visible.
     *
     * @param locator the locator of the element
     * @return true, if the component visible
     */
    boolean isComponentVisible(String locator);

    /**
     * Determines if the specified element is visible.
     *
     * @param locator the locator of the element
     * @param seconds the time in seconds, where element needs to maintain visibility
     * @return true, if the component is visible for the specified time
     */
    boolean isComponentVisible(String locator, long seconds);


    boolean isComponentVisibleWithTime(String locator) ;

    /**
     * Checks if a specified component is not visible.
     *
     * @param locator the locator of the element
     * @return true, if the specified component is not visible
     */
    boolean isComponentNotVisible(String locator);


    /**
     * Checks if a component is not visible, for a specified time frame.
     *
     * @param locator the locator of the element
     * @param seconds the time in seconds, where element needs to maintain invisibility
     * @return true, if the component is not visible for the specified time
     */
    boolean isComponentNotVisible(String locator, long seconds);

    /**
     * Checks if a component (check box/radio) is checked (selected).
     *
     * @param locator the locator of the element
     * @return true, if the specified component is selected
     */
    boolean isComponentSelected(String locator);

    /**
     * Checks if a component (check box/radio) is not selected.
     *
     * @param locator the locator of the element
     * @return true, if the specified component is not selected
     */
    boolean isComponentNotSelected(String locator);

    /**
     * Press link name.
     *
     * @param linkName the name of the link you want to press
     */
    void pressLinkName(String linkName);


    /**
     * Press link name and wait for page to load.
     *
     * @param linkName the name of the link you want to press
     */
    void pressLinkNameAndWaitForPageToLoad(String linkName);


    /**
     * Press link name and click ok in alert.
     *
     * @param linkName the link name
     */
    void pressLinkNameAndClickOkInAlert(String linkName);


    /**
     * Press link name and click ok in alert. No new  page is expected load.
     *
     * @param linkName the link name
     */
    void pressLinkNameAndClickOkInAlertNoPageLoad(String linkName);

    /**
     * Press link name and click cancel in alert.
     *
     * @param linkName the link name
     */
    void pressLinkNameAndClickCancelInAlert(String linkName);

    /**
     * Type keys.Simulates keystroke events on the specified element, as though you typed the value key-by-key.
     *
     * @param locator the locator of the element you want to type
     * @param value   the key values to type
     */
    void typeKeys(String locator, String value);

    /**
     * Key down.Simulates a user pressing a key down
     *
     * @param locator the locator of the element
     * @param thekey  the key whose pressing you want to simulate
     */
    void keyDown(String locator, KeyInfo thekey);

    /**
     * Key up.Simulates a user releasing a key
     *
     * @param locator the locator of the element
     * @param thekey  the key to release
     */
    void keyUp(String locator, KeyInfo thekey);


    /**
     * Key press. Simulates the action of a user to press a key(once) and release it
     *
     * @param locator the element locator
     * @param thekey  the key you want to press
     */
    void keyPress(String locator, KeyInfo thekey);


    /**
     * Key down.This action simulates a user pressing a key (without releasing it yet) by sending a native operating system
     * keystroke.
     *
     * @param thekey the keys you want to press down
     */
    void keyDown(KeyInfo thekey);


    /**
     * Key up. This action simulates a user releasing a key by sending a native operating system
     * keystroke.
     *
     * @param thekey the key to release
     */
    void keyUp(KeyInfo thekey);


    /**
     * Key press. This action simulates a user pressing and releasing a key by sending a native operating system keystroke.
     *
     * @param thekey the key to press
     */
    void keyPress(KeyInfo thekey);

    /**
     * Click OK in an alert pop-up.
     */
    void clickOkInAlert();

    /**
     * Prompt input press ok. This action simulates the alerts, that user input is required, before pressing ok
     *
     * @param inputMessage the input message to type in the input prompt
     */
    void promptInputPressOK(String inputMessage);

    /**
     * Prompt input press cancel.  This action simulates the alerts, that user input is required, before pressing cancel
     *
     * @param inputMessage the input message to type in the input prompt
     */
    void promptInputPressCancel(String inputMessage);

    /**
     * Click Cancel in an alert pop-up.
     */
    void clickCancelInAlert();


    /**
     * Refresh. Simulates the refresh button of the browser
     */
    void refresh();


    /**
     * Gets the value of an element attribute.
     *
     * @param locator   the element locator
     * @param attribute the attribute value you want to retrieve
     * @return the attribute value
     */
    String getAttributeValue(String locator, String attribute);


    /**
     * Drag and drop.
     *
     * @param locatorFrom the locator from drag is performed
     * @param locatorTo   the locator where the drop will take place
     */
    void dragAndDrop(String locatorFrom, String locatorTo);

    /**
     * Gets the alert text. Retrieves the message of a JavaScript alert generated during the previous action
     *
     * @return the text of alert
     */
    String getAlertText();

    /**
     * Gets all option labels in the specified select drop-down.
     *
     * @param locator the locator of the select drop down
     * @return all the list options
     */
    List<String> getAllListOptions(String locator);


    /**
     * Gets the number of elements that match a locator.
     *
     * @param locator the element locator
     * @return the number of elements that match exactly the given locator
     */
    int getNumberOfElementsMatchLocator(String locator);

    /**
     * Move to Element and use offset.
     *
     * @param locator the locator of the element to move
     * @param x       the x offset to move, from original place
     * @param y       the y offset to move, from original place
     */
    void moveToElement(String locator, int x, int y);

    /**
     * Move to an Element.
     *
     * @param locator the element locator
     */
    void moveToElement(String locator);

    /**
     * Move to Element By offset.
     *
     * @param xOffset the x offset to move, from original place
     * @param yOffset the y offset to move, from original place
     */
    void moveByOffset(int xOffset, int yOffset);


    /**
     * Causes the current thread to sleep for a specific number of milliseconds.
     *
     * @param milliseconds the time in milliseconds for sleeping
     */
    void sleep(long milliseconds);

    /**
     * Drag and drop.
     *
     * @param locatorFrom the locator of the element to drag
     * @param xOffset     the x offset to drop
     * @param yOffset     the y offset to drop
     */
    void dragAndDrop(String locatorFrom, int xOffset, int yOffset);

    /**
     * Gets the element position.
     *
     * @param locator the element locator
     * @return the element position
     */
    Point getElementPosition(String locator);

    /**
     * Get Page HTML Source Code.
     *
     * @return the page source
     */
    String getPageSource();

    Optional<WebElement> elementExists(String locator);

    void scrollDown(int pageDown);

    void scrollTillElementDisappear(WebElement webElement);

    public void swipeUpFromTo(Point point1, Point point2);
}
