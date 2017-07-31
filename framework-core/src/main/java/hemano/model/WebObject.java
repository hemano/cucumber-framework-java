package hemano.model;

import com.google.common.base.Optional;
import org.openqa.selenium.internal.WrapsElement;

/**
 * Created by hemantojha on 25/07/16.
 */
public interface WebObject extends WrapsElement
{

    /**
     * The parent PageObject
     *
     * @return a PageObject interface. to ba cast on extending classes
     */
    Optional<WebObject> getParentObject();

    boolean isDisplayed();
}