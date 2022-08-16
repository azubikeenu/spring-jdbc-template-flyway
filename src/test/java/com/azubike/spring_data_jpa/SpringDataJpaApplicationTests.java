package com.azubike.spring_data_jpa;

import com.azubike.spring_data_jpa.repositories.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SpringDataJpaApplicationTests {
  @Autowired BookRepository bookRepository;

  @Test
  void testBookRepository() {
    Long count = bookRepository.count();
    assertThat(count).isGreaterThan(0);
  }

  @Test
  void contextLoads() {}
}
