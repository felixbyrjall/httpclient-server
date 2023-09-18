package http.server;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class HttpResponse {

    public String statusLine;
    public final Map<String, String> headers;
    public String body;

    public HttpResponse(HttpRequest request) {
        headers = new HashMap<>();
        if (request.getMethod().equals("GET")) {
            if (request.getResource().equals("/")) {
                body = "<html><h1>Hello world!</h1></html>";
                statusLine = "HTTP/1.1 200 OK\r\n";
                headers.put("Content-Length", body.getBytes(StandardCharsets.UTF_8).length + "\r\n");
                headers.put("Content-Type", "text/html\r\n");
            } else if (request.getResource().equals("/cat/flavorcountry")) {
                body = "<html><h1>Cats only come in one flavor</h1></html>";
                statusLine = "HTTP/1.1 200 OK\r\n";
                headers.put("Content-Length", body.getBytes(StandardCharsets.UTF_8).length + "\r\n");
                headers.put("Content-Type", "text/html\r\n");
            } else {
                body = "<html><h1>404 NOT FOUND</h1></html>";
                statusLine = "HTTP/1.1 404 NOT FOUND\r\n";
            }
        } else if (request.getMethod().equals("POST")) {
            body = "<html><h1>POST</h1></html>";
            statusLine = "HTTP/1.1 200 OK\r\n";
            headers.put("Content-Length", body.getBytes(StandardCharsets.UTF_8).length + "\r\n");
            headers.put("Content-Type", "text/html\r\n");
        } else if (request.getResource().equals("/") && request.getMethod().equals("POST")) {
            body = "<html><h1>405</h1></html>";
            statusLine = "HTTP/1.1 405\r\n";
            headers.put("Content-Length", body.getBytes(StandardCharsets.UTF_8).length + "\r\n");
            headers.put("Content-Type", "text/html\r\n");
        }
    }
}
