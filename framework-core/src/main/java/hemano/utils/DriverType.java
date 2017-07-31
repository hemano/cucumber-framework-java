package hemano.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hemantojha on 09/07/16.
 */
public enum DriverType implements DriverSetup {

    DOCKER_CHROME {
        public DesiredCapabilities getDesiredCapabilities() {


            File folder = new File(Paths.get(System.getProperty("user.dir")).getParent() + "/downloads");
            folder.mkdir();

            DesiredCapabilities capabilities = DesiredCapabilities.chrome();
            capabilities.setCapability("chrome.switches",
                    Arrays.asList("--no-default-browser-check"));
            HashMap<String, Object> chromePreferences = new HashMap<>();
            chromePreferences.put(
                    "profile.password_manager_enabled", "false");
            chromePreferences.put("profile.default_content_settings.popups", 0);
            chromePreferences.put("download.default_directory", folder.getAbsolutePath());

            ChromeOptions options = new ChromeOptions();
            options.setExperimentalOption("prefs", chromePreferences);
            options.addArguments("--test-type");
            options.addArguments("--start-maximized");
            options.addArguments("--kiosk");
            options.addArguments("--disable-save-password-bubble");
            options.addArguments("--no-sandbox");
            options.addArguments("--no-proxy-server");


            capabilities.setCapability("chrome.prefs", chromePreferences);

            capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
            capabilities.setCapability(ChromeOptions.CAPABILITY, options);

            return capabilities;
        }

        public WebDriver getWebDriverObject(DesiredCapabilities capabilities) {
            MyPropertyReader propertyReader = new MyPropertyReader("preferences.properties");
            String URL = propertyReader.readProperty("docker.hub.url");

            try {
                return new RemoteWebDriver(new URL(URL), capabilities);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            return null;
        }
    },
    DOCKER_FIREFOX {
        public DesiredCapabilities getDesiredCapabilities() {

            DesiredCapabilities capabilities = DesiredCapabilities.firefox();
            return capabilities;
        }

        public WebDriver getWebDriverObject(DesiredCapabilities capabilities) {
            MyPropertyReader propertyReader = new MyPropertyReader("preferences.properties");
            String URL = propertyReader.readProperty("docker.hub.url");

            File folder = new File(Paths.get(System.getProperty("user.dir")).getParent() + "/downloads");
            folder.mkdir();

            FirefoxProfile profile = new FirefoxProfile();
            profile.setPreference("browser.download.dir", folder.getAbsolutePath());
            profile.setPreference("browser.download.folderList", 2);
            profile.setPreference("browser.helperApps.neverAsk.saveToDisk",
                    "image/jpeg, application/pdf, application/octet-stream, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            profile.setPreference("pdfjs.disabled", true);
//                    profile.setPreference( "browser.download.manager.showWhenStarting", false );
//                    profile.setPreference( "browser.helperApps.neverAsk.openFile",
//                            "text/csv,application/x-msexcel,application/excel,application/x-excel,application/vnd.ms-excel,image/png,image/jpeg,text/html,text/plain,application/msword,application/xml" );
//                    profile.setPreference( "browser.download.manager.alertOnEXEOpen", false );
//                    profile.setPreference( "browser.download.manager.closeWhenDone", false );
//                    profile.setPreference( "browser.download.manager.showAlertOnComplete", false );

            capabilities.setCapability(FirefoxDriver.PROFILE, profile);

            try {
                return new RemoteWebDriver(new URL(URL), capabilities);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            return null;
        }
    },


    FIREFOX {
        public DesiredCapabilities getDesiredCapabilities() {
            DesiredCapabilities capabilities =
                    DesiredCapabilities.firefox();

            return capabilities;
        }

        public WebDriver getWebDriverObject(DesiredCapabilities capabilities) {
            try {

                configureGecko();

                FirefoxProfile profile = new FirefoxProfile();
                profile.setPreference("browser.download.folderList", 2);
                profile.setPreference("browser.helperApps.neverAsk.saveToDisk",
                        "image/jpeg, application/pdf, application/octet-stream, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
                profile.setPreference("pdfjs.disabled", true);

                capabilities.setCapability(FirefoxDriver.PROFILE, profile);

                return new FirefoxDriver(capabilities);
            } catch (Exception e) {
                throw new WebDriverException("Unable to launch the browser", e);
            }
        }
    },
    CHROME {
        public DesiredCapabilities getDesiredCapabilities() {
            //downloads folder to automatically save the downloaded files
            File folder = new File(Paths.get(System.getProperty("user.dir")).getParent() + "/downloads");
            folder.mkdir();

            configureChrome();

            ChromeOptions options = new ChromeOptions();
            Map<String, Object> prefs = new HashMap<String, Object>();
            prefs.put("credentials_enable_service", false);
            prefs.put("profile.password_manager_enabled", false);

            options.setExperimentalOption("prefs", prefs);
            options.addArguments("--test-type");
            options.addArguments("--start-maximized");
            options.addArguments("--disable-save-password-bubble");


            DesiredCapabilities capabilities = new DesiredCapabilities().chrome();
            capabilities.setCapability("chrome.switches", Arrays.asList("--no-default-browser-check"));
            HashMap<String, Object> chromePreferences = new HashMap<>();
            chromePreferences.put("profile.password_manager_enabled", "false");
            chromePreferences.put("credentials_enable_service", "false");
            chromePreferences.put("profile.default_content_settings.popups", 0);
            chromePreferences.put("download.default_directory", folder.getAbsolutePath());
            capabilities.setCapability("chrome.prefs", chromePreferences);

            capabilities.setCapability(ChromeOptions.CAPABILITY, options);
            capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
            capabilities.setCapability(ChromeOptions.CAPABILITY, options);

            return capabilities;
        }

        public WebDriver getWebDriverObject(DesiredCapabilities capabilities) {
            return new ChromeDriver(capabilities);
        }
    },

    SAUCE_FIREFOX {
        public DesiredCapabilities getDesiredCapabilities() {

            //Creating a profile
            FirefoxProfile profile = new FirefoxProfile();
            profile.setPreference("browser.download.folderList", 2);
            profile.setPreference("browser.helperApps.neverAsk.saveToDisk",
                    "image/jpeg, application/pdf, application/octet-stream, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            profile.setPreference("pdfjs.disabled", true);

            if (determineEffectivePropertyValue("enable_ff_java").equalsIgnoreCase("true")) {
                profile.setPreference("plugin.state.java", 2);
            }


            //Create Desired Capability Instance
            DesiredCapabilities capabilities = DesiredCapabilities.firefox();

            //configure capability with firefox version
            String ff_version = determineEffectivePropertyValue("ff_version");

            if (null == ff_version) {
                capabilities.setCapability("version", "47.0");
            } else {
                capabilities.setCapability("version", ff_version);
            }

            //configure capability with platform type
            String platform = determineEffectivePropertyValue("platform");

            if (null == platform) {
                capabilities.setCapability("platform", "Windows XP");
            } else {
                platform = platform.replace("_"," ");
                capabilities.setCapability("platform", platform);
            }

            //configure capability for setting up Test Case name for Sauce Jobs
            String testName = System.getProperty("test_name");
            capabilities.setCapability("name", testName);

            capabilities.setCapability(FirefoxDriver.PROFILE, profile);
            return capabilities;
        }

        public WebDriver getWebDriverObject(DesiredCapabilities capabilities) {
            final String URL = getSauceHubUrl();

            try {
                RemoteWebDriver driver = new RemoteWebDriver(new URL(URL), capabilities);
                driver.setFileDetector(new LocalFileDetector());
                return driver;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            return null;
        }
    },

    FIREFOX_LOCAL {
        public DesiredCapabilities getDesiredCapabilities() {
            DesiredCapabilities capabilities =
                    DesiredCapabilities.firefox();
            return capabilities;
        }

        public WebDriver getWebDriverObject(DesiredCapabilities capabilities) {
            FirefoxProfile profile = new FirefoxProfile();

            try {

                configureGecko();
// TODO: 18/04/17 Set the firebug and firepath extension
//                String firebugExt =
//                String firepathExt =
//                profile.addExtension(new File(firebugExt));
//                profile.addExtension(new File(firepathExt));

                String ext = "extensions.firebug.";
                String ext1 = "extensions.firepath.";

                profile.setPreference(ext + "currentVersion", "2.0.16");
                profile.setPreference(ext1 + "currentVersion", "0.9.7");
                profile.setPreference(ext + "allPagesActivation", "on");
                profile.setPreference(ext + "defaultPanelName", "net");
                profile.setPreference(ext + "net.enableSites", true);


                WebDriver firefox = new FirefoxDriver(profile);
                firefox.manage().deleteAllCookies();
                return firefox;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return new FirefoxDriver(capabilities);
        }
    },
    IE {
        public DesiredCapabilities getDesiredCapabilities() {

            configureIE();

            DesiredCapabilities capabilities =
                    new DesiredCapabilities().internetExplorer();
            capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
            capabilities.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION,
                    true);
            capabilities.setCapability(InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING,
                    true);
            capabilities.setCapability("requireWindowFocus",
                    true);

            return capabilities;
        }

        public WebDriver getWebDriverObject(DesiredCapabilities capabilities) {
            return new InternetExplorerDriver(capabilities);
        }
    },

    SAFARI {
        public DesiredCapabilities getDesiredCapabilities() {
            DesiredCapabilities capabilities =
                    DesiredCapabilities.safari();
            capabilities.setCapability("safari.cleansession",
                    true);

            return capabilities;
        }

        public WebDriver getWebDriverObject(DesiredCapabilities capabilities) {
            return new SafariDriver(capabilities);
        }
    };

    private static String getSauceHubUrl() {

        String USERNAME = determineEffectivePropertyValue("sauce.username");
        String ACCESS_KEY = determineEffectivePropertyValue("sauce.password");

        return "http://" + USERNAME + ":" + ACCESS_KEY + "@ondemand.saucelabs.com:80/wd/hub";
    }

    /**
     * It configures the Gecko driver
     */
    private static void configureGecko() {

        String ff_version = determineEffectivePropertyValue("ff_version");
        if (null != ff_version && Double.parseDouble(ff_version) < 48) {
            System.setProperty("webdriver.firefox.marionette", "false");
        } else {
            String os = System.getProperty("os.name").toLowerCase();
            String geckoPath = null;

            if (os.indexOf("mac") >= 0) {
                geckoPath =  Paths.get(System.getProperty("user.dir")).getParent() + "/vendors/gecko/mac/geckodriver";
            } else if (os.indexOf("win") >= 0) {
                geckoPath =  Paths.get(System.getProperty("user.dir")).getParent() + "/vendors/gecko/win/geckodriver.exe";
            } else {
                throw new IllegalArgumentException("Operating System : " + os + " is not supported");
            }
            System.setProperty("webdriver.gecko.driver", geckoPath);
        }
    }

    /**
     * It configures the Chrome driver
     */
    private static void configureChrome() {
        String os = System.getProperty("os.name").toLowerCase();
        String chromePath = null;
        if (os.indexOf("mac") >= 0) {
            chromePath =  Paths.get(System.getProperty("user.dir")).getParent() + "/vendors/chrome/mac/chromedriver";
        } else if (os.indexOf("win") >= 0) {
            chromePath =  Paths.get(System.getProperty("user.dir")).getParent() + "/vendors/chrome/win/chromedriver.exe";
        } else {
            throw new IllegalArgumentException("Operating System : " + os + " is not supported");
        }
        System.setProperty("webdriver.chrome.driver", chromePath);
    }

    /**
     * It configures the Internet Explorer driver
     */
    private static void configureIE() {
        String os = System.getProperty("os.name").toLowerCase();
        String ieDriverPath = null;
        if (os.indexOf("mac") >= 0) {
            throw new IllegalArgumentException("Internet Explorer not available on Mac");
        } else if (os.indexOf("win") >= 0) {
            ieDriverPath =  Paths.get(System.getProperty("user.dir")).getParent() + "/vendors/ie/IEDriverServer.exe";
        } else {
            throw new IllegalArgumentException("Operating System : " + os + " is not supported");
        }
        System.setProperty("webdriver.ie.driver", ieDriverPath);
    }


    /**
     * It returns the property value specified in either environment variable or configuration.properties
     * It gives priority to the property specified in Java environment variable For e.g. -Ddriver_id=FIREFOX
     *
     * @param key
     * @return
     */
    private static String determineEffectivePropertyValue(String key) {

        if (null != System.getProperty(key)) {
            return System.getProperty(key);
        } else {
            MyPropertyReader propertyReader = new MyPropertyReader("preferences.properties");
            return propertyReader.readProperty(key);
        }
    }



}
