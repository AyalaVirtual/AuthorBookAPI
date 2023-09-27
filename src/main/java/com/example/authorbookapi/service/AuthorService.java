package com.example.authorbookapi.service;

import com.example.authorbookapi.model.Author;
import com.example.authorbookapi.repository.AuthorRepository;
import com.example.authorbookapi.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;


@Service
public class AuthorService {

    private AuthorRepository authorRepository;
    private BookRepository bookRepository;


    @Autowired // This enables us to use the methods from JpaRepository
    public void setAuthorRepository(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Autowired
    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }


    // GET all authors
    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }


    // GET individual author by id (use optional - check if present -> throw not found exception)
    public Optional<Author> getAuthorById(Long authorId) {
        Optional<Author> authorOptional = authorRepository.findById(authorId);

        if (authorOptional.isPresent()) {
            return authorOptional;
        } else {
            throw new InformationNotFoundException("author with id " + authorId + " not found");
        }
    }


    // POST (create) author (check if exists [!= null] / throw exception



    // PUT (update) existing author (use optional - check if present -> throw not found exception)



    // DELETE existing author





    // GET all books (?? use optional - check if present -> throw not found exception ??)



    // GET individual book by id (use optional - check if present -> throw not found exception)



    // POST (create) book (check if exists [!= null] / throw exception



    // PUT (update) existing book (use optional - check if present -> throw not found exception)



    // DELETE existing book


}
