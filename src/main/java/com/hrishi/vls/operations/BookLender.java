package com.hrishi.vls.operations;

import com.hrishi.vls.models.Book;
import com.hrishi.vls.models.Library;
import com.hrishi.vls.models.TransactionLog;
import com.hrishi.vls.search.BookSearcher;
import com.hrishi.vls.search.SearchStatus;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class BookLender {
    private static Library library = new Library();
    private static Scanner sc = new Scanner(System.in);
    private BookSearcher bookSearcher;

    public BookLender() {
        // Initialize the bookSearcher object
        this.bookSearcher = new BookSearcher();
    }



    public void borrowByISBN(List<Book> books, List<TransactionLog> log) {
        System.out.println("Enter the ISBN of the book to borrow: ");
        String isbn = sc.next();
        Book book = bookSearcher.searchByISBN(isbn, books).stream().findFirst().orElse(null);

        if (book == null) {
            System.out.println("Book with the given ISBN not found");
            return; // fail fast if the book is not found
        }

        System.out.println("The book you have selected is: " + book.getTitle());
        new SearchStatus().showStatus(book);
        System.out.println("Do you want to borrow this book? (Y/N)");
        String choice = sc.next();

        if (!choice.equalsIgnoreCase("y")) {
            System.out.println("Borrowing cancelled. Thank you.");
            return; // fail fast if borrowing is cancelled
        }

        if (book.getStatus().equalsIgnoreCase("out of stock")) {
            System.out.println("********** ALERT **********");
            System.out.println("Sorry, but the book is out of stock");
            System.out.println("***************************");
            provideNavigationOptions();
            return; // fail fast if book is out of stock
        }

        System.out.println("Enter your UserId to borrow the book: ");
        int userId = sc.nextInt();

        OverdueNotification(userId, book, log); // notify if overdue

        log.add(new TransactionLog(userId, isbn, "yes", LocalDate.now(), "No", null));

        System.out.println("The book has been borrowed");

        book.setNoOfCopies(book.getNoOfCopies() - 1);
        if (book.getNoOfCopies() < 1) {
            book.setStatus("Out of stock");
        } else {
            book.setStatus(book.getNoOfCopies() + " copies available");
        }
    }


    public void OverdueNotification(int UserId, Book book, List<TransactionLog> log) {
        for (TransactionLog lg : log) {
            if (lg.getUserId() == UserId && lg.getReturned().equalsIgnoreCase("No") && lg.getBorrowDate().isBefore(LocalDate.now().minusMonths(3))) {
                System.out.println("Before borrowing new book we kindly notify that you have previously borrowed the book " + book.getTitle() + "\n"
                        + "It already have been overdue so please return the book otherwise the library will charge you. ");
            } else if (lg.getUserId() == UserId && lg.getReturned().equalsIgnoreCase("No")) {
                System.out.println("You previously have borrowed " + book.getTitle() + " book it is going to overdue on " + lg.getBorrowDate().plusMonths(3) + " please return it before date");

            }
        }
    }


    private void provideNavigationOptions() {
        int option;
        do {
            System.out.println("Please select an option:");
            System.out.println("1 - Return to the main menu.");
            System.out.println("2 - Perform another search.");
            System.out.println("3 - Exit.");
            option = sc.nextInt();

            switch (option) {
                case 1:
                    // logic to return to main menu
                    break;
                case 2:
                    BookSearcher bookSearcher = new BookSearcher(library.books);
                    bookSearcher.search();
                    break;
                case 3:
                    System.out.println("Exiting system. Thank you for using our library!");
                    System.exit(0);
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        } while (option < 1 || option > 3);
    }


}
