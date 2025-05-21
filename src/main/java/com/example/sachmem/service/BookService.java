package com.example.sachmem.service;

import com.example.sachmem.dto.reponse.BookReponse;
import com.example.sachmem.dto.reponse.BookReponseAdmin;
import com.example.sachmem.dto.request.BookRequest;
import com.example.sachmem.model.Book;

import java.util.List;

public interface BookService {
    public List<BookReponseAdmin> getBooksEnable();
    public List<BookReponse> getBooksEnableAndStatusOk(Long userId);
    public Book getBookById(Long id);
    public Book updateBook(BookRequest request, Long id);
    public Book addBook(BookRequest request);
    public boolean deleteBookById(Long id);
//    public int progress(Long userId, Long bookId);
    public int totalSections(Long bookId);
    public int completedSections(Long userId, Long bookId);
}
