package hemano.utils;


import com.google.common.collect.Maps;
import hemano.utils.driver.DriverFactory;
import hemano.utils.driver.controller.MyContextParameters;
import hemano.utils.driver.controller.ControllerBase;
import org.openqa.selenium.WebDriverException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by hemantojha on 25/09/16.
 * Singleton class to manage a set of key value pairs in the memory during the test execution
 *
 */
public class SessionContextManager {

    private static final Logger logger = LoggerFactory.getLogger(SessionContextManager.class);

    private static SessionContextManager ourInstance = new SessionContextManager();

    private static final AtomicInteger threadSeq = new AtomicInteger((int) (System.currentTimeMillis() % 0xcafe));

    //getting the instance of the object
    public static SessionContextManager getInstance() {
        return ourInstance;
    }

    private Map<String, Object> attributes = Maps.newHashMap();

    private SessionContextManager() {
    }


    /**
     * The inner Class Context.
     */
    static class Context {

        /** The controller. */
        private ControllerBase controller;

        /** The verify. */
        private Verify verify;

        /** The is web driver. */
        private boolean isWebDriver;

        /** The params registry. */
        private Map<String,String> paramsRegistry;

        private int waitForPageToLoad = 120;
        private int waitForElement = 25;
        private int waitForElementInvisibility = 25;
        private ApplicationContext context;

        /**
         * Clear context.
         */
        public void clear() {
            if (controller != null) {
                try {
                    controller.quit();
                } catch (WebDriverException wde) {
                    logger.warn("Exception caught calling controller.quit(): \""+wde.getMessage()+"\" additional info: "+wde.getAdditionalInformation());
                }
            }
            controller = null;
            verify = null;
            isWebDriver = false;
            if (paramsRegistry != null) {
                paramsRegistry.clear();
            }

            Thread.currentThread().setName("Context - Inactive");
            logger.info("Context closed, controller shutdown");
        }

        public int getWaitForPageToLoad() {
            return waitForPageToLoad;
        }

        public void setWaitForPageToLoad(int waitForPageToLoad) {
            this.waitForPageToLoad = waitForPageToLoad;
        }

        public int getWaitForElement() {
            return waitForElement;
        }

        public void setWaitForElement(int waitForElement) {
            this.waitForElement = waitForElement;
        }

        public int getWaitForElementInvisibility() {
            return waitForElementInvisibility;
        }

        public void setWaitForElementInvisibility(int waitForElementInvisibility) {
            this.waitForElementInvisibility = waitForElementInvisibility;
        }

        public ApplicationContext getContext() {
            return context;
        }

        public void setContext(ApplicationContext context) {
            this.context = context;
        }
    }


    /** The inner context as a thread local variable. */
    private static ThreadLocal<Context> innerContext = new ThreadLocal<SessionContextManager.Context>() {

        @Override
        protected Context initialValue() {
            return new Context(); //initial is empty;
        }

    };


    /**
     * Gets the web controller.
     *
     * @return the web controller
     */
    public static ControllerBase getController() {
        return innerContext.get().controller;
    }


    /**
     * Determines the instance of the Web Controller
     *
     * @return true, if it is instance of WebDriverWebController false if it is instance of SeleniumWebController
     */
    public static boolean isWebDriver() {
        return innerContext.get().isWebDriver;
    }

    /**
     * Adds parameters to registry; if a parameter exists already it will be overwritten.
     * @param params
     */
    public static void registerParameters(MyContextParameters params) {
        Map<String, String> paramsRegistry = innerContext.get().paramsRegistry;
        if (paramsRegistry == null) {
            innerContext.get().paramsRegistry = new HashMap<String, String>();
        }
        innerContext.get().paramsRegistry.putAll(params.getAllParameters());
    }

    /**
     * get a parameter from the registry.
     *
     * @param paramName the param name
     * @return the parameter value
     */
    public static String getParam(String paramName) {
        return innerContext.get().paramsRegistry.get(paramName);
    }

    /**
     * add a parameter to the registry.
     *
     * @param paramName the param name
     * @return the parameter value
     */
    public static String addParam(String paramName, String paramValue) {
        return innerContext.get().paramsRegistry.put(paramName, paramValue);
    }


    /**
     * Gets the params.
     *
     * @return a Map of the registered parameters
     */
    public static Map<String,String> getParams() {
        return innerContext.get().paramsRegistry;
    }

    /**
     * Register the controller in the context of current thread's copy for this thread-local variable.
     *
     */
    public static void setController(ControllerBase instance) {

        Context context = innerContext.get();
        context.controller = instance;
    }


    /**
     * return a verify helper initialized with the right controller.
     *
     * @return the verify
     */
    public static Verify verify() {
        Context context = innerContext.get();
        if (context.verify == null) {
            context.verify=new Verify();
        }
        return context.verify;
    }


    /**
     * Clean the local thread context
     */
    public static void clean() {
        innerContext.get().clear();
        innerContext.remove();
        DriverFactory.closeDriverObjects();
    }

    public static int getWaitForPageToLoad() {
        return innerContext.get().getWaitForPageToLoad();
    }

    public static void setWaitForPageToLoad(int waitForPageToLoad) {
        innerContext.get().setWaitForPageToLoad(waitForPageToLoad);
    }

    public static int getWaitForElement() {
        return innerContext.get().getWaitForElement();
    }

    public static void setWaitForElement(int waitForElement) {
        innerContext.get().setWaitForElement(waitForElement);
    }

    public static int getWaitForElementInvisibility() {
        return innerContext.get().getWaitForElementInvisibility();
    }

    public static void setWaitForElementInvisibility(int waitForElementInvisibility) {
        innerContext.get().setWaitForElementInvisibility(waitForElementInvisibility);
    }

    public final Object getAttribute(final String name) {
        return this.attributes.get(name);
    }


    public final void setAttribute(final String name, final Object value) {
        logger.info("\nAttribute set KEY : [{}] \n VALUE : [{}]", name, value.toString());
        this.attributes.put(name, value);
    }

    public final Set<String> getAttributeNames() {
        return this.attributes.keySet();
    }

    public final Object removeAttribute(final String name) {
        if (attributes.containsKey(name)) {
            return this.attributes.remove(name);
        }
        return name;
    }

    public final void clearAttributes() {
        attributes.clear();
    }

    public boolean isAttributeExists(String attributeName) {
        return attributes.containsKey(attributeName);
    }

    public static void attachSpringContext(ApplicationContext applicationContext) {
        innerContext.get().setContext(applicationContext);
    }
}
