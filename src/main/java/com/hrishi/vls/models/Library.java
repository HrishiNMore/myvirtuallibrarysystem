package com.hrishi.vls.models;


import com.hrishi.vls.ScannerUtils;
import com.hrishi.vls.analyzers.AuthorTrendAnalyzer;
import com.hrishi.vls.analyzers.BorrowingTrendAnalyzer;
import com.hrishi.vls.analyzers.GenreTrendAnalyzer;
import com.hrishi.vls.analyzers.MostBorrowedBooks;
import com.hrishi.vls.isbn.ISBNChecker;
import com.hrishi.vls.operations.*;
import com.hrishi.vls.search.BookSearcher;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import java.util.Scanner;


public class Library {


    public List<Book> books = new ArrayList<>();
    private ISBNChecker check = new ISBNChecker();
    public List<TransactionLog> log = new ArrayList<>();

    private AuthorTrendAnalyzer authorTrendAnalyzer;
    private BorrowingTrendAnalyzer borrowingTrendAnalyzer;
    private GenreTrendAnalyzer genreTrendAnalyzer;
    private MostBorrowedBooks mostBorrowedBooks;
    // BookLender bookLender;
    private BookLender bookLender;


    private BookReturner bookReturner;
    private BookStatisticsCalculator bookStatisticsCalculator;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    Scanner sc = new Scanner(System.in);


    public Library() {
        this.books = new ArrayList<>();
        this.check = new ISBNChecker();
        this.bookLender = new BookLender();
        this.bookReturner = new BookReturner();
        this.authorTrendAnalyzer = new AuthorTrendAnalyzer();
        this.borrowingTrendAnalyzer = new BorrowingTrendAnalyzer();
        this.genreTrendAnalyzer = new GenreTrendAnalyzer();
        this.mostBorrowedBooks = new MostBorrowedBooks();
        this.bookStatisticsCalculator = new BookStatisticsCalculator();
    }


    // add book
    public void add(Book book) {
        boolean isbnUnique = check.isISBNUnique(book.getISBN(), books);
        if (isbnUnique) {
            books.add(book);
        }
    }

    //-------------------------------------------------------------------------------------------------


    //-----------------------------------------------------------------------------------------------

    public void viewLog() {
        for (TransactionLog lg : log) {
            System.out.println("UserID : " + lg.getUserId() + "," + "ISBN : " + lg.getISBN() + "," + "Borrowed : " + lg.getBorrowed() + "," + "Borrow Date : " + lg.getBorrowDate()
                    + "," + "Returned : " + lg.getReturned() + "," + "Return Date : " + lg.getReturnDate());
        }
    }

    //----------------------------------------------------------------------------------------------------


    public void bookInventory() {
        for (Book bk : books) {
            System.out.println("Title : " + bk.getTitle() + " , " + "No of copies : " + bk.getNoOfCopies());
        }
    }

    public void searchBook() {
        BookSearcher bookSearcher = new BookSearcher(books);
        bookSearcher.search();
    }

    public void borrowBook() {
        bookLender.borrowByISBN(books, log);
    }


    public void returnBook() {
        bookReturner.ReturnBook(books, log);
    }

    public void UploadBook(String path) {
        BookUploader upload = new BookUploader();
        upload.uploadBook(path, books);
    }

    public void showStatistics() {
        while (true) {
            printMenu1();
            int statsChoice = ScannerUtils.getIntInput("Choose an option:");

            switch (statsChoice) {
                case 1:
                    bookStatisticsCalculator.displayLibraryStatistics(books, log);
                    break;
                case 2:
                    System.out.println("Total number of books present: " + bookStatisticsCalculator.getTotalBooks(books));
                    break;
                case 3:
                    System.out.println("Number of currently borrowed books: " + bookStatisticsCalculator.calculateCurrentlyBorrowedBooksCount(log));
                    break;
                case 4:
                    System.out.println("List of titles of all borrowed books:");
                    List<String> borrowedTitles = bookStatisticsCalculator.getAllBorrowedBookTitles(log, books);
                    for (String title : borrowedTitles) {
                        System.out.println(title);
                    }
                    break;
                case 5:
                    break;
                default:
                    System.out.println("Invalid selection. Please try again.");
                    break;
            }

            if (statsChoice == 5)
                break;
        }

    }

    public void analyzer() {
        while (true) {
            printMenu2();
            int analyzerChoice = ScannerUtils.getIntInput("Choose an option:");

            switch (analyzerChoice) {
                case 1:
                    borrowingTrendAnalyzer.analyzeBorrowingTrendsPerMonth(log);
                    break;
                case 2:
                    borrowingTrendAnalyzer.analyzeBorrowingTrendsPerQuarter(log);
                    break;
                case 3:
                    System.out.println("Enter the year to analyze : ");
                    int year = sc.nextInt();
                    borrowingTrendAnalyzer.analyzeBorrowingTrendsPerYear(log, year);
                    break;
                case 4:
                    genreTrendAnalyzer.analyzeGenreTrends(books, log);
                    break;
                case 5:
                    authorTrendAnalyzer.analyzeAuthorTrends(books, log);
                    break;
                case 6:
                    System.out.println("Display Top - ");
                    int limit = sc.nextInt();
                    mostBorrowedBooks.analyzeMostBorrowedBooks(books, log, limit);
                    break;
                case 7:
                    break;
                default:
                    System.out.println("Invalid selection. Please try again.");
                    break;
            }

            if (analyzerChoice == 7)
                break;
        }
    }

    private static void printMenu1() {
        System.out.println("\nBooks Statistics Overview:");
        System.out.println("1. Show All Books ");
        System.out.println("2. Total No. of Books Present");
        System.out.println("3. Number of currently borrowed books");
        System.out.println("4. List of titles of all borrowed books");
        System.out.println("5. Back to Main Menu");
        //System.out.print("Choose an option: ");
    }

    private static void printMenu2() {
        System.out.println("\nAnalyzers:");
        System.out.println("1. Analyze Borrowing Trends per Month");
        System.out.println("2. Analyze Borrowing Trends per Quarter");
        System.out.println("3. Analyze Borrowing Trends per Year");
        System.out.println("4. Analyze Trending Genres");
        System.out.println("5. Analyze Trending Authors");
        System.out.println("6. Analyze Most Popular Book");
        System.out.println("7. Back to Main Menu");

    }


}