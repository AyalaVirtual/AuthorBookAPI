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
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.http.MediaType;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Option;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.mockito.ArgumentMatchers.anyLong;


@WebMvcTest(AuthorController.class)
public class AuthorControllerTest {

    // using Spring's @Autowired annotation to inject an instance of MockMvc into this class
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthorService authorService;


    @Autowired
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
    @Test
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
    @Test
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
    @Test
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
     * Perform the mock request and expect the response status to be not found. Expect the jsonPath of the payload and a not null value. And expect the jsonPath of the 'message' key of the payload to have a value of 'cannot find author with id 1'. Then print the message.
     *
     * @throws Exception if author not found
     */
    @Test
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


    /**
     * This test says that when we call authorService.updateAuthor() in successful instances where the author is found, to create a mock of any author, then return the updated author if it exists.
     * Create a mock request and set it equal to calling a PUT request to the endpoint and uri variable ("/api/authors/{id}/", 1L). Then set the content type you're expecting, which is 'MediaType.APPLICATION_JSON'. Accept the content and convert it from Java to JSON, then write the value of the author object as a string.
     * Perform the mock request and expect the response status to be ok. Expect the jsonPath of the payload and a not null value. Expect the jsonPath of the attributes in the payload to be equal to the value of the get method for that attribute. And expect the jsonPath of the 'message' key of the payload to have a value of 'author with id 1 has been successfully updated'. Then print the message.
     *
     * @throws Exception if author not found
     */
    @Test
    public void updateAuthorRecord_success() throws Exception {

        Long authorId = 1L;
        Author author = new Author(authorId, "Original First Name", "Original Last Name");
        Author updatedAuthor = new Author(authorId, "Updated First Name", "Updated Last Name");

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


    /**
     * This test says that when we call authorService.deleteAuthor() in instances where the author is not found, then return an empty optional.
     * Create a mock request and set it equal to calling a DELETE request to the endpoint and uri variable ("/api/authors/{id}/", "1"). Set the content type you're expecting, which is 'MediaType.APPLICATION_JSON', and accept it.
     * Perform the mock request and expect the response status to be not found. And expect the jsonPath of the 'message' key of the payload to have a value of 'cannot find author with id 1'. Then print the message.
     *
     * @throws Exception if author not found
     */
    @Test
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


    /**
     * This test says that when we call authorService.deleteAuthor() in successful instances where the author is found, then to return the author if it exists.
     * Create a mock request and set it equal to calling a DELETE request to the endpoint and uri variable ("/api/authors/{id}/", "1"). Set the content type you're expecting, which is 'MediaType.APPLICATION_JSON', and accept it.
     * Perform the mock request and expect the response status to be ok. Expect the jsonPath of the payload and a not null value. Expect the jsonPath of the attributes in the payload to be equal to the value of the get method for that attribute. And expect the jsonPath of the 'message' key of the payload to have a value of 'author with id 1 has been successfully deleted'. Then print the message.
     *
     * @throws Exception if author not found
     */
    @Test
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
    @Test
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


    @Test
    public void getBookRecord_success() throws Exception {
        // Mock the behavior of authorService to return the sample book
        when(authorService.getBookById(anyLong(), anyLong())).thenReturn(Optional.of(BOOK_1));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/authors/{authorId}/books/{bookId}/", 2, 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

}
