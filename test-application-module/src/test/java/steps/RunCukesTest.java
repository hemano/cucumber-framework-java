package steps;

import com.cucumber.listener.Reporter;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import java.io.File;

/**
 * Created by hemantojha on 21/07/16.
 */
//    @CucumberOptions(features = "classpath:features",
//    glue = {"steps"},
//    tags = {"@sanity"}, plugin = {"pretty", "html:target/cucumber-html-report"})
//    public class RunCukesTest extends AbstractTestNGCucumberTests {
//
//    }
// mvn clean test -Dcucumber.options="--tags @sanity"
      /*mvn site
      mvn jetty:run
      (http://localhost:8080/)*/


//@RunWith(Cucumber.class)
//@CucumberOptions(features = {"classpath:features"},
//        plugin = {"pretty", "html:target/cucumber", "json:target/cucumber-report.json"},
//        glue = {},
//        tags = {"~@ignore"})
//public class RunCukesTest {
//}

//plugin = {"pretty", "html:target/cucumber", "json:target/cucumber-report.json"},

@RunWith(Cucumber.class)
@CucumberOptions(features = {"classpath:features"},
        plugin = {"com.cucumber.listener.ExtentCucumberFormatter:"},
        glue = {},
        tags = {"~@ignore"})
public class RunCukesTest {

    @BeforeClass
    public static void setup() {
        System.setProperty("cucumberReportPath", "output/myreport.html");
    }

    @AfterClass
    public static void teardown() {
        Reporter.loadXMLConfig(new File("src/test/resources/extent-config.xml"));
        Reporter.setSystemInfo("user", System.getProperty("user.name"));
        Reporter.setSystemInfo("os", "Mac OSX");
        Reporter.setTestRunnerOutput("Sample test runner output message");
    }
}

