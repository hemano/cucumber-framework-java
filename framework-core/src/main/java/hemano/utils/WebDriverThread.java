package hemano.utils;

import com.google.common.base.Throwables;
import org.openqa.selenium.Platform;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.yandex.qatools.allure.annotations.Step;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by hemantojha on 09/07/16.
 */
public class WebDriverThread {
    private static final Logger logger = LoggerFactory.getLogger( WebDriverThread.class );

    private WebDriver driver;
    private DriverType driverType;
    private DriverType selectedDriverType;
    private Proxy seleniumProxy;

    private final DriverType defaultDriverType = DriverType.FIREFOX_LOCAL;

    //    private String browser;
    private String driver_id;

    private final String operatingSystem =
            System.getProperty( "os.name" ).toUpperCase();
    private final String systemArchitecture =
            System.getProperty( "os.arch" ).toUpperCase();
    private final boolean useRemoteWebDriver =
            Boolean.getBoolean( "remoteDriver" );

    public WebDriver getDriver() throws Exception
    {

        if( null == driver )
        {
            selectedDriverType = determineEffectiveDriverType();

            DesiredCapabilities desiredCapabilities = selectedDriverType.getDesiredCapabilities();
            desiredCapabilities.setCapability( CapabilityType.PROXY, seleniumProxy );

            instantiateWebDriver( desiredCapabilities );
        }
        return driver;
    }


    /**
     * <p>
     * Returns the most appropriate driver type It allows to read the driver_id
     * from the environment variables
     * </p>
     *
     * @return DriverType
     */
    private DriverType determineEffectiveDriverType() {
        driverType = defaultDriverType;
        try {

            // get the browser id from Command Line
            if (null != System.getProperty("driver_id")) {
                driver_id = System.getProperty("driver_id");
                driverType = DriverType.valueOf(driver_id);
            } else {
                MyPropertyReader propertyReader = new MyPropertyReader("preferences.properties");
                driver_id = propertyReader.readProperty("driver_id").toString();
                driverType = DriverType.valueOf(driver_id);
            }

        } catch (IllegalArgumentException ignore) {
            Throwables.propagate(
                    new IllegalArgumentException("Unknown DriverType specified... defaulting to " + driverType));
        } catch (NullPointerException ignore) {
            Throwables.propagate(
                    new IllegalArgumentException("Unknown DriverType specified... defaulting to " + driverType));
        }

        return driverType;
    }


    private void instantiateWebDriver( DesiredCapabilities desiredCapabilities ) throws MalformedURLException
    {

        System.out.println( String.format( "Current Operating System : {%s}"
                , operatingSystem ) );
        System.out.println( String.format( "Current System Architecture : {%s}"
                , systemArchitecture ) );
        System.out.println( String.format( "Current Browser : {%s}\n"
                , driverType ) );

        if( useRemoteWebDriver )
        {
            URL seleniumGridURL = new URL( System.getProperty( "gridURL" ) );
            System.out.println( "seleniumGridURL = " + seleniumGridURL );
            String desiredBrowserVersion = System.getProperty( "desiredBrowserVersion" );
            String desiredPlatform = System.getProperty( "desiredPlatform" );

            if( ( null != desiredPlatform ) && desiredPlatform.isEmpty() )
            {
                desiredCapabilities.setPlatform( Platform.valueOf( desiredPlatform ) );
            }

            if( null != desiredBrowserVersion && desiredBrowserVersion.isEmpty() )
            {
                desiredCapabilities.setVersion( desiredBrowserVersion );
            }
            driver = new RemoteWebDriver( seleniumGridURL, desiredCapabilities );

        } else
        {
            driver = selectedDriverType.getWebDriverObject( desiredCapabilities );
        }
    }

    public void quitDriver()
    {
        if( null != driver )
        {
            logToReport( "Closing the driver..." );
            driver.close();
            driver.quit();
            driver = null;
        }
    }

    @Step( "{0}" )
    public void logToReport( String message )
    {
        logger.info( message ); //or System.out.println(message);
    }


}
