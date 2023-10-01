package com.example.authorbookapi.controller;

import com.example.authorbookapi.model.Author;
import com.example.authorbookapi.model.Book;
import com.example.authorbookapi.service.AuthorService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Optional;
import static org.mockito.Mockito.when;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.http.MediaType;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Option;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.anyLong;


@WebMvcTest(AuthorController.class)
public class AuthorControllerTest {

    // using Spring's @Autowired annotation to inject an instance of MockMvc into this class
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthorService authorService;


    @Autowired
    // This is typically used to convert Java objects to JSON and vice versa. It's commonly used in Spring applications for JSON serialization and deserialization.
    ObjectMapper objectMapper;


    Author AUTHOR_1 = new Author(1L, "First Name 1", "Last Name 1");
    Author AUTHOR_2 = new Author(2L, "First Name 2", "Last Name 2");
    Author AUTHOR_3 = new Author(3L, "First Name 3", "Last Name 3");

    Book BOOK_1 = new Book(1L, "Name 1", "Description 1", "ISBN 1", AUTHOR_1);
    Book BOOK_2 = new Book(2L, "Name 2", "Description 2", "ISBN 2", AUTHOR_2);
    Book BOOK_3 = new Book(3L, "Name 3", "Description 3", "ISBN 3", AUTHOR_3);


    /**
     * This test says that when we call authorService.getAllAuthors(), then to return all authors.
     * Use mockMvc to perform a GET request to the endpoint ("/api/authors/"), set the content type you're expecting, which is MediaType.APPLICATION_JSON. Expect the response status to be ok. Expect the jsonPath of the 'data' key of the payload to have a size of 3. Expect the jsonPath of the 'message' key of the payload to have a value of 'success'. Then print the message.
     *
     * @throws Exception if list of authors not found
     */
    @Test // GET /api/authors/
    public void getAllAuthorRecords_success() throws Exception {
        List<Author> authors = new ArrayList<>(Arrays.asList(AUTHOR_1, AUTHOR_2, AUTHOR_3));

        when(authorService.getAllAuthors()).thenReturn(authors);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/authors/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                // jsonPath helps us check that we have a data key and a message key. '$' represents the payload
                .andExpect(jsonPath("$.data", hasSize(3))) // This represents the "data" key from Postman for this particular HTTP request
                .andExpect(jsonPath("$.message").value("success"))
                .andDo(print());
    }


    /**
     * This test says that when we call authorService.getAuthorById(), then to return the author if it exists.
     * Perform a GET request to the endpoint and uri variable ("/api/authors/{id}/", "1"), then set the content type you're expecting, which is MediaType.APPLICATION_JSON. Expect the response status to be ok. Expect the jsonPath of the attributes in the payload to be equal to the value of the get method for that attribute. Expect the jsonPath of the 'message' key of the payload to have a value of 'success'. Then print the message.
     *
     * @throws Exception if author not found
     */
    @Test // GET /api/authors/1/
    public void getAuthorRecord_success() throws Exception {

        when(authorService.getAuthorById(AUTHOR_1.getId())).thenReturn(Optional.of(AUTHOR_1));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/authors/{id}/", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(AUTHOR_1.getId()))
                .andExpect(jsonPath("$.data.firstName").value(AUTHOR_1.getFirstName()))
                .andExpect(jsonPath("$.data.lastName").value(AUTHOR_1.getLastName()))
                .andExpect(jsonPath("$.message").value("success"))
                .andDo(print());
    }


    /**
     *
     * This test says that when we call authorService.createAuthor(), create a mock of any author, then return the author.
     * Create a mock request and set it equal to calling a POST request to the endpoint ("/api/authors/"), then set the content type you're expecting, which is MediaType.APPLICATION_JSON. Accept the content and  convert it from Java to JSON, then write the value of the author's record as a string.
     * Perform the mock request and expect the response status to be isCreated. Expect the jsonPath of the payload and a not null value. Expect the jsonPath of the attributes in the payload to be equal to the value of the get method for that attribute. Expect the jsonPath of the 'message' key of the payload to have a value of 'success'. Then print the message.
     *
     * @throws Exception if author already exists
     */
    @Test // POST /api/authors/
    public void createAuthorRecord_success() throws Exception {

        when(authorService.createAuthor(Mockito.any(Author.class))).thenReturn(AUTHOR_1);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/api/authors/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(AUTHOR_1));

        mockMvc.perform(mockRequest)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.data.id").value(AUTHOR_1.getId()))
                .andExpect(jsonPath("$.data.firstName").value(AUTHOR_1.getFirstName()))
                .andExpect(jsonPath("$.data.lastName").value(AUTHOR_1.getLastName()))
                .andExpect(jsonPath("$.message").value("success"))
                .andDo(print());
    }


    /**
     * This test says that when we call authorService.updateAuthor() in instances where the author is not found, to create a mock of any author and then return an empty optional.
     * Create a mock request and set it equal to calling a DELETE request to the endpoint and uri variable ("/api/authors/{id}/", 1L). Then set the content type you're expecting, which is 'MediaType.APPLICATION_JSON', and accept it.
     * Perform the mock request and expect the response status to be not found. Expect the jsonPath of the payload, and a not null value. And expect the jsonPath of the 'message' key of the payload to have a value of 'cannot find author with id 1'. Then print the message.
     *
     * @throws Exception
     */
    @Test // PUT /api/authors/1/
    public void updateAuthorRecord_recordNotFound() throws Exception {

        when(authorService.updateAuthor(anyLong(), Mockito.any(Author.class))).thenReturn(Optional.empty());

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.delete("/api/authors/{id}/", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(mockRequest)
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.message").value("cannot find author with id 1"))
                .andDo(print());
    }


    // Create a variable representing the author's id and set it equal to 1L. Create a new Author object and an updated Author object.
    // When calling authorService.updateAuthor(), create a mock of any author id using 'anyLong(), Mockito.any(Author.class)'. Then return an optional of the updated author.
    // Create a mock request using MockHttpServletRequestBuilder and set it equal to calling a PUT request to the endpoint and uri variable (which is the id, so it would be "/api/authors/{id}/", 1L) from MockMvcRequestBuilders. Set the content type you're expecting, which is 'MediaType.APPLICATION_JSON'. Accept 'MediaType.APPLICATION_JSON'. Set the content using 'this.objectMapper' (used to convert the Java object to JSON and vice versa) to write the value of the author object as a string ( .writeValueAsString(author) ).
    // Use mockMvc to perform the mock request. And expect the (response) status is ok. And expect the jsonPath of the payload, and a not null value. And expect the jsonPath of the 'data.id' key of the payload, and the value of id of the updated author object (updatedAuthor.getId()). And expect the jsonPath of each of the model's attributes (so the 'data.firstName' key of the payload, and the value of firstName of the updated author object (updatedAuthor.getFirstName()), then repeat from 'And expect the jsonPath' for the 'data.lastName' key of the payload, and the value of lastName of the updated author object (updatedAuthor.getLastName()). And expect the jsonPath of the 'message' key of the payload to have a value of 'author with id 1 has been successfully updated'. And do print (the message).
    @Test // PUT /api/authors/1/
    public void updateAuthorRecord_success() throws Exception {

        Long authorId = 1L;
        Author author = new Author(authorId, "Nicholaj", "De Mattos Frisvold");
        Author updatedAuthor = new Author(authorId, "Jose", "Leitao");

        when(authorService.updateAuthor(anyLong(), Mockito.any(Author.class))).thenReturn(Optional.of(updatedAuthor));

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/api/authors/{id}/", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(author));

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.data.id").value(updatedAuthor.getId()))
                .andExpect(jsonPath("$.data.firstName").value(updatedAuthor.getFirstName()))
                .andExpect(jsonPath("$.data.lastName").value(updatedAuthor.getLastName()))
                .andExpect(jsonPath("$.message").value("author with id 1 has been successfully updated"))
                .andDo(print());
    }


    // When calling authorService.deleteAuthor(), use the author's record to call the getID method. Then return an empty optional.
    // Create a mock request using MockHttpServletRequestBuilder and set it equal to calling a DELETE request to the endpoint and uri variable (which is the id, so it would be "/api/authors/{id}/", "1") from MockMvcRequestBuilders. Set the content type you're expecting, which is 'MediaType.APPLICATION_JSON'. Accept 'MediaType.APPLICATION_JSON'.
    // Use mockMvc to perform the mock request. And expect the (response) status is not found. And expect the jsonPath of the 'message' key of the payload to have a value of 'cannot find author with id 1'. And do print (the message).
    @Test // DELETE /api/authors/1/
    public void deleteAuthorRecord_recordNotFound() throws Exception {

        when(authorService.deleteAuthor(AUTHOR_1.getId())).thenReturn(Optional.empty());

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.delete("/api/authors/{id}/", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(mockRequest)
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("cannot find author with id 1"))
                .andDo(print());
    }


    // When calling authorService.deleteAuthor(), use the author's record to call the getID method. Then return an optional of the author's record.
    // Create a mock request using MockHttpServletRequestBuilder and set it equal to calling a DELETE request to the endpoint and uri variable (which is the id, so it would be "/api/authors/{id}/", "1") from MockMvcRequestBuilders. Set the content type you're expecting, which is 'MediaType.APPLICATION_JSON'. Accept 'MediaType.APPLICATION_JSON'.
    // Use mockMvc to perform the mock request. And expect the (response) status is ok. And expect the jsonPath of the payload, and a not null value. And expect the jsonPath of the 'data.id' key of the payload, and the value of id of the author's record (AUTHOR_1.getId()). And expect the jsonPath of each of the model's attributes (so the 'data.firstName' key of the payload, and the value of firstName of the author's record (AUTHOR_1.getFirstName()), then repeat from 'And expect the jsonPath' for the 'data.lastName' key of the payload, and the value of lastName of the author's record (AUTHOR_1.getLastName()). And expect the jsonPath of the 'message' key of the payload to have a value of 'author with id 1 has been successfully deleted'. And do print (the message).
    @Test // DELETE /api/authors/1/
    public void deleteAuthorRecord_success() throws Exception {

        when(authorService.deleteAuthor(AUTHOR_1.getId())).thenReturn(Optional.of(AUTHOR_1));

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.delete("/api/authors/{id}/", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.data.id").value(AUTHOR_1.getId()))
                .andExpect(jsonPath("$.data.firstName").value(AUTHOR_1.getFirstName()))
                .andExpect(jsonPath("$.data.lastName").value(AUTHOR_1.getLastName()))
                .andExpect(jsonPath("$.message").value("author with id 1 has been successfully deleted"))
                .andDo(print());
    }




    /**
     * This test says that when we call authorService.getAllBooks(), then to return all books.
     * Use mockMvc to perform a GET request to the endpoint ("/api/authors/books/"), set the content type you're expecting, which is MediaType.APPLICATION_JSON. Expect the response status to be ok. Expect the jsonPath of the 'data' key of the payload to have a size of 3. Expect the jsonPath of the 'message' key of the payload to have a value of 'success'. Then print the message.
     *
     * @throws Exception if list of books not found
     */
    @Test // GET /api/authors/books/
    public void getAllBookRecords_success() throws Exception {

        List<Book> books = new ArrayList<>(Arrays.asList(BOOK_1, BOOK_2, BOOK_3));

        when(authorService.getAllBooks()).thenReturn(books);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/authors/books/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(3)))
                .andExpect(jsonPath("$.message").value("success"))
                .andDo(print());
    }


    // When calling authorService.getBookById(), use the Book's record to call the getID method. Then return an optional of the book's record.
    // Use mockMvc to perform a GET request to the endpoint and uri variable (which is the id, so it would be "/api/authors/{id}/books/{id}/", "1") using MockMvcRequestBuilders. Set the content type you're expecting, which is MediaType.APPLICATION_JSON. And expect the (response) status is ok. And expect the jsonPath of the 'data.id' key of the payload, and the value of id of the book's record (BOOK_1.getId()). And expect the jsonPath of the 'data.name' key of the payload, and the value of name of the book's record (BOOK_1.getName()). And expect the jsonPath for the 'data.description' key of the payload, and the value of description of the book's record (BOOK_1.getDescription()). And expect the jsonPath of the 'data.isbn' key of the payload, and the value of isbn of the book's record (BOOK_1.getIsbn()). And expect the jsonPath of the 'data.author' key of the payload, and the value of author of the book's record (BOOK_1.getAuthor()). And expect the jsonPath of the 'message' key of the payload to have a value of 'success'. And do print (the message).
    @Test // GET /api/authors/1/books/1/
    public void getBookRecord_success() throws Exception {

        when(authorService.getBookById(AUTHOR_1.getId(), BOOK_1.getId())).thenReturn(Optional.of(BOOK_1));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/authors/{id}/books/{id}/", "1", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(BOOK_1.getId()))
                .andExpect(jsonPath("$.data.name").value(BOOK_1.getName()))
                .andExpect(jsonPath("$.data.description").value(BOOK_1.getDescription()))
                .andExpect(jsonPath("$.data.isbn").value(BOOK_1.getIsbn()))
                .andExpect(jsonPath("$.data.author").value(BOOK_1.getAuthor()))
                .andExpect(jsonPath("$.message").value("success"))
                .andDo(print());
    }


    // When calling authorService.createBook(), create a mock of any Book using 'Mockito.any(Book.class)'. Then return the book's record.
    // Create a mock request using MockHttpServletRequestBuilder and set it equal to calling a POST request to the endpoint ("/api/authors/1/books/") from MockMvcRequestBuilders. Set the content type you're expecting, which is 'MediaType.APPLICATION_JSON'. Accept 'MediaType.APPLICATION_JSON'. Set the content using 'this.objectMapper' (used to convert the Java object to JSON and vice versa) to write the value of the author's record as a string ( .writeValueAsString(BOOK_1) ).
    // Use mockMvc to perform the mock request. And expect the (response) status is created. And expect the jsonPath of the payload, and a not null value. And expect the jsonPath of the 'data.id' key of the payload, and the value of id of the book's record (BOOK_1.getId()). And expect the jsonPath of the 'data.name' key of the payload, and the value of name of the book's record (BOOK_1.getName()). And expect the jsonPath for the 'data.description' key of the payload, and the value of description of the book's record (BOOK_1.getDescription()). And expect the jsonPath of the 'data.isbn' key of the payload, and the value of isbn of the book's record (BOOK_1.getIsbn()). And expect the jsonPath of the 'data.author' key of the payload, and the value of author of the book's record (BOOK_1.getAuthor()). And expect the jsonPath of the 'message' key of the payload to have a value of 'success'. And do print (the message).
    @Test // POST /api/authors/1/books/
    public void createBookRecord_success() throws Exception {

        when(authorService.createBook(AUTHOR_1.getId(), Mockito.any(Book.class))).thenReturn(BOOK_1);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/api/authors/{id}/books/", "1", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(BOOK_1));

        mockMvc.perform(mockRequest)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.data.id").value(BOOK_1.getId()))
                .andExpect(jsonPath("$.data.name").value(BOOK_1.getName()))
                .andExpect(jsonPath("$.data.description").value(BOOK_1.getDescription()))
                .andExpect(jsonPath("$.data.isbn").value(BOOK_1.getIsbn()))
                .andExpect(jsonPath("$.data.author").value(BOOK_1.getAuthor()))
                .andExpect(jsonPath("$.message").value("success"))
                .andDo(print());
    }


    // When calling authorService.updateBook(), create a mock of any book id using 'anyLong(), Mockito.any(Book.class)'. Then return an empty optional.
    // Create a mock request using MockHttpServletRequestBuilder and set it equal to calling a DELETE request to the endpoint and uri variable (which is the id, so it would be "/api/authors/{id}/books/{id}/", 1L) from MockMvcRequestBuilders. Set the content type you're expecting, which is 'MediaType.APPLICATION_JSON'. Accept 'MediaType.APPLICATION_JSON'.
    // Use mockMvc to perform the mock request. And expect the (response) status is not found. And expect the jsonPath of the payload, and a not null value. And expect the jsonPath of the 'message' key of the payload to have a value of 'cannot find author with id 1'. And do print (the message).
    @Test // PUT /api/authors/1/books/1/
    public void updateBookRecord_recordNotFound() throws Exception {


    }


    // Create a variable representing the book's id and set it equal to 1L. Create a new Book object and an updated Book object.
    // When calling authorService.updateBook(), create a mock of any book id using 'anyLong(), Mockito.any(Book.class)'. Then return an optional of the updated book.
    // Create a mock request using MockHttpServletRequestBuilder and set it equal to calling a PUT request to the endpoint and uri variable (which is the id, so it would be "/api/authors/{id}/books/{id}/", 1L) from MockMvcRequestBuilders. Set the content type you're expecting, which is 'MediaType.APPLICATION_JSON'. Accept 'MediaType.APPLICATION_JSON'. Set the content using 'this.objectMapper' (used to convert the Java object to JSON and vice versa) to write the value of the book object as a string ( .writeValueAsString(book) ).
    // Use mockMvc to perform the mock request. And expect the (response) status is ok. And expect the jsonPath of the payload, and a not null value. And expect the jsonPath of the 'data.id' key of the payload, and the value of id of the book's record (BOOK_1.getId()). And expect the jsonPath of the 'data.name' key of the payload, and the value of name of the book's record (BOOK_1.getName()). And expect the jsonPath for the 'data.description' key of the payload, and the value of description of the book's record (BOOK_1.getDescription()). And expect the jsonPath of the 'data.isbn' key of the payload, and the value of isbn of the book's record (BOOK_1.getIsbn()). And expect the jsonPath of the 'data.author' key of the payload, and the value of author of the book's record (BOOK_1.getAuthor()). And expect the jsonPath of the 'message' key of the payload to have a value of 'book with id 1 has been successfully updated'. And do print (the message).
    @Test // PUT /api/authors/1/books/1/
    public void updateBookRecord_success() throws Exception {


    }


    // When calling authorService.deleteBook(), use the book's record to call the getID method. Then return an empty optional.
    // Create a mock request using MockHttpServletRequestBuilder and set it equal to calling a DELETE request to the endpoint and uri variable (which is the id, so it would be "/api/authors/{id}/books/{id}/", "1") from MockMvcRequestBuilders. Set the content type you're expecting, which is 'MediaType.APPLICATION_JSON'. Accept 'MediaType.APPLICATION_JSON'.
    // Use mockMvc to perform the mock request. And expect the (response) status is not found. And expect the jsonPath of the 'message' key of the payload to have a value of 'cannot find author with id 1'. And do print (the message).
    @Test // DELETE /api/authors/1/books/1/
    public void deleteBookRecord_recordNotFound() throws Exception {


    }


    // When calling authorService.deleteBook(), use the book's record to call the getID method. Then return an optional of the book's record.
    // Create a mock request using MockHttpServletRequestBuilder and set it equal to calling a DELETE request to the endpoint and uri variable (which is the id, so it would be "/api/authors/{id}/books/{id}/", "1") from MockMvcRequestBuilders. Set the content type you're expecting, which is 'MediaType.APPLICATION_JSON'. Accept 'MediaType.APPLICATION_JSON'.
    // Use mockMvc to perform the mock request. And expect the (response) status is ok. And expect the jsonPath of the payload, and a not null value. And expect the jsonPath of the 'data.id' key of the payload, and the value of id of the book's record (BOOK_1.getId()). And expect the jsonPath of the 'data.name' key of the payload, and the value of name of the book's record (BOOK_1.getName()). And expect the jsonPath for the 'data.description' key of the payload, and the value of description of the book's record (BOOK_1.getDescription()). And expect the jsonPath of the 'data.isbn' key of the payload, and the value of isbn of the book's record (BOOK_1.getIsbn()). And expect the jsonPath of the 'data.author' key of the payload, and the value of author of the book's record (BOOK_1.getAuthor()). And expect the jsonPath of the 'message' key of the payload to have a value of 'book with id 1 has been successfully deleted'. And do print (the message).
    @Test // DELETE /api/authors/1/books/1/
    public void deleteBookRecord_success() throws Exception {


    }

}
