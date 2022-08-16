package com.azubike.spring_data_jpa.repositories;

import com.azubike.spring_data_jpa.domain.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface AuthorRepository extends JpaRepository<Author, Long> {
  @Transactional
  @Modifying
  @Query(value = "TRUNCATE TABLE author", nativeQuery = true)
  void truncateTable();
}
