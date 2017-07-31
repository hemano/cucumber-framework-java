package hemano.interfaces;

import org.openqa.selenium.support.ui.WebDriverWait;


/**
 * Created by hemantojha on 24/07/16.
 */
public interface Synchronization
{

    /**
     * Help method that returns a {@linkplain WebDriverWait}
     *
     * @return a new {@code WebDriverWait} instance
     */
    WebDriverWait waitSeconds(final int seconds, final int polling);

    /**
     * Help method that returns a {@linkplain WebDriverWait}
     * instance of {@code timeoutSeconds} seconds. polling interval will be divided to 5.
     *
     * @param
     *
     */
    WebDriverWait waitSeconds(final int seconds);

    /**
     * help method that returns a {@linkplain WebDriverWait}
     * instance of 1 second, and a polling interval of <b>200</b> milliseconds
     *
     * @return an instance of {@code WebDriverWait} set to 1 second and 200 millis polling interval.
     */
    WebDriverWait wait1();

    /**
     * help method that returns a {@linkplain WebDriverWait}
     * instance of 1 second, and a polling interval of <b>200</b> milliseconds
     *
     * @return an instance of {@code WebDriverWait} set to 2 seconds and 400 millis polling interval.
     *
     */
    WebDriverWait wait2();

    /**
     * help method that returns a {@linkplain WebDriverWait}
     * instance of 5 seconds, and a polling interval of <b>1000</b> milliseconds
     *
     * @return an instance of {@code WebDriverWait} set to 5 seconds and 1000 millis polling interval.
     */
    WebDriverWait wait5();

    /**
     * help method that returns a {@linkplain WebDriverWait}
     * instance of 10 seconds, and a polling interval of <b>2000</b> milliseconds
     *
     * @return an instance of {@code WebDriverWait} set to 10 seconds and 2000 millis polling interval.
     */
    WebDriverWait wait10();

    /**
     * help method that returns a {@linkplain WebDriverWait}
     * instance of 20 seconds, and a polling interval of <b>4000</b> milliseconds
     *
     * @return an instance of {@code WebDriverWait} set to 20 seconds and 4000 millis polling interval.
     */
    WebDriverWait wait20();

    /**
     * help method that returns a {@linkplain WebDriverWait}
     * instance of 60 seconds, and a polling interval of <b>5000</b> milliseconds.
     *
     * @return an instance of {@code WebDriverWait} set to 30 seconds and 5000 millis polling interval.
     */
    WebDriverWait wait30();


}
