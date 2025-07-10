package mx.aluras.literatura.gutendex.client;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.springframework.stereotype.Service;

@Service
public class GutendexApiClient {
    public final HttpClient client = HttpClient.newHttpClient();

    public String getJSONToString(URI uri) throws IOException {
        try {
            HttpRequest resquest = HttpRequest.newBuilder()
                    .uri(uri)
                    .build();
            HttpResponse<String> response = client.send(resquest, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200)
                return null;
            return response.body();
        } catch (Exception e) {
            throw new IOException("\t[Gutendex] Error al realizar la consulta a API", e);
        }
    }
}
