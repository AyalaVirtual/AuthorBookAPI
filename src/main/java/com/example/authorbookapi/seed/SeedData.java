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

        Author author1 = new Author();
        author1.setFirstName("George R.R.");
        author1.setLastName("Martin");
        authorRepository.save(author1);

        Book book1A = new Book();
        book1A.setName("A Dance with Dragons");
        book1A.setDescription("The Seven Kingdoms face new threats and conflicts. Diverse characters will encounter challenges, some succeeding, others growing in darkness. In a time of rising tension, destiny and politics will lead to the grandest dance.");
        book1A.setIsbn("8142011");
        book1A.setAuthor(author1);
        bookRepository.save(book1A);

        Book book1B = new Book();
        book1B.setName("Fire & Blood");
        book1B.setDescription("House Targaryen, the sole surviving dragonlord lineage post-Valyria's destruction, struggles for dominance, culminating in a perilous civil conflict.");
        book1B.setIsbn("8675309");
        book1B.setAuthor(author1);
        bookRepository.save(book1B);


        Author author2 = new Author();
        author2.setFirstName("Sistah");
        author2.setLastName("Souljah");
        authorRepository.save(author2);

        Book book2A = new Book();
        book2A.setName("Coldest Winter Ever");
        book2A.setDescription("Winter is the daughter of a notorious drug kingpin. However, when an unexpected twist of fate forces Winter down an undesirable path, this young woman from the ghetto must use her street smarts and seductive prowess to fight for her position.");
        book2A.setIsbn("8102005");
        book2A.setAuthor(author2);
        bookRepository.save(book2A);

        Book book2B = new Book();
        book2B.setName("Midnight");
        book2B.setDescription("Midnight, a lieutenant to a prominent drug kingpin in Brooklyn, faces a world of challenges. After his father's empire is attacked, he uses his cultural insights to protect loved ones, rebuild his life, and stay true to his beliefs.");
        book2B.setIsbn("7192007");
        book2B.setAuthor(author2);
        bookRepository.save(book2B);


        Author author3 = new Author();
        author3.setFirstName("Chris");
        author3.setLastName("Van Allsburg");
        authorRepository.save(author3);

        Book book3A = new Book();
        book3A.setName("The Polar Express");
        book3A.setDescription("On Christmas Eve, a young boy lies awake waiting for Santa. When he is welcomed aboard a magical train called The Polar Express, he makes his way to the North Pole to tell Santa his Christmas wish.");
        book3A.setIsbn("1224124");
        book3A.setAuthor(author3);
        bookRepository.save(book3A);

        Book book3B = new Book();
        book3B.setName("Jumanji");
        book3B.setDescription("The board game under the tree caught Peters and Judy's attention. Bored and restless, they decided to try Jumanji, unaware that this ordinary-looking board game would lead them into an extraordinary and thrilling adventure.");
        book3B.setIsbn("7021977");
        book3B.setAuthor(author3);
        bookRepository.save(book3B);

    }
}
