package hemano.utils.misc;

import org.joda.time.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by hemantojha on 24/07/16.
 */
public class Sleeper
{

    //region Sleeper - Variables Declaration and Initialization Section.

    private static final Logger logger = LoggerFactory.getLogger( Sleeper.class );

    private Sleeper() throws IllegalAccessException
    {
        throw new IllegalAccessException( "Utility class should not be constructed" );
    }

    //endregion


    //region Sleeper - Public Methods Section

    /**
     * Sleeps without explicitly throwing an InterruptedException
     *
     * @param duration Sleep time in milliseconds.
     *
     * @throws RuntimeException wrapping an InterruptedException if one gets thrown
     */
    public static void pauseFor( final Duration duration )
    {
        try
        {
            sleep( duration.getMillis() );
        }
        catch ( InterruptedException e )
        {
            logger.warn( "Wait interrupted:" + e.getMessage() );
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Sleeps without explicitly throwing an InterruptedException
     *
     * @param timeInMilliseconds the amount of time to sleep
     *
     * @throws RuntimeException wrapping an InterruptedException if one gets thrown
     */
    protected static void sleep( long timeInMilliseconds ) throws InterruptedException
    {
        Thread.sleep( timeInMilliseconds );
    }

    //endregion
}
