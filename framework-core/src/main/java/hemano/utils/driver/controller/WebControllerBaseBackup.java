package hemano.utils.driver.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * The Class WebControllerBase.
 */
public abstract class WebControllerBaseBackup {

    /** The Constant LOG. */
    private static final Logger WEB_CONTROLLER_BASE_LOG = LoggerFactory.getLogger(WebControllerBaseBackup.class);
    /**
     * uses Reporter to log the screenshot of a failed log entry (error(...)) to
     * the logs
     *
     * @param file
     *            file must exist
     */
    protected void reportLogScreenshot(File file) {
        WEB_CONTROLLER_BASE_LOG.warn("Screenshot has been generated, path is {}", file.getAbsolutePath());
    }

    /**
     * Info.
     *
     * @param message
     *            the message
     */
    public void info(String message) {
        WEB_CONTROLLER_BASE_LOG.info(message);
    }

    /**
     * Warn.
     *
     * @param message
     *            the message
     */
    public void warn(String message) {
        WEB_CONTROLLER_BASE_LOG.warn(message);
    }

    /**
     * Error.
     *
     * @param message
     *            the message
     */
    public void error(String message) {
        WEB_CONTROLLER_BASE_LOG.error(message);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.persado.oss.quality.stevia.selenium.core.WebController#sleep(
     * long)
     */
    public void sleep(long milliseconds) {
        try {
            WEB_CONTROLLER_BASE_LOG.info("About to sleep for " + milliseconds + " msecs");
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            WEB_CONTROLLER_BASE_LOG.error(e.getMessage());
        }

    }

    /**
     * make a filename within the reportng folders so we can use relative paths.
     *
     * @return the file
     */
    protected File createScreenshotFile() {

        if (System.getProperty("screenShotDirectory") != null) {

            File outputDir = new File(System.getProperty("screenShotDirectory"));
            try {
                outputDir.mkdirs();
            } catch (SecurityException e) {
                WEB_CONTROLLER_BASE_LOG.error(e.getMessage());
                return null;
            }
            return new File(outputDir, "screenshot-" + System.nanoTime() + ".png");
        }
        return new File("screenshot-" + System.nanoTime() + ".png");
    }

    /**
     * Make relative path.
     *
     * @param file
     *            the file instance
     * @return the file path
     */
    private String makeRelativePath(File file) {
        String outputDir = new File(System.getProperty("outputDirectory")).getParent();
        if (outputDir != null) {
            String absolute = file.getAbsolutePath();
            int beginIndex = absolute.indexOf(outputDir) + outputDir.length();
            String relative = absolute.substring(beginIndex);
            return ".." + relative.replace('\\', '/');
        }
        return file.getPath();
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.persado.oss.quality.stevia.selenium.core.WebController#getTableInfo
     * (java.lang.String, int)
     */
//    @Override
//    public Map<String, Map<String, String>> getTableInfo(String locator, int numberOfColumns) {
//        Map<String, Map<String, String>> tableData = new HashMap<String, Map<String, String>>();
//        Map<String, String> tableColumns = null;
//        waitForElement(locator);
//        int rowNumber = 1;
//        for (int counter = 1; counter <= getNumberOfTotalRows(locator); counter++) {
//            tableColumns = new HashMap<String, String>();
//            for (int columns = 1; columns <= numberOfColumns; columns++) {
//                if (locator.startsWith("css")) {
//                    tableColumns.put("column_" + Integer.toString(columns), getText(locator + " *:nth-child(" + counter + ") *:nth-child(" + columns + ")"));
//                } else if (locator.startsWith("//") || locator.startsWith("xpath")) {
//                    tableColumns.put("column_" + Integer.toString(columns), getText(locator + "//*[" + counter + "]//*[" + columns + "]"));
//                } else {
//                    tableColumns.put("column_" + Integer.toString(columns), getText("css=#" + locator + " *:nth-child(" + counter + ") *:nth-child(" + columns + ")"));
//
//                }
//            }
//            tableData.put("row_" + Integer.toString(rowNumber), tableColumns);
//            rowNumber = rowNumber + 1;
//        }
//        return tableData;
//    }

    public void enableActionsLogging() {
    }

    /*
     * (non-Javadoc)
     *
     * @see com.persado.oss.quality.stevia.selenium.core.WebController#
     * disableActionsLogging()
     */

    public void disableActionsLogging() {

    }
}
