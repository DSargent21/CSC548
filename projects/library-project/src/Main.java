import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        Library library = new Library();

        loadBooksFromCsv(library, "data/books.csv");

        System.out.println("=== Available books ===");
        printBooks(library.listAvailableBooks());

        System.out.println("");
        System.out.println("=== Adding a new book ===");
        boolean added = library.addBook("Dune", "Frank Herbert", "9780441172719");
        if (added == true) {
            System.out.println("Book added.");
        }

        System.out.println("");
        System.out.println("=== Adding a book with bad ISBN ===");
        library.addBook("Bad Book", "No One", "123");

        System.out.println("");
        System.out.println("=== Borrowing first book ===");
        ArrayList<Book> available = library.listAvailableBooks();
        if (available.size() > 0) {
            String isbnToBorrow = available.get(0).getIsbn();
            library.borrowBook(isbnToBorrow);
            System.out.println("Borrowed: " + available.get(0).getTitle());
        }

        System.out.println("");
        System.out.println("=== Available books after borrow ===");
        printBooks(library.listAvailableBooks());

        System.out.println("");
        System.out.println("=== Returning the book ===");
        if (available.size() > 0) {
            String isbnToReturn = available.get(0).getIsbn();
            library.returnBook(isbnToReturn);
            System.out.println("Returned: " + available.get(0).getTitle());
        }

        System.out.println("");
        System.out.println("=== Final available books ===");
        printBooks(library.listAvailableBooks());
    }

    public static void loadBooksFromCsv(Library library, String filePath) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line = reader.readLine();
            int count = 0;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", 3);
                if (parts.length < 3) {
                    continue;
                }
                String title = parts[0].trim();
                String author = parts[1].trim();
                String isbn = parts[2].trim();
                boolean ok = library.addBook(title, author, isbn);
                if (ok == true) {
                    count = count + 1;
                }
            }
            reader.close();
            System.out.println("Loaded " + count + " books from " + filePath);
            System.out.println("");
        } catch (Exception e) {
            System.out.println("Could not load CSV file: " + filePath);
            System.out.println("Error was: " + e.getMessage());
        }
    }

    public static void printBooks(ArrayList<Book> books) {
        if (books.size() == 0) {
            System.out.println("No books available.");
            return;
        }
        for (int i = 0; i < books.size(); i++) {
            System.out.println((i + 1) + ". " + books.get(i).toString());
        }
    }
}
