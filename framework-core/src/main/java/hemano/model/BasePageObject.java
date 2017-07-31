package hemano.model;

import com.cucumber.listener.Reporter;
import hemano.events.EventFiringSynchronization;
import hemano.interfaces.Synchronization;
import hemano.utils.driver.DriverFactory;
import hemano.utils.driver.controller.WebComponent;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;


/**
 * BasePageObject
 * It makes the common helper functions available to all the pages.
 */
public abstract class BasePageObject extends WebComponent {

    private static final Logger logger = LoggerFactory.getLogger( BasePageObject.class );

    protected Synchronization sync;

    //region BasePageObject - Constructor Methods Section
    protected BasePageObject()
    {
        this.sync = new EventFiringSynchronization( getWrappedDriver() );
    }

    //endregion

    public final WebDriver getWrappedDriver()
    {
        return DriverFactory.getDriver();
    }


    //region BasePageObject - Public Methods Section
    public final Set<Cookie> getCookies()
    {
        return getWrappedDriver().manage().getCookies();
    }

    //endregion


    //region BasePageObject - Protected Methods Section

    protected void logToReport(String message){
        logger.info(message);
        Reporter.addStepLog(message);
    }
    //endregion

}