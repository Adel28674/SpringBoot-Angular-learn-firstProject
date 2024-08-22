package com.example.firstProject.Controller;

import com.example.firstProject.Model.BookEntity;
import com.example.firstProject.Service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RequestMapping("/api/books")
@RestController
public class BookController {

    @Autowired
    private final BookService bookService;

    @GetMapping("/")
    public String viewHomePage(Model model) {
        model.addAttribute("books", new BookEntity());
        model.addAttribute("books", bookService.getAllBooks());
        return "books";
    }

//    @GetMapping("/redirectToBooks")
//    public String redirectToBooks(Model model) {
//        return "redirect:/";
//
//    }

    @GetMapping("/book/{bookId}")
    public ResponseEntity<BookEntity> getBook(@PathVariable Long bookId){
        return bookService.getBookById(bookId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/getAllBooks")
    public List<BookEntity> getAllBooks(){
        return bookService.getAllBooks();
    }


//    @PostMapping("/addOne")
//    public ResponseEntity<BookEntity> addOneBook(@RequestBody BookEntity bookEntity){
//        return ResponseEntity.ok(bookService.addOneBook(bookEntity));
//    }


    @PostMapping("/addOne")
    public String addOneBook(@RequestBody BookEntity bookEntity, Model model){
        bookService.addOneBook(bookEntity);
        model.addAttribute("books", bookService.getAllBooks());
        return "books";
    }

    @PostMapping("/addAll")
    public ResponseEntity<List<BookEntity>> addAllBooks(@RequestBody List<BookEntity> booksEntity){
        return ResponseEntity.ok(bookService.addAllBooks(booksEntity));
    }


    @DeleteMapping("/deleteOne/{bookId}")
    public ResponseEntity deleteOne(@PathVariable Long bookId){
        bookService.deleteOneBook(bookId);
        return ResponseEntity.ok(bookId + " has been deleted successfully");
    }

    @DeleteMapping("/deleteAllByIds")
    public ResponseEntity deleteAllByIds(@RequestBody List<Long> bookIds){
        bookService.deleteAllBooks(bookIds);
        return ResponseEntity.ok(bookIds.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(", "))
                + " have been deleted successfully");

    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity deleteAll(){
        bookService.deleteAllBooks();
        return ResponseEntity.ok("All books have been deleted successfully");
    }

}
