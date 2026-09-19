import java.util.ArrayList;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Library {

    private ArrayList<Book> books;
    private ArrayList<Patron> patrons;
    private static final int LOAN_DAYS = 14;

    public Library() {
        books = new ArrayList<Book>();
        patrons = new ArrayList<Patron>();
    }

    public boolean addBook(String title, String author, String isbn) {
        if (Book.isValidIsbn(isbn) == false) {
            System.out.println("Cannot add book. Bad ISBN: " + isbn);
            return false;
        }

        Book existing = findByIsbn(isbn);
        if (existing != null) {
            System.out.println("Cannot add book. ISBN already exists: " + isbn);
            return false;
        }

        try {
            Book newBook = new Book(title, author, isbn);
            books.add(newBook);
            return true;
        } catch (IllegalArgumentException e) {
            System.out.println("Cannot add book. " + e.getMessage());
            return false;
        }
    }

    public boolean addPatron(String id, String name) {
        try {
            Patron p = new Patron(id, name);
            for (int i = 0; i < patrons.size(); i++) {
                if (patrons.get(i).getId().equals(id)) {
                    System.out.println("Patron ID already exists: " + id);
                    return false;
                }
            }
            patrons.add(p);
            return true;
        } catch (IllegalArgumentException e) {
            System.out.println("Cannot add patron. " + e.getMessage());
            return false;
        }
    }

    public ArrayList<Book> listAvailableBooks() {
        ArrayList<Book> result = new ArrayList<Book>();
        for (int i = 0; i < books.size(); i++) {
            Book b = books.get(i);
            if (b.isAvailable() == true) {
                result.add(b);
            }
        }
        return result;
    }

    public boolean borrowBook(String isbn, String patronId) {
        Book book = findByIsbn(isbn);
        if (book == null) {
            System.out.println("Book not found: " + isbn);
            return false;
        }
        if (book.isBorrowed() == true) {
            System.out.println("Book already borrowed: " + book.getTitle());
            return false;
        }

        Patron patron = findPatronById(patronId);
        if (patron == null) {
            System.out.println("Patron not found: " + patronId);
            return false;
        }

        book.setBorrowed(true);
        book.setBorrowedBy(patron.getName());

        LocalDate due = LocalDate.now().plusDays(LOAN_DAYS);
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        book.setDueDate(due.format(fmt));
        return true;
    }

    public boolean returnBook(String isbn) {
        Book book = findByIsbn(isbn);
        if (book == null) {
            System.out.println("Book not found: " + isbn);
            return false;
        }
        if (book.isBorrowed() == false) {
            System.out.println("Book was not borrowed: " + book.getTitle());
            return false;
        }
        book.setBorrowed(false);
        return true;
    }

    public ArrayList<Book> searchByTitle(String query) {
        ArrayList<Book> result = new ArrayList<Book>();
        if (query == null) {
            return result;
        }
        String lower = query.toLowerCase();
        for (int i = 0; i < books.size(); i++) {
            Book b = books.get(i);
            if (b.getTitle().toLowerCase().contains(lower)) {
                result.add(b);
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
            Book b = books.get(i);
            if (b.getAuthor().toLowerCase().contains(lower)) {
                result.add(b);
            }
        }
        return result;
    }

    public ArrayList<Book> listOverdueBooks(String today) {
        ArrayList<Book> result = new ArrayList<Book>();
        for (int i = 0; i < books.size(); i++) {
            Book b = books.get(i);
            if (b.isOverdue(today) == true) {
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
            Book b = books.get(i);
            String have = b.getIsbn().replace("-", "").replace(" ", "");
            if (have.equals(wanted)) {
                return b;
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

    public ArrayList<Book> getAllBooks() {
        return books;
    }

    public ArrayList<Patron> getAllPatrons() {
        return patrons;
    }

    public int getBookCount() {
        return books.size();
    }

    public int getPatronCount() {
        return patrons.size();
    }
}
