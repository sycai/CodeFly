package codeFly;
import codeFly.dispatcher.*;
import codeFly.fileSystem.Repository;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Entry point of the server
 */

public class CodeFly {
    public static final Logger logger = Logger.getLogger("CodeFly");
    public static final String ROOT_DIR = "src/codeFly/";
    // FIXME: delete FileSystemEmulator if necessary since it is no longer used.
    public static Repository repo;

    public static void main(String[] args) {

        try {
            repo = Repository.getInstance();
            repo.setUpExample();
        } catch (IOException ex) {
            // Log and exit
            CodeFly.logger.severe("Failed to initialize file system. Shut down server now.");
            CodeFly.logger.severe(ex.getCause().toString());
            return;
        }

        // Logger configuration
        logger.setLevel(Level.INFO);

        // Start server
        logger.info("Starting CodeFly server...");
        TaskDispatcher dispatcher = TaskDispatcher.getTaskDispatcher(8080);
        dispatcher.start();
        logger.info("Server running. Type Ctrl-C to shut down");
    }
}
