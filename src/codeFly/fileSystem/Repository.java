package fileSystem;

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

public class Repository {
    private Map<String, String> loginInfo;
    private int latestQuestionNum;
    
    private static final String rootDirectory = "./Repository/";
    private static final String loginInfoPath = rootDirectory + "LoginInfo.txt";
    
    public Repository() throws IOException {
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
    
    // getters
    public String getQuestionDescription(int qNum) throws IOException {
        if (qNum < 1 || qNum > latestQuestionNum) throw new IOException("Question" + qNum + "doesn't exist.");
        String qFolder = getQuestionFolder(qNum);
        String path = rootDirectory + qFolder + File.separator + "QuestionDescription.txt";
        
        //        File f = new File(path);
        //        if(f.exists() && !f.isDirectory()) something goes wrong when adding question
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded);
    }
    
    public File getQuestionTest(int qNum) throws IOException {
        if (qNum < 1 || qNum > latestQuestionNum) throw new IOException("Question" + qNum + "doesn't exist.");
        String qFolder = getQuestionFolder(qNum);
        String path = rootDirectory + qFolder + File.separator + "Test.txt";
        File f = new File(path);
        
        //        if(f.exists() && !f.isDirectory()) something goes wrong when adding question
        return f;
    }
    
    public File getUserCode(int qNum, String userName, String language) throws IOException {
        if (qNum < 1 || qNum > latestQuestionNum) throw new IOException("Question" + qNum + "doesn't exist.");
        String qFolder = getQuestionFolder(qNum);
        String path = rootDirectory + qFolder + File.separator + userName + File.separator + "Solution." + language;
        File f = new File(path);
        
        //        if(f.exists() && !f.isDirectory()) something goes wrong when adding userCode
        return f;
    }
    
    public Map<String, String> getLoginInfo() {
        return loginInfo;
    }
    
    // setters
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
    
    
    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub
        Repository repository = new Repository();
        
        /**************************************************************************/
        // only need to add user account once, each time we run it, repository will
        // read login information from LoginInfo.txt
        
        //        repository.addUserAccount("Amy", "123456");
        //        repository.addUserAccount("Bob", "000000");
        //        repository.addUserAccount("John", "246135");
        
        Map<String, String> loginInfo = repository.getLoginInfo();
        System.out.println("Login Information: ");
        for (Map.Entry<String, String> entry : loginInfo.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
        
        repository.addQuestion("qDescription1", "test1");
        repository.addQuestion("qDescription2", "test2");
        
        repository.writeUserCode(1, "Bob", "b..");
        repository.writeUserCode(1, "Amy", "a..");
        repository.writeUserCode(2, "Bob", "bb..");
        
        String qd = repository.getQuestionDescription(2);
        System.out.println("\ngetQuestionDescription \"2\":");
        System.out.println(qd);
        
        File qt = repository.getQuestionTest(1);
        System.out.println("\ngetQuestionTest \"1\":");
        BufferedReader br = new BufferedReader(new FileReader(qt));
        String st;
        while ((st = br.readLine()) != null) {
            System.out.println(st);
        }
        
        File uc = repository.getUserCode(1, "Bob", "java");
        System.out.println("\ngetUserCode \"1, bob, java\":");
        br = new BufferedReader(new FileReader(uc));
        while ((st = br.readLine()) != null) {
            System.out.println(st);
        }
        
    }
    
}
