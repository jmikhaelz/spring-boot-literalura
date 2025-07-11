package mx.aluras.literatura.gutendex.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import mx.aluras.literatura.gutendex.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByTituloContainsIgnoreCase(String tituloBook);
}
