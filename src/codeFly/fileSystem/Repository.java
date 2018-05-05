package codeFly.fileSystem;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Repository management module responsible for writing and reading files of source code, information, tests etc.
 */
public class Repository {
    private Map<String, String> loginInfo;
    private int latestQuestionNum;

    private static final String rootDirectory = "./Repository/";
    private static final String loginInfoPath = rootDirectory + "LoginInfo.txt";

    private static Repository obj;

    /**
     * Factory method for singleton Repository class
     * @return
     * @throws IOException
     */
    public static Repository getInstance() throws IOException {
        if (obj == null) {
            obj = new Repository();
        }
        return obj;
    }
    //get questionNum
    public int getQuestionNum(){
        return latestQuestionNum;
    }
    private Repository() throws IOException {
        loginInfo = new HashMap<>();
        latestQuestionNum = 0;


        File rootDir = new File(rootDirectory);
        if (!rootDir.exists()) rootDir.mkdir();

        for (File qFile : rootDir.listFiles()) {
            if (qFile.getName().matches("^Q\\d+")) {
                latestQuestionNum++;
            }
        }

        File logInFile = new File(loginInfoPath);
        if (!logInFile.exists()) logInFile.createNewFile();

        FileReader fileReader = new FileReader(logInFile);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String username = null;
        while((username = bufferedReader.readLine()) != null) {
            String pwd = bufferedReader.readLine();
            loginInfo.put(username, pwd);
        }

        bufferedReader.close();
    }

    public String getQuestionTitle(int qNum) throws IOException {
        if (qNum < 1 || qNum > latestQuestionNum) throw new IOException("Question" + qNum + "doesn't exist.");
        String qFolder = getQuestionFolder(qNum);
        String path = rootDirectory + qFolder + File.separator + "QuestionDescription.txt";

        String title = Files.readAllLines(Paths.get(path)).get(0);
        return title;
    }

    public String getQuestionDifficulty(int qNum) throws IOException {
        if (qNum < 1 || qNum > latestQuestionNum) throw new IOException("Question" + qNum + "doesn't exist.");
        String qFolder = getQuestionFolder(qNum);
        String path = rootDirectory + qFolder + File.separator + "QuestionDescription.txt";

        String difficulty = Files.readAllLines(Paths.get(path)).get(1);
        return difficulty;
    }

    public String getQuestionDescription(int qNum) throws IOException {
        if (qNum < 1 || qNum > latestQuestionNum) throw new IOException("Question" + qNum + "doesn't exist.");
        String qFolder = getQuestionFolder(qNum);
        String path = rootDirectory + qFolder + File.separator + "QuestionDescription.txt";

        List<String> file = Files.readAllLines(Paths.get(path));
        StringBuilder description = new StringBuilder();
        for (int i = 2; i < file.size() - 1; i++) {
            description.append(file.get(i)).append("\n");
        }
        description.append(file.get(file.size() - 1));

        return description.toString();
    }

    public File getQuestionTest(int qNum) throws IOException {
        if (qNum < 1 || qNum > latestQuestionNum) throw new IOException("Question" + qNum + "doesn't exist.");
        String qFolder = getQuestionFolder(qNum);
        String path = rootDirectory + qFolder + File.separator + "Test.java";
        File f = new File(path);

        return f.getCanonicalFile();
    }

    public File getUserCode(int qNum, String userName, String language) throws IOException {
        if (qNum < 1 || qNum > latestQuestionNum) throw new IOException("Question" + qNum + "doesn't exist.");
        String qFolder = getQuestionFolder(qNum);
        String path = rootDirectory + qFolder + File.separator + userName + File.separator + "Solution." + language.toLowerCase();
        File f = new File(path);

        return f.getCanonicalFile();
    }

    //show hint code
    public File getHintCode(int qNum, String userName, String language) throws IOException {
        if (qNum < 1 || qNum > latestQuestionNum) throw new IOException("Question" + qNum + "doesn't exist.");
        String qFolder = getQuestionFolder(qNum);
        String path = rootDirectory + qFolder + File.separator + userName + File.separator + "HintCode." + language.toLowerCase();
        File f = new File(path);

        return f.getCanonicalFile();
    }

    public Map<String, String> getLoginInfo() {
        return loginInfo;
    }

    public void writeUserCode(int qNum, String userName, String content) throws IOException {
        String path = rootDirectory + getQuestionFolder(qNum) + File.separator
                + userName + File.separator + "Solution.java";
        File file = new File(path);

        file.getParentFile().mkdirs();
        file.createNewFile();
        PrintWriter out = new PrintWriter(path);
        out.println(content);
        out.close();
    }

    public void writeHintCode(int qNum, String userName, String content) throws IOException {
        String path = rootDirectory + getQuestionFolder(qNum) + File.separator
                + userName + File.separator + "HintCode.java";
        File file = new File(path);

        file.getParentFile().mkdirs();
        file.createNewFile();
        PrintWriter out = new PrintWriter(path);
        out.println(content);
        out.close();
    }

    public void addUserAccount(String userName, String pwd) throws IOException {
        if (loginInfo.containsKey(userName)) {
            throw new IOException("username " + userName + " already exists.");
        }
        if (pwd == "" || pwd.length() == 0) {
            throw new IOException("password " + pwd + " is invalid.");
        }

        loginInfo.put(userName, pwd);

        File file = new File(loginInfoPath);
        PrintWriter out = new PrintWriter(new FileWriter(file, true));
        out.append(userName + "\n" + pwd + "\n");
        out.close();
    }

