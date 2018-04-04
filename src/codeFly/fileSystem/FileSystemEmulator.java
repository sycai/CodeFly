package codeFly.fileSystem;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * This is just a emulator of file system.
 */
public class FileSystemEmulator {
    private HashMap<String, String> loginInfo;
    private ArrayList<String> questionList;
    private HashMap<Integer, HashMap<String, String>> userCodes;


    public FileSystemEmulator() {
        // Initialize accounts
        loginInfo = new HashMap<>();
        // Add virtual user names and their passwords
        loginInfo.put("Alice","123abc");
        loginInfo.put("Bob","321cba");

        // Add questions description
        String questionOneDesc = "This is the description of question 1";
        String questionTwoDesc = "This is the description of question 2";
        questionList = new ArrayList<>(Arrays.asList(questionOneDesc, questionTwoDesc));

        // Initialized user code
        userCodes = new HashMap<>();

        String code = "public class Solution{\n" +
                      "    public static void main(String[] args) {\n" +
                      "        System.out.println(\"Hello, World\");\n" +
                      "    }\n" +
                      "}\n";
        try {
            writeUserCode(0, "Alice", code);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Questions index starts from 1 rather than 0
     */
    public String getQuestionDescription(int qNum) throws IOException {
        if (qNum <= 0 || qNum > questionList.size()) {
            throw new IOException("Question" + qNum + "doesn't exist.");
        }
        return questionList.get(qNum - 1);
    }

    /**
     * Test feature will be added later, don't call this function for testing!!
     */
    @Deprecated
    public File getQuestionTest(int qNum) throws IOException {
        return null;
    }

    /**
     * To test code passing, call getUserCodeString instead.
     */
    @Deprecated
    public File getUserCode(int qNum, String userName, String language) throws IOException {
        return null;
    }

    public String getUserCodeString (int qNum, String userName, String language) throws IOException {
        if (!userCodes.containsKey(qNum)) {
            throw new IOException("Question" + qNum + "doesn't exist.");
        }
        if (!userCodes.get(qNum).containsKey(userName)) {
            throw new IOException("User name " + userName + " doesn't exist.");
        }
        return userCodes.get(qNum).get(userName);
    }

    public HashMap<String, String> getLoginInfo() throws IOException {
        return loginInfo;
    }

    public void writeUserCode(int qNum, String userName, String contents) throws IOException {
        HashMap<String, String> temp = userCodes.getOrDefault(qNum, new HashMap<>());
        temp.put(userName, contents);
        userCodes.put(qNum, temp);
    }

    public void addUserAccount(String userName, String password) throws IOException {
        if (loginInfo.containsKey(userName)) {
            throw new IOException("User " + userName + " already exists.");
        }
        loginInfo.put(userName, password);
    }

    /*
    public static void main(String[] args) throws Exception {
        FileSystemEmulator emulator = new FileSystemEmulator();
        System.out.println(emulator.getQuestionDescription(1));
        emulator.addUserAccount("Charlie","123");
        System.out.println(emulator.getLoginInfo());
        System.out.println(emulator.getUserCodeString(0, "Alice", "Java"));
    }
    */
}
