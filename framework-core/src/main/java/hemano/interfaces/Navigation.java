package hemano.interfaces;

import org.openqa.selenium.WebElement;

/**
 * Created by hemantojha on 23/07/16.
 */
@Deprecated
public interface Navigation
{
    /**
     * @param item the navigation item to select
     */
    void selectNavigationItem(Enum<?> item);

    /**
     * @param item the navigation item to retrieve
     */
    WebElement getNavigationItem(Enum<?> item);
}