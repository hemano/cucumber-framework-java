package pages.uiLocators;

import org.openqa.selenium.By;

/**
 * Created by hemantojha on 13/01/17.
 */
public enum DashboardPageLoc implements ILocator {

    //region -  locators
    TRANSACTION_DATE_TEXT("id=transactionDate"),
    DURATION_SELECT("id=duration"),
    APPLY_BTN("id=txnStats"),
    COUNT_SPAN_LABEL("css=#initWithmPointStateLi .dash-count-small>span"),
    INIT_WITH_MPOINT_BTN("css=#initWithmPointStateLi"),

    //endregion

    ;

    String byString;

    DashboardPageLoc(String byString) {
        this.byString = byString;
    }

    public By getBy() {
        return locator(get());
    }

    public String get() {
        return byString;
    }
}
