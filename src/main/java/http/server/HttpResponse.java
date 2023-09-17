package http.server;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class HttpResponse {

    public final String statusLine;
    public final Map<String, String> headers;
    public final String body;

    public HttpResponse(HttpRequest request) {
        headers = new HashMap<>();
        body = "<html><h1>Hello world!</h1></html>";
        statusLine = "HTTP/1.1 200 OK\r\n";
        headers.put("Content-Length", body.getBytes(StandardCharsets.UTF_8).length + "\r\n");
        headers.put("Content-Type", "text/html\r\n");
    }
}
