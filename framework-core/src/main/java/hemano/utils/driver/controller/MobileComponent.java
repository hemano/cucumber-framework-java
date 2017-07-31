package hemano.utils.driver.controller;

import hemano.utils.SessionContextManager;
import org.jfairy.Fairy;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hemantojha on 09/03/17.
 */
public class MobileComponent {

    protected Fairy dataGen = Fairy.create();

    protected Map<String , String > dataMap = new HashMap<>();

    public MobileController controller() {

        if(SessionContextManager.getParam("driver_id").contains("ANDROID_DRIVER")){
            return (AndroidDriverController) SessionContextManager.getController();
        }else{
            return (IOSDriverController) SessionContextManager.getController();
        }
    }

    public String getParameter(String key){
        if(dataMap.get(key).contains("random")){
            if(key.equals("first_name")){
                return dataGen.person().firstName();
            }
        }else {
            return dataMap.get(key);
        }

        return null;
    }
}
