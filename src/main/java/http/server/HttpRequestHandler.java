package http.server;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class HttpRequestHandler extends Thread {

    private final Socket clientSocket;

    public HttpRequestHandler(Socket socket) {
        clientSocket = socket;
    }

    @Override
    public void run() {
        try {
            HttpRequest request = new HttpRequest(clientSocket);
            HttpResponse response = new HttpResponse(request);
            OutputStream out = clientSocket.getOutputStream();

            out.write(response.statusLine.getBytes(StandardCharsets.UTF_8));
            response.headers.forEach((key, value) -> {
                try {
                    out.write(String.format("%s:%s", key, value).getBytes(StandardCharsets.UTF_8));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            out.write("\r\n".getBytes(StandardCharsets.UTF_8));
            out.write(response.body.getBytes(StandardCharsets.UTF_8));
            clientSocket.getOutputStream().close();
            clientSocket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
