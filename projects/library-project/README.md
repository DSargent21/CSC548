# Library Management System

School project for CSC548. Basic system to manage books in a library.

## What it does

- Add new books with title, author, and ISBN
- List all available books
- Borrow a book by ISBN
- Return a book by ISBN
- Checks that ISBN looks valid (10 or 13 digits, dashes ok)

## Files

- `src/Book.java` - book class with ISBN check
- `src/Library.java` - list of books, borrow and return
- `src/Main.java` - demo program
- `data/books.csv` - starter books (title,author,isbn format)

## How to run

You need Java 17.

```
javac -d out src/*.java
java -cp out Main
```

The demo loads `data/books.csv`, adds one book, borrows one, returns it, and prints the lists.

## Dataset

Starter data in `data/books.csv` follows the same format as Kaggle book datasets (title, author, isbn). Just a few classic books to test with.
