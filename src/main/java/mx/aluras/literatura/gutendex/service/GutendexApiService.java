package mx.aluras.literatura.gutendex.service;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class GutendexApiService {
    private ObjectMapper objectMapper = new ObjectMapper();

    public <T> T getAbout(String json, Class<T> aboutClass) throws Exception {
        try {
            if (!(json.isEmpty()))
                return objectMapper.readValue(json, aboutClass);
            else
                throw new Exception("[getAbout] : Conversion de Object.");
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
