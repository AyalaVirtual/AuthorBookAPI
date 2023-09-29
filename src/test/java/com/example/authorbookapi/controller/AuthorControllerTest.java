package com.example.authorbookapi.controller;

import com.example.authorbookapi.model.Author;
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
import static org.mockito.Mockito.when;
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


    Author RECORD_1 = new Author(1L, "First Name 1", "Last Name 1");
    Author RECORD_2 = new Author(1L, "First Name 2", "Last Name 2");
    Author RECORD_3 = new Author(1L, "First Name 3", "Last Name 3");


    // Create an arraylist as a list of authors ( List<Author> records = new ArrayList<>(Arrays.asList(RECORD_1, RECORD_2, RECORD_3)) ). When calling authorService, get all authors. Then return authors.
    // Use mockMvc to perform a GET request to the endpoint ("/api/authors/") using MockMvcRequestBuilders. Set the content type you're expecting, which is MediaType.APPLICATION_JSON. And expect the (response) status is ok. And expect the jsonPath of the 'data' key of the payload to have a size (how many attributes the model has) of 3. And expect the jsonPath of the 'message' key of the payload to have a value of 'success'. And do print (the message).
    // GET /api/authors/
    @Test
    public void getAuthorRecords_success() throws Exception {
        List<Author> records = new ArrayList<>(Arrays.asList(RECORD_1, RECORD_2, RECORD_3));

        when(authorService.getAllAuthors()).thenReturn(records);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/authors/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                // jsonPath helps us check that we have a data key and a message key. '$' represents the payload
                .andExpect(jsonPath("$.data", hasSize(3))) // This represents the "data" key from Postman for this particular HTTP request
                .andExpect(jsonPath("$.message").value("success"))
                .andDo(print());
    }


    // When calling authorService, get author by id using the author's record to call the getID method. Then return an optional of the author's record.
    // Use mockMvc to perform a GET request to the endpoint ("/api/authors/{id}/") using MockMvcRequestBuilders. Set the content type you're expecting, which is MediaType.APPLICATION_JSON. And expect the (response) status is ok. And expect the jsonPath of the 'data.id' key of the payload, and the value of id of the author's record (RECORD_1.getId()). And expect the jsonPath of each of the model's attributes (so the 'data.firstName' key of the payload, and the value of firstName of the author's record (RECORD_1.getFirstName()), then repeat from 'And expect the jsonPath' for the 'data.lastName' key of the payload, and the value of lastName of the author's record (RECORD_1.getLastName()).
    // And expect the jsonPath of the 'message' key of the payload to have a value of 'success'. And do print (the message).
    // GET /api/authors/1/



    // POST /api/authors/



    // PUT /api/authors/1/



    // DELETE /api/authors/1/



}
