package pages.uiLocators.facebook;

import org.openqa.selenium.By;
import pages.uiLocators.ILocator;

/**
 * Created by hemantojha on 13/01/17.
 */
public enum HomePageLoc implements ILocator {

    //region -  locators
    USER_NAME_LBL("xpath=//a[@title='Profile']/span"),
    STATUS_TEXT("name=xhpc_message"),
    POST_BTN("xpath=//*[@data-testid='react-composer-post-button']"),
    USER_NAV_DOWN_ARROW("id=userNavigationLabel"),
    LOG_OUT_BTN("xpath=//*[@text='Logout']"),

    //endregion

    ;

    String byString;

    HomePageLoc(String byString) {
        this.byString = byString;
    }

    public By getBy() {
        return locator(get());
    }

    public String get() {
        return byString;
    }
}
