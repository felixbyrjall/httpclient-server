package http;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class HttpServer extends Thread {

    private final int port;

    public HttpServer(int port) {
        this.port=port;
    }

    @Override
    public void run() {
        try(ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                new SocketThread(clientSocket).start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private class SocketThread extends Thread {

        Socket clientSocket;

        public SocketThread(Socket clientSocket) {
            this.clientSocket = clientSocket;
            System.out.println("New thread!");
        }

        @Override
        public void run() {
            try {
                HttpMessage message = new HttpMessage(clientSocket);
                var body = "<html><h1>Hello world!</h1></html>";
                var contentLength = body.getBytes().length;
                clientSocket.getOutputStream().write(("HTTP/1.1 200 OK\r\n" +
                        "Content-Type: text/html\r\n" +
                        "Content-Length: " + contentLength + "\r\n" +
                        "\r\n" +
                        body).getBytes(StandardCharsets.UTF_8));
                printStuff(message);
                clientSocket.getOutputStream().close();
                clientSocket.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void printStuff(HttpMessage message) {
        System.out.println(message.requestLine);
        message.headers.forEach((key, value) -> System.out.printf("key: %s value: %s%n", key, value));
    }
}
