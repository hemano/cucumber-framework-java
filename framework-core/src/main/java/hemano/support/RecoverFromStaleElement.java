package hemano.support;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import hemano.interfaces.Recoverable;
import hemano.utils.driver.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by hemantojha on 05/12/16.
 */
@Deprecated
public class RecoverFromStaleElement implements Recoverable {

    private static final Logger logger = LoggerFactory.getLogger( RecoverFromStaleElement.class );


    public final WebDriver getWrappedDriver()
    {
        return DriverFactory.getDriver();
    }

    @Override
    public WebElement recoverFromStaleElementReferenceException( WebElement element )
    {
        Preconditions.checkNotNull( element, "WebElement cannot be null" );
        List<By> bys = getLocators( element );
        WebElement found = null;
        logger.debug( "start to recovering from StaleElementReferenceException. bys list size is < {} >", bys.size() );
        for( By by : bys )
        {
            logger.debug( "Searching by [ {} ] using SearchContext [ {} ]", by.toString(), found == null ? "WebDriver" : "WebElement" );

            found = found == null ? /* SearchContext WebDriver */ getWrappedDriver().findElement( by ) : /* SearchContext WebElement */ found.findElement( by );
        }

        return found;
    }


    @Override
    public List<String> getLocatorsList( WebElement element )
    {
        Preconditions.checkNotNull( element, "WebElement cannot be null" );
        logger.trace( "parsing locator list for element -> {}", toString() );
        String[] str = toString().replaceAll( "(\\[{1,20}(FirefoxDriver.*?|ChromeDriver.*?|SafariDriver.*?|InternetExplorerDriver.*?)\\] -> )", "" )
                .replaceAll( "(])\\1+", "$1" )
                .replaceAll( "(])\\1+", "$1" )
                .replaceAll( " -> ", "|" )
                .split( "\\|", -1 );

        List<String> list = Lists.newArrayList();
        for( String s : str )
        {
            if( !s.contains( "[" ) && s.endsWith( "]" ) )
            {
                list.add( s.replaceFirst( "\\]", "" ) );
            } else
            {
                list.add( s );
            }
        }

        return list;
    }


    @Override
    public List<By> getLocators( WebElement element )
    {
//        if( null != locators )
//        {
//            return locators;
//        }

        Preconditions.checkNotNull( element, "WebElement cannot be null" );
        List<By> bys = Lists.newLinkedList();
        List<String> locators = /* get the parsed locators as List<String> */ getLocatorsList( element );
                        /*
                        Locators were parsed, now we need to assembly an ordered list of By elements.
						start looping list.
						*/
        for( String s : locators )
        {
            if( s.startsWith( Locator.CLASS_NAME.toString() ) )
            {
                String value = s.substring( Locator.CLASS_NAME.toString().length() + 2 );
                bys.add( By.className( value ) );
                logger.trace( "created locator {}", By.className( value ).toString() );
            } else if( s.startsWith( Locator.CSS_SELECTOR.toString() ) )
            {
                String value = s.substring( Locator.CSS_SELECTOR.toString().length() + 2 );
                bys.add( By.cssSelector( value ) );
                logger.trace( "created locator {}", By.cssSelector( value ).toString() );
            } else if( s.startsWith( Locator.NAME.toString() ) )
            {
                String value = s.substring( Locator.NAME.toString().length() + 2 );
                bys.add( By.name( value ) );
                logger.trace( "created locator {}", By.name( value ).toString() );
            } else if( s.startsWith( Locator.ID.toString() ) )
            {
                String value = s.substring( Locator.ID.toString().length() + 2 );
                bys.add( By.id( value ) );
                logger.trace( "created locator {}", By.id( value ).toString() );
            } else if( s.startsWith( Locator.LINK_TEXT.toString() ) )
            {
                String value = s.substring( Locator.LINK_TEXT.toString().length() + 2 );
                bys.add( By.linkText( value ) );
                logger.trace( "created locator {}", By.linkText( value ).toString() );
            } else if( s.startsWith( Locator.PARTIAL_LINK_TEXT.toString() ) )
            {
                String value = s.substring( Locator.PARTIAL_LINK_TEXT.toString().length() + 2 );
                bys.add( By.partialLinkText( value ) );
                logger.trace( "created locator {}", By.partialLinkText( value ).toString() );
            } else if( s.startsWith( Locator.XPATH.toString() ) )
            {
                String value = s.substring( Locator.XPATH.toString().length() + 2 );
                bys.add( By.xpath( value ) );
                logger.trace( "created locator {}", By.xpath( value ).toString() );
            } else if( s.startsWith( Locator.TAG_NAME.toString() ) )
            {
                String value = s.substring( Locator.TAG_NAME.toString().length() + 2 );
                bys.add( By.tagName( value ) );
                logger.trace( "created locator {}", By.tagName( value ).toString() );
            }
        }

        return bys;
    }

}