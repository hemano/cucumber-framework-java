package hemano.utils.driver;

/**
 * Created by hemantojha on 27/07/16.
 */

import ch.lambdaj.util.NotUniqueItemException;
import com.google.common.base.Optional;
import hemano.utils.JavaScriptHelper;
import hemano.utils.misc.Sleeper;
import org.joda.time.Duration;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.html5.ApplicationCache;
import org.openqa.selenium.html5.LocationContext;
import org.openqa.selenium.html5.WebStorage;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Set;


/**
 * Created with IntelliJ IDEA
 * <p>
 * Package: hemano.utils.driver
 * <p>
 * Name   : WebDriverUtils
 * <p>
 * User   : Hemant
 * <p>
 * Date   : 2016-07-01
 * <p>
 * Time   : 07:54
 */

public class WebDriverUtils {

    //region WebDriverUtils - Variables Declaration and Initialization Section.

    private static final Logger logger = LoggerFactory.getLogger( WebDriverUtils.class );

    //endregion


    //region WebDriverUtils - Constructor Methods Section

    private WebDriverUtils() throws IllegalAccessException
    {
        throw new IllegalAccessException( "Utility class should not be constructed" );
    }

    //endregion


    //region WebDriverUtils - Driver Methods Section

    public static void scrollTop(String mode_slow_fast)
    {
        JavaScriptHelper js = new JavaScriptHelper();
        js.executeScript( "$('html, body').animate({ scrollTop: 0 }, '" + mode_slow_fast + "');" );
        Sleeper.pauseFor( Duration.millis( 500 ) );
    }

    public static void scrollToElement(WebElement element )
    {
        JavaScriptHelper js = new JavaScriptHelper();
        js.executeScript( "arguments[0].scrollIntoView(true);",element );
    }


    public static void scrollByLines( WebDriver driver, final int lines )
    {
        JavaScriptHelper js = new JavaScriptHelper();
        js.executeScript( "window.scrollByLines( arguments[0] );", lines );
    }

    public static void scrollByPages( WebDriver driver, final int pages )
    {
        JavaScriptHelper js = new JavaScriptHelper();
        js.executeScript( "window.scrollByPages( arguments[ 0 ] );", pages );
    }

    public static Optional<WebElement> elementExists( WebDriver driver, By locator )
    {
        List<WebElement> list = driver.findElements( locator );
        if( list.size() == 1 )
        {
            return Optional.of( list.get( 0 ) );
        } else if( list.isEmpty() )
        {
            return Optional.absent();
        } else
        {
            throw new NotUniqueItemException();
        }
    }

    public static Optional<WebElement> elementExists( WebDriver driver, By locator, int timeout )
    {
        int duration = timeout * 100 / 5;
        WebDriverWait wdw = new WebDriverWait( driver, timeout, duration );
        try
        {
            WebElement we = wdw.until( ExpectedConditions.presenceOfElementLocated( locator ) );
            return Optional.of( we );
        } catch( TimeoutException te )
        {
            return Optional.absent();
        }
    }

    public static Capabilities getCapabilities( WebDriver driver )
    {
        return ( ( HasCapabilities ) driver ).getCapabilities();
    }

//    public static SessionId getSessionId(WebDriver driver )
//    {
//        return ( (RemoteWebDriver) ( ( EventFiringWebDriver ) driver ).getWrappedDriver() ).getSessionId();
//    }

    public static Actions createAction( WebDriver driver )
    {
        return new Actions( driver );
    }

    //endregion


    //region WebDriverUtils - Html5 Methods Section

    public static ApplicationCache cache( WebDriver driver )
    {
        return null;
    }

    public static WebStorage storage( WebDriver driver )
    {
        return null;
    }

    public static LocationContext location( WebDriver driver )
    {
        return null;
    }

    public static boolean isLocationContextSupported( WebDriver driver )
    {
        return false;
    }

    public static boolean isApplicationCacheSupported( WebDriver driver )
    {
        return false;
    }

    public static boolean isWebStorageSupported( WebDriver driver )
    {
        return false;
    }

    public static boolean isSqlDatabaseSupported( WebDriver driver )
    {
        return false;
    }


    //endregion


    //region WebDriverUtils - Browser Methods Section

    public static boolean isARemoteDriver( Class<? extends WebDriver> driverClass )
    {
        return ( RemoteWebDriver.class == driverClass );
    }

    public static boolean isAFirefoxDriver( Class<? extends WebDriver> driverClass )
    {
        return ( FirefoxDriver.class.isAssignableFrom( driverClass ) );
    }

    public static boolean isAChromeDriver( Class<? extends WebDriver> driverClass )
    {
        return ( ChromeDriver.class.isAssignableFrom( driverClass ) );
    }

