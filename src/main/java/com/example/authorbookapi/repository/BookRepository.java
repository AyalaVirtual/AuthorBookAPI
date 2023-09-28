package com.example.authorbookapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.authorbookapi.model.Book;
import org.springframework.stereotype.Repository;


@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    // This method finds a book by its name
    Book findByName(String bookName);

}
