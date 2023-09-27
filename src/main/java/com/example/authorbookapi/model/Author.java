package com.example.authorbookapi.model;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;


@Entity // This is a marker that defines that a class can be mapped to a table
@Table(name = "authors") // This makes it a table
public class Author {

    @Id // Primary key
    @Column // This marks it as a column in the table
    @GeneratedValue(strategy = GenerationType.IDENTITY) // This means to generate the value for the ID as the next available integer
    private Long id;

    @Column
    private String firstName;

    @Column
    private String lastName;


    // This links the table representing the Author model to the table representing the Book model
    @OneToMany(mappedBy = "author", orphanRemoval = true) // This means it's a one-to-many relationship that is mappedBy the variable representing the link to the other table. orphanRemoval = true means that if we delete the author, delete the book as well
    @LazyCollection(LazyCollectionOption.FALSE) // This means when you fetch an instance of an author, fetch the associated books
    private List<Book> bookList;


    public Author() {
    }

    public Author(Long id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    public List<Book> getBookList() {
        return bookList;
    }

    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
    }


    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }

}
