package hemano.utils.misc;

import hemano.utils.ScreenShotUtil;
import hemano.utils.driver.DriverFactory;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.MatcherAssert;
import org.hamcrest.StringDescription;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import ru.yandex.qatools.allure.annotations.Attachment;
import ru.yandex.qatools.allure.annotations.Step;

/**
 * Created by hemantojha on 16/09/16.
 */
public class UpdateAssert extends MatcherAssert {


    public static <T> void assertThat(T actual, Matcher<? super T> matcher) {
        assertThat("", actual, matcher);
    }


    @Step("Matching Actual - {1} and Expected {2}")
    public static <T> void assertThat(String reason, T actual, Matcher<? super T> matcher) {
        if (!matcher.matches(actual)) {
            Description description = new StringDescription();
            description.appendText(reason)
                    .appendText("\nExpected: ")
                    .appendDescriptionOf(matcher)
                    .appendText("\n     but: ");
            matcher.describeMismatch(actual, description);
            
            ScreenShotUtil.takeScreenShot();
            throw new AssertionError(description.toString());
        }
    }

    @Attachment()
    private static byte[] attachScreenShot() {
        return ((TakesScreenshot) DriverFactory.getDriver()).getScreenshotAs(OutputType.BYTES);
    }

}
