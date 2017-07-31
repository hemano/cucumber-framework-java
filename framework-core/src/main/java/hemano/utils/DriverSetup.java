package hemano.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;

/**
 * Created by hemantojha on 09/07/16.
 */
public interface DriverSetup {

    WebDriver getWebDriverObject(DesiredCapabilities desiredCapabilities) throws MalformedURLException;
    DesiredCapabilities getDesiredCapabilities();

}
