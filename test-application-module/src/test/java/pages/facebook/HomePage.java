package pages.facebook;

import hemano.model.BasePageObject;
import de.svenjacobs.loremipsum.LoremIpsum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.uiLocators.facebook.HomePageLoc;


/**
 * Created by hemantojha on 21/07/16.
 */
public class HomePage extends BasePageObject {

    Logger logger = LoggerFactory.getLogger(HomePage.class);

    public HomePage() {
        super();
    }


    public String  getUserName(){
        return controller().getText(HomePageLoc.USER_NAME_LBL.get());
    }

    public boolean isStatusPosted(){
        LoremIpsum loremIpsum = new LoremIpsum();
        String message = loremIpsum.getWords(3);

        controller().input(HomePageLoc.STATUS_TEXT.get(), message);
        logToReport(String.format("Entered the status text as ", message));

        controller().click(HomePageLoc.POST_BTN.get());
        logToReport(String.format("Click on Post button"));

        controller().waitForAjaxComplete(10000);
        return controller().isTextPresent(message);
    }

}
