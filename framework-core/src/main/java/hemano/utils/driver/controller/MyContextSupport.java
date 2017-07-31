package hemano.utils.driver.controller;

import java.util.HashMap;
import java.util.Map;

/**
 * Generic interface to add parameters (custom per suite) to the generic
 * @see hemano.utils.SessionContextManager
 * Created by hemantojha on 28/03/17.
 */
public class MyContextSupport {

    public static MyContextParameters getParameters(final Map<String,String> map) {
        return new MyContextParameters() {

            /**
             * UID
             */
            private static final long serialVersionUID = 18484837231L;


            public Map<String, String> getAllParameters() {
                return map;
            }


            public void setAllParameters(Map<String, String> params) {
                throw new IllegalArgumentException("this implementation does not allow setting the map!");
            }

        };
    }

    public static MyContextParameters getParameters(String key, String value) {
        Map<String,String> map = new HashMap<String,String>();
        map.put(key, value);
        return getParameters(map);
    }
}
