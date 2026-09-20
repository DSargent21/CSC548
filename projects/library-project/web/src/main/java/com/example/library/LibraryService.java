package com.example.library;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import org.springframework.stereotype.Service;

@Service
public class LibraryService {

    private ArrayList<Book> books = new ArrayList<Book>();
    private ArrayList<Patron> patrons = new ArrayList<Patron>();
    private static final int LOAN_DAYS = 14;

    public LibraryService() {
        books.add(new Book("Pride and Prejudice", "Jane Austen", "9780141439518"));
        books.add(new Book("Moby Dick", "Herman Melville", "9780142437247"));
        books.add(new Book("Frankenstein", "Mary Shelley", "9780486282114"));
        patrons.add(new Patron("P001", "Alice Smith"));
        patrons.add(new Patron("P002", "Bob Jones"));
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

    public ArrayList<Patron> getAllPatrons() {
        return patrons;
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

    public String addPatron(Patron patron) {
        if (patron.getId() == null || patron.getId().trim().equals("")) {
            return "Patron ID cannot be empty.";
        }
        if (patron.getName() == null || patron.getName().trim().equals("")) {
            return "Patron name cannot be empty.";
        }
        for (int i = 0; i < patrons.size(); i++) {
            if (patrons.get(i).getId().equals(patron.getId())) {
                return "Patron ID already exists: " + patron.getId();
            }
        }
        patron.setId(patron.getId().trim());
        patron.setName(patron.getName().trim());
        patrons.add(patron);
        return null;
    }

    public String borrowBook(String isbn, String patronId) {
        Book book = findByIsbn(isbn);
        if (book == null) {
            return "Book not found: " + isbn;
        }
        if (book.isBorrowed() == true) {
            return "Book already borrowed: " + book.getTitle();
        }
        Patron patron = findPatronById(patronId);
        if (patron == null) {
            return "Patron not found: " + patronId;
        }
        book.setBorrowed(true);
        book.setBorrowedBy(patron.getName());
        LocalDate due = LocalDate.now().plusDays(LOAN_DAYS);
        book.setDueDate(due.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
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

    public ArrayList<Book> searchByTitle(String query) {
        ArrayList<Book> result = new ArrayList<Book>();
        if (query == null) {
            return result;
        }
        String lower = query.toLowerCase();
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getTitle().toLowerCase().contains(lower)) {
                result.add(books.get(i));
            }
        }
        return result;
    }

    public ArrayList<Book> searchByAuthor(String query) {
        ArrayList<Book> result = new ArrayList<Book>();
        if (query == null) {
            return result;
        }
        String lower = query.toLowerCase();
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getAuthor().toLowerCase().contains(lower)) {
                result.add(books.get(i));
            }
        }
        return result;
    }

    public ArrayList<Book> listOverdueBooks() {
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        ArrayList<Book> result = new ArrayList<Book>();
        for (int i = 0; i < books.size(); i++) {
            Book b = books.get(i);
            if (b.isBorrowed() == true && !b.getDueDate().equals("")
                    && b.getDueDate().compareTo(today) < 0) {
                result.add(b);
            }
        }
        return result;
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

    public Patron findPatronById(String id) {
        if (id == null) {
            return null;
        }
        for (int i = 0; i < patrons.size(); i++) {
            if (patrons.get(i).getId().equals(id)) {
                return patrons.get(i);
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
