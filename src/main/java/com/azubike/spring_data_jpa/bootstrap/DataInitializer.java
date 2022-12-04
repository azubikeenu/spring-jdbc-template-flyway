package com.azubike.spring_data_jpa.bootstrap;

import com.azubike.spring_data_jpa.domain.Author;
import com.azubike.spring_data_jpa.domain.Book;
import com.azubike.spring_data_jpa.repositories.AuthorRepository;
import com.azubike.spring_data_jpa.repositories.BookRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile({"local", "default"})
public class DataInitializer implements CommandLineRunner {
  private final BookRepository bookRepository;
  private final AuthorRepository authorRepository;

  public DataInitializer(BookRepository bookRepository, AuthorRepository authorRepository) {
    this.bookRepository = bookRepository;
    this.authorRepository = authorRepository;
  }

  @Override
  public void run(String... args) throws Exception {
    performCleanUp();
    Book DDD = new Book("Domain driven design", "123", "Waldorf Publishers", null);
    bookRepository.save(DDD);
    final Book spring_in_action = new Book("Spring in action", "1278", "O'Reilly", null);
    bookRepository.save(spring_in_action);
    // Create Authors
    authorRepository.save(new Author("Richard", "Enu"));
    authorRepository.save(new Author("Amaka", "Enu"));

    System.out.println("----------Books-------------");
    bookRepository
        .findAll()
        .forEach(
            book -> {
              System.out.printf("Title:%s - id : %d \n", book.getTitle(), book.getId());
            });

    System.out.println("----------Authors-------------");
    authorRepository
        .findAll()
        .forEach(
            author -> {
              System.out.printf(
                  "First_name :%s - Last_name : %s \n",
                  author.getFirstName(), author.getLastName());
            });
  }

  private void performCleanUp() {
    bookRepository.deleteAll();
    authorRepository.deleteAll();
    authorRepository.truncateTable();
    bookRepository.truncateTable();
  }
}
