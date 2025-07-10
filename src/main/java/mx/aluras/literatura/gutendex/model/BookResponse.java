package mx.aluras.literatura.gutendex.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record BookResponse(
        @JsonAlias("results") List<DataBook> resultados) {
}
