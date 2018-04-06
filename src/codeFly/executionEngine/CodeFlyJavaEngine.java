package codeFly.executionEngine;

import codeFly.CodeFly;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.*;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * Compiles Java source files on the server.
 */
public class CodeFlyJavaEngine {
    private static final JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
    private static final String PREDEFINED_CLASS_NAME = "Solution";
    private static final String STDOUT_FILE_NAME = "out";
    private static final String STDERR_FILE_NAME = "err";

    private static Object getReturnValue(File compilationDir, String methodName, Object[] args) {
        Object retVal = null;
        try {
            // The output files are in the same directory as the source code
            File toRun = new File(compilationDir, PREDEFINED_CLASS_NAME + ".java");

            //TODO: reset compiler output directory (optional)
            compiler.run(null, null, null, toRun.getPath());

            // Load class instance
            URLClassLoader classLoader = URLClassLoader.newInstance(new URL[]{compilationDir.toURI().toURL()});
            Class<?> cls = Class.forName(PREDEFINED_CLASS_NAME, true, classLoader);
            Object instance = cls.newInstance();

            // Redirect stdout and stderr to files
            File outputFile = new File(compilationDir, STDOUT_FILE_NAME);
            if (!outputFile.exists()) {
                outputFile.createNewFile();
            }
            File errorFile = new File(compilationDir, STDERR_FILE_NAME);
            if (!errorFile.exists()) {
                errorFile.createNewFile();
            }
            PrintStream stdout = System.out;
            PrintStream stderr = System.err;
            System.setOut(new PrintStream(new FileOutputStream(new File(compilationDir, STDOUT_FILE_NAME))));
            System.setErr(new PrintStream(new FileOutputStream(new File(compilationDir, STDERR_FILE_NAME))));

            // Get parameter types of this method
            Class<?>[] parameterTypes = new Class<?>[args.length];
            for (int i = 0; i < args.length; i++) {
                parameterTypes[i] = args[i].getClass();
            }
            // Fetch and invoke this method
            Method method = cls.getDeclaredMethod(methodName, parameterTypes);
            try {
                retVal = method.invoke(instance, args);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }

            // Cancel IO rediection
            System.setOut(stdout);
            System.setErr(stderr);

        } catch (Exception ex) {
            CodeFly.logger.severe(ex.getMessage());
        }
        return retVal;
    }

    private static String readContent(File target) throws IOException{
        StringBuilder contentBuilder = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(target)));
        String line;
        while ((line = reader.readLine()) != null) {
            contentBuilder.append(line);
        }
        return contentBuilder.toString();
    }

    /**
     * Build the target file and invoke the target method. This function returns the return val as well as all the
     * standard output and standard error wrapped in the ExecutionResult object.
     * @param target file to be compiled
     * @param methodName method to be invoked
     * @param args arguments to be passed to the invoked method
     * @return return value of the method, standard output and standard error
     */
    public static ExecutionResult getRunningResult(File target, String methodName, Object[] args) {
        ExecutionResult res = null;
        try {
            if (!target.isFile() || !target.getName().endsWith(PREDEFINED_CLASS_NAME + ".java")) {
                throw new IOException("Illegal file name to be compiled: " + target.getPath());
            }
            File compilationDir = target.getCanonicalFile().getParentFile();
            Object retVal = getReturnValue(compilationDir, methodName, args);

            File outputFile = new File(compilationDir, STDOUT_FILE_NAME);
            String stdOutput = readContent(outputFile);
            File errorFile = new File(compilationDir, STDERR_FILE_NAME);
            String stdError = readContent(errorFile);

            res = new ExecutionResult(retVal, stdOutput, stdError);

        } catch (IOException ex) {
            CodeFly.logger.severe(ex.getMessage());
        }
        return res;
    }

    public static void main(String[] args) throws Exception {
        File toExec = new File("src/execEngineTestFiles/Solution.java");
        String methodName = "callMe";
        Object[] methodArgs = {2, 2.5};
        ExecutionResult result = CodeFlyJavaEngine.getRunningResult(toExec, methodName, methodArgs);
        System.out.println(result);
    }
}
