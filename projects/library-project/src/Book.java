import java.util.ArrayList;

public class Book {

    private String title;
    private String author;
    private String isbn;
    private boolean isBorrowed;

    public Book(String title, String author, String isbn) {
        if (title == null || title.trim().equals("")) {
            throw new IllegalArgumentException("Title cannot be empty.");
        }
        if (author == null || author.trim().equals("")) {
            throw new IllegalArgumentException("Author cannot be empty.");
        }
        if (isValidIsbn(isbn) == false) {
            throw new IllegalArgumentException("ISBN is not valid: " + isbn);
        }

        this.title = title.trim();
        this.author = author.trim();
        this.isbn = isbn.trim();
        this.isBorrowed = false;
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

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getIsbn() {
        return isbn;
    }

    public boolean isAvailable() {
        if (isBorrowed == true) {
            return false;
        } else {
            return true;
        }
    }

    public boolean isBorrowed() {
        return isBorrowed;
    }

    public void setBorrowed(boolean borrowed) {
        this.isBorrowed = borrowed;
    }

    public String toString() {
        String status = "";
        if (isBorrowed == true) {
            status = "Borrowed";
        } else {
            status = "Available";
        }
        return title + " by " + author + " (ISBN: " + isbn + ") - " + status;
    }
}
