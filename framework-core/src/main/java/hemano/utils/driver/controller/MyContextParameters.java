package hemano.utils.driver.controller;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by hemantojha on 28/03/17.
 *
 * Generic interface to add parameters to the Session context
 */
public interface MyContextParameters extends Serializable {

    Map<String,String> getAllParameters();

    void setAllParameters(Map<String,String> params);
}
