package com.example.authorbookapi.controller;

import com.example.authorbookapi.exception.InformationExistException;
import com.example.authorbookapi.exception.InformationNotFoundException;
import com.example.authorbookapi.model.Author;
import com.example.authorbookapi.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.authorbookapi.service.AuthorService;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api") // http://localhost:9092/api
public class AuthorController {

    private AuthorService authorService;

    // TODO
    // These attributes are for testing
    // static HashMap<String, Object> result = new HashMap<>();
    // static HashMap<String, Object> message = new HashMap<>();


    @Autowired
    public void setAuthorService(AuthorService authorService) {
        this.authorService = authorService;
    }


    // REFACTOR ALL CRUD METHODS FOR TESTING

    // GET all authors
    @GetMapping(path = "/authors/") // http://localhost:9092/api/authors/
    public List<Author> getAllAuthors() {
        return authorService.getAllAuthors();
    }


    // GET individual author by id
    @GetMapping(path = "/authors/{authorId}/") // http://localhost:9092/api/authors/1/
    public Optional<Author> getAuthorById(@PathVariable(value = "authorId") Long authorId) {
        return authorService.getAuthorById(authorId);
    }


    // POST (create) author
    @PostMapping(path = "/authors/") // http://localhost:9092/api/authors/
    public Author createAuthor(@RequestBody Author authorObject) {
        return authorService.createAuthor(authorObject);
    }


    // PUT (update) existing author
    @PutMapping(path = "/authors/{authorId}/") // http://localhost:9092/api/authors/1/
    public Optional<Author> updateAuthor(@PathVariable(value = "authorId") Long authorId, @RequestBody Author authorObject) throws InformationNotFoundException {
        return authorService.updateAuthor(authorId, authorObject);
    }


    // DELETE existing author
    @DeleteMapping(path = "/authors/{authorId}/") // http://localhost:9092/api/authors/1/
    public Optional<Author> deleteAuthor(@PathVariable(value = "authorId") Long authorId) {
        return authorService.deleteAuthor(authorId);
    }




    // GET all books
    @GetMapping(path = "/authors/books/") // http://localhost:9092/api/authors/books/
    public List<Book> getAllBooks() {
        return authorService.getAllBooks();
    }


    // GET individual book by id
    @GetMapping(path = "/authors/{authorId}/books/{bookId}/") // http://localhost:9092/api/authors/1/books/1/
    public Optional<Book> getBookById(@PathVariable(value = "authorId") Long authorId, @PathVariable(value = "bookId") Long bookId) {
        return authorService.getBookById(authorId, bookId);
    }


    // POST (create) book
    @PostMapping(path = "/authors/{authorId}/books/") // http://localhost:9092/api/authors/1/books/
    public Book createBook(@PathVariable(value = "authorId") Long authorId, @RequestBody Book bookObject) {
        return authorService.createBook(authorId, bookObject);
    }


    // PUT (update) existing book
    @PutMapping(path = "/authors/{authorId}/books/{bookId}/") // http://localhost:9092/api/authors/1/books/1/
    public Optional<Book> updateBook(@PathVariable(value = "bookId") Long bookId, @RequestBody Book bookObject) {
        return authorService.updateBook(bookId, bookObject);
    }


    // DELETE existing book
    //@DeleteMapping(path = "/authors/{authorId}/books{bookId}/") // http://localhost:9092/api/authors/1/books/1/


}
