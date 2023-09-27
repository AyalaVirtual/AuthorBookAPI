package com.example.authorbookapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;


@Entity
@Table(name = "books")
public class Book {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private String isbn;


    // This links the table representing the Book model to the table representing the Author model
    @JsonIgnore // This prevents a stack overflow/API crashing from authors and books calling each other back and forth
    @ManyToOne
    @JoinColumn(name = "author_id") // This represents the foreign key in SQL joining the columns to connect the 2 tables
    private Author author;








}
