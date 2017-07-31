package pages.uiLocators.walletHub;

import org.openqa.selenium.By;
import pages.uiLocators.ILocator;

/**
 * Created by hemantojha on 13/01/17.
 */
public enum ProfilePageLoc implements ILocator {

    //region -  locators
    FIVE_STARS_LINK("css=.wh-rating.rating_5"),
    STARS_RATING_HOLDER("css=.wh-rating-choices-holder"),
    STARS_RATING_OPTIONS("css=.wh-rating-choices-holder a"),

    //endregion

    ;

    String byString;

    ProfilePageLoc(String byString) {
        this.byString = byString;
    }

    public By getBy() {
        return locator(get());
    }

    public String get() {
        return byString;
    }
}
