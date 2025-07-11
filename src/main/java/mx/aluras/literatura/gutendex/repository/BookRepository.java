package mx.aluras.literatura.gutendex.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import mx.aluras.literatura.gutendex.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
    @Query(value = "SELECT * FROM libros WHERE titulo ILIKE %:titulo% LIMIT 1", nativeQuery = true)
    Optional<Book> buscarPorTitulo(@Param("titulo") String titulo);

    List<Book> findByIdioma(String idioma);
}
