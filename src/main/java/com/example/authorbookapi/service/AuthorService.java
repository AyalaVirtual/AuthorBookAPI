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


    /**
     * This is a GET request that checks to see if the list of authors is empty before either throwing an InformationNotFoundException, or  returning the list of authors
     *
     * @return a list of all authors
     */
    // GET /api/authors/
    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }


    /**
     * This is a GET request that checks to see if an individual author exists before either returning it, or throwing an InformationNotFoundException
     *
     * @param authorId represents the id of a specific author
     * @return author by id if it exists
     */
    // GET /api/authors/{authorId}/
    public Optional<Author> getAuthorById(Long authorId) {
        Optional<Author> authorOptional = authorRepository.findById(authorId);

        if (authorOptional.isPresent()) {
            return authorOptional;
        } else {
            throw new InformationNotFoundException("author with id " + authorId + " not found");
        }
    }


    /**
     * This is a POST request that checks to see if an author already exists before either throwing an InformationExistException, or saving the newly created author to the repository
     *
     * @param authorObject represents the author the user is trying to create
     * @return newly created author
     */
    // POST /api/authors/
    public Author createAuthor(Author authorObject) {
        Author author = authorRepository.findByFirstNameAndLastName(authorObject.getFirstName(), authorObject.getLastName());

        if (author != null) {
            throw new InformationExistException("author with name " + authorObject.getFullName() + " already exists");
        } else {
            return authorRepository.save(authorObject);
        }
    }


    /**
     * This is a PUT request that checks to see if an author exists before either throwing an InformationNotFoundException, or saving the newly updated author to the repository
     *
     * @param authorId represents the author the user is trying to update
     * @param authorObject represents the author the user is trying to update
     * @return the newly updated author
     */
    // PUT /api/authors/{authorId}/
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


    /**
     * This is a DELETE request that checks to see if an individual author exists before either deleting it, or throwing an InformationNotFoundException
     *
     * @param authorId represents the id of a specific author
     * @return the deleted author
     */
    // DELETE /api/authors/{authorId}/
    public Optional<Author> deleteAuthor(Long authorId) {
        Optional<Author> authorOptional = authorRepository.findById(authorId);

        if (authorOptional.isPresent()) {
            authorRepository.deleteById(authorId);
            return authorOptional;
        } else {
            throw new InformationNotFoundException("author with id " + authorId + " not found");
        }
    }




    // GET /api/authors/books/
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }


    // GET /api/authors/{authorId}/books/{bookId}/
    public Optional<Book> getBookById(Long authorId, Long bookId) {
        Optional<Book> bookOptional = bookRepository.findById(bookId);

        Optional<Author> author = authorRepository.findById(authorId);

         if (bookOptional.isPresent() && author.get().getBookList().contains(bookOptional.get())) {
            return bookOptional;
         } else {
            throw new InformationNotFoundException("book with id " + bookId + " not found");
         }
    }


    // POST /api/authors/{authorId}/books/
    public Book createBook(Long authorId, Book bookObject) {
        // find if author exists by id
        Optional<Author> author = authorRepository.findById(authorId);
        // if author does not exist, throw error
        if (author.isEmpty()) {
            throw new InformationNotFoundException("author with id " + authorId + " not found");
        }
        // find if book name exists
        Book book = bookRepository.findByName(bookObject.getName());
        // if it already exists, throw error
        if (book != null) {
            throw new InformationExistException("book with name " + bookObject.getName() + " already exists");
        }
        // or else, save book (set author and save to repository)
        bookObject.setAuthor(author.get());
        List<Book> bookList = author.get().getBookList();
        author.get().addToBookList(bookObject);
        return bookRepository.save(bookObject);
    }


    // PUT /api/authors/{authorId}/books/{bookId}/
    public Optional<Book> updateBook(Long bookId, Book bookObject) {
        Optional<Book> bookOptional = bookRepository.findById(bookId);

        if (bookOptional.isPresent()) {

            bookOptional.get().setName(bookObject.getName());
            bookOptional.get().setDescription(bookObject.getDescription());
            bookOptional.get().setIsbn(bookObject.getIsbn());
            bookOptional.get().setAuthor(bookObject.getAuthor());
            bookRepository.save(bookOptional.get());
            return bookOptional;

        } else {

            throw new InformationNotFoundException("book with id " + bookId + " not found");
        }
    }


    // DELETE /api/authors/{authorId}/books/{bookId}/
    public Optional<Book> deleteBook(Long bookId) {
        Optional<Book> bookOptional = bookRepository.findById(bookId);

        if (bookOptional.isPresent()) {
            bookRepository.deleteById(bookId);
            return bookOptional;
        } else {
            throw new InformationNotFoundException("book with id " + bookId + " not found");
        }
    }

}
