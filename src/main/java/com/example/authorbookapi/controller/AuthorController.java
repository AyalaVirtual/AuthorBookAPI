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

    static HashMap<String, Object> result = new HashMap<>();
    static HashMap<String, Object> message = new HashMap<>();

    @Autowired
    public void setAuthorService(AuthorService authorService) {
        this.authorService = authorService;
    }


    /**
     * This sets the path for GET requests for all authors and checks if the list of authors is empty or not before deciding whether to send an HTTP status message of OK or NOT FOUND
     *
     * @return the HTTP status message
     */
    @GetMapping(path = "/authors/")
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


    /**
     * This sets the path for GET requests for an individual author and checks if the author exists or not before deciding whether to send an HTTP status message of OK or NOT FOUND
     *
     * @param authorId represents the id of the specific author the user is trying to get
     * @return the HTTP status message
     */
    @GetMapping(path = "/authors/{authorId}/")
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


    /**
     * This sets the path for POST requests for a new author and checks if the author exists or not before deciding whether to send an HTTP status message of CREATED or OK
     *
     * @param authorObject represents the new author the user is trying to create
     * @return the HTTP status message
     */
    @PostMapping(path = "/authors/")
    public ResponseEntity<?> createAuthor(@RequestBody Author authorObject) {

        Author newAuthor = authorService.createAuthor(authorObject);

        if (newAuthor != null) {
            message.put("message", "success");
            message.put("data", newAuthor);
            return new ResponseEntity<>(message, HttpStatus.CREATED);
        } else {
            message.put("message", "unable to create an author at this time");
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
    }


    /**
     * This sets the path for PUT requests for an existing author and checks if the author exists or not before deciding whether to send an HTTP status message of OK or NOT FOUND
     *
     * @param authorId represents the id of the author the user is trying to update
     * @param authorObject represents the updated version of the author
     * @return the HTTP status message
     */
    @PutMapping(path = "/authors/{authorId}/")
    public ResponseEntity<?> updateAuthor(@PathVariable(value = "authorId") Long authorId, @RequestBody Author authorObject) throws InformationNotFoundException {

        Optional<Author> authorToUpdate = authorService.updateAuthor(authorId, authorObject);

        if (authorToUpdate.isPresent()) {
            message.put("message", "author with id " + authorId + " has been successfully updated");
            message.put("data", authorToUpdate.get());
            return new ResponseEntity<>(message, HttpStatus.OK);
        } else {
            message.put("message", "author with id " + authorId + " not found");
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
    }


    /**
     * This sets the path for DELETE requests for an existing author and checks if the author exists or not before deciding whether to send an HTTP status message of OK or NOT FOUND
     *
     * @param authorId represents the id of the author the user is trying to delete
     * @return the HTTP status message
     */
    @DeleteMapping(path = "/authors/{authorId}/")
    public ResponseEntity<?> deleteAuthor(@PathVariable(value = "authorId") Long authorId) {

        Optional<Author> authorToDelete = authorService.deleteAuthor(authorId);

        if (authorToDelete.isPresent()) {
            message.put("message", "author with id " + authorId + " has been successfully deleted");
            message.put("data", authorToDelete.get());
            return new ResponseEntity<>(message, HttpStatus.OK);
        } else {
            message.put("message", "cannot find author with id " + authorId);
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
    }


    /**
     * This sets the path for GET requests for all books and checks if the list of books is empty or not before deciding whether to send an HTTP status message of OK or NOT FOUND
     *
     * @return the HTTP status message
     */
    @GetMapping(path = "/authors/books/")
    public ResponseEntity<?> getAllBooks() {

        List<Book> bookList = authorService.getAllBooks();

        if (bookList.isEmpty()) {
            message.put("message", "cannot find any books ");
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        } else {
            message.put("message", "success");
            message.put("data", bookList);
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
    }


    /**
     * This sets the path for GET requests for an individual book and checks if the book exists or not before deciding whether to send an HTTP status message of OK or NOT FOUND
     *
     * @param bookId represents the id of the specific book the user is trying to get
     * @return the HTTP status message
     */
    @GetMapping(path = "/authors/{authorId}/books/{bookId}/")
    public ResponseEntity<?> getBookById(@PathVariable(value = "authorId") Long authorId, @PathVariable(value = "bookId") Long bookId) {

        Optional<Book> bookOptional = authorService.getBookById(authorId, bookId);

        if (bookOptional.isPresent()) {
            message.put("message", "success");
            message.put("data", bookOptional.get());
            return new ResponseEntity<>(message, HttpStatus.OK);
        } else {
            message.put("message", "cannot find book with id " + bookId);
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
    }


    /**
     * This sets the path for POST requests for a new book and checks if the author and book exist or not before deciding whether to send an HTTP status message of CREATED or OK
     *
     * @param authorId represents the id of the specific author whose book list the user is trying to create the book in
     * @param bookObject represents the new book the user is trying to create
     * @return the HTTP status message
     */
    @PostMapping(path = "/authors/{authorId}/books/")
    public ResponseEntity<?> createBook(@PathVariable(value = "authorId") Long authorId, @RequestBody Book bookObject) {

        Optional<Author> author = authorService.getAuthorById(authorId);
        Book newBook = authorService.createBook(authorId, bookObject);

        if (author.isPresent() && newBook != null) {
            message.put("message", "success");
            message.put("data", newBook);
            return new ResponseEntity<>(message, HttpStatus.CREATED);
        } else {
            message.put("message", "unable to create a book at this time");
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
    }


    /**
     * This sets the path for PUT requests for an individual book and checks if the book exists or not before deciding whether to send an HTTP status message of OK or NOT FOUND
     *
     * @param bookId represents the id of the specific book the user is trying to update
     * @param bookObject represents the new book the user is trying to update
     * @return the HTTP status message
     */
    @PutMapping(path = "/authors/{authorId}/books/{bookId}/")
    public ResponseEntity<?> updateBook(@PathVariable(value = "bookId") Long bookId, @RequestBody Book bookObject) {

        Optional<Book> bookToUpdate = authorService.updateBook(bookId, bookObject);

        if (bookToUpdate.isPresent()) {
            message.put("message", "book with id " + bookId + " has been successfully updated");
            message.put("data", bookToUpdate.get());
            return new ResponseEntity<>(message, HttpStatus.OK);
        } else {
            message.put("message", "book with id " + bookId + " not found");
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
    }


    /**
     * This sets the path for DELETE requests for an individual book and checks if the book exists or not before deciding whether to send an HTTP status message of OK or NOT FOUND
     *
     * @param bookId represents the id of the specific book the user is trying to delete
     * @return the HTTP status message
     */
    @DeleteMapping(path = "/authors/{authorId}/books/{bookId}/")
    public ResponseEntity<?> deleteBook(@PathVariable(value = "bookId") Long bookId) {

        Optional<Book> bookToDelete = authorService.deleteBook(bookId);

        if (bookToDelete.isPresent()) {
            message.put("message", "book with id " + bookId + " has been successfully deleted");
            message.put("data", bookToDelete.get());
            return new ResponseEntity<>(message, HttpStatus.OK);
        } else {
            message.put("message", "cannot find book with id " + bookId);
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
    }

}
