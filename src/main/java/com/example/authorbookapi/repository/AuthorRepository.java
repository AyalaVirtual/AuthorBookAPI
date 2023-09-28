package com.example.authorbookapi.repository;

import com.example.authorbookapi.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    // This method finds an author by their full name
    Author findByFullName(String authorFullName);

    // This method finds an author by their id
    Author findByAuthorId(Long authorId);

}