    public static boolean isAnInternetExplorerDriver( Class<? extends WebDriver> driverClass )
    {
        return ( InternetExplorerDriver.class.isAssignableFrom( driverClass ) );
    }

//    public static String getUserAgent( WebDriver driver )
//    {
//        return JavaScriptHelper.withDriver( driver ).getString( "return navigator.userAgent;" );
//    }

    public Set<String> getDomVariables( final Set<String> excludes )
    {
        return null;
    }

//    public Optional<String> getZoomLevel(WebDriver driver)
//    {
//        JavaScriptHelper js = JavaScriptHelper.withDriver( driver );
//        Number number = null;
//
//        if ( js.isDefined( "window.devicePixelRatio" ) )
//        {
//            number = js.getNumber( "return Math.round( window.devicePixelRatio * 100 );" );
//        }
//        else
//        {
//            if ( getBrowserName( driver ).equals( BrowserType.CHROME ) )
//            {
//                Number n = js.getNumber( "return Math.round( ( ( window.outerWidth) / window.innerWidth ) * 100 ) / 100;" );
//            }
//            else if ( getBrowserName( driver ).equals( BrowserType.SAFARI ) )
//            {
//                Number n = js.getNumber( "return Math.round( ( ( document.documentElement.clientWidth ) / window.innerWidth ) * 100) / 100;" );
//            }
//            else if ( getBrowserName( driver ).equals( BrowserType.IE ) )
//            {
//                // String version = ( ( Capabilities ) driver ).getVersion();
//                // ie10 Math.round((document.documentElement.offsetHeight / window.innerHeight) * 100) / 100
//                // ie9 Math.round((screen.deviceXDPI / screen.logicalXDPI) * 100) / 100
//                throw new org.apache.commons.lang3.NotImplementedException( "InternetExplorer driver is currently not supported" );
//            }
//        }
//
//        if ( number != null )
//        {
//            float devicePixelRatio = number.floatValue();
//            NumberFormat defaultFormat = NumberFormat.getPercentInstance();
//            defaultFormat.setMinimumFractionDigits( 1 );
//            Optional.fromNullable( defaultFormat.format( devicePixelRatio ) );
//        }
//
//        return Optional.absent();
//    }

    /**
     * @return the browser name
     *
     * @see Capabilities#getBrowserName()
     */
    public static String getBrowserName( WebDriver driver )
    {
        Capabilities capabilities = WebDriverUtils.getCapabilities( driver );
        return capabilities.getBrowserName();
    }

    public static void SelectByVisibleText_BootStrap( WebDriver driver, By elementById, String visibleOptionText )
    {
        try
        {
            String elementId = elementById.toString().split( ":" )[ 1 ].trim();

            String js = String.format( "document.getElementById('%s').removeAttribute('class');", elementId );
            ( ( JavascriptExecutor ) driver ).executeScript( js );
//( ( JavascriptExecutor ) driver ).executeScript( String.format( "document.getElementById('%s').style.display='block';", elementId ) );
            String cssSelector = String.format( "select#%s", elementId );
            Select selectObject = new Select( driver.findElement( By.cssSelector( cssSelector ) ) );
            selectObject.selectByVisibleText( visibleOptionText );
        } catch( Exception e )
        {
            throw new WebDriverException("Some error occured while Selecting option", e);
        }
    }

//    /**
//     * @return the browser version
//     *
//     * @see Capabilities#getVersion()
//     */
//    public static String getBrowserVersion( WebDriver driver )
//    {
//        Pattern p;
//        String userAgent = getUserAgent( driver );
//        if ( userAgent.contains( "Chrome/" ) )
//        {
//            p = Pattern.compile( "Chrome/(\\*|\\d+(\\.\\d+){0,2}(\\.\\*)?)", Pattern.CASE_INSENSITIVE );
//        }
//        else if ( userAgent.contains( "Firefox/" ) )
//        {
//            p = Pattern.compile( "Firefox/(\\*|\\d+(\\.\\d+){0,2}(\\.\\*)?)", Pattern.CASE_INSENSITIVE );
//        }
//        else if ( userAgent.contains( "Windows" ) )
//        {
//            p = Pattern.compile( "rv:(\\*|\\d+(\\.\\d+){0,2}(\\.\\*)?)", Pattern.CASE_INSENSITIVE );
//        }
//        else if ( userAgent.contains( "Safari/" ) )
//        {
//            p = Pattern.compile( "Version/(\\*|\\d+(\\.\\d+){0,2}(\\.\\*)?)", Pattern.CASE_INSENSITIVE );
//        }
//        else
//        {
//            throw new UnsupportedOperationException( "Browser type is currently not supported" );
//        }
//
//        Matcher m = p.matcher( userAgent );
//        if ( m.find() )
//        {
//            return m.group().replaceAll( "Firefox/|Chrome/|Version/|rv:", "" );
//        }
//
//        return Constants.EMPTY;
//
//    }


    //endregion

}
