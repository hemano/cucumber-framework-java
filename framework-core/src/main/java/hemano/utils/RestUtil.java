package hemano.utils;


import com.google.common.base.Throwables;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.path.xml.XmlPath;
import com.jayway.restassured.path.xml.element.NodeChildren;
import com.jayway.restassured.response.Response;
import org.dom4j.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.yandex.qatools.allure.annotations.Attachment;

import java.io.IOException;
import java.util.Map;

import static com.jayway.restassured.RestAssured.*;

/**
 * Created by hemantojha on 25/07/16.
 */
public class RestUtil {

    private static final Logger logger = LoggerFactory.getLogger( RestUtil.class );

    //Global Setup Variables
    public static String path; //Rest request path

    /*
    ***Sets Base URI***
    Before starting the test, we should set the RestAssured.baseURI
    */
    public static void setBaseURI( String baseURI )
    {

        RestAssured.baseURI = baseURI;
    }

    public static void setHeader( String key, String value )
    {
        given().header( key, value );
    }


    /*
    ***Sets base path***
    Before starting the test, we should set the RestAssured.basePath
    */
    public static void setBasePath( String basePathTerm )
    {
        RestAssured.basePath = basePathTerm;
    }

    /*
    ***Reset Base URI (after test)***
    After the test, we should reset the RestAssured.baseURI
    */
    public static void resetBaseURI()
    {
        RestAssured.baseURI = null;
    }

    /*
    ***Reset base path (after test)***
    After the test, we should reset the RestAssured.basePath
    */
    public static void resetBasePath()
    {
        RestAssured.basePath = null;
    }

    /*
    ***Sets ContentType***
    We should set content type as JSON or XML before starting the test
    */
    public static void setContentType( ContentType Type )
    {
        given().contentType( Type );
    }

    /*
    ***Returns response***
    We send "path" as a parameter to the Rest Assured'a "get" method
    and "get" method returns response of API
    */
    public static Response getResponse()
    {
        return get( path );
    }

    public static void setBody( String body )
    {
        given().request().body( body );
    }

    public static Response postResponse( String endPoint )
    {
        return RestAssured.post( endPoint );
    }


    /*
    ***Returns JsonPath object***
    * First convert the API's response to String type with "asString()" method.
    * Then, send this String formatted json response to the JsonPath class and return the JsonPath
    */
    public static JsonPath getJsonPath( Response res )
    {
        String json = res.asString();
        //System.out.print("returned json: " + json +"\n");
        return new JsonPath( json );
    }

    public static Response sendPostRequest( String endPoint, Map headerMap, String modifiedXMLRequest )
    {

        String requestDetails = "\nEndpoint : " + endPoint + "\n--------------------\n" + modifiedXMLRequest;
        saveTextLog( "Modified XML Request", requestDetails );
        String responseXML = new String();
        try
        {
            Response response = given()
                    .headers( headerMap )
                    .body( modifiedXMLRequest )
                    .post( endPoint );

            responseXML = getResponseInString( response );
            String responseDetails = "\nResponse : " + "\n--------------------\n" + ModifyXML.getPrettyXML( responseXML );
            saveTextLog( "Response", responseDetails );

            return response;
        } catch( Exception e )
        {

            Throwables.propagate( new Exception( String.format( "Failed at sending post request : URI - [%s] and endpoint - [%s] : \n and the response is %s", baseURI, endPoint, responseXML ), e ) );
        }
        return null;
    }

    public static Response sendPostRequest( String endPoint, Map dataMap, Map headerMap, String modifiedXMLRequest )
    {

        String requestDetails = "\nEndpoint : " + endPoint + "\n--------------------\n" + modifiedXMLRequest;
        saveTextLog( "Modified XML Request", requestDetails );
        String responseXML = new String();
        try
        {
            Response response = given()
                    .auth().basic( dataMap.get( "username" ).toString(), dataMap.get( "password" ).toString() )
                    .headers( headerMap )
                    .body( modifiedXMLRequest )
                    .post( endPoint );

            responseXML = getResponseInString( response );
            String responseDetails = "\nResponse : " + "\n--------------------\n" + ModifyXML.getPrettyXML( responseXML );
            saveTextLog( "Response", responseDetails );

            return response;
        } catch( Exception e )
        {

            Throwables.propagate( new Exception( String.format( "Failed at sending post request : URI - [%s] and endpoint - [%s] : \n and the response is %s", baseURI, endPoint, responseXML ), e ) );
        }
        return null;
    }

    public static Response sendPostRequest( Map headerMap, String modifiedXMLRequest )
    {
        Response response = given()
                .headers( headerMap )
                .body( modifiedXMLRequest )
                .post();

        return response;
    }


    public static String getValueFromResponse( String path )
    {
        XmlPath xmlPath = ( XmlPath ) SessionContextManager.getInstance().getAttribute( "xmlPath" );
        return xmlPath.get( path ).toString();
    }

    public static String getValueFromResponse( String nodeParentPath, String childNodeName, String attribute )
    {
        XmlPath xmlPath = ( XmlPath ) SessionContextManager.getInstance().getAttribute( "xmlPath" );
        NodeChildren nodeChildren = xmlPath.getNodeChildren( nodeParentPath );
        String attributeValue = new String();

        for( int i = 0; i < nodeChildren.size(); i++ )
        {
            attributeValue = nodeChildren.get( i ).getNode( childNodeName ).getAttribute( attribute );
        }
        return attribute;
    }

    public static String getResponseInString( Response response ) throws IOException, DocumentException
    {
        String responseXML = response.andReturn().asString();
        return responseXML;
    }

    @Attachment( value = "{0}", type = "text/plain" )
    public static String saveTextLog( String attachName, String message )
    {
        logger.info( message );
        return message;
    }


}

