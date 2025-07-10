package mx.aluras.literatura.gutendex.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DataBook(
        @JsonAlias("title") String titulo,
        @JsonAlias("authors") List<DataAuthor> autores,
        @JsonAlias("summaries") List<String> sipnosis,
        @JsonAlias("languages") List<String> idiomas,
        @JsonAlias("download_count") int descargas) {

    @Override
    public String toString() {
    return "\n\t|\t\tBOOK\t\t|\n\t>"+titulo+"\n\tAutor(es):"+autores
        +"\n\t Descargas:"+descargas+" Idioma(s): "+idiomas+""
        +"\n\t Resumen:"+sipnosis.get(0);
    }
}
