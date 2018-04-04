package codeFly;
import codeFly.dispatcher.*;
import codeFly.fileSystem.FileSystemEmulator;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Entry point of the server
 */

public class CodeFly {
    public static final Logger logger = Logger.getLogger("CodeFly");
    public static final String ROOT_DIR = "src/codeFly/";
    // FIXME: This should be the real file system in the final product
    public static final FileSystemEmulator fileSys = new FileSystemEmulator();

    public static void main(String[] args) {

        // Logger configuration
        logger.setLevel(Level.INFO);

        // Start server
        logger.info("Starting CodeFly server...");
        TaskDispatcher dispatcher = TaskDispatcher.getTaskDispatcher(8080);
        dispatcher.start();
        logger.info("Server running. Type Ctrl-C to shut down");
    }
}