    public void addQuestion(String qDescription, String test) throws IOException {
        latestQuestionNum++;
        int qNum = latestQuestionNum;
        String path = rootDirectory + getQuestionFolder(qNum) + File.separator;

        File file = new File(path);
        file.mkdir();

        PrintWriter out;
        String descripPath = path + File.separator + "QuestionDescription.txt";
        File qDescrip = new File(descripPath);
        qDescrip.createNewFile();
        out = new PrintWriter(descripPath);
        out.println(qDescription);
        out.close();

        String testPath = path + File.separator + "Test.java";
        File qTest = new File(testPath);
        qTest.createNewFile();
        out = new PrintWriter(testPath);
        out.println(test);
        out.close();
    }


    private String getQuestionFolder(int qNum) {
        return "Q" + String.valueOf(qNum);
    }


    /**
     * Will only do its work when the repo has no questions!
     * @throws IOException
     */
    public void setUpExample() throws IOException {
        if (loginInfo.isEmpty()) {
            addUserAccount("Amy", "123456");
            addUserAccount("Bob", "000000");
            addUserAccount("John", "246135");
        }

        if (new File(rootDirectory, "Q1").exists()) {
            // There's already something in the repo
            // Don't set up examples
            return;
        }

        QuestionList ql = new QuestionList();
        ql.initializeQuestionList();
        for (String[] strs: ql.getQuestionList()) {
            addQuestion(strs[0], strs[1]);
        }

        //answer
        String q1AmyAns =
                "public class Solution {\n" +
                "    public int addOne(int i) {\n" +
                "        System.out.println(\"Amy code's q1 standard output\");\n" +
                "        return i + 1;\n" +
                "    }\n" +
                "}";

        String q1BobAns =
                "public class Solution {\n" +
                "    public int addOne(int i) {\n" +
                "        System.out.println(\"Bob code's q1 standard output\");\n" +
                "        return i + 2;\n" +
                "    }\n" +
                "}";

        String q2AmyAns =
                "public class Solution {\n" +
                        "    public int addTwo(int i) {\n" +
                        "        int a = 0;\n" +
                        "        System.out.println(\"Amy's q2 stdout\");\n" +
                        "        return i + 2;\n" +
                        "    }\n" +
                        "}";

        String q2BobAns =
                "public class Solution {\n" +
                "    public int addTwo(int i) {\n" +
                "        int a = 0;\n" +
                "        a = i/a;\n" +
                "        return i + 2;\n" +
                "    }\n" +
                "}";

        //hint
        String q1AmyHint =
                "public class Solution {\n" +
                        "    public int addOne(int i) {\n" +
                        "        \n" +
                        "    }\n" +
                        "}";
        String q1BobHint =
                "public class Solution {\n" +
                        "    public int addOne(int i) {\n" +
                        "        \n" +
                        "    }\n" +
                        "}";


        String q2AmyHint =
                "public class Solution {\n" +
                        "    public int addTwo(int i) {\n" +
                        "        \n" +
                        "    }\n" +
                        "}";
        String q2BobHint =
                "public class Solution {\n" +
                        "    public int addTwo(int i) {\n" +
                        "        \n" +
                        "    }\n" +
                        "}";


        String q3AmyHint =
                "public class Solution {\n" +
                        "    public int[] twoSum(int[] nums, int target) {\n" +
                        "        \n" +
                        "    }\n" +
                        "}";
        String q3BobHint =
                "public class Solution {\n" +
                        "    public int[] twoSum(int[] nums, int target) {\n" +
                        "        \n" +
                        "    }\n" +
                        "}";


        String q4AmyHint =
                "public class Solution {\n" +
                        "    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {\n" +
                        "        \n" +
                        "    }\n" +
                        "}";
        String q4BobHint =
                "public class Solution {\n" +
                        "    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {\n" +
                        "        \n" +
                        "    }\n" +
                        "}";


        String q5AmyHint =
                "public class Solution {\n" +
                        "    public int lengthOfLongestSubstring(String s) {\n" +
                        "        \n" +
                        "    }\n" +
                        "}";
        String q5BobHint =
                "public class Solution {\n" +
                        "    public int lengthOfLongestSubstring(String s) {\n" +
                        "        \n" +
                        "    }\n" +
                        "}";

        String q6AmyHint =
                "public class Solution {\n" +
                        "    public double findMedianSortedArrays(int[] A, int[] B) {\n" +
                        "        \n" +
                        "    }\n" +
                        "}";
        String q6BobHint =
                "public class Solution {\n" +
                        "    public double findMedianSortedArrays(int[] A, int[] B) {\n" +
                        "        \n" +
                        "    }\n" +
                        "}";
        
        //write user code
        writeUserCode(1, "Bob", q1BobAns);
        writeUserCode(2, "Bob", q2BobAns);
        writeUserCode(1, "Amy", q1AmyAns);
        writeUserCode(2, "Amy", q2AmyAns);

        //write hint code
        writeHintCode(1, "Bob", q1BobHint);
        writeHintCode(1, "Amy", q1AmyHint);
        writeHintCode(2, "Bob", q2BobHint);
        writeHintCode(2, "Amy", q2AmyHint);
        writeHintCode(3, "Bob", q3BobHint);
        writeHintCode(3, "Amy", q3AmyHint);
        writeHintCode(4, "Bob", q4BobHint);
        writeHintCode(4, "Amy", q4AmyHint);
        writeHintCode(5, "Bob", q5BobHint);
        writeHintCode(5, "Amy", q5AmyHint);
        writeHintCode(6, "Bob", q6BobHint);
        writeHintCode(6, "Amy", q6AmyHint);
    }
}