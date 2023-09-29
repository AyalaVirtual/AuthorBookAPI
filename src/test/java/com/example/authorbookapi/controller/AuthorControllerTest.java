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


    // GET /api/authors/1/



    // POST /api/authors/



    // PUT /api/authors/1/



    // DELETE /api/authors/1/



}
