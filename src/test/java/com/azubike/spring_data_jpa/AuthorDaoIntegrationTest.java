package com.azubike.spring_data_jpa;

import com.azubike.spring_data_jpa.dao.AuthorDao;
import com.azubike.spring_data_jpa.dao.AuthorDaoImpl;
import com.azubike.spring_data_jpa.domain.Author;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("local")
@DataJpaTest
@Import(AuthorDaoImpl.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AuthorDaoIntegrationTest {

  @Autowired AuthorDao authorDao;

  @Test
  void testSaveAuthor() {
    Author author = new Author();
    author.setFirstName("John");
    author.setLastName("Thompson");
    Author saved = authorDao.saveNewAuthor(author);
    assertThat(saved).isNotNull();
  }

  @Test
  void testGetAuthorByName() {
    Author author = authorDao.findAuthorByName("Amaka", "Enu");
    assertThat(author).isNotNull();
  }

  @Test
  void testGetAuthor() {
    Author author = authorDao.getById(1L);
    assertThat(author).isNotNull();
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
    Author deleted = authorDao.getById(saved.getId());
    assertThat(deleted).isNull();
  }
}
