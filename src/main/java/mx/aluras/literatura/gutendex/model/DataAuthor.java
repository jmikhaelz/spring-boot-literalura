package mx.aluras.literatura.gutendex.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DataAuthor(
        @JsonAlias("name") String nombre,
        @JsonAlias("birth_year") int nacimiento,
        @JsonAlias("death_year") int fallecimiento) {

    @Override
    public String toString() {
        return "\n\t"+nombre+" ("+nacimiento+"-"+fallecimiento+")";
    }
}
