package mx.aluras.literatura.gutendex.model;

import java.util.List;
import java.util.stream.Collectors;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import mx.aluras.literatura.gutendex.client.GeminiApiClient;

@Entity
@Table(name = "libros")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String titulo;
    private String idioma;
    // @Lob Si es necesario mas extenso el texto
    private String sipnosis;
    private Integer descargas;
    
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(name = "libros_autores", joinColumns = @JoinColumn(name = "libro_id"), inverseJoinColumns = @JoinColumn(name = "autor_id"))
    private List<Author> autores;

    public Book() {
    }

    public Book(DataBook libro, GeminiApiProperties geminiApiProperties) {
        this.titulo = libro.titulo();
        this.idioma = libro.idiomas().get(0);
        this.sipnosis = GeminiApiClient.getTranslate(libro.sipnosis().get(0), "spanish", geminiApiProperties.getKey());
        this.descargas = libro.descargas();
        this.autores = libro.autores().stream()
                .map(a -> new Author(a))
                .collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public String getSipnosis() {
        return sipnosis;
    }

    public void setSipnosis(String sipnosis) {
        this.sipnosis = sipnosis;
    }

    public Integer getDescargas() {
        return descargas;
    }

    public void setDescargas(Integer descargas) {
        this.descargas = descargas;
    }

    public List<Author> getAutores() {
        return autores;
    }

    public void setAutores(List<Author> autores) {
        this.autores = autores;
    }

    @Override
    public String toString() {
        return "\n\t Titulo: " + getTitulo()
                + "\t Autores : " + getAutores()
                + "\n\t Idioma : " + getIdioma()
                + "\t Descargas: " + getDescargas()
                + "\n\t" + getSipnosis();
    }

}
