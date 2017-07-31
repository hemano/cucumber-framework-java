package hemano.utils;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import hemano.utils.driver.DriverFactory;
import hemano.utils.misc.Constants;
import org.apache.commons.lang3.text.StrBuilder;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;


/**
 * Created with IntelliJ IDEA
 */

public class JavaScriptHelper
{

    //region JavaScriptHelper - Variables Declaration and Initialization Section.

    private static final Logger logger = LoggerFactory.getLogger( JavaScriptHelper.class );

    private static final String JQUERY_MIN = "reporter/resources/js/jquery-3.1.1.min.js";

    // ==========================  END OF STATIC MEMBERS ========================================================================

    private final WebDriver driver;


    //endregion


    //region JavaScriptHelper - Constructor Methods Section

    public JavaScriptHelper()
    {
        //this.driver = driver;
        this.driver =  DriverFactory.getDriver();
    }

    //endregion


    //region JavaScriptHelper - Common Java Script Methods Section

    public Object executeScript( String script, Object... args )
    {
        return ( (JavascriptExecutor) driver ).executeScript( script, args );
    }

    public Object executeAsyncScript( String script, Object... args )
    {
        return ( (JavascriptExecutor) driver ).executeAsyncScript( script, args );
    }

    public ArrayList getArrayList( final String script, final Object... args )
    {
        Object o = executeScript( script, args );

        if( Objects.nonNull( o ) && o instanceof ArrayList )
        {
            return ( ArrayList ) o;
        }

        return null;
    }

    public Boolean getBoolean( final String script, final Object... args )
    {
        Object o = executeScript( script, args );
        if( Objects.nonNull( o ) && o instanceof Boolean )
        {
            return ( Boolean ) o;
        }

        return null;
    }

    public Map getMap( final String script, final Object... args )
    {
        Object o = executeScript( script, args );
        if( Objects.nonNull( o ) && o instanceof Map )
        {
            return ( Map ) o;
        }

        return null;
    }

    public Number getNumber( final String script, final Object... args )
    {
        Object o = executeScript( script, args );
        if( Objects.nonNull( o ) && o instanceof Number )
        {
            return ( Number ) o;
        }

        return null;
    }

    public String getString( final String script, final Object... args )
    {
        Object o = executeScript( script, args );
        if( Objects.nonNull( o ) && o instanceof String )
        {
            return ( String ) o;
        }
        return null;
    }

    public String[] getStringArray( final String script, final Object... args )
    {
        Object o = executeScript( script, args );
        if( Objects.nonNull( o ) && o instanceof ArrayList )
        {
            List objects = ( List ) o;
            Object[] ao = objects.toArray( new String[ objects.size() ] );
            return ( String[] ) ao;
        }

        return null;
    }

    public boolean isDefined( final String expression )
    {
        String script = String.format( "return typeof %s === 'undefined';", expression );
        boolean response = getBoolean( script );
        return !response;
    }

    //endregion


    //region JavaScriptHelper - XMLHttpRequest Methods Section

    public JsonElement sendXMLHttpGETRequest( final String url, final boolean async )
    {
        ( ( EventFiringWebDriver ) driver ).manage().timeouts().setScriptTimeout( 20, TimeUnit.SECONDS );
        StrBuilder builder = new StrBuilder( "var callback = arguments[arguments.length - 1];\n" );
        builder.appendln( "var xhr = new XMLHttpRequest();" );
        builder.append( "xhr.open( 'GET', '" ).append( url ).append( "', '" ).append( async ).append( "' );" );
        builder.appendln( "xhr.onreadystatechange = function() { " );
        builder.appendln( "  if (xhr.readyState == 4) { " );
        builder.appendln( "    callback(xhr.responseText);" );
        builder.appendln( "  }" );
        builder.appendln( "}" );
        builder.appendln( "xhr.send();" );

        Object response = executeAsyncScript( builder.toString() );
        Gson gson = new Gson();
        return gson.fromJson( ( String ) response, JsonElement.class );
    }

    //endregion


    //region JavaScriptHelper - JQueryEnable Methods Section

//    public boolean isJQueryAvailable()
//    {
//        boolean jqueryIntegrationEnabled = configuration.isJQueryIntegrationEnabled();
//        if( jqueryIntegrationEnabled )
//        {
//
//            return getBoolean( "return (typeof jQuery === 'function')" );
//        }
//
//        return false;
//    }
//
//    public void injectJQueryPlugins()
//    {
//
//    }

