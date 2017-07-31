package steps;


import com.cucumber.listener.Reporter;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import hemano.utils.MyPropertyReader;
import hemano.utils.SessionContextManager;
import hemano.utils.driver.DriverFactory;
import hemano.utils.driver.controller.MyContextSupport;
import hemano.utils.driver.controller.WebDriverWebController;
import org.apache.commons.io.IOUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.Augmenter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import ru.yandex.qatools.allure.annotations.Attachment;
import ru.yandex.qatools.allure.annotations.Step;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

/**
 * Created by hemantojha on 21/07/16.
 */
public class StartingSteps extends DriverFactory{

    private static final Logger logger = LoggerFactory.getLogger( StartingSteps.class );

    @Autowired
    private ApplicationContext ctx;


    @Before
    public void beforeScenario( Scenario scenario ) throws Exception
    {
        log( "***************************************************************************");
        log( "Executing Scenario : " + scenario.getName() );
        log( "***************************************************************************");

        //Read all parameters from preferences.properties and make it available to the SessionContextManager
        Map allParams = new MyPropertyReader("preferences.properties").getAllParams();
        SessionContextManager.registerParameters(MyContextSupport.getParameters(allParams));

        //attach the Spring Application Context to the SessionContextManager
        SessionContextManager.attachSpringContext(ctx);

        //Get the object of WebDriverWebController(gives access to all WebUI actions)
        WebDriverWebController webController = (WebDriverWebController)ctx.getBean("webDriverWebController");

        //Making driver available to the WebController
        webController.setDriver(DriverFactory.getDriver());

        //making webController available to SessionContextManager
        SessionContextManager.setController(webController);

        //Lunches the driver
        SessionContextManager.getInstance().setAttribute( "execution_type", "browser" );

        getDriver().manage().window().maximize();
        getDriver().manage().deleteAllCookies();
    }

//    @After( "@mConsole" )
//    public void afterMconsoleExecutes( ) throws Exception
//    {
//        userLogoutFromMConsole();
//    }


    @After
    public void afterScenario( Scenario scenario ) throws Exception
    {
        try
        {
            attachLog();

            if( scenario.isFailed() )
            {
                try
                {
                    scenario.write( "Current Page URL is " + getDriver().getCurrentUrl() );
                    byte[] screenshot;
                    try
                    {
                        screenshot = ( ( TakesScreenshot ) getDriver() ).getScreenshotAs( OutputType.BYTES );
                    } catch( ClassCastException weNeedToAugmentOurDriverObject )
                    {
                        screenshot = ( ( TakesScreenshot ) new Augmenter().augment( getDriver() ) ).getScreenshotAs( OutputType.BYTES );
                    }
                    String relativeScrnShotPath = takeScreenshot().substring(takeScreenshot().indexOf("screenshots"));
                    Reporter.addScreenCaptureFromPath(relativeScrnShotPath, getDriver().getCurrentUrl());
                } catch( WebDriverException somePlatformsDontSupportScreenshots )
                {
                    System.err.println( somePlatformsDontSupportScreenshots.getMessage() );
                }
            }
        } finally
        {
            closeDriverObjects();
        }
    }

    @Attachment()
    public byte[] attachLog() throws IOException
    {
        InputStream inputStream = new FileInputStream( new File( "./execution.log" ) );
        return IOUtils.toByteArray( inputStream );
    }

    @Step( "{0}" )
    private void log( String value )
    {
        logger.info( value );
    }


    /**
     * <p>
     * Take the screenshot
     * </p>
     *
     * @return
     */
    private static String takeScreenshot() {

        try {
            // now copy the screenshot to desired location using copyFile
            Path screenshotFolder;
            screenshotFolder = Paths.get(System.getProperty("user.dir"), "output/screenshots");

            if (Files.notExists(screenshotFolder))
                Files.createDirectory(screenshotFolder);

            String fileName = "Screenshot" + "_" + System.nanoTime();

            Path screenshotPath = Paths.get(screenshotFolder.toString(), fileName + ".png");

            Screenshot screenshot = new AShot()
                    .takeScreenshot(getDriver());

            ImageIO.write(screenshot.getImage(), "PNG", new File(screenshotPath.toString()));

            return screenshotPath.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}
