package com.example.authorbookapi.repository;

import com.example.authorbookapi.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    Author findByFullName(String authorFullName);

}
