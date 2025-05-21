package com.example.sachmem.controller;

import com.example.sachmem.dto.reponse.BookReponse;
import com.example.sachmem.dto.reponse.BookReponseAdmin;
import com.example.sachmem.dto.request.BookRequest;
import com.example.sachmem.model.Book;
import com.example.sachmem.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/book")
@CrossOrigin("*")
@RequiredArgsConstructor
public class BookController {

    @Autowired
    private BookService bookService;

    // Lấy danh sách sách đang bật (enable = true)
    @GetMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<BookReponseAdmin>> getBooks() {
        List<BookReponseAdmin> books = bookService.getBooksEnable();
        return ResponseEntity.ok(books);
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<BookReponse>> getBooksByUser(@PathVariable Long userId) {
        List<BookReponse> books = bookService.getBooksEnableAndStatusOk(userId);
        return ResponseEntity.ok(books);
    }
    // Lấy thông tin chi tiết sách theo ID
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBook(@PathVariable Long id) {
        Book book = bookService.getBookById(id);
        return ResponseEntity.ok(book);
    }

    // Thêm mới sách
    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addBook(@RequestBody BookRequest bookRequest) {
        return ResponseEntity.ok(bookService.addBook(bookRequest));
    }

    // Cập nhật sách
    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateBook(@PathVariable Long id, @RequestBody BookRequest bookRequest) {
        Book updated = bookService.updateBook(bookRequest, id);
        return ResponseEntity.ok(updated);
    }

    // Xoá sách (thực chất là disable)
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteBook(@PathVariable Long id) {
        boolean deleted = bookService.deleteBookById(id);
        if (deleted) {
            return ResponseEntity.ok("Xoá sách thành công!");
        } else {
            return ResponseEntity.badRequest().body("Không tìm thấy sách để xoá.");
        }
    }
}
