package com.azubike.spring_data_jpa.dao.hibernate;

import com.azubike.spring_data_jpa.dao.AuthorDao;
import com.azubike.spring_data_jpa.domain.Author;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles("local")
@DataJpaTest
@Import(AuthorHibernateDao.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AuthorHibernateDaoTest {
  @Autowired AuthorDao authorDao;

  @Test
  void getById() {
    final Author foundAuthor = authorDao.getById(1L);
    assertThat(foundAuthor).isNotNull();
    assertThat(1L).isEqualTo(foundAuthor.getId());
  }

  @Test
  void shouldThrowAndExceptionWhenIdNotFound() {
    assertThrows(
        RuntimeException.class,
        () -> {
          authorDao.getById(10L);
        });
  }

  @Test
  void findAuthorByName() {
    final Author authorByName = authorDao.findAuthorByName("Richard", "Enu");
    assertThat(authorByName).isNotNull();
  }

  @Test
  void saveNewAuthor() {
    Author author = new Author();
    author.setFirstName("John");
    author.setLastName("Richard");
    final Author savedAuthor = authorDao.saveNewAuthor(author);
    assertThat(savedAuthor).isNotNull();
    assertThat(savedAuthor.getId()).isNotNull();
  }

  @Test
  void updateAuthor() {
    Author author = new Author("Sandra", "Richards");
    var savedAuthor = authorDao.saveNewAuthor(author);
    assertThat(savedAuthor).isNotNull();
    savedAuthor.setLastName("Enu");
    final Author updatedAuthor = authorDao.updateAuthor(savedAuthor);
    assertThat(updatedAuthor.getLastName()).isEqualTo("Enu");
    assertThat(updatedAuthor).isEqualTo(savedAuthor);
  }

  @Test
  void deleteAuthorById() {}
}
