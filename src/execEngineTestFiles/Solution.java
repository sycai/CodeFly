
public class Solution {
    // FIXME: Support primitive-typed arguments
    public int callMe(Integer a, Double b) {
        System.out.println(String.format("%d + %f = %f", a, b, a+b));
//        if (true) {
//            throw new RuntimeException("Stderr testing");
//        }
        return a;
    }
}
