package com.azubike.spring_data_jpa.dao.jdbc;

import com.azubike.spring_data_jpa.dao.AuthorDao;
import com.azubike.spring_data_jpa.domain.Author;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthorJdbc implements AuthorDao {
  JdbcTemplate jdbcTemplate;

  private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

  public AuthorJdbc(
      JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
    this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
  }

  @Override
  public Author getById(Long id) {
    final String QUERY = "SELECT * FROM author WHERE id = ?";
    return jdbcTemplate.queryForObject(QUERY, this::mapToAuthor, id);
  }

  @Override
  public Author findAuthorByName(String firstName, String lastName) {
    final String QUERY = "SELECT * FROM author where first_name = ? and last_name = ?";
    return jdbcTemplate.queryForObject(QUERY, this::mapToAuthor, firstName, lastName);
  }

  @Override
  public Author saveNewAuthor(Author author) {
    final String QUERY =
        "INSERT INTO author (first_name, last_name) values (:firstName, :lastName)";
    MapSqlParameterSource mapSqlParameterSource =
        new MapSqlParameterSource()
            .addValue("firstName", author.getFirstName())
            .addValue("lastName", author.getLastName());

    namedParameterJdbcTemplate.update(QUERY, mapSqlParameterSource);

    Long createdId = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Long.class);
    return this.getById(createdId);
  }

  @Override
  public Author updateAuthor(Author author) {
    final String QUERY =
        "UPDATE author set first_name = :firstName, last_name = :lastName  where author.id = :id";
    MapSqlParameterSource mapSqlParameterSource =
        new MapSqlParameterSource()
            .addValue("firstName", author.getFirstName())
            .addValue("lastName", author.getLastName())
            .addValue("id", author.getId());
    namedParameterJdbcTemplate.update(QUERY, mapSqlParameterSource);
    return this.getById(author.getId());
  }

  @Override
  public void deleteAuthorById(Long id) {
    final String QUERY = "DELETE FROM author WHERE id = :id";
    MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource().addValue("id", id);
    namedParameterJdbcTemplate.update(QUERY, mapSqlParameterSource);
  }

  private Author mapToAuthor(ResultSet rs, int rowNum) throws SQLException {
    Author author = new Author();
    author.setLastName(rs.getString("last_name"));
    author.setFirstName(rs.getString("first_name"));
    author.setId(rs.getLong("id"));
    return author;
  }
}
