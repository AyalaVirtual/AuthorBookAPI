package com.example.authorbookapi.model;

import javax.persistence.*;


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


    // Link to Book class here


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


    // Create getter and setter for attribute representing Book list


    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }

}
