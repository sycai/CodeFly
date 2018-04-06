package filesystem;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyStore.Entry;
import java.util.HashMap;
import java.util.Map;

import javax.swing.text.AbstractDocument.BranchElement;
import javax.xml.soap.DetailEntry;

public class Repository {
	private Map<String, String> loginInfo;
	private int latestQuestionNum;
	
	private static final String rootDirectory = "../Repository/";
	
	public Repository() {
		loginInfo = new HashMap<>();
		latestQuestionNum = 0;
	}
	
	// getters
	public String getQuestionDescription(int qNum) throws IOException {
		if (qNum < 1 || qNum > latestQuestionNum) return new String();
		String qFolder = getQuestionFolder(qNum);
		String path = rootDirectory + qFolder + File.separator + "QuestionDescription.txt";
		File f = new File(path);
		if(f.exists() && !f.isDirectory()) { 
		    try {
		    		byte[] encoded = Files.readAllBytes(Paths.get(path));
		    		return new String(encoded);
		    } catch (Exception e) {
				
		    }
		}
		return new String();
	}
	
	public File getQuestionTest(int qNum) throws IOException {
		if (qNum < 1 || qNum > latestQuestionNum) return new File("");
		String qFolder = getQuestionFolder(qNum);
		String path = rootDirectory + qFolder + File.separator + "Test.txt";
		File f = new File(path);
		if(f.exists() && !f.isDirectory()) { 
		    try {
		    		return f;
		    } catch (Exception e) {
				
		    }
		}
		return new File("");
	}
	
	public File getUserCode(int qNum, String userName, String language) throws IOException {
		if (qNum < 1 || qNum > latestQuestionNum) return new File("");
		String qFolder = getQuestionFolder(qNum);
		String path = rootDirectory + qFolder + File.separator + userName + File.separator + "Solution." + language;
		File f = new File(path);
		if(f.exists() && !f.isDirectory()) { 
		    try {
		    		return f;
		    } catch (Exception e) {
				
		    }
		}
		return new File("");
	}
	
	public Map<String, String> getLoginInfo() {
		return loginInfo;
	}
	
	// setters
	public void writeUserCode(int qNum, String userName, String content) {
		String path = rootDirectory + getQuestionFolder(qNum) + File.separator 
						+ userName + File.separator + "Solution.java";
		File file = new File(path);
		try {
			file.getParentFile().mkdirs(); 
			file.createNewFile();
			PrintWriter out = new PrintWriter(path);
			out.println(content);
			out.close();
		}
		catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public void addUserAccount(String userName, String pwd) {
		if (!loginInfo.containsKey(userName)) {
			loginInfo.put(userName, pwd);
		}
	}
	
	public void addQuestion(String qDescription, String test) throws IOException {
		latestQuestionNum++;
		int qNum = latestQuestionNum;
		String path = rootDirectory + getQuestionFolder(qNum) + File.separator;
		
		try {
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
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	//
	private String getQuestionFolder(int qNum) {
		return "Q" + String.valueOf(qNum);
	}
	
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Repository repository = new Repository();
		repository.addUserAccount("user1", "123456");
		repository.addUserAccount("user2", "000000");
		
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
		File qt = repository.getQuestionTest(1);
		File uc = repository.getUserCode(1, "Bob", "java");
		System.out.println(qd);
		
		BufferedReader br = new BufferedReader(new FileReader(qt));
		String st;
		while ((st = br.readLine()) != null) {
			System.out.println(st);
		}
		System.out.println("");
		
		br = new BufferedReader(new FileReader(uc));
		while ((st = br.readLine()) != null) {
			System.out.println(st);
		}

	}

}
