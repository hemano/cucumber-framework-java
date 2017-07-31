package pages.walletHub;

import hemano.model.BasePageObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.uiLocators.walletHub.ProfilePageLoc;


/**
 * Created by hemantojha on 21/07/16.
 */
public class ProfilePage extends BasePageObject {

    Logger logger = LoggerFactory.getLogger(ProfilePage.class);

    public ProfilePage() {
        super();
    }

    public void selectTheRating(int rating){

        controller().mouseOver(ProfilePageLoc.FIVE_STARS_LINK.get());
        controller().isComponentVisible(ProfilePageLoc.STARS_RATING_HOLDER.get());


        controller().findChildElement(ProfilePageLoc.STARS_RATING_OPTIONS.get(), 3).click();
    }

}
