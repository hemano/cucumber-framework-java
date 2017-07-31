package pages.uiLocators.walletHub;

import org.openqa.selenium.By;
import pages.uiLocators.ILocator;

/**
 * Created by hemantojha on 13/01/17.
 */
public enum ReviewActionPageLoc implements ILocator {

    //region -  locators
    POLICY_DROPDOWN("css=.dropdown-title"),
    POLICY_OPTIONS("css=.drop-el a"),
    REVIEW_CONTEXT_TEXT("id=review-content"),
    SUBMIT_BTN("css=div.submit>input"),
    OVERALL_RATING_STARS("css=span#overallRating a"),
    SUCCESS_MESSAGE("id=review"),
    ERROR_MESSAGE("css=div.error-page"),


    //endregion

    ;

    String byString;

    ReviewActionPageLoc(String byString) {
        this.byString = byString;
    }

    public By getBy() {
        return locator(get());
    }

    public String get() {
        return byString;
    }
}
