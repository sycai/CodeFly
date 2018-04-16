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
    
    private Repository() throws IOException {
        loginInfo = new HashMap<>();
        latestQuestionNum = 0;
        
        File file = new File(rootDirectory);
        if (!file.exists()) file.mkdir();
        
        file = new File(loginInfoPath);
        if (!file.exists()) file.createNewFile();
        
        FileReader fileReader = new FileReader(file);
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

    public String getQuestionDescription(int qNum) throws IOException {
        if (qNum < 1 || qNum > latestQuestionNum) throw new IOException("Question" + qNum + "doesn't exist.");
        String qFolder = getQuestionFolder(qNum);
        String path = rootDirectory + qFolder + File.separator + "QuestionDescription.txt";

        List<String> file = Files.readAllLines(Paths.get(path));
        StringBuilder description = new StringBuilder();
        for (int i = 1; i < file.size(); i++) {
            description.append(file.get(i));
        }
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


    public void setUpExample() throws IOException {
        if (loginInfo.isEmpty()) {
            addUserAccount("Amy", "123456");
            addUserAccount("Bob", "000000");
            addUserAccount("John", "246135");
        }

        String q1Desc = "Add One\n" +
                "Write a function addOne that takes an integer v and return v + 1.";
        String test1 =
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

        String q2Desc= "Add Two\n" +
                "Write a function addTwo that takes an integer v and return v + 2.";
        String test2 =
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
                "            retVals[i] = i+2;\n" +
                "        }\n" +
                "    }\n" +
                "}\n" +
                "\n";



        addQuestion(q1Desc, test1);
        addQuestion(q2Desc, test2);

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

        String q2BobAns =
                "public class Solution {\n" +
                "    public int addOne(int i) {\n" +
                "        int a = 0;" +
                "        a = i/a;\n" +
                "        return i + 2;\n" +
                "    }\n" +
                "}";


        writeUserCode(1, "Bob", q1BobAns);
        writeUserCode(2, "Bob", q2BobAns);
        writeUserCode(1, "Amy", q1AmyAns);
    }
}
