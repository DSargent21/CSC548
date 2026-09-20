package com.example.library;

import java.util.ArrayList;
import java.util.HashMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class LibraryController {

    private LibraryService library;

    public LibraryController(LibraryService library) {
        this.library = library;
    }

    @GetMapping("/books")
    public ArrayList<Book> getAll() {
        return library.getAllBooks();
    }

    @GetMapping("/books/available")
    public ArrayList<Book> getAvailable() {
        return library.getAvailableBooks();
    }

    @GetMapping("/books/overdue")
    public ArrayList<Book> getOverdue() {
        return library.listOverdueBooks();
    }

    @GetMapping("/books/search")
    public ArrayList<Book> search(@RequestParam(value = "q", required = false) String q,
                                  @RequestParam(value = "by", defaultValue = "title") String by) {
        if (by.equals("author")) {
            return library.searchByAuthor(q);
        }
        return library.searchByTitle(q);
    }

    @GetMapping("/patrons")
    public ArrayList<Patron> getPatrons() {
        return library.getAllPatrons();
    }

    @PostMapping("/books")
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

    @PostMapping("/patrons")
    public HashMap<String, String> addPatron(@RequestBody Patron patron) {
        HashMap<String, String> answer = new HashMap<String, String>();
        String error = library.addPatron(patron);
        if (error == null) {
            answer.put("status", "ok");
        } else {
            answer.put("status", "error");
            answer.put("message", error);
        }
        return answer;
    }

    @PostMapping("/books/{isbn}/borrow")
    public HashMap<String, String> borrow(@PathVariable String isbn,
                                          @RequestParam(value = "patronId", required = false) String patronId) {
        HashMap<String, String> answer = new HashMap<String, String>();
        String error = library.borrowBook(isbn, patronId);
        if (error == null) {
            answer.put("status", "ok");
        } else {
            answer.put("status", "error");
            answer.put("message", error);
        }
        return answer;
    }

    @PostMapping("/books/{isbn}/return")
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
