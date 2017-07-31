package pages.walletHub;

import hemano.model.BasePageObject;
import de.svenjacobs.loremipsum.LoremIpsum;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.uiLocators.walletHub.ReviewActionPageLoc;

import java.util.List;


/**
 * Created by hemantojha on 21/07/16.
 */
public class ReviewActionPage extends BasePageObject {

    Logger logger = LoggerFactory.getLogger(ReviewActionPage.class);

    public ReviewActionPage() {
        super();
    }

    public void selectThePolicy(String policy){

        controller().click(ReviewActionPageLoc.POLICY_DROPDOWN.get());
        List<WebElement> options = controller().findElements(ReviewActionPageLoc.POLICY_OPTIONS.get());

        for(WebElement element: options){
            if(element.getAttribute("data-target").equalsIgnoreCase(policy)){
                element.click();
                break;
            }
        }
        controller().waitForAjaxComplete(5000);
    }

    public void writeReview(){
        LoremIpsum loremIpsum = new LoremIpsum();
        String reviewText = loremIpsum.getWords(40);
        controller().input(ReviewActionPageLoc.REVIEW_CONTEXT_TEXT.get(), reviewText);
    }

    public void submitReview(){
        controller().click(ReviewActionPageLoc.SUBMIT_BTN.get());
    }

    public boolean isSuccessMessageVisible(){
        return controller().isComponentVisible(ReviewActionPageLoc.SUCCESS_MESSAGE.get());
    }

    public boolean isErrorPageVisible(){
        return controller().isComponentVisible(ReviewActionPageLoc.ERROR_MESSAGE.get());
    }


}
