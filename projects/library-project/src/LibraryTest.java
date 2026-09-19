public class LibraryTest {

    private static int passed = 0;
    private static int failed = 0;

    public static void main(String[] args) {
        testValidIsbn();
        testInvalidIsbn();
        testAddBook();
        testDuplicateIsbn();
        testBadIsbnRejected();
        testBorrowAndReturn();
        testBorrowWithPatron();
        testSearch();
        testSaveAndLoad();

        System.out.println("");
        System.out.println("Results: " + passed + " passed, " + failed + " failed");
        if (failed > 0) {
            System.exit(1);
        }
    }

    static void testValidIsbn() {
        if (Book.isValidIsbn("9780141439518") == true) {
            passed++;
        } else {
            failed++;
            System.out.println("FAIL: valid 13-digit ISBN rejected");
        }
        if (Book.isValidIsbn("0134685997") == true) {
            passed++;
        } else {
            failed++;
            System.out.println("FAIL: valid 10-digit ISBN rejected");
        }
        if (Book.isValidIsbn("013468599X") == true) {
            passed++;
        } else {
            failed++;
            System.out.println("FAIL: valid ISBN ending in X rejected");
        }
        if (Book.isValidIsbn("978-0-14-143951-8") == true) {
            passed++;
        } else {
            failed++;
            System.out.println("FAIL: ISBN with dashes rejected");
        }
    }

    static void testInvalidIsbn() {
        if (Book.isValidIsbn("123") == false) {
            passed++;
        } else {
            failed++;
            System.out.println("FAIL: short ISBN accepted");
        }
        if (Book.isValidIsbn("abcdefghij") == false) {
            passed++;
        } else {
            failed++;
            System.out.println("FAIL: letters ISBN accepted");
        }
        if (Book.isValidIsbn(null) == false) {
            passed++;
        } else {
            failed++;
            System.out.println("FAIL: null ISBN accepted");
        }
    }

    static void testAddBook() {
        Library lib = new Library();
        boolean ok = lib.addBook("Dune", "Frank Herbert", "9780441172719");
        if (ok == true && lib.getBookCount() == 1) {
            passed++;
        } else {
            failed++;
            System.out.println("FAIL: addBook did not work");
        }
    }

    static void testDuplicateIsbn() {
        Library lib = new Library();
        lib.addBook("Dune", "Frank Herbert", "9780441172719");
        boolean ok = lib.addBook("Dune 2", "Frank Herbert", "9780441172719");
        if (ok == false && lib.getBookCount() == 1) {
            passed++;
        } else {
            failed++;
            System.out.println("FAIL: duplicate ISBN accepted");
        }
    }

    static void testBadIsbnRejected() {
        Library lib = new Library();
        boolean ok = lib.addBook("Bad Book", "No One", "123");
        if (ok == false && lib.getBookCount() == 0) {
            passed++;
        } else {
            failed++;
            System.out.println("FAIL: bad ISBN accepted");
        }
    }

    static void testBorrowAndReturn() {
        Library lib = new Library();
        lib.addBook("Dune", "Frank Herbert", "9780441172719");
        lib.addPatron("P001", "Alice");

        boolean borrowed = lib.borrowBook("9780441172719", "P001");
        if (borrowed == true && lib.listAvailableBooks().size() == 0) {
            passed++;
        } else {
            failed++;
            System.out.println("FAIL: borrow did not work");
        }

        boolean returned = lib.returnBook("9780441172719");
        if (returned == true && lib.listAvailableBooks().size() == 1) {
            passed++;
        } else {
            failed++;
            System.out.println("FAIL: return did not work");
        }
    }

    static void testBorrowWithPatron() {
        Library lib = new Library();
        lib.addBook("Dune", "Frank Herbert", "9780441172719");

        boolean noPatron = lib.borrowBook("9780441172719", "P999");
        if (noPatron == false) {
            passed++;
        } else {
            failed++;
            System.out.println("FAIL: borrow without patron succeeded");
        }

        lib.addPatron("P001", "Alice");
        boolean ok = lib.borrowBook("9780441172719", "P001");
        Book b = lib.findByIsbn("9780441172719");
        if (ok == true && b.getBorrowedBy().equals("Alice") && b.getDueDate().length() == 10) {
            passed++;
        } else {
            failed++;
            System.out.println("FAIL: patron tracking broken");
        }
    }

    static void testSearch() {
        Library lib = new Library();
        lib.addBook("Dune", "Frank Herbert", "9780441172719");
        lib.addBook("Pride and Prejudice", "Jane Austen", "9780141439518");

        if (lib.searchByTitle("dune").size() == 1) {
            passed++;
        } else {
            failed++;
            System.out.println("FAIL: search by title");
        }
        if (lib.searchByAuthor("austen").size() == 1) {
            passed++;
        } else {
            failed++;
            System.out.println("FAIL: search by author");
        }
        if (lib.searchByTitle("zzz").size() == 0) {
            passed++;
        } else {
            failed++;
            System.out.println("FAIL: search returns wrong results");
        }
    }

    static void testSaveAndLoad() {
        Library lib = new Library();
        lib.addBook("Dune", "Frank Herbert", "9780441172719");
        lib.addPatron("P001", "Alice");
        lib.borrowBook("9780441172719", "P001");

        LibraryPersistence.save(lib, "/tmp/test_library_save.txt");

        Library lib2 = new Library();
        LibraryPersistence.load(lib2, "/tmp/test_library_save.txt");

        Book loaded = lib2.findByIsbn("9780441172719");
        if (loaded != null && loaded.isBorrowed() == true && loaded.getBorrowedBy().equals("Alice")) {
            passed++;
        } else {
            failed++;
            System.out.println("FAIL: save/load lost borrow state");
        }
        if (lib2.getPatronCount() == 1) {
            passed++;
        } else {
            failed++;
            System.out.println("FAIL: save/load lost patron");
        }
    }
}
