public class Test {
    public int TEST_CASE_NUM = 5;
    public String METHOD_NAME = "addOne";
    public Class<?>[] parameterTypes;
    public Object[][] args;
    public Object[] retVals;

    public Test() {
        parameterTypes = new Class<?>[] {int.class};
        args = new Object[TEST_CASE_NUM][parameterTypes.length];
        retVals = new Object[TEST_CASE_NUM];

        for (int i = 0; i < TEST_CASE_NUM; i++) {
            args[i] = new Object[] {i};
            retVals[i] = i+2;
        }
    }
}


