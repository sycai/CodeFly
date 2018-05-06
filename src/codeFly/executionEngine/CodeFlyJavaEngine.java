package codeFly.executionEngine;

import codeFly.CodeFly;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.*;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.concurrent.*;

/**
 * Compiles Java source files on the server.
 */
public class CodeFlyJavaEngine {
    private static final JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
    private static final String PREDEFINED_CLASS_NAME = "Solution";
    private static final String STDOUT_FILE_NAME = "out";
    private static final String STDERR_FILE_NAME = "err";

    /**
     * So far it is now known how to redirect stdout and stderr on a per-thread basis. Thus we must use synchronized
     * function here to guarantee that redirection only happens during method invocation. In addition, Java doesn't
     * support killing a thread without its consent. If the user code contains a potential infinite loop, the
     * invoker thread won't exit.
     * Perhaps a better solution is to use a process instead of a thread to invoke the target method.
     * // FIXME: let's see whether we would have enough time in the end to refactor this chunk of code.
     * @param compilationDir the directory where .class file is located
     * @param methodName the method to be invoked
     * @param args the arguments for this method
     * @return
     */
    private synchronized static Object getReturnValue(File compilationDir, String methodName,
                                                      Class<?>[] paramTypes, Object[] args) {
        Object retVal = null;
        PrintStream stdout = System.out;
        PrintStream stderr = System.err;
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<Object> future = null;
        try {
            // The output files are in the same directory as the source code
            File toRun = new File(compilationDir, PREDEFINED_CLASS_NAME + ".java");
            File oldClass = new File(compilationDir, PREDEFINED_CLASS_NAME + ".class");
            if (oldClass.exists()) {
                oldClass.delete();
            }

            // Redirect stdout and stderr to files
            File outputFile = new File(compilationDir, STDOUT_FILE_NAME);
            if (!outputFile.exists()) {
                outputFile.createNewFile();
            }
            File errorFile = new File(compilationDir, STDERR_FILE_NAME);
            if (!errorFile.exists()) {
                errorFile.createNewFile();
            }
            System.setOut(new PrintStream(new FileOutputStream(new File(compilationDir, STDOUT_FILE_NAME))));
            System.setErr(new PrintStream(new FileOutputStream(new File(compilationDir, STDERR_FILE_NAME))));

            compiler.run(null, null, System.err, toRun.getPath());

            // Load class instance
            URLClassLoader classLoader = URLClassLoader.newInstance(new URL[]{compilationDir.toURI().toURL()});
            Class<?> cls = Class.forName(PREDEFINED_CLASS_NAME, true, classLoader);
            Object instance = cls.newInstance();


            // Fetch and invoke this method
            Method method = cls.getDeclaredMethod(methodName, paramTypes);
            // Invoke method in another thread so we could set timeout for execution
            future = executor.submit(new InvokeTask(method, instance, args));

            // 1 second time out
            retVal = future.get(1, TimeUnit.SECONDS);

        } catch (TimeoutException ex){
            System.err.println("Time Limit Exceeded");
        } catch (ExecutionException ex) {
            System.err.println(ex);
        } catch (ClassNotFoundException ignore) {
            // Do nothing.
        } catch (Exception ex) {
            CodeFly.logger.severe(ex.getMessage());
        } finally {
            executor.shutdownNow();
            // Cancel IO rediection
            System.setOut(stdout);
            System.setErr(stderr);
            return retVal;
        }
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
    public static ExecutionResult getRunningResult(File target, String methodName, Class<?>[] paramTypes, Object[] args) {
        ExecutionResult res = null;
        try {
            if (!target.isFile() || !target.getName().endsWith(PREDEFINED_CLASS_NAME + ".java")) {
                throw new IOException("Illegal file name to be compiled: " + target.getPath());
            }
            File compilationDir = target.getCanonicalFile().getParentFile();

            Object retVal = getReturnValue(compilationDir, methodName, paramTypes, args);


            File outputFile = new File(compilationDir, STDOUT_FILE_NAME);
            String stdOutput = readContent(outputFile);
            File errorFile = new File(compilationDir, STDERR_FILE_NAME);
            String stdError = readContent(errorFile).replaceAll("^.*java:", "");
            if (!stdError.isEmpty() && stdError.contains("error")) {
                stdError = "line " + stdError;
            }

            res = new ExecutionResult(retVal, stdOutput, stdError);

        } catch (IOException ex) {
            CodeFly.logger.severe(ex.getCause().toString());
        }
        return res;
    }
}
