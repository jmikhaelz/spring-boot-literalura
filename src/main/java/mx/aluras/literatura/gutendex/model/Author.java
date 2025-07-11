package mx.aluras.literatura.gutendex.model;

import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;

@Entity
@Table(name = "autores")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column(unique = true)
    private String nombre;
    private Integer nacimiento;
    private Integer fallecimiento;

    @ManyToMany(mappedBy = "autores", cascade = CascadeType.ALL)
    private List<Book> libros;

    public Author() {
    }

    public Author(DataAuthor autor) {
        this.nombre = autor.nombre();
        this.nacimiento = (autor.nacimiento() != null) ? autor.nacimiento() : 0;
        this.fallecimiento = (autor.fallecimiento() != null) ? autor.fallecimiento() : 0;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getNacimiento() {
        return nacimiento;
    }

    public void setNacimiento(Integer nacimiento) {
        this.nacimiento = nacimiento;
    }

    public Integer getFallecimiento() {
        return fallecimiento;
    }

    public void setFallecimiento(Integer fallecimiento) {
        this.fallecimiento = fallecimiento;
    }

    public List<Book> getLibros() {
        return libros;
    }

    public void setLibros(List<Book> libros) {
        this.libros = libros;
    }

    @Override
    public String toString() {
        return " " + getNombre() + " (" + getNacimiento() + "-" + getFallecimiento() + ")";
    }

}
