package com.azubike.spring_data_jpa.repositories;

import com.azubike.spring_data_jpa.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {

}
