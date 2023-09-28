package com.example.authorbookapi.repository;

import com.example.authorbookapi.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    // This method finds an author by their full name
    Author findByFirstNameAndLastName(String authorFirstName, String authorLastName);

    Author findByLastName(String authorLastName);

    Optional<Author> findById(Long authorId);

}
