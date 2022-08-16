package com.azubike.spring_data_jpa.dao.jdbc;

import com.azubike.spring_data_jpa.dao.AuthorDao;
import com.azubike.spring_data_jpa.domain.Author;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles("local")
@Import(AuthorJdbc.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
class AuthorJdbcTest {
  @Autowired AuthorDao authorDao;

  @Test
  void getById() {
    Author author = authorDao.getById(1L);
    assertThat(author).isNotNull();
  }

  @Test
  void testGetAuthorByName() {
    Author author = authorDao.findAuthorByName("Amaka", "Enu");
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
