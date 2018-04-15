package codeFly.dispatcher;

import codeFly.CodeFly;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;

public class EditorPageHandler implements HttpHandler{
    @Override
    public void handle (HttpExchange exchange) throws IOException{
        // FIXME: request should provide the question number and server should return the proper question description
        File editorPage = new File(CodeFly.ROOT_DIR + "frontEnd/editor.html");
        // response with a success response
        exchange.sendResponseHeaders(200, editorPage.length());
        OutputStream os = exchange.getResponseBody();
        Files.copy(editorPage.toPath(), os);
        os.close();
        CodeFly.logger.info(String.format("Sending %s to client: %S",
                editorPage.getName(), exchange.getRemoteAddress()));
    }
}
