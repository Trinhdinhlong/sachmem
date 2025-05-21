package com.example.sachmem.service.impl;

import com.example.sachmem.dto.reponse.BookReponse;
import com.example.sachmem.dto.reponse.BookReponseAdmin;
import com.example.sachmem.dto.request.BookRequest;
import com.example.sachmem.exception.EntityNotFoundException;
import com.example.sachmem.model.Book;
import com.example.sachmem.repository.*;
import com.example.sachmem.service.BookService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private final SubjectRepository subjectRepository;

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private SectionRepository sectionRepository;
    @Autowired
    private AttemptRepository attemptsRepository;

    @Override
    public List<BookReponseAdmin> getBooksEnable() {
        List<Book> books = bookRepository.findByEnableTrue();

        return books.stream().map(book -> new BookReponseAdmin(
                book.getId(),
                book.getTitle(),
                book.getDescription(),
                book.getSubject().getName(),     // assuming getSubject() returns a Subject object
                book.getStatus().getName(),      // assuming getStatus() returns a Status object
                book.getImage(),
                book.getAuthor(),
                book.getPublisher(),
                book.getLanguage(),
                book.getCreatedAt().toString()   // format nếu cần
        )).collect(Collectors.toList());
    }


    @Override
    public List<BookReponse> getBooksEnableAndStatusOk(Long userId) {
        List<Book> books = bookRepository.findByEnableTrueAndStatus_Id(1L); // hoặc viết custom query
        List<BookReponse> responseList = new ArrayList<>();

        for (Book book : books) {
            BookReponse res = new BookReponse();
            res.setTitle(book.getTitle());
            res.setDescription(book.getDescription());
            res.setLevel(book.getSubject() != null ? book.getSubject().getName() : ""); // Assuming 'level' = subject name
            res.setAuthor(book.getAuthor());
            res.setPublisher(book.getPublisher());
            res.setLanguage(book.getLanguage());

            // Tính toán số liệu
            int total = totalSections(book.getId());
            int completed = completedSections(userId, book.getId());
            int progress = total == 0 ? 0 : (int) ((completed * 100.0) / total);

            res.setTotalSections(total);
            res.setCompletedSections(completed);
            res.setProgress(progress);

            responseList.add(res);
        }

        return responseList;
    }


    @Override
    public Book getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy sách với ID: " + id));
    }

    @Override
    @Transactional
    public Book updateBook(BookRequest request, Long id) {
        Book book = getBookById(id);

        book.setTitle(request.getTitle());
        book.setDescription(request.getDescription());
        book.setAuthor(request.getAuthor());
        book.setPublisher(request.getPublisher());
        book.setLanguage(request.getLanguage());
        book.setImage(request.getImage());
        book.setEnable(true);

        if (request.getSubjectId() != null) {
            book.setSubject(subjectRepository.findById(request.getSubjectId())
                    .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy ngành")));
        }

        if (request.getStatusId() != null) {
            book.setStatus(statusRepository.findById(request.getStatusId())
                    .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy trạng thái")));
        }

        return bookRepository.save(book);
    }

    @Override
    public Book addBook(BookRequest request) {
        try {
            Book book = new Book();

            book.setTitle(request.getTitle());
            book.setDescription(request.getDescription());
            book.setAuthor(request.getAuthor());
            book.setPublisher(request.getPublisher());
            book.setLanguage(request.getLanguage());
            book.setImage(request.getImage());
            book.setEnable(true); // Bạn đang dùng trường enable để lọc sách hợp lệ

            // Gán Subject nếu có
            if (request.getSubjectId() != null) {
                book.setSubject(subjectRepository.findById(request.getSubjectId())
                        .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy ngành")));
            }

            // Gán Status nếu có
            if (request.getStatusId() != null) {
                book.setStatus(statusRepository.findById(request.getStatusId())
                        .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy trạng thái")));
            }

            bookRepository.save(book);
            return bookRepository.save(book);

        } catch (Exception e) {
            e.printStackTrace(); // Hoặc log ra log file nếu production
            throw new RuntimeException("Không thêm thành công");
        }
    }


    @Override
    @Transactional
    public boolean deleteBookById(Long id) {
        Optional<Book> bookOpt = bookRepository.findById(id);
        if (bookOpt.isPresent()) {
            Book book = bookOpt.get();
            book.setEnable(false);  // set trường enable thành false
            bookRepository.save(book); // lưu lại thay đổi
            return true;
        }
        return false;
    }

//    @Override
//    public int progress(Long userId, Long bookId) {
//        // Đếm hoạt động đã hoàn thành
//        int completedLectures = attemptsRepository.countCompletedLectures(userId, bookId);
//        int completedLectureExercises = attemptsRepository.countCompletedLectureExercises(userId, bookId);
//        int completedExercises = attemptsRepository.countCompletedExercises(userId, bookId);
//        int completed = completedLectures + completedLectureExercises + completedExercises;
//
//        // Tổng số hoạt động có trong sách
//        int totalLectures = sectionRepository.countLecturesByBookId(bookId);
//        int totalLectureExercises = sectionRepository.countLectureExercisesByBookId(bookId);
//        int totalExercises = sectionRepository.countExercisesByBookId(bookId);
//        int total = totalLectures + totalLectureExercises + totalExercises;
//
//        if (total == 0) return 0;
//        return (int) ((completed * 100.0) / total);
//    }

    @Override
    public int totalSections(Long bookId) {
        return sectionRepository.countTotalSectionsWithStatusActiveByBookId(bookId);
    }

    @Override
    public int completedSections(Long userId, Long bookId) {
        List<Long> sectionIds = sectionRepository.findAllActiveSectionIdsByBookId(bookId);
        int completedCount = 0;

        for (Long sectionId : sectionIds) {
            int completedLectures = attemptsRepository.countLecturesCompletedInSection(userId, sectionId);
            int completedExercises = attemptsRepository.countExercisesCompletedInSection(userId, sectionId);

            // Kiểm tra xem user đã hoàn thành ít nhất 1 trong mỗi loại
            if (completedLectures > 0 && completedExercises > 0) {
                completedCount++;
            }
        }
        return completedCount;
    }
}
