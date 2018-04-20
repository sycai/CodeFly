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

    public void addQuestion(String description, String test) {
        String[] strs = new String[2];
        strs[0] = description;
        strs[1] = test;
        questionList.add(strs);
    }

    public void initializeQuestionList() {
        String qDesc = "Add One\n" +
                "easy\n" +
                "Write a function addOne that takes an integer v and return v + 1.";
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
        addQuestion(qDesc, test);

        qDesc= "Add Two\n" +
                "easy\n" +
                "Write a function addTwo that takes an integer v and return v + 2.";
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
        addQuestion(qDesc, test);

        qDesc= "Two Sum\n" +
                "easy\n" +
                "Given an array of integers, return indices of the two numbers such that they add up to a specific target.\n" +
                "\n" +
                "You may assume that each input would have exactly one solution, and you may not use the same element twice.";
        test = "";
        addQuestion(qDesc, test);

        qDesc= "Add Two Numbers\n" +
                "medium\n" +
                "You are given two non-empty linked lists representing two non-negative integers. The digits are stored in reverse order and each of their nodes contain a single digit. Add the two numbers and return it as a linked list.\n" +
                "\n" +
                "You may assume the two numbers do not contain any leading zero, except the number 0 itself.";
        test = "";
        addQuestion(qDesc, test);

        qDesc= "Longest Substring Without Repeating Characters\n" +
                "medium\n" +
                "Given a string, find the length of the longest substring without repeating characters.\n" +
                "\n" +
                "Examples:\n" +
                "\n" +
                "Given \"abcabcbb\", the answer is \"abc\", which the length is 3.\n" +
                "\n" +
                "Given \"bbbbb\", the answer is \"b\", with the length of 1.\n" +
                "\n" +
                "Given \"pwwkew\", the answer is \"wke\", with the length of 3. Note that the answer must be a substring, \"pwke\" is a subsequence and not a substring.";
        test = "";
        addQuestion(qDesc, test);

        qDesc= "Median of Two Sorted Arrays\n" +
                "hard\n" +
                "There are two sorted arrays nums1 and nums2 of size m and n respectively.\n" +
                "\n" +
                "Find the median of the two sorted arrays. The overall run time complexity should be O(log (m+n)).";
        test = "";
        addQuestion(qDesc, test);

        qDesc= "Longest Palindromic Substring\n" +
                "medium\n" +
                "Given a string s, find the longest palindromic substring in s. You may assume that the maximum length of s is 1000.";
        test = "";
        addQuestion(qDesc, test);

        qDesc= "Reverse Integer\n" +
                "easy\n" +
                "Given a 32-bit signed integer, reverse digits of an integer.";
        test = "";
        addQuestion(qDesc, test);

        qDesc= "String to Integer (atoi)\n" +
                "medium\n" +
                "Implement atoi which converts a string to an integer.\n" +
                "\n" +
                "The function first discards as many whitespace characters as necessary until the first non-whitespace character is found. Then, starting from this character, takes an optional initial plus or minus sign followed by as many numerical digits as possible, and interprets them as a numerical value.\n" +
                "\n" +
                "The string can contain additional characters after those that form the integral number, which are ignored and have no effect on the behavior of this function.\n" +
                "\n" +
                "If the first sequence of non-whitespace characters in str is not a valid integral number, or if no such sequence exists because either str is empty or it contains only whitespace characters, no conversion is performed.\n" +
                "\n" +
                "If no valid conversion could be performed, a zero value is returned.";
        test = "";
        addQuestion(qDesc, test);

        qDesc= "Palindrome Number\n" +
                "easy\n" +
                "Determine whether an integer is a palindrome. An integer is a palindrome when it reads the same backward as forward.";
        test = "";
        addQuestion(qDesc, test);

        qDesc= "Container With Most Water\n" +
                "medium\n" +
                "Given n non-negative integers a1, a2, ..., an, where each represents a point at coordinate (i, ai). n vertical lines are drawn such that the two endpoints of line i is at (i, ai) and (i, 0). Find two lines, which together with x-axis forms a container, such that the container contains the most water.\n" +
                "\n" +
                "Note: You may not slant the container and n is at least 2.";
        test = "";
        addQuestion(qDesc, test);

        qDesc= "Merge k Sorted Lists\n" +
                "hard\n" +
                "Merge k sorted linked lists and return it as one sorted list. Analyze and describe its complexity.";
        test = "";
        addQuestion(qDesc, test);
    }
}
