package com.example.authorbookapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Optional;


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


    public Book() {
    }

    public Book(Long id, String name, String description, String isbn, Author author) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.isbn = isbn;
        this.author = author;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }


    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", isbn='" + isbn + '\'' +
                ", author=" + author +
                '}';
    }

}
