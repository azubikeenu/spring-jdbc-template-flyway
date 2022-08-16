package com.azubike.spring_data_jpa.dao;

import com.azubike.spring_data_jpa.domain.Author;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthorMapper implements RowMapper<Author> {
  @Override
  public Author mapRow(ResultSet rs, int rowNum) throws SQLException {
    Author author = new Author();
    author.setLastName(rs.getString("last_name"));
    author.setFirstName(rs.getString("first_name"));
    author.setId(rs.getLong("id"));
    return author;
  }
}
