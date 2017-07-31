package pages.facebook;

import hemano.model.BasePageObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.uiLocators.facebook.LoginPageLoc;


/**
 * Created by hemantojha on 21/07/16.
 */
public class LoginPage extends BasePageObject {

    Logger logger = LoggerFactory.getLogger(LoginPage.class);


    public LoginPage() {
        super();
    }

    public void enterUserName(String userName) {
        controller().input(LoginPageLoc.USERNAME_TEXT.get(), userName);
        logToReport(String.format("Entered the user name"));
    }

    public void enterPassword(String password) {
        controller().input(LoginPageLoc.PASSWORD_TEXT.get(), password);
        logToReport(String.format("Entered the password"));
    }

    public void clickLogin() {
        controller().click(LoginPageLoc.LOGIN_BTN.get());
        logToReport(String.format("Clicked on Log in button"));
    }

}
