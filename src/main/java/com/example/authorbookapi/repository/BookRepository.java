package com.example.authorbookapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.authorbookapi.model.Book;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    // This method finds a book by its name
    Book findByName(String bookName);

    // This method finds a book by its id and its author's id
    Optional<Book> findById(Long bookId);

}
