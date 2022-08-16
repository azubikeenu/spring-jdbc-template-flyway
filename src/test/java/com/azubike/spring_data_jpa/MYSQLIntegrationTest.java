package com.azubike.spring_data_jpa;

import com.azubike.spring_data_jpa.repositories.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("local")
@DataJpaTest
@ComponentScan({"com.azubike.spring_data_jpa.bootstrap"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // This specifies that the spring-context should not replace the default datasource
public class MYSQLIntegrationTest {
  @Autowired BookRepository repository;

  @Test
  void testTotalCountOfBookEntries() {
    var count = repository.count();
    assertEquals(2, count);
  }
}
