package codeFly.dispatcher;

import codeFly.CodeFly;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;

/**
 * Send About page to the browser
 */
public class AboutHandler implements HttpHandler {
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		File AboutPage = new File(CodeFly.ROOT_DIR + "frontEnd/about.html");
		// response with a success response
		exchange.sendResponseHeaders(200, AboutPage.length());
		OutputStream os = exchange.getResponseBody();
		Files.copy(AboutPage.toPath(), os);
		os.close();
		CodeFly.logger
				.info(String.format("Sending %s to client: %S", AboutPage.getName(), exchange.getRemoteAddress()));
	}
}
