import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public class LibraryPersistence {

    public static void save(Library library, String filePath) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));

            ArrayList<Patron> patrons = library.getAllPatrons();
            for (int i = 0; i < patrons.size(); i++) {
                Patron p = patrons.get(i);
                writer.write("PATRON," + p.getId() + "," + p.getName());
                writer.newLine();
            }

            ArrayList<Book> books = library.getAllBooks();
            for (int i = 0; i < books.size(); i++) {
                Book b = books.get(i);
                String line = "BOOK," + b.getTitle() + "," + b.getAuthor() + "," + b.getIsbn()
                        + "," + b.isBorrowed() + "," + b.getBorrowedBy() + "," + b.getDueDate();
                writer.write(line);
                writer.newLine();
            }

            writer.close();
            System.out.println("Saved " + library.getBookCount() + " books and "
                    + library.getPatronCount() + " patrons to " + filePath);
        } catch (Exception e) {
            System.out.println("Could not save to file: " + filePath);
            System.out.println("Error was: " + e.getMessage());
        }
    }

    public static void load(Library library, String filePath) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;
            int bookCount = 0;
            int patronCount = 0;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", -1);
                if (parts.length == 0) {
                    continue;
                }

                if (parts[0].equals("PATRON") && parts.length >= 3) {
                    boolean ok = library.addPatron(parts[1], parts[2]);
                    if (ok == true) {
                        patronCount = patronCount + 1;
                    }
                } else if (parts[0].equals("BOOK") && parts.length >= 7) {
                    String title = parts[1];
                    String author = parts[2];
                    String isbn = parts[3];
                    boolean borrowed = Boolean.parseBoolean(parts[4]);
                    String borrowedBy = parts[5];
                    String dueDate = parts[6];

                    boolean ok = library.addBook(title, author, isbn);
                    if (ok == true) {
                        bookCount = bookCount + 1;
                        if (borrowed == true) {
                            Book b = library.findByIsbn(isbn);
                            if (b != null) {
                                b.setBorrowed(true);
                                b.setBorrowedBy(borrowedBy);
                                b.setDueDate(dueDate);
                            }
                        }
                    }
                }
            }

            reader.close();
            System.out.println("Loaded " + bookCount + " books and " + patronCount
                    + " patrons from " + filePath);
        } catch (Exception e) {
            System.out.println("Could not load from file: " + filePath);
            System.out.println("Error was: " + e.getMessage());
        }
    }
}
