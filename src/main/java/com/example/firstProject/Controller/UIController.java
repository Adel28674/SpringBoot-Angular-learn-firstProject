package com.example.firstProject.Controller;

import com.example.firstProject.Service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequiredArgsConstructor
public class UIController {

    @Autowired
    private final BookService bookService;

    @RequestMapping("/home")
    String home(Model model) {
        model.addAttribute("message", "Bienvenue sur notre interface !");
        return "home";
    }

    @RequestMapping("/books")
    public String viewHomePage(Model model) {
        model.addAttribute("books", bookService.getAllBooks());
        return "books";
    }

}
