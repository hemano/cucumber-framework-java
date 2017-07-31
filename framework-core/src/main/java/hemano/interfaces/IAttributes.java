package hemano.interfaces;

import org.openqa.selenium.WebDriver;

import java.io.Serializable;
import java.util.Set;

/**
 * Created by hemantojha on 25/09/16.
 */
@Deprecated
public interface IAttributes extends Serializable {
    /**
     * @param name The name of the attribute to return
     */
    public Object getAttribute(String name);

    /**
     * Set a custom attribute.
     */
    public void setAttribute(String name, Object value);

    /**
     * @return all the attributes names.
     */
    public Set<String> getAttributeNames();

    /**
     * Remove the attribute
     *
     * @return the attribute value if found, null otherwise
     */
    public Object removeAttribute(String name);

    public void clearAttributes();

    public boolean isAttributeExists(String attributeName);

    public WebDriver getWrappedDriver();
}
