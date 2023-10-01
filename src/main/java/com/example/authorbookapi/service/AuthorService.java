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
     * @param authorId represents the id of the specific author the user is trying to get
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
     * @param authorObject represents the new author the user is trying to create
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
     * This is a PUT request that checks to see if an author exists before either throwing an InformationNotFoundException, or setting the attributes and saving the newly updated author to the repository
     *
     * @param authorId represents the id of the author the user is trying to update
     * @param authorObject represents the updated version of the author
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
     * @param authorId represents the id of the author the user is trying to delete
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




    /**
     * This is a GET request that returns a list of all books
     *
     * @return all books
     */
    // GET /api/authors/books/
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }


    /**
     * This is a GET request that checks to see if an individual book exists and is in the author's book list before either returning it, or throwing an InformationNotFoundException
     *
     * @param authorId represents the id of the specific author whose book list the user is trying to get a book from
     * @param bookId represents the id of the specific book the user is trying to get
     * @return book by id if it exists
     */
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


    /**
     * This is a POST request that checks to see if the author whose book list the user is trying to create a book in already exists before either throwing an InformationNotFoundException, or moving on to check if the book already exists. From there, it either saves the newly created book to the repository, or throws an InformationExistException
     *
     * @param authorId represents the id of a specific author whose book list the user is trying to create a book in
     * @param bookObject represents the book the user is trying to create
     * @return the newly created book
     */
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


    /**
     * This is a PUT request that checks to see if a book exists before either throwing an InformationNotFoundException, or setting its attributes and saving the newly updated book to the repository
     *
     * @param bookId represents id of the book the user is trying to update
     * @param bookObject represents the updated version of the book
     * @return the newly updated book
     */
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


    /**
     * This is a DELETE request that checks to see if an individual book exists before either deleting it, or throwing an InformationNotFoundException
     *
     * @param bookId represents the id of the specific book the user is trying to delete
     * @return the deleted book
     */
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
