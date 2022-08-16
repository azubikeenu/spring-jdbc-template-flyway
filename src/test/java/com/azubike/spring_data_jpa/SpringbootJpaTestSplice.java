package com.azubike.spring_data_jpa;

import com.azubike.spring_data_jpa.domain.Book;
import com.azubike.spring_data_jpa.repositories.BookRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.Commit;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ComponentScan({"com.azubike.spring_data_jpa.bootstrap"})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class SpringbootJpaTestSplice {
  @Autowired BookRepository bookRepository;

  @Test
  @Order(0)
  @Commit
  void testSplice() {
    Long count = bookRepository.count();
    assertThat(count).isEqualTo(2);
    bookRepository.save(new Book("Dummy book", "1233", "James Darwin", null));
    Long countAfter = bookRepository.count();
    assertThat(count).isLessThan(countAfter);
  }

  @Order(1)
  @Test
  void testSpliceTransaction() {
    Long count = bookRepository.count();
    assertThat(count).isEqualTo(3);
  }
}
