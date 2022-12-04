package com.azubike.spring_data_jpa.dao.hibernate;

import com.azubike.spring_data_jpa.dao.AuthorDao;
import com.azubike.spring_data_jpa.domain.Author;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.Optional;

@Component
public class AuthorHibernateDao implements AuthorDao {
  private final EntityManagerFactory entityManagerFactory;

  public AuthorHibernateDao(EntityManagerFactory entityManagerFactory) {
    this.entityManagerFactory = entityManagerFactory;
  }

  @Override
  public Author getById(Long id) {
    return findSavedAuthor(id)
        .orElseThrow(() -> new RuntimeException("Author not found with id" + id));
  }

  @Override
  public Author findAuthorByName(String firstName, String lastName) {
    final String QUERY =
        " SELECT  a  from  Author  a  WHERE a.firstName = :firstName AND a.lastName = :lastName ";
    return getEntityManager()
        .createQuery(QUERY, Author.class)
        .setParameter("firstName", firstName)
        .setParameter("lastName", lastName)
        .getSingleResult();
  }

  @Override
  public Author saveNewAuthor(Author author) {
    final EntityManager em = getEntityManager();
    em.getTransaction().begin();
    em.persist(author);
    em.getTransaction().commit();
    return author;
  }

  @Override
  public Author updateAuthor(Author author) {
    final EntityManager em = getEntityManager();
    em.joinTransaction();
    em.merge(author);
    em.flush(); // forces database update
    em.clear(); // clear the first level cache
    return em.find(Author.class, author.getId());
  }

  @Override
  public void deleteAuthorById(Long id) {}

  private EntityManager getEntityManager() {
    return entityManagerFactory.createEntityManager();
  }

  private Optional<Author> findSavedAuthor(Long id) {
    return Optional.of(getEntityManager().find(Author.class, id));
  }
}
