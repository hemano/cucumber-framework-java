package hemano.interfaces;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Created by hemantojha on 05/12/16.
 */
@Deprecated
public interface Recoverable {

    WebElement recoverFromStaleElementReferenceException(WebElement element);

    /**
     * @return the parsed json of {@linkplain WebElement#toString()}
     */
    List<By> getLocators(WebElement element);

    List<String> getLocatorsList(WebElement element);

}
