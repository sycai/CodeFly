package codeFly.executionEngine;


/**
 * Wrapper class that contains the return value of the invoked method, the possible standard output
 * and the standard error
 */
public class ExecutionResult {
    private Object returnValue;
    private String stdOutput;
    private String stdError;

    protected ExecutionResult(Object returnValue, String stdOutput, String stdError) {
        this.returnValue = returnValue;
        this.stdOutput = stdOutput;
        this.stdError = stdError;
    }

    public Object getReturnValue() {
        return this.returnValue;
    }

    public String getStdOutput() {
        return this.stdOutput;
    }

    public String getStdError() {
        return this.stdError;
    }

    // Used mainly for debugging
    @Override
    public String toString() {
        String retValStr;
        if (returnValue == null) {
            retValStr = "null";
        } else {
            retValStr = returnValue.toString();
        }
        return String.format("Ret val: %s\nStdout: %s\nStderr: %s", retValStr, stdOutput, stdError);
    }
}
