import http.server.HttpServer;

public class Application {

    public static void main(String[] args) {
        HttpServer server = new HttpServer(8181);
        server.start();
    }
}
