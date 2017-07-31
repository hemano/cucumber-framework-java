package hemano.utils.driver.controller;

import hemano.utils.SessionContextManager;

/**
 * Created by hemantojha on 09/03/17.
 */
public class WebComponent {

    public WebDriverWebController controller() {
        return (WebDriverWebController) SessionContextManager.getController();
    }
}
