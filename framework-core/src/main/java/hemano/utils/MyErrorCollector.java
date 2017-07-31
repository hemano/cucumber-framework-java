package hemano.utils;


import org.hamcrest.Matcher;
import org.junit.rules.ErrorCollector;
import org.junit.runners.model.MultipleFailureException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import static hemano.utils.misc.UpdateAssert.assertThat;

/**
 * Created by hemantojha on 26/07/16.
 */
public class MyErrorCollector extends ErrorCollector {

    private List<Throwable> errors = new ArrayList<Throwable>();

    @Override
    public void verify() throws Throwable {
        MultipleFailureException.assertEmpty(errors);
    }

    /**
     * Adds a Throwable to the table.  Execution continues, but the test will fail at the end.
     */
    public void addError(Throwable error) {
        errors.add(error);
    }

    /**
     * Adds a failure to the table if {@code matcher} does not match {@code value}.
     * Execution continues, but the test will fail at the end if the match fails.
     */
    public <T> void checkThat(final T value, final Matcher<T> matcher) {
        checkThat("", value, matcher);
    }

    /**
     * Adds a failure with the given {@code reason}
     * to the table if {@code matcher} does not match {@code value}.
     * Execution continues, but the test will fail at the end if the match fails.
     */
    public <T> void checkThat(final String reason, final T value, final Matcher<T> matcher) {
        checkSucceed(new Callable<Object>() {
            public Object call() throws Exception {
                assertThat(reason, value, matcher);
                return value;
            }
        });
    }

    /**
     * Adds to the table the exception, if any, thrown from {@code callable}.
     * Execution continues, but the test will fail at the end if
     * {@code callable} threw an exception.
     */
    public Object checkSucceed(Callable<Object> callable) {
        try {
            return callable.call();
        } catch (Throwable e) {
            addError(e);
            return null;
        }
    }
}
