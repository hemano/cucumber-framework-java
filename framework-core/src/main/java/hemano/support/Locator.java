package hemano.support;

import com.google.common.base.Throwables;

import java.lang.reflect.Field;

/**
 * Created by hemantojha on 05/12/16.
 */
@Deprecated
public enum Locator
{
    //region Locator - Enumeration Members Section.

    /**
     * a locator found by {@link org.openqa.selenium.By.ByClassName}
     */
    CLASS_NAME( "class name" ),

    /**
     * a locator found by {@link org.openqa.selenium.By.ByCssSelector}
     */
    CSS_SELECTOR( "css selector" ),

    /**
     * a locator found by {@link org.openqa.selenium.By.ById}
     */
    ID( "id" ),

    /**
     * a locator found by {@link org.openqa.selenium.By.ByLinkText}
     */
    LINK_TEXT( "link text" ),

    /**
     * a locator found by {@link org.openqa.selenium.By.ByName}
     */
    NAME( "name" ),

    /**
     * a locator found by {@link org.openqa.selenium.By.ByPartialLinkText}
     */
    PARTIAL_LINK_TEXT( "partial link text" ),

    /**
     * a locator found by {@link org.openqa.selenium.By.ByTagName}
     */
    TAG_NAME( "tag name" ),

    /**
     * a locator found by {@link org.openqa.selenium.By.ByXPath}
     */
    XPATH( "xpath" );

    //endregion


    //region Locator - Constructor Methods Section

    Locator( String name )
    {
        try
        {
            Field fieldName = getClass().getSuperclass().getDeclaredField( "name" );
            fieldName.setAccessible( true );
            fieldName.set( this, name );
            fieldName.setAccessible( false );
        }
        catch( Exception e )
        {
            Throwables.propagate( e );
        }
    }

    //endregion
}