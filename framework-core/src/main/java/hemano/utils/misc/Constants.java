package hemano.utils.misc;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

import java.math.BigInteger;

/**
 * Created by hemantojha on 25/09/16.
 */
public class Constants
{

    //region Constants - Constructor Methods Section

    public static final String[] EMPTY_STR_ARRAY = {};

    //endregion


    //region Constants - Misc Constants Section


    //endregion

    /**
     * A regex pattern for recognizing blocks of whitespace characters.
     * The apparent contentedness of the pattern serves the purpose of ignoring "blocks" consisting of only a single space:
     * the pattern is used only to normalize whitespace, condensing "blocks" down to a single space,
     * thus matching the same would likely cause a great many no-op replacements.
     */
    public static final String WHITESPACE_PATTERN_MATCHER = "(?: |\\u00A0|\\s|[\\s&&[^ ]])\\s*";

	/*
     * ----------------------------------------------------------------------
     * P A T T E R N  C O N S T A N T S
     * ----------------------------------------------------------------------
     */

    //region Constants - Pattern Constants Section

    public static final String NUMBER_PATTERN_MATCHER = "([0-9]+)";

    /**
     * We begin by telling the parser to find the beginning of the string (^), followed by any lowercase letter (a-z),
     * number (0-9), an underscore, or a hyphen.
     * Next, {3,16} makes sure that are at least 3 of those characters, but no more than 16. Finally, we want the end of the string ($).
     *
     * String that matches: my-us3r_n4m3
     * String that doesn't match: th1s1s-wayt00_l0ngt0beausername (too long)
     */
    public static final String USER_PATTERN_MATCHER = "[a-z0-9_-]{3,16}";

    /**
     * We begin by telling the parser to find the beginning of the string (^).
     * Next, a number sign is optional because it is followed a question mark.
     * The question mark tells the parser that the preceding character — in this case a number sign — is optional,
     * but to be "greedy" and capture it if it's there. Next, inside the first group (first group of parentheses), we can have two different situations.
     * The first is any lowercase letter between a and f or a number six times.
     * The vertical bar tells us that we can also have three lowercase letters between a and f or numbers instead.
     * Finally, we want the end of the string ($).
     */
    public static final String HEX_PATTERN_MATCHER = "#?([a-f0-9]{6}|[a-f0-9]{3})";

    /**
     * We begin by telling the parser to find the beginning of the string (^).
     * Inside the first group, we match one or more lowercase letters, numbers, underscores, dots, or hyphens.
     * escaped the dot because a non-escaped dot means any character.
     * Directly after that, there must be an at sign.
     * Next is the domain name which must be: one or more lowercase letters, numbers, underscores, dots, or hyphens.
     */
    public static final String EMAIL_PATTERN_MATCHER = "([a-z0-9_\\.-]+)@([\\da-z\\.-]+)\\.([a-z\\.]{2,6})";

    /**
     * The first capturing group is all option.
     * It allows the URL to begin with "http://", "https://", or neither of them.
     * a question mark after the s to allow URL's that have http or https.
     * In order to make this entire group optional, added a question mark to the end of it.
     *
     * Next is the domain name: one or more numbers, letters, dots, or hyphens followed by another dot then two to six letters or dots.
     * The following section is the optional files and directories.
     * Inside the group, we want to match any number of forward slashes, letters, numbers, underscores, spaces, dots, or hyphens.
     * Then we say that this group can be matched as many times as we want.
     * Pretty much this allows multiple directories to be matched along with a file at the end.
     * have been used the star instead of the question mark because the star says zero or more, not zero or one.
     * If a question mark was to be used there, only one file/directory would be able to be matched.
     *
     * Then a trailing slash is matched, but it can be optional. Finally we end with the end of the line.
     */
    public static final String URL_PATTERN_MATCHER = "(https?:\\/\\/)?([\\da-z\\.-]+)\\.([a-z\\.]{2,6})([\\/\\w \\.-]*)*\\/?";

    /**
     * was placed inside which tells the parser to not capture this group (more on this in the last regex).
     * We also want this non-captured group to be repeated three times — the {3} at the end of the group.
     * This group contains another group, a subgroup, and a literal dot. The parser looks for a match in the subgroup then a dot to move on.
     *
     * The subgroup is also another non-capture group. It's just a bunch of character sets (things inside brackets):
     * the string "25" followed by a number between 0 and 5; or the string "2" and a number between 0 and 4 and any number;
     * or an optional zero or one followed by two numbers, with the second being optional.
     *
     * After we match three of those, it's onto the next non-capturing group.
     * This one wants: the string "25" followed by a number between 0 and 5;
     * or the string "2" with a number between 0 and 4 and another number at the end; or an optional zero or one followed by two numbers,
     * with the second being optional.
     */
    public static final String IP_ADDRESS_MATCHER = "(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)";

    public static final String XPATH_TRANSLATE = "translate( ${var}, 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz')";

    public static final String SIGMA = "∑";

    //endregion

	/*
     * ----------------------------------------------------------------------
     * S T R I N G   C O N S T A N T S
     * ----------------------------------------------------------------------
     */

    //region Constants - String Constants Section

    public static final String OPEN_BOLD = "【";

    public static final String CLOSE_BOLD = "】";

    public static final String ARROW = " ➜";

    public static final String KEY_VALUE_SEPARATOR = ": ";

    /**
     * A String for linefeed LF ("\n").
     *
     * @see <a href="http://docs.oracle.com/javase/specs/jls/se7/html/jls-3.html#jls-3.10.6">JLF: Escape Sequences
     *      for Character and String Literals</a>
     * @since 3.2
     */
    public static final String LF = SystemUtils.LINE_SEPARATOR;

    /**
     * A String for carriage return CR ("\r").
     *
     * @see <a href="http://docs.oracle.com/javase/specs/jls/se7/html/jls-3.html#jls-3.10.6">JLF: Escape Sequences
     *      for Character and String Literals</a>
     * @since 3.2
     */
    public static final String CR = "\r";

    public static final String NA = "N/A";

    /**
     * A String for a space character.
     */
    public static final String BLANK = " ";

    public static final String EMPTY = StringUtils.EMPTY;

    public static final String DBL_SPACE = "  ";

    public static final String ERR_INFO = "error-information".toUpperCase().replaceAll( ".(?!$)", "$0 " );

    public static final String SYS_INFO = "system-information".toUpperCase().replaceAll( ".(?!$)", "$0 " );

    public static final String EXTRA_INFO = "additional-information".toUpperCase().replaceAll( ".(?!$)", "$0 " );

    public static final String ASSERTION_INFO = "assertion-error-information".toUpperCase().replaceAll( ".(?!$)", "$0 " ).replace( "-", DBL_SPACE );

    public static final String CHECKPOINT_INFO = "checkpoint-error-information".toUpperCase().replaceAll( ".(?!$)", "$0 " ).replace( "-", DBL_SPACE );

    public static final String TAB6 = StringUtils.repeat( Constants.TAB_CHAR, 6 );

    public static final String TAB7 = StringUtils.repeat( Constants.TAB_CHAR, 7 );

    public static final String TAB8 = StringUtils.repeat( Constants.TAB_CHAR, 8 );

    public static final String TAB9 = StringUtils.repeat( Constants.TAB_CHAR, 9 );

    //endregion

	/*
     * ----------------------------------------------------------------------
     * C H A R A C T E R  C O N S T A N T S
     * ----------------------------------------------------------------------
     */

    //region Constants - Characters Constants Section

    public static final char UNDERSCORE_CHAR = '_';

    /**
     * Constant for the disabled list delimiter.
     * This character is passed to the list parsing methods if delimiter parsing is disabled.
     * So this character should not occur in string property values.
     */
    public static final char NULL_CHAR = '\0';

    /** Constant for a literal, vertical bar, pipe symbol */
    public static final char LITERAL_CHAR = '|';

    /** Constant for the list delimiter as char.*/
    public static final char ESCAPE_CHAR = '\\';


    /** Constant for percent sign symbol */
    public static final char PERCENT_CHAR = '%';

    /** Constant for hash, number sign, pound sign symbol */
    public static final char HASH_CHAR = '#';

    /** assign or equal symbol */
    public static final char EQUALS_CHAR = '=';

    public static final char ROUND_OPEN_CHAR = '(';

    public static final char ROUND_CLOSE_CHAR = ')';

    public static final char CURLY_OPEN_CHAR = '{';

    public static final char CURLY_CLOSE_CHAR = '}';

    /** end token */
    public static final String END_TOKEN = String.valueOf( Constants.CURLY_CLOSE_CHAR );

    public static final char SQUARE_OPEN_CHAR = '[';

    public static final char SQUARE_CLOSE_CHAR = ']';

    public static final char SLASH_CHAR = '/';

    /** less-than, chevron symbol */
    public static final char CHEVRON_OPEN_CHAR = '<';

    public static final char CHEVRON_CLOSE_CHAR = '>';

    /** comma symbol */
    public static final char COMMA_CHAR = ',';

    /** a double quote symbol */
    public static final char DOUBLE_QUOTE_CHAR = '"';

    /**
     * The apostrophe ( ’ or ' ) is a punctuation mark, and sometimes a diacritical mark
     */
    public static final char SINGLE_QUOTE_CHAR = '\'';

    /**
     * The colon {@code ( : )} is a punctuation mark consisting of two equally sized dots centered on the same vertical line.
     */
    public static final char COLON_CHAR = ':';

    /**
     * The semicolon {@code ( ; )}  is a punctuation mark that separates major sentence elements.
     */
    public static final char SEMICOLON_CHAR = ';';

    /**
     * The exclamation mark {@code ( ! )} is a punctuation mark usually used to indicate high volume,
     * and often marks the end of a sentence.
     */
    public static final char EXCLAMATION_MARK_CHAR = '!';

    /**
     * A middle-dash is a punctuation mark that is similar to a hyphen or minus sign,
     * but differs from both of these symbols primarily in length and function.
     */
    public static final char   DASH_CHAR = '-';

    public static final char PLUS_CHAR = '+';

    public static final char ASTERISK_CHAR = '*';

    /** a space symbol */
    public static final char SPACE_CHAR = ' ';

    /**
     * In punctuation, period is a punctuation mark placed at the end of a sentence.
     */
    public static final char PERIOD_CHAR = '.';

    /** a tab symbol */
    public static final char TAB_CHAR = '\t';

    /** a dollar-sign symbol */
    public static final char DOLLAR_SIGN_CHAR = '$';

    /** start token */
    public static final String START_TOKEN = String.valueOf( Constants.DOLLAR_SIGN_CHAR ) + String.valueOf( Constants.CURLY_OPEN_CHAR );

    /** a pound-sign symbol */
    public static final char POUND_SIGN_CHAR = '£';

    /**
     * The number of bytes in a kilobyte.
     */
    public static final long ONE_KB = 1024;

    /**
     * The number of bytes in a kilobyte.
     *
     * @since 2.4
     */
    public static final BigInteger ONE_KB_BI = BigInteger.valueOf( ONE_KB );

    /**
     * The number of bytes in a megabyte.
     */
    public static final long ONE_MB = ONE_KB * ONE_KB;

    //endregion

	/*
     * ----------------------------------------------------------------------
     * F I L E   S I Z E   C O N S T A N T S
     * ----------------------------------------------------------------------
     */

    //region Constants - File Size Constants Section

    /**
     * The number of bytes in a megabyte.
     *
     * @since 2.4
     */
    public static final BigInteger ONE_MB_BI = ONE_KB_BI.multiply( ONE_KB_BI );

    /**
     * The number of bytes in a gigabyte.
     */
    public static final long ONE_GB = ONE_KB * ONE_MB;

    /**
     * The number of bytes in a gigabyte.
     *
     * @since 2.4
     */
    public static final BigInteger ONE_GB_BI = ONE_KB_BI.multiply( ONE_MB_BI );

    /**
     * The number of bytes in a terabyte.
     */
    public static final long ONE_TB = ONE_KB * ONE_GB;

    /**
     * The number of bytes in a terabyte.
     *
     * @since 2.4
     */
    public static final BigInteger ONE_TB_BI = ONE_KB_BI.multiply( ONE_GB_BI );

    /**
     * The number of bytes in a petabyte.
     */
    public static final long ONE_PB = ONE_KB * ONE_TB;

    /**
     * The number of bytes in a petabyte.
     *
     * @since 2.4
     */
    public static final BigInteger ONE_PB_BI = ONE_KB_BI.multiply( ONE_TB_BI );

    /**
     * The number of bytes in an exabyte.
     */
    public static final long ONE_EB = ONE_KB * ONE_PB;

    /**
     * The number of bytes in an exabyte.
     */
    public static final BigInteger ONE_EB_BI = ONE_KB_BI.multiply( ONE_PB_BI );

    /**
     * The number of bytes in a zettabyte.
     */
    public static final BigInteger ONE_ZB = BigInteger.valueOf( ONE_KB ).multiply( BigInteger.valueOf( ONE_EB ) );

    public static final String DATE_TIME_FORMATTER_LONG = "yyyy-MM-dd HH:mm:ss.SSS";

    public static final String FILENAME_DATE_TIME_FORMATTER_LONG = "yyyy_MM_dd_HH_mm_ss_SS";

    public static final String DATE_TIME_FORMATTER_SHORT = "HH:mm:ss.SSS";

    public static final String TIMESTAMP_PATTERN = "EEEE, MMMM dd, yyyy hh:mm:ss a";

    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormat.forPattern( "yyyy-MMM-dd" );

    public static final DateTimeFormatter HUMANIZED_DATE_FORMATTER = DateTimeFormat.forPattern( "yyyy-MMM-dd HH:mm:ss" );

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormat.forPattern( DATE_TIME_FORMATTER_LONG );


    //endregion

	/*
     * ----------------------------------------------------------------------
     * T I M E - C O N S T A N T S
     * ----------------------------------------------------------------------
     */

    //region Constants - Time Constants Section

    public static final PeriodFormatter FULL_FORMATTER = new PeriodFormatterBuilder().
            appendMinutes().appendSuffix( " minute", " minutes" ).
            appendSeparator( ", " ).
            appendSeconds().appendSuffix( " second", " seconds" ).
            appendSeparator( " and " ).
            appendMillis3Digit().
            appendSuffix( " millisecond", " milliseconds" ).
            toFormatter();

    public static final PeriodFormatter ABBR_FORMATTER = new PeriodFormatterBuilder().
            appendMinutes().appendSuffix( " min", " mins" ).
            appendSeparator( ", " ).
            appendSeconds().appendSuffix( " sec", " secs" ).
            appendSeparator( " and " ).
            appendMillis3Digit().
            appendSuffix( " millis", " millis" ).
            toFormatter();

    /** The default value for listDelimiter */
    public static char LIST_DELIMITER = LITERAL_CHAR;

    private Constants() throws IllegalAccessException
    {
        throw new IllegalAccessException( "Utility class should not be constructed" );
    }



    //endregion
}