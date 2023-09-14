package http;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class HttpMessage {

    public final String requestLine;
    public final Map<String, String> headers;
    public HttpMessage(Socket clientSocket) throws IOException {
        this.headers = new HashMap<>();
        requestLine = readLine(clientSocket);
        fetchHeaders(clientSocket);
    }

    private void fetchHeaders(Socket clientSocket) throws IOException {
        String headerLine;
        while(!(headerLine = readLine(clientSocket)).isEmpty()) {
            String[] headerParts = headerLine.split(" *: *", 2);
            headers.put(headerParts[0], headerParts[1]);
        }
    }

    private String readLine(Socket socket) throws IOException {
        StringBuilder line = new StringBuilder();
        int c;
        while ((c = socket.getInputStream().read()) != '\r') {
            line.append((char)c);
        }
        c = socket.getInputStream().read(); // read the next \n
        return line.toString();
    }
}
