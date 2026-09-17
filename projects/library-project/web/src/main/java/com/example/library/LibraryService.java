package com.example.library;

import java.util.ArrayList;
import org.springframework.stereotype.Service;

@Service
public class LibraryService {

    private ArrayList<Book> books = new ArrayList<Book>();

    public LibraryService() {
        books.add(new Book("Pride and Prejudice", "Jane Austen", "9780141439518"));
        books.add(new Book("Moby Dick", "Herman Melville", "9780142437247"));
        books.add(new Book("Frankenstein", "Mary Shelley", "9780486282114"));
    }

    public ArrayList<Book> getAllBooks() {
        return books;
    }

    public ArrayList<Book> getAvailableBooks() {
        ArrayList<Book> result = new ArrayList<Book>();
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).isAvailable() == true) {
                result.add(books.get(i));
            }
        }
        return result;
    }

    public String addBook(Book book) {
        if (book.getTitle() == null || book.getTitle().trim().equals("")) {
            return "Title cannot be empty.";
        }
        if (book.getAuthor() == null || book.getAuthor().trim().equals("")) {
            return "Author cannot be empty.";
        }
        if (isValidIsbn(book.getIsbn()) == false) {
            return "Bad ISBN: " + book.getIsbn();
        }
        if (findByIsbn(book.getIsbn()) != null) {
            return "ISBN already exists: " + book.getIsbn();
        }
        book.setTitle(book.getTitle().trim());
        book.setAuthor(book.getAuthor().trim());
        book.setIsbn(book.getIsbn().trim());
        book.setBorrowed(false);
        books.add(book);
        return null;
    }

    public String borrowBook(String isbn) {
        Book book = findByIsbn(isbn);
        if (book == null) {
            return "Book not found: " + isbn;
        }
        if (book.isBorrowed() == true) {
            return "Book already borrowed: " + book.getTitle();
        }
        book.setBorrowed(true);
        return null;
    }

    public String returnBook(String isbn) {
        Book book = findByIsbn(isbn);
        if (book == null) {
            return "Book not found: " + isbn;
        }
        if (book.isBorrowed() == false) {
            return "Book was not borrowed: " + book.getTitle();
        }
        book.setBorrowed(false);
        return null;
    }

    public Book findByIsbn(String isbn) {
        if (isbn == null) {
            return null;
        }
        String wanted = isbn.replace("-", "").replace(" ", "");
        for (int i = 0; i < books.size(); i++) {
            String have = books.get(i).getIsbn().replace("-", "").replace(" ", "");
            if (have.equals(wanted)) {
                return books.get(i);
            }
        }
        return null;
    }

    public static boolean isValidIsbn(String isbn) {
        if (isbn == null) {
            return false;
        }
        String cleaned = isbn.replace("-", "").replace(" ", "");
        if (cleaned.length() == 10) {
            for (int i = 0; i < 9; i++) {
                if (Character.isDigit(cleaned.charAt(i)) == false) {
                    return false;
                }
            }
            char last = cleaned.charAt(9);
            if (Character.isDigit(last) == false && last != 'X' && last != 'x') {
                return false;
            }
            return true;
        } else if (cleaned.length() == 13) {
            for (int i = 0; i < 13; i++) {
                if (Character.isDigit(cleaned.charAt(i)) == false) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }
}
