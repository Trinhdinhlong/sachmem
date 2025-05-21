package com.example.sachmem.repository;

import com.example.sachmem.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByEnableTrue();
    List<Book> findByEnableTrueAndStatus_Id(Long statusId);
    List<Book> findByEnableTrueOrderByCreatedAtDesc();

}
