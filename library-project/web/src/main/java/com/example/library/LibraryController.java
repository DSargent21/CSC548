package com.example.library;

import java.util.ArrayList;
import java.util.HashMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/books")
public class LibraryController {

    private LibraryService library;

    public LibraryController(LibraryService library) {
        this.library = library;
    }

    @GetMapping
    public ArrayList<Book> getAll() {
        return library.getAllBooks();
    }

    @GetMapping("/available")
    public ArrayList<Book> getAvailable() {
        return library.getAvailableBooks();
    }

    @PostMapping
    public HashMap<String, String> add(@RequestBody Book book) {
        HashMap<String, String> answer = new HashMap<String, String>();
        String error = library.addBook(book);
        if (error == null) {
            answer.put("status", "ok");
        } else {
            answer.put("status", "error");
            answer.put("message", error);
        }
        return answer;
    }

    @PostMapping("/{isbn}/borrow")
    public HashMap<String, String> borrow(@PathVariable String isbn) {
        HashMap<String, String> answer = new HashMap<String, String>();
        String error = library.borrowBook(isbn);
        if (error == null) {
            answer.put("status", "ok");
        } else {
            answer.put("status", "error");
            answer.put("message", error);
        }
        return answer;
    }

    @PostMapping("/{isbn}/return")
    public HashMap<String, String> giveBack(@PathVariable String isbn) {
        HashMap<String, String> answer = new HashMap<String, String>();
        String error = library.returnBook(isbn);
        if (error == null) {
            answer.put("status", "ok");
        } else {
            answer.put("status", "error");
            answer.put("message", error);
        }
        return answer;
    }
}
