package com.azubike.spring_data_jpa.dao.jdbc;

import com.azubike.spring_data_jpa.dao.AuthorDao;
import com.azubike.spring_data_jpa.domain.Author;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles("local")
@Import(AuthorJdbc.class)
@ComponentScan({"com.azubike.spring_data_jpa.bootstrap"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
class AuthorJdbcTest {
  @Autowired AuthorDao authorDao;
  Author savedAuthor;

  @BeforeEach
  void setUp() {
    Author author = new Author();
    author.setLastName("Richard");
    author.setFirstName("Enu");
    savedAuthor = authorDao.saveNewAuthor(author);
  }

  @Test
  void getById() {
    Author foundAuthor = authorDao.getById(savedAuthor.getId());
    assertThat(foundAuthor).isNotNull();
    System.out.println(authorDao.getClass().toString());
  }

  @Test
  void testGetAuthorByName() {
    Author author = authorDao.findAuthorByName(savedAuthor.getFirstName(), savedAuthor.getLastName());
    assertThat(author).isNotNull();
  }

  @Test
  void testSaveAuthor() {
    Author author = new Author();
    author.setFirstName("John");
    author.setLastName("Thompson");
    Author saved = authorDao.saveNewAuthor(author);
    assertThat(saved).isNotNull();
  }

  @Test
  void testUpdateAuthor() {
    Author author = new Author("Sandra", "Richards");
    var savedAuthor = authorDao.saveNewAuthor(author);
    assertThat(savedAuthor).isNotNull();
    savedAuthor.setLastName("Enu");
    final Author updatedAuthor = authorDao.updateAuthor(savedAuthor);
    assertThat(updatedAuthor.getLastName()).isEqualTo("Enu");
    assertThat(updatedAuthor).isEqualTo(savedAuthor);
  }

  @Test
  void testDeleteAuthor() {
    Author author = new Author();
    author.setFirstName("john");
    author.setLastName("t");
    Author saved = authorDao.saveNewAuthor(author);
    authorDao.deleteAuthorById(saved.getId());
    assertThrows(EmptyResultDataAccessException.class, () -> authorDao.getById(saved.getId()));
  }
}
