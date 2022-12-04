package com.azubike.spring_data_jpa.repositories;

import com.azubike.spring_data_jpa.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface BookRepository extends JpaRepository<Book, Long> {
  @Transactional
  @Modifying
  @Query(value = "TRUNCATE TABLE book", nativeQuery = true)
  void truncateTable();
}
