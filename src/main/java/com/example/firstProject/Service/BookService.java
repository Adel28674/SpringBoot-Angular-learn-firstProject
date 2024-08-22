package com.example.firstProject.Service;

import com.example.firstProject.Interface.BookRepository;
import com.example.firstProject.Model.BookEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {

    @Autowired
    private final BookRepository bookRepository;
    public Optional<BookEntity> getBookById(Long bookId){
        return bookRepository.findById(bookId);
    }

    public List<BookEntity> getAllBooks(){
        return bookRepository.findAll();
    }

    public BookEntity addOneBook(BookEntity bookEntity){
        return bookRepository.save(bookEntity);
    }

    public List<BookEntity> addAllBooks(List<BookEntity> books){
        return bookRepository.saveAll(books);
    }

    public void deleteOneBook(Long bookId){
        bookRepository.deleteById(bookId);
    }

    public void deleteAllBooks(List<Long> books){
        bookRepository.deleteAllById(books);
    }

    public void deleteAllBooks(){
        bookRepository.deleteAll();
    }
}