    private String getFileAsString( final String resourcePath )
    {
        String content = Constants.EMPTY;
        try
        {
            URL fileUrl = getClass().getClassLoader().getResource( resourcePath );
            if( fileUrl != null )
            {
                content = Resources.toString( fileUrl, Charsets.UTF_8 );
            }
        }
        catch( Exception e )
        {
            throw new RuntimeException( e );
        }
        return content;
    }

    public void injectJQuery()
    {
        executeScriptFrom( JQUERY_MIN );
    }

    //endregion


    //region JavaScriptHelper - Service Methods Section

    public void executeScriptFrom( String scriptSource )
    {
        String script = getFileAsString( scriptSource );
        executeScript( script );
    }

    public void sleepOnBrowser( final int timeInMillis )
    {
        final String SCRIPT = String.format( "window.setTimeout( arguments[arguments.length - 1], %d );", timeInMillis );
        executeAsyncScript( SCRIPT );
    }

    public String executeAndGetJsonAsString( final String script, final Object... params )
    {
        return ( String ) executeScript( "return JSON.stringify(JSON.decycle(function(arguments){" + script + "}(arguments)));", params );
    }


    //endregion


    //endregion


    //region JavaScript - JQuery methods Section.


    //endregion


    //region JavaScript - Protected Methods Section

    //endregion


    //region JavaScript - Private Function Section


    //
    //		private <T> T deserializeJsonAs( Class<T> classOfT, final String objString )
    //		{
    //			ObjectMapper mapper = getMapper();
    //			ObjectReader reader = mapper.reader( classOfT );
    //			if ( inject != null )
    //			{
    //				reader = reader.with( inject );
    //			}
    //			try
    //			{
    //				return reader.readValue( objString );
    //			}
    //			catch ( JsonParseException e )
    //			{
    //				throw new WebDriverException( e );
    //			}
    //			catch ( JsonMappingException e )
    //			{
    //				throw new WebDriverException( e );
    //			}
    //			catch ( IOException e )
    //			{
    //				throw new WebDriverException( e );
    //			}
    //		}
    //
    //	private <T> List<T> deserializeJsonAsListOf( Class<T> classOfT, final String objString )
    //	{
    //		ObjectMapper mapper = getMapper();
    //		ObjectReader reader = mapper.reader( TypeFactory.defaultInstance().constructCollectionType( List.class, classOfT ) );
    //		if ( inject != null )
    //		{
    //			reader = reader.with( inject );
    //		}
    //		try
    //		{
    //			return reader.readValue( objString );
    //		}
    //		catch ( JsonParseException e )
    //		{
    //			throw new WebDriverException( e );
    //		}
    //		catch ( JsonMappingException e )
    //		{
    //			throw new WebDriverException( e );
    //		}
    //		catch ( IOException e )
    //		{
    //			throw new WebDriverException( e );
    //		}
    //	}
    //
    //	private ObjectMapper getMapper()
    //	{
    //		if ( mapper == null )
    //		{
    //			mapper = new ObjectMapper();
    //		}
    //		return mapper;
    //	}
    //
    //	/**
    //	 * Executes the JavaScript code and deserializes the resulting object as a classOfT.
    //	 *
    //	 * @param classOfT Java Class
    //	 * @param script that returns JavaScript Object
    //	 * @param params for the script
    //	 * @return deserialized as classOfT object
    //	 */
    //	public <T> T deserializeScriptResultAs( Class<T> classOfT, final String script, final Object... params )
    //	{
    //		String objString = executeAndGetJsonAsString( script, params );
    //		if ( objString == null )
    //		{
    //			return null;
    //		}
    //		return deserializeJsonAs( classOfT, objString );
    //	}
    //
    //	/**
    //	 * Executes the JavaScript code and deserializes the resulting object as a List of classOfT.
    //	 *
    //	 * @param classOfT Java Class to reflect on
    //	 * @param script that returns JavaScript Object
    //	 * @return deserialized as List of classOfT
    //	 */
    //	public <T> List<T> deserializeScriptResultAsListOf( Class<T> classOfT, final String script, final Object... params )
    //	{
    //		String objString = executeAndGetJsonAsString( script, params );
    //		if ( objString == null )
    //		{
    //			return null;
    //		}
    //		return deserializeJsonAsListOf( classOfT, objString );
    //	}
    //


    //endregion


    //region JavaScript - Inner Classes Implementation Section


    //endregion

}
