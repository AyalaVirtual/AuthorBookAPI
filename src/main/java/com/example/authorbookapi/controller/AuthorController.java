package com.example.authorbookapi.controller;

import com.example.authorbookapi.exception.InformationExistException;
import com.example.authorbookapi.exception.InformationNotFoundException;
import com.example.authorbookapi.model.Author;
import com.example.authorbookapi.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.authorbookapi.service.AuthorService;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/") // http://localhost:9092/api
public class AuthorController {

    private AuthorService authorService;

    // These attributes are for testing
    static HashMap<String, Object> result = new HashMap<>();
    static HashMap<String, Object> message = new HashMap<>();

    @Autowired
    public void setAuthorService(AuthorService authorService) {
        this.authorService = authorService;
    }


    // GET all authors
    @GetMapping(path = "/authors/") // http://localhost:9092/api/authors/
//    public List<Author> getAllAuthors() {
//        return authorService.getAllAuthors();
//    }
    public ResponseEntity<?> getAllAuthors() {
        List<Author> authorList = authorService.getAllAuthors();

        if (authorList.isEmpty()) {
            message.put("message", "cannot find any authors ");
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        } else {
            message.put("message", "success");
            message.put("data", authorList);
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
    }


    // GET individual author by id
    @GetMapping(path = "/authors/{authorId}/") // http://localhost:9092/api/authors/1/
//    public Optional<Author> getAuthorById(@PathVariable(value = "authorId") Long authorId) {
//        return authorService.getAuthorById(authorId);
//    }
    public ResponseEntity<?> getAuthorById(@PathVariable(value = "authorId") Long authorId) {
        Optional<Author> authorOptional = authorService.getAuthorById(authorId);

        if (authorOptional.isPresent()) {
            message.put("message", "success");
            message.put("data", authorOptional.get());
            return new ResponseEntity<>(message, HttpStatus.OK);
        } else {
            message.put("message", "cannot find author with id " + authorId);
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
    }


    // POST (create) author
    @PostMapping(path = "/authors/") // http://localhost:9092/api/authors/
//    public Author createAuthor(@RequestBody Author authorObject) {
//        return authorService.createAuthor(authorObject);
//    }
    public ResponseEntity<?> createAuthor(@RequestBody Author authorObject) {
        Author newAuthor = authorService.createAuthor(authorObject);

        if (newAuthor != null) {
            message.put("message", "success");
            message.put("data", newAuthor);
            return new ResponseEntity<>(message, HttpStatus.CREATED);
        } else {
            message.put("message", "unable to create a author at this time");
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
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
    @DeleteMapping(path = "/authors/{authorId}/books/{bookId}/") // http://localhost:9092/api/authors/1/books/1/
    public Optional<Book> deleteBook(@PathVariable(value = "bookId") Long bookId) {
        return authorService.deleteBook(bookId);
    }

}
