package codeFly.fileSystem;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.rmi.activation.UnknownObjectException;
import java.security.KeyStore.Entry;
import java.util.HashMap;
import java.util.Map;

import javax.swing.text.AbstractDocument.BranchElement;
import javax.xml.soap.DetailEntry;

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

    public String getQuestionDescription(int qNum) throws IOException {
        if (qNum < 1 || qNum > latestQuestionNum) throw new IOException("Question" + qNum + "doesn't exist.");
        String qFolder = getQuestionFolder(qNum);
        String path = rootDirectory + qFolder + File.separator + "QuestionDescription.txt";

        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded);
    }
    
    public File getQuestionTest(int qNum) throws IOException {
        if (qNum < 1 || qNum > latestQuestionNum) throw new IOException("Question" + qNum + "doesn't exist.");
        String qFolder = getQuestionFolder(qNum);
        String path = rootDirectory + qFolder + File.separator + "Test.txt";
        File f = new File(path);

        return f;
    }
    
    public File getUserCode(int qNum, String userName, String language) throws IOException {
        if (qNum < 1 || qNum > latestQuestionNum) throw new IOException("Question" + qNum + "doesn't exist.");
        String qFolder = getQuestionFolder(qNum);
        String path = rootDirectory + qFolder + File.separator + userName + File.separator + "Solution." + language;
        File f = new File(path);

        return f;
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
        
        String testPath = path + File.separator + "Test.txt";
        File qTest = new File(testPath);
        qTest.createNewFile();
        out = new PrintWriter(testPath);
        out.println(test);
        out.close();
    }
    
    //
    private String getQuestionFolder(int qNum) {
        return "Q" + String.valueOf(qNum);
    }


    public void setUpExample() throws IOException {
        if (loginInfo.isEmpty()) {
            addUserAccount("Amy", "123456");
            addUserAccount("Bob", "000000");
            addUserAccount("John", "246135");
        }

        addQuestion("qDescription1", "test1");
        addQuestion("qDescription2", "test2");

        writeUserCode(1, "Bob", "");
        writeUserCode(1, "Amy", "");
        writeUserCode(2, "Bob", "");
    }
}
