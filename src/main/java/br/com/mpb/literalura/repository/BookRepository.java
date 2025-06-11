package br.com.mpb.literalura.repository;

import br.com.mpb.literalura.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findByTitle(String title);

    @Query(value = "SELECT * FROM book WHERE :languages = ANY(languages)", nativeQuery = true)
    List<Book> buscarPorIdioma(String languages);

    List<Book> findTop10ByOrderByDownloadCountDesc();
}

