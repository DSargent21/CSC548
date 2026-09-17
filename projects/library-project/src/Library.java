import java.util.ArrayList;

public class Library {

    private ArrayList<Book> books;

    public Library() {
        books = new ArrayList<Book>();
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

    public boolean borrowBook(String isbn) {
        Book book = findByIsbn(isbn);
        if (book == null) {
            System.out.println("Book not found: " + isbn);
            return false;
        }
        if (book.isBorrowed() == true) {
            System.out.println("Book already borrowed: " + book.getTitle());
            return false;
        }
        book.setBorrowed(true);
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

    public ArrayList<Book> getAllBooks() {
        return books;
    }

    public int getBookCount() {
        return books.size();
    }
}
