package com.example.authorbookapi.seed;

import com.example.authorbookapi.model.Author;
import com.example.authorbookapi.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.example.authorbookapi.repository.AuthorRepository;
import com.example.authorbookapi.repository.BookRepository;


@Component
public class SeedData implements CommandLineRunner {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;


    @Autowired
    public SeedData(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }


    @Override
    public void run(String... args) throws Exception {

        // In production, we would not have this file!! This is just for testing.

        Author author = new Author();
        author.setFirstName("George R.R.");
        author.setLastName("Martin");
        authorRepository.save(author);


        Book book1 = new Book();
        book1.setAuthor(author);
        book1.setName("Fire & Blood");
        book1.setDescription("Long before the events in A Game of Thrones, House Targaryen, the sole surviving dragonlord lineage post-Valyria's destruction, made Dragonstone their home. Fire and Blood commences with Aegon the Conqueror, the Iron Throne's founder, and proceeds to chronicle generations of Targaryen struggles for dominance, culminating in a perilous civil conflict.");
        book1.setIsbn("8675309");
        bookRepository.save(book1);

    }
}
