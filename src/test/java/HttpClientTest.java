import http.HttpClient;
import http.HttpServer;
import org.junit.jupiter.api.Test;

public class HttpClientTest {

    @Test
    void shouldMakeHttpCall() {
        HttpClient client = new HttpClient("httpbin.org", 80, "/html");
        assert client.statusCode == 200;
    }

    @Test
    void shouldTalkToServer() {

        HttpServer server = new HttpServer(8181);
        server.start();
        HttpClient client = new HttpClient("localhost", 8181, "/");
        assert client.statusCode == 200;
        server.stop();
    }
}
