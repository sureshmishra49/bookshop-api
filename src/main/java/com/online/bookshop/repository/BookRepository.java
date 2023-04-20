package com.online.bookshop.repository;

import com.online.bookshop.repository.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByIdAndIsbn(Long id, String isbn);
}
