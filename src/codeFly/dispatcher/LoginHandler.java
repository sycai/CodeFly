package codeFly.dispatcher;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import codeFly.CodeFly;

import com.sun.org.apache.bcel.internal.classfile.Code;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.Map;

/**
 * Accept "GET/POST" request for login context, GET for users to access the
 * Login view, POST for users to input user name and password to login to his
 * own question list view
 */
public class LoginHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) {
        // Fetch request method
        String methodType = exchange.getRequestMethod();
        String userName = HandlerTools.getUserName(exchange);
        try {
            // GET method
            if (methodType.equals("GET")) {
                File nextPage;
                if (HandlerTools.isActiveUser(userName)) {
                    // The user session is active. There's no need to login again
                    nextPage = new File(CodeFly.ROOT_DIR + "frontEnd/loginJump.html");
                } else {
                    // Provide login window
                    nextPage = new File(CodeFly.ROOT_DIR + "frontEnd/login.html");
                }
                // response with a html file of the login view
                exchange.sendResponseHeaders(200, nextPage.length());
                OutputStream os = exchange.getResponseBody();
                Files.copy(nextPage.toPath(), os);
                os.close();
                CodeFly.logger.info(
                        String.format("Sending %s to client: %S", nextPage.getName(), exchange.getRemoteAddress()));
            }

            // POST method
            else if (methodType.equals("POST")) {
                // Parse the request
                BufferedReader reader = new BufferedReader(new InputStreamReader(exchange.getRequestBody()));
                String line;
                String username = "";
                String password = "";
                // Get username and password from the parse result
                while ((line = reader.readLine()) != null) {
                    String[] tokens = line.split("&");
                    String[] user_tokens = tokens[0].split("=");
                    String[] pw_tokens = tokens[1].split("=");
                    if (user_tokens[0].equals("username")) {
                        username = user_tokens[1];
                    }
                    if (pw_tokens[0].equals("password")) {
                        password = pw_tokens[1];
                    }
                }
                // User name and password authentication
                Map<String, String> loginInDb = CodeFly.repo.getLoginInfo();
                if (loginInDb.containsKey(username)) {

                    if (loginInDb.get(username).equals(password)) {
                        // user exists, password correct
                        JSONObject testResultJson = new JSONObject();
                        testResultJson.put("result", "Success");
                        String jsonStr = testResultJson.toString();
                        // Set cookie
                        String cookie = TaskDispatcher.COOKIE_KEY + username + ";SameSite=Strict";
                        exchange.getResponseHeaders().set("Set-Cookie", cookie);
                        TaskDispatcher.activeUsers.add(username);

                        // Send json back
                        exchange.sendResponseHeaders(200, jsonStr.length());
                        exchange.getResponseBody().write(jsonStr.getBytes());
                        exchange.getResponseBody().close();
                        CodeFly.logger.info(
                                String.format("Sending login success to client: %S", exchange.getRemoteAddress()));
                    } else {
                        // user exists, password incorrect
                        JSONObject testResultJson = new JSONObject();
                        testResultJson.put("result", "Incorrect password");
                        String jsonStr = testResultJson.toString();
                        // Send json back
                        exchange.sendResponseHeaders(200, jsonStr.length());
                        exchange.getResponseBody().write(jsonStr.getBytes());
                        exchange.getResponseBody().close();
                        CodeFly.logger.info(
                                String.format("Sending incorrect password to client: %S", exchange.getRemoteAddress()));
                    }

                } else {
                    // user not exist
                    JSONObject testResultJson = new JSONObject();
                    testResultJson.put("result", "User does not exist");
                    String jsonStr = testResultJson.toString();
                    // Send json back
                    exchange.sendResponseHeaders(200, jsonStr.length());
                    exchange.getResponseBody().write(jsonStr.getBytes());
                    exchange.getResponseBody().close();
                    CodeFly.logger.info(String.format("Sending no user to client: %S", exchange.getRemoteAddress()));
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