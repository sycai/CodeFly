package codeFly.tester;

import codeFly.CodeFly;
import codeFly.executionEngine.CodeFlyJavaEngine;
import codeFly.executionEngine.ExecutionResult;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;

public class JavaTestEngine {
    private static final JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
    private static final String TEST_CLASS_NAME = "Test";
    private static final String TEST_NUM_FIELD = "TEST_CASE_NUM";
    private static final String PARAM_TYPE_FIELD = "parameterTypes";
    private static final String ARGS_FIELDS = "args";
    private static final String RETURN_VALUE_FIELDS = "retVals";
    private static final String METHOD_NAME_FIELD = "METHOD_NAME";

    // TODO: handle cases where file/dir doesn't exist
    public static TestResult getTestResult(int qNum, String userName) throws Exception {
        File codeFile = CodeFly.repo.getUserCode(qNum, userName, "Java");
        File testFile = CodeFly.repo.getQuestionTest(qNum);

        compiler.run(null, null, null, testFile.getPath());

        URLClassLoader classLoader = URLClassLoader.newInstance(new URL[]{testFile.getParentFile().toURI().toURL()});
        Class<?> testClass = Class.forName(TEST_CLASS_NAME, true, classLoader);
        Object instance = testClass.newInstance();

        Class<?>[] paramTypes = (Class<?> []) testClass.getDeclaredField(PARAM_TYPE_FIELD).get(instance);
        Object[][] args = (Object[][]) testClass.getDeclaredField(ARGS_FIELDS).get(instance);
        Object[] retVals = (Object[]) testClass.getDeclaredField(RETURN_VALUE_FIELDS).get(instance);
        String methodName = (String) testClass.getDeclaredField(METHOD_NAME_FIELD).get(instance);
        int testNum = (int) testClass.getDeclaredField(TEST_NUM_FIELD).get(instance);

        ExecutionResult res = null;
        int passCount = 0;
        for (int i = 0; i < testNum; i++) {
            res = CodeFlyJavaEngine.getRunningResult(codeFile, methodName, paramTypes,args[i]);
            if (res.hasReturnValue() && res.getReturnValue().equals(retVals[i])) {
                passCount++;
            } else {
                return new TestResult(passCount, testNum, args[i], res, retVals[i]);
            }
        }
        return new TestResult(testNum, testNum, null, res, null);

    }

}
