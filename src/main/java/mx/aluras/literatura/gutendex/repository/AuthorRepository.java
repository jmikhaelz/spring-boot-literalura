package mx.aluras.literatura.gutendex.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import mx.aluras.literatura.gutendex.model.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    @Query(value = "SELECT * FROM autores WHERE nombre ILIKE %:name%", nativeQuery = true)
    List<Author> buscarPorAutor(@Param("name") String nombre);

    @Query(value = "SELECT * FROM autores WHERE fallecimiento = %:fecha% or nacimiento = %:fecha%", nativeQuery = true)
    List<Author> buscarPorYear(@Param("fecha") int year);
}
