package codeFly.tester;

import codeFly.executionEngine.ExecutionResult;

/**
 * Wrapper class for bundling all testing information together.
 */
public class TestResult {
    private boolean isPassed;
    private int totalTestsNumber;
    private int totalPassedNumber;

    // Information for the first failed test
    private Object[] failedInput;
    private ExecutionResult res;
    private Object expectedOutput;

    public TestResult (int totalPassedNumber, int totalTestsNumber, Object[] failedInput, ExecutionResult exeRes,
                       Object expectedOutput){
        this.totalPassedNumber = totalPassedNumber;
        this.totalTestsNumber = totalTestsNumber;
        this.res = exeRes;

        // Test suit is passed if and only if all test cases are passed
        if (this.totalPassedNumber == this.totalTestsNumber) {
            this.isPassed = true;
            this.failedInput = null;
            this.expectedOutput = null;
        } else {
            this.failedInput = failedInput;
            this.isPassed = false;
            this.expectedOutput = expectedOutput;
        }
    }

    public boolean isPassed() {
        return isPassed;
    }

    public int getTotalTestsNumber() {
        return this.totalTestsNumber;
    }

    public int getTotalPassedNumber() {
        return this.totalPassedNumber;
    }

    public Object[] getFailedInput() {
        return failedInput;
    }

    public Object getWrongOutput() {
        return res.getReturnValue();

    }

    public String getStdOut() {
        return res.getStdOutput();

    }

    public boolean hasReturnValue() {
        return res.hasReturnValue();
    }

    public String getStdErr() {
        return res.getStdError();
    }

    public Object getExpectedOutput() {
        return this.expectedOutput;
    }

    @Override
    public String toString() {
        String ret = getTotalPassedNumber() + "/" + getTotalTestsNumber() + " passed.";
        ret += " stdout: " + getStdOut() + " ";
        ret += " stderr: " + getStdErr() + " ";
        return ret;
    }
}
