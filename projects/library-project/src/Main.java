import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        Library library = new Library();

        loadBooksFromCsv(library, "data/books.csv");
        library.addPatron("P001", "Alice Smith");
        library.addPatron("P002", "Bob Jones");

        System.out.println("=== All books ===");
        printBooks(library.getAllBooks());

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
        System.out.println("=== Borrowing with patron ===");
        ArrayList<Book> available = library.listAvailableBooks();
        if (available.size() > 0) {
            String isbnToBorrow = available.get(0).getIsbn();
            boolean ok = library.borrowBook(isbnToBorrow, "P001");
            if (ok == true) {
                Book b = library.findByIsbn(isbnToBorrow);
                System.out.println("Borrowed: " + b.getTitle() + " by " + b.getBorrowedBy()
                        + ", due " + b.getDueDate());
            }
        }

        System.out.println("");
        System.out.println("=== Available books after borrow ===");
        printBooks(library.listAvailableBooks());

        System.out.println("");
        System.out.println("=== Search for 'pride' ===");
        printBooks(library.searchByTitle("pride"));

        System.out.println("");
        System.out.println("=== Search by author 'bronte' ===");
        printBooks(library.searchByAuthor("bronte"));

        System.out.println("");
        System.out.println("=== Returning the book ===");
        if (available.size() > 0) {
            String isbnToReturn = available.get(0).getIsbn();
            library.returnBook(isbnToReturn);
            System.out.println("Returned: " + available.get(0).getTitle());
        }

        System.out.println("");
        System.out.println("=== Save to file ===");
        LibraryPersistence.save(library, "data/library_state.txt");

        System.out.println("");
        System.out.println("=== Load from file ===");
        Library library2 = new Library();
        LibraryPersistence.load(library2, "data/library_state.txt");
        printBooks(library2.getAllBooks());

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
            System.out.println("No books found.");
            return;
        }
        for (int i = 0; i < books.size(); i++) {
            System.out.println((i + 1) + ". " + books.get(i).toString());
        }
    }
}
