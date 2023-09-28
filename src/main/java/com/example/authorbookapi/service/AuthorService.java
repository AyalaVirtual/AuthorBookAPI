package com.example.authorbookapi.service;

import com.example.authorbookapi.exception.InformationExistException;
import com.example.authorbookapi.exception.InformationNotFoundException;
import com.example.authorbookapi.model.Author;
import com.example.authorbookapi.model.Book;
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


    // GET all authors (return find all from repository)
    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }


    // GET individual author by id (use optional - check repository by id if present -> return optional / throw not found exception)
    public Optional<Author> getAuthorById(Long authorId) {
        Optional<Author> authorOptional = authorRepository.findById(authorId);

        if (authorOptional.isPresent()) {
            return authorOptional;
        } else {
            throw new InformationNotFoundException("author with id " + authorId + " not found");
        }
    }


    // POST (create) author (check repository by name if exists [!= null] -> throw exists exception / return save to repository)
    public Author createAuthor(Author authorObject) {
        Author author = authorRepository.findByFullName(authorObject.getFullName());

        if (author != null) {
            throw new InformationExistException("author with name " + authorObject.getFullName() + " already exists");
        } else {
            return authorRepository.save(authorObject);
        }
    }


    // PUT (update) existing author (use optional - check repository by id if present -> set attributes, save to repository, return optional / throw not found exception)
    public Optional<Author> updateAuthor(Long authorId, Author authorObject) {
        Optional<Author> authorOptional = authorRepository.findById(authorId);

        if (authorOptional.isPresent()) {

            authorOptional.get().setFirstName(authorObject.getFirstName());
            authorOptional.get().setLastName(authorObject.getLastName());
            authorRepository.save(authorOptional.get());
            return authorOptional;

        } else {

            throw new InformationNotFoundException("author with id " + authorId + " not found");
        }
    }


    // DELETE existing author (use optional - check repository by id if present -> delete from repository by id and return optional / throw not found exception)
    public Optional<Author> deleteAuthor(Long authorId) {
        Optional<Author> authorOptional = authorRepository.findById(authorId);

        if (authorOptional.isPresent()) {
            authorRepository.deleteById(authorId);
            return authorOptional;
        } else {
            throw new InformationNotFoundException("author with id " + authorId + " not found");
        }
    }




    // GET all books (?? use optional ?? - check repository if list is empty -> throw not found exception / return find all from repository ??)
    public Optional<List<Book>> getAllBooks() {

        // Create optional of author?? Search author's (repository ?? Create custom findAllBooks method in repository returns author's bookList ) bookList for this book by id. If

    }


    // GET individual book by id (use optional - check repository by id if present -> return optional / throw not found exception)
    public Optional<Book> getBookById() {

        // Create optional of author?? Search author's (repository ?? Create custom findBookById method in repository returns author's bookList ??) bookList for this book by id. If

    }


    // POST (create) book (check repository by name if exists [!= null] -> throw exists exception / return save to repository)
    public Book createBook() {

        // Books must be added/saved to author's bookList (in repository ?? ---> " if (optional.isPresent()) { list.add(optional.get()) } ").

    }


    // PUT (update) existing book (use optional - check repository by id if present -> set attributes, save to repository, return optional / throw not found exception)



    // DELETE existing book (use optional - check repository by id if present -> delete from repository by id and return optional / throw not found exception)


}
