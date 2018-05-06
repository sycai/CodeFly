package codeFly.fileSystem;

import java.util.*;

public class QuestionList {
    private List<String[]> questionList;

    public QuestionList() {
        questionList = new ArrayList<>();
    }

    public List<String[]> getQuestionList() {
        return questionList;
    }

    public void addQuestion(String description, String hintCode, String test) {
        String[] strs = {description, hintCode, test};
        questionList.add(strs);
    }

    public void initializeQuestionList() {
        String qDesc = "Add One\n" +
                "easy\n" +
                "Write a function addOne that takes an integer v and return v + 1.";
        String hintCode =
                "public class Solution {\n" +
                        "    public int addOne(int i) {\n" +
                        "        \n" +
                        "    }\n" +
                        "}";
        String test =
                "public class Test {\n" +
                        "    public int TEST_CASE_NUM = 5;\n" +
                        "    public String METHOD_NAME = \"addOne\";\n" +
                        "    public Class<?>[] parameterTypes;\n" +
                        "    public Object[][] args;\n" +
                        "    public Object[] retVals;\n" +
                        "\n" +
                        "    public Test() {\n" +
                        "        parameterTypes = new Class<?>[] {int.class};\n" +
                        "        args = new Object[TEST_CASE_NUM][parameterTypes.length];\n" +
                        "        retVals = new Object[TEST_CASE_NUM];\n" +
                        "\n" +
                        "        for (int i = 0; i < TEST_CASE_NUM; i++) {\n" +
                        "            args[i] = new Object[] {i};\n" +
                        "            retVals[i] = i+1;\n" +
                        "        }\n" +
                        "    }\n" +
                        "}\n" +
                        "\n";

        addQuestion(qDesc, hintCode, test);

        qDesc= "Add Two\n" +
                "easy\n" +
                "Write a function addTwo that takes an integer v and return v + 2.";
        hintCode =
                "public class Solution {\n" +
                        "    public int addTwo(int i) {\n" +
                        "        \n" +
                        "    }\n" +
                        "}";
        test =
                "public class Test {\n" +
                        "    public int TEST_CASE_NUM = 5;\n" +
                        "    public String METHOD_NAME = \"addTwo\";\n" +
                        "    public Class<?>[] parameterTypes;\n" +
                        "    public Object[][] args;\n" +
                        "    public Object[] retVals;\n" +
                        "\n" +
                        "    public Test() {\n" +
                        "        parameterTypes = new Class<?>[] {int.class};\n" +
                        "        args = new Object[TEST_CASE_NUM][parameterTypes.length];\n" +
                        "        retVals = new Object[TEST_CASE_NUM];\n" +
                        "\n" +
                        "        for (int i = 0; i < TEST_CASE_NUM; i++) {\n" +
                        "            args[i] = new Object[] {i};\n" +
                        "            retVals[i] = i+2;\n" +
                        "        }\n" +
                        "    }\n" +
                        "}\n" +
                        "\n";

        addQuestion(qDesc, hintCode, test);

        qDesc= "Two Sum\n" +
                "easy\n" +
                "Given an array of integers, return whether there are two numbers such that they add up to a specific target.\n" +
                "You may assume that each input would have exactly one solution, and you may not use the same element twice.";
        hintCode =
                "public class Solution {\n" +
                        "    public boolean twoSum(int[] nums, int target) {\n" +
                        "        \n" +
                        "    }\n" +
                        "}";
        test = "public class Test {\n" +
                "    public int TEST_CASE_NUM = 3;\n" +
                "    public String METHOD_NAME = \"twoSum\";\n" +
                "    public Class<?>[] parameterTypes;\n" +
                "    public Object[][] args;\n" +
                "    public Object[] retVals;\n" +
                "\n" +
                "    public Test() {\n" +
                "        parameterTypes = new Class<?>[] {int[].class, int.class};\n" +
                "        args = new Object[TEST_CASE_NUM][parameterTypes.length];\n" +
                "        retVals = new Object[TEST_CASE_NUM];\n" +
                "\n" +
                "        args[0] = new Object[] {new int[] {5,2,1,6}, 8};\n" +
                "        retVals[0] = true;\n" +
                "\n" +
                "        args[1] = new Object[] {new int[] {2,1,4}, 10};\n" +
                "        retVals[1] = false;\n" +
                "\n" +
                "        args[2] = new Object[] {new int[] {1,2,3}, 1};\n" +
                "        retVals[2] = false;\n" +
                "    }\n" +
                "}\n";


        addQuestion(qDesc, hintCode, test);

        qDesc= "Longest Substring Without Repeating Characters\n" +
                "medium\n" +
                "Given a string, find the length of the longest substring without repeating characters.\n" +
                "Examples:\n" +
                "Given \"abcabcbb\", the answer is \"abc\", which the length is 3.\n" +
                "Given \"bbbbb\", the answer is \"b\", with the length of 1.\n" +
                "Given \"pwwkew\", the answer is \"wke\", with the length of 3. Note that the answer must be a substring, \"pwke\" is a subsequence and not a substring.";
        hintCode =
                "public class Solution {\n" +
                        "    public int lengthOfLongestSubstring(String s) {\n" +
                        "        \n" +
                        "    }\n" +
                        "}";
        test = "public class Test {\n" +
                "    public int TEST_CASE_NUM = 3;\n" +
                "    public String METHOD_NAME = \"lengthOfLongestSubstring\";\n" +
                "    public Class<?>[] parameterTypes;\n" +
                "    public Object[][] args;\n" +
                "    public Object[] retVals;\n" +
                "\n" +
                "    public Test() {\n" +
                "        parameterTypes = new Class<?>[] {String.class};\n" +
                "        args = new Object[TEST_CASE_NUM][parameterTypes.length];\n" +
                "        retVals = new Object[TEST_CASE_NUM];\n" +
                "\n" +
                "        args[0] = new Object[] {\"abcabcbb\"};\n" +
                "        retVals[0] = 3;\n" +
                "\n" +
                "        args[1] = new Object[] {\"bbbbb\"};\n" +
                "        retVals[1] = 1;\n" +
                "\n" +
                "        args[2] = new Object[] {\"pwwkew\"};\n" +
                "        retVals[2] = 3;\n" +
                "    }\n" +
                "}";
        addQuestion(qDesc, hintCode, test);

        qDesc= "Median of Two Sorted Arrays\n" +
                "hard\n" +
                "There are two sorted arrays nums1 and nums2 of size m and n respectively.\n" +
                "Find the median of the two sorted arrays. The overall run time complexity should be O(log (m+n)).";
        hintCode =
                "public class Solution {\n" +
                        "    public int findMedianSortedArrays(int[] A, int[] B) {\n" +
                        "        \n" +
                        "    }\n" +
                        "}";
        test = "public class Test {\n" +
                "    public int TEST_CASE_NUM = 3;\n" +
                "    public String METHOD_NAME = \"findMedianSortedArrays\";\n" +
                "    public Class<?>[] parameterTypes;\n" +
                "    public Object[][] args;\n" +
                "    public Object[] retVals;\n" +
                "\n" +
                "    public Test() {\n" +
                "        parameterTypes = new Class<?>[] {int[].class, int[].class};\n" +
                "        args = new Object[TEST_CASE_NUM][parameterTypes.length];\n" +
                "        retVals = new Object[TEST_CASE_NUM];\n" +
                "\n" +
                "        args[0] = new Object[] {new int[] {1,3,5}, new int[] {2,4}};\n" +
                "        retVals[0] = 3;\n" +
                "\n" +
                "        args[1] = new Object[] {new int[] {1,2}, new int[] {3,4,5}};\n" +
                "        retVals[1] = 3;\n" +
                "\n" +
                "        args[2] = new Object[] {new int[] {}, new int[] {1,2,3,4,5}};\n" +
                "        retVals[2] = 3;\n" +
                "    }\n" +
                "}";
        addQuestion(qDesc, hintCode, test);
    }
}
