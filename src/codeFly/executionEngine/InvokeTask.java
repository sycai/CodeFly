package codeFly.executionEngine;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class InvokeTask implements Callable<Object> {
    private Method method;
    private Object instance;
    private Object[] args;

    @Override
    public Object call() {
        Object ret = null;
        try {
            ret = method.invoke(instance, args);
        } catch (Exception ex) {
            System.err.println(ex.getCause());
        }
        return ret;
    }

    public InvokeTask(Method method, Object instance, Object[] args) {
        this.method = method;
        this.instance = instance;
        this.args = args;
    }
}
