package http;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class HttpClient {

    public final Map<String, String> headers = new HashMap<>();
    public final int statusCode;
    public final String status;
    public final String body;

    public HttpClient(String host, int port, String url) {
        try (Socket socket = new Socket(host, port)) {
            String request = "GET " + url + " HTTP/1.1\r\n" +
                    "Host: " + host + "\r\n" +
                    "\r\n";
            socket.getOutputStream().write(request.getBytes());

            //Read line one for status
            String[] responseLine = readLine(socket).split(" ", 3);
            statusCode = Integer.parseInt(responseLine[1]);
            status = responseLine[2];

            String headerLine;
            while(!(headerLine = readLine(socket)).isEmpty()) {
                String[] headerParts = headerLine.split(" *: *", 2);
                headers.put(headerParts[0], headerParts[1]);
            }

            StringBuilder body = new StringBuilder();
            for (int i = 0; i < getContentLength(); i++) {
                body.append((char) socket.getInputStream().read());
            }
            this.body = body.toString();

            printThings();

        } catch (IOException e) {
            throw new RuntimeException(e);
        } ;
    }

    private String readLine(Socket socket) throws IOException {
        StringBuilder line = new StringBuilder();
        int c;
        while ((c = socket.getInputStream().read()) != '\r') {
            line.append((char)c);
        }
        c = socket.getInputStream().read(); // read next \n character
        if (c != '\n') {
            throw new IllegalStateException("Invalid http header - \\r not followed by \\n");
        }
        return line.toString();
    }

    private int getContentLength() {
        return Integer.parseInt(headers.get("Content-Length"));
    }

    private void printThings() {
        System.out.printf("status: %s, status code: %s \r\n", status, statusCode);
        headers.forEach((key, value) -> System.out.printf("%s:%s%n", key, value));
        System.out.println(body);
    }


}
