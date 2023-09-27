package com.example.authorbookapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.authorbookapi.service.AuthorService;


@RestController
@RequestMapping("/api") // http://localhost:9092/api
public class AuthorController {

    private AuthorService authorService;


    @Autowired
    public void setAuthorService(AuthorService authorService) {
        this.authorService = authorService;
    }


    // GET all authors
    @GetMapping(path = "/authors/") // http://localhost:9092/api/authors/


    // GET individual author by id
    @GetMapping(path = "/authors/{authorId}/") // http://localhost:9092/api/authors/1/


    // POST (create) author
    @PostMapping(path = "/authors/") // http://localhost:9092/api/authors/


    // PUT (update) existing author
    @PutMapping(path = "/authors/{authorId}/") // http://localhost:9092/api/authors/1/


    // DELETE existing author
    @DeleteMapping(path = "/authors/{authorId}/") // http://localhost:9092/api/authors/1/





    // GET all books
    @GetMapping(path = "/authors/books/") // http://localhost:9092/api/authors/books/


    // GET individual book by id
    @GetMapping(path = "/authors/{authorId}/books/{bookId}/") // http://localhost:9092/api/authors/1/books/1/


    // POST (create) book
    @PostMapping(path = "/authors/{authorId}/books/") // http://localhost:9092/api/authors/1/books/


    // PUT (update) existing book
    @PutMapping(path = "/authors/{authorId}/books/{bookId}/") // http://localhost:9092/api/authors/1/books/1/


    // DELETE existing book
    @DeleteMapping(path = "/authors/{authorId}/books{bookId}/") // http://localhost:9092/api/authors/1/books/1/


}
