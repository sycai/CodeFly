package codeFly.dispatcher;

import codeFly.CodeFly;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.json.JSONObject;

import java.io.*;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.util.Map;

/**
 * Accept "GET/POST" request for register context, GET for users to access the
 * register view, POST for users to input user name and password to register for
 * their account on filesystem. If user already exists, register will fail
 */
public class RegisterHandler implements HttpHandler {
	@Override
	public void handle(HttpExchange exchange) {
		// Fetch method
		String method = exchange.getRequestMethod();
		try {
			// GET method
			if (method.equalsIgnoreCase("GET")) {
				File RegisterPage = new File(CodeFly.ROOT_DIR + "frontEnd/register.html");
				// response with a html file with register view
				exchange.sendResponseHeaders(200, RegisterPage.length());
				OutputStream os = exchange.getResponseBody();
				Files.copy(RegisterPage.toPath(), os);
				os.close();
				CodeFly.logger.info(
						String.format("Sending %s to client: %S", RegisterPage.getName(), exchange.getRemoteAddress()));
			}
			// POST method
			else if (method.equalsIgnoreCase("POST")) {
				// Parse request and get username and password
				Map<String, String> queryPairs = HandlerTools.parseUriQuery(exchange.getRequestURI().getQuery());
				String username = queryPairs.get("username");
				String password = queryPairs.get("password");

				// Comparison with user name in file system's database and send back different
				// result based on the comparison result
				Map<String, String> loginInDb = CodeFly.repo.getLoginInfo();
				if (loginInDb.containsKey(username)) {
					// user exists, fail to register
					System.out.println("user exist\n");
					JSONObject ResultJson = new JSONObject();
					ResultJson.put("result", "Fail: user already exists");
					String jsonStr = ResultJson.toString();
					// Send json back
					exchange.sendResponseHeaders(200, jsonStr.length());
					exchange.getResponseBody().write(jsonStr.getBytes());
					exchange.getResponseBody().close();
					CodeFly.logger
							.info(String.format("Sending register failure to client: %S", exchange.getRemoteAddress()));
				} else {
					// user not exists, update filesystem' user information
					System.out.println("user not exists\n");
					// Update filesystem' user information
					CodeFly.repo.addUserAccount(username, password);
					JSONObject ResultJson = new JSONObject();
					ResultJson.put("result", "Success");
					String jsonStr = ResultJson.toString();
					// Send json back
					exchange.sendResponseHeaders(200, jsonStr.length());
					exchange.getResponseBody().write(jsonStr.getBytes());
					exchange.getResponseBody().close();
					CodeFly.logger
							.info(String.format("Sending register success to client: %S", exchange.getRemoteAddress()));
				}

			} else {
				// All other methods are invalid
				HandlerTools.send404NotFound(exchange);
			}
		} catch (IOException ex) {
			CodeFly.logger.severe(ex.getCause().toString());
		}
	}
}
