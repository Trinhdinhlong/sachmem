package com.example.sachmem.service;

import com.example.sachmem.dto.reponse.BookDetailReponse;
import com.example.sachmem.dto.reponse.BookReponse;
import com.example.sachmem.dto.reponse.BookReponseAdmin;
import com.example.sachmem.dto.request.BookRequest;
import com.example.sachmem.model.Book;

import java.util.List;

public interface BookService {
    List<BookReponseAdmin> getBooksEnable();
    List<BookReponse> getBooksEnableAndStatusOk(Long userId);
    Book getBookById(Long id);
    Book updateBook(BookRequest request, Long id);
    Book addBook(BookRequest request);
    boolean deleteBookById(Long id);
    int totalSections(Long bookId);
    int completedSections(Long userId, Long bookId);
    BookDetailReponse getBookDetail(Long id);
}
