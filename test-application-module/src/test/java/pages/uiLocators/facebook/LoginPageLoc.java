package pages.uiLocators.facebook;

import org.openqa.selenium.By;
import pages.uiLocators.ILocator;

/**
 * Created by hemantojha on 13/01/17.
 */
public enum LoginPageLoc implements ILocator {

    //region -  locators
    USERNAME_TEXT("id=email"),
    PASSWORD_TEXT("id=pass"),
    LOGIN_BTN("id=loginbutton"),

    //endregion

    ;

    String byString;

    LoginPageLoc(String byString) {
        this.byString = byString;
    }

    public By getBy() {
        return locator(get());
    }

    public String get() {
        return byString;
    }
}
