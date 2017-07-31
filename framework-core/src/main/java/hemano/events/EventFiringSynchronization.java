package hemano.events;

import hemano.interfaces.Synchronization;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by hemantojha on 24/07/16.
 * Implementation of Synchronization Interface
 * It enable to use Explicit WebDriver wait in DSL form.
 */
public class EventFiringSynchronization implements Synchronization
{

    //region EventFiringSynchronization - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( EventFiringSynchronization.class );

    private final WebDriver driver;

    //endregion


    //region EventFiringSynchronization - Constructor Methods Section

    public EventFiringSynchronization( WebDriver driver )
    {
        this.driver = driver;
    }


    //endregion


    //region EventFiringSynchronization - Public Methods Section


    /**
     * Help method that returns a {@linkplain WebDriverWait}
     * instance of {@code timeoutSeconds} seconds. polling interval will be divided to 5.
     *
     */
    @Override
    public WebDriverWait waitSeconds(final int seconds)
    {
//        Preconditions.checkArgument( timeOutInSeconds.getSeconds() > 0, "The timeout should be greater than zero." );

        int polling = (seconds/2) * 100;

        return new WebDriverWait(driver,seconds,polling);
    }

    /**
     * Help method that returns a {@linkplain WebDriverWait}
     *
     * @return  a new {@code WebDriverWait} instance
     */
    @Override
    public WebDriverWait waitSeconds(final int seconds, final int polling )
    {

        return new WebDriverWait(driver,seconds,polling);
    }


    /**
     * help method that returns a {@linkplain WebDriverWait}
     * instance of 1 second, and a polling interval of <b>200</b> milliseconds
     *
     * @return an instance of {@code WebDriverWait} set to 1 second and 200 millis polling interval.
     */
    @Override
    public WebDriverWait wait1()
    {
        int s = 1;
        int d= 200;
        return new WebDriverWait( driver, s, d );
    }

    /**
     * help method that returns a {@linkplain WebDriverWait}
     * instance of 1 second, and a polling interval of <b>200</b> milliseconds
     *
     * @return an instance of {@code WebDriverWait} set to 2 seconds and 400 millis polling interval.
     *
     */
    @Override
    public WebDriverWait wait2()
    {
        int s = 2;
        int d= 400;
        return new WebDriverWait( driver, s, d );
    }

    /**
     * help method that returns a {@linkplain WebDriverWait}
     * instance of 5 seconds, and a polling interval of <b>1000</b> milliseconds
     *
     * @return an instance of {@code WebDriverWait} set to 5 seconds and 1000 millis polling interval.
     */
    @Override
    public WebDriverWait wait5()
    {
        int s = 5;
        int d= 1000;
        return new WebDriverWait( driver, s, d );
    }

    /**
     * help method that returns a {@linkplain WebDriverWait}
     * instance of 10 seconds, and a polling interval of <b>2000</b> milliseconds
     *
     * @return an instance of {@code WebDriverWait} set to 10 seconds and 2000 millis polling interval.
     */
    @Override
    public WebDriverWait wait10()
    {
        int s = 10;
        int d= 2000;
        return new WebDriverWait( driver, s, d );
    }

    /**
     * help method that returns a {@linkplain WebDriverWait}
     * instance of 20 seconds, and a polling interval of <b>4000</b> milliseconds
     *
     * @return an instance of {@code WebDriverWait} set to 20 seconds and 4000 millis polling interval.
     */
    @Override
    public WebDriverWait wait20()
    {
        int s = 20;
        int d= 4000;
        return new WebDriverWait( driver, s, d );
    }

    /**
     * help method that returns a {@linkplain WebDriverWait}
     * instance of 60 seconds, and a polling interval of <b>5000</b> milliseconds.
     *
     * @return an instance of {@code WebDriverWait} set to 30 seconds and 5000 millis polling interval.
     */
    @Override
    public WebDriverWait wait30()
    {
        int s = 30;
        int d= 5000;
        return new WebDriverWait( driver, s, d );
    }

}
