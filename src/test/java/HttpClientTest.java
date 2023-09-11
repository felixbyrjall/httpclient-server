import http.HttpClient;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class HttpClientTest {

    @Test
    void shouldMakeHttpCall() throws IOException {
        HttpClient client = new HttpClient("httpbin.org", 80, "/html");
        assert client.statusCode == 200;
    }
}
