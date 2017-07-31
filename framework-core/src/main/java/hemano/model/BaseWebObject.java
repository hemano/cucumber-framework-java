package hemano.model;

import hemano.events.EventFiringSynchronization;
import hemano.interfaces.Synchronization;
import hemano.utils.driver.DriverFactory;
import hemano.utils.misc.Sleeper;
import org.joda.time.Duration;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;


/**
 * Created by hemantojha on 24/07/16.
 */
public abstract class BaseWebObject implements WebObject {

    //region AbstractPageObject - Variables Declaration and Initialization Section.

    private static final Logger logger = LoggerFactory.getLogger( BaseWebObject.class );


    // ==========================  END OF STATIC MEMBERS ========================================================================

    //endregion

    protected Synchronization sync;
    private WebElement rootElement;
    private By rootBy;

    //region AbstractPageObject - Constructor Methods Section
    protected BaseWebObject()
    {
        this.sync = new EventFiringSynchronization( getWrappedDriver() );
    }

    protected BaseWebObject( final By rootBy )
    {
        this.rootBy = rootBy;
        this.sync = new EventFiringSynchronization( getWrappedDriver() );
        this.rootElement = sync.wait10().until( ExpectedConditions.presenceOfElementLocated( rootBy ) );
//        initElements();

    }

    //endregion

    public final WebDriver getWrappedDriver()
    {
        return DriverFactory.getDriver();
    }

    //region AbstractPageObject - Public Methods Section
    public final Set<Cookie> getCookies()
    {
        return getWrappedDriver().manage().getCookies();
    }


    public Set<String> getWindowHandles()
    {
        return getWrappedDriver().getWindowHandles();
    }

    @Override
    public WebElement getWrappedElement()
    {
        try
        {
            rootElement.getTagName();
            return rootElement;
        } catch( StaleElementReferenceException e )
        {
            e.printStackTrace();
            Sleeper.pauseFor( Duration.millis( 2000 ) );
            this.rootElement = sync.wait10().until( ExpectedConditions.presenceOfElementLocated( rootBy ) );
            return rootElement;
        }
    }

    protected void initElements(){

    }

    //endregion


    //region AbstractPageObject - Protected Methods Section

    //endregion

}