package tests;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;

public class ProjectLogger {

    public final static Logger logger = Logger.getLogger("Prestashop");
    private Handler fileHandler;
    {
        try {
            logger.setUseParentHandlers(false);
            fileHandler = new FileHandler("logFile.txt");
            logger.addHandler(fileHandler);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
