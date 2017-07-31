package pages.uiLocators;

import org.openqa.selenium.By;
import org.openqa.selenium.NotFoundException;

/**
 * <h1>ILocator</h1>
 * Interface to define Locator
 * It has default locator method with returns By objects
 *
 */
public interface ILocator {
//    public By locator();

    default By locator(String id) {
        if (id.contains("//") || id.contains(".//")) {
            return By.xpath(id.replace("xpath=", ""));
        } else if(id.contains("css")){
            return By.cssSelector(id.replace("css=", ""));
        }else if(id.contains("id")) {
            return By.id(id.replace("id=", ""));
        }else if(id.contains("name")) {
            return By.name(id.replace("name=", ""));
        }else if(id.contains("link")) {
            return By.linkText(id.replace("link=", ""));
        }else {
         throw new NotFoundException("The locator type is not found");
        }
    }
}
