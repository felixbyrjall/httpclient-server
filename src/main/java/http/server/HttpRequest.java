package http.server;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest {

    private String method;
    private String resource;
    private String httpVersion;
    private final Map<String, String> headers;
    private String body;

    public HttpRequest(Socket clientSocket) throws IOException {
        this.headers = new HashMap<>();
        readRequestLine(clientSocket);
        fetchHeaders(clientSocket);
        readBody(clientSocket);
        printStuff();
    }

    private void readRequestLine(Socket clientSocket) throws IOException {
        String[] requestLine = readLine(clientSocket).split(" ", 3);
        method = requestLine[0];
        resource = requestLine[1];
        httpVersion = requestLine[2];
    }

    private void fetchHeaders(Socket clientSocket) throws IOException {
        String headerLine;
        while (!(headerLine = readLine(clientSocket)).isEmpty()) {
            String[] headerParts = headerLine.split(" *: *", 2);
            headers.put(headerParts[0], headerParts[1]);
        }
    }

    private void readBody(Socket clientSocket) throws IOException {
        int contentLength = 0;
        try {
            StringBuilder body = new StringBuilder();
            contentLength = Integer.parseInt(headers.get("Content-Length"));
            for (int i = 0; i < contentLength; i++) {
                body.append((char) clientSocket.getInputStream().read());
            }
            this.body = body.toString();
        } catch (NumberFormatException e) {
            body = "";
        }
    }

    private String readLine(Socket socket) throws IOException {
        StringBuilder line = new StringBuilder();
        int c;
        while ((c = socket.getInputStream().read()) != -1) {
            if (c == '\r') {
                c = socket.getInputStream().read();
                break;
            } else {
                line.append((char) c);
            }

        }
        return line.toString();
    }


    private void printStuff() {
        System.out.printf("%s %s %s%n", method, resource, httpVersion);
        headers.forEach((key, value) -> System.out.printf("%s:%s%n", key, value));
        System.out.println(body);
    }

    public String getMethod() {
        return method;
    }

    public String getResource() {
        return resource;
    }

    public String getHttpVersion() {
        return httpVersion;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public String getBody() {
        return body;
    }
}
