package com.hrishi.vls;


import com.hrishi.vls.analyzers.AuthorTrendAnalyzer;
import com.hrishi.vls.analyzers.BorrowingTrendAnalyzer;
import com.hrishi.vls.analyzers.GenreTrendAnalyzer;
import com.hrishi.vls.analyzers.MostBorrowedBooks;
import com.hrishi.vls.isbn.ISBNChecker;
import com.hrishi.vls.models.Book;
import com.hrishi.vls.models.TransactionLog;
import com.hrishi.vls.operations.*;
import com.hrishi.vls.search.BookSearcher;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import java.util.Scanner;


public class LibraryManager {


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


    public LibraryManager() {
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
//--------------------------------------------------------------------------------------------------
    public enum StatisticsOption {
        DISPLAY_LIBRARY_STATISTICS(1),
        TOTAL_BOOKS_PRESENT(2),
        CURRENTLY_BORROWED_BOOKS_COUNT(3),
        LIST_BORROWED_BOOK_TITLES(4),
        EXIT(5);

        private final int value;

        StatisticsOption(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static StatisticsOption getByValue(int value) {
            for (StatisticsOption option : values()) {
                if (option.getValue() == value) {
                    return option;
                }
            }
            return null;
        }
    }
    public void showStatistics() {
        while (true) {
            printMenu1();
            int statsChoice = ScannerUtils.getIntInput("Choose an option:");
            StatisticsOption option = StatisticsOption.getByValue(statsChoice);

            if (option != null) {
                switch (option) {
                    case DISPLAY_LIBRARY_STATISTICS:
                        bookStatisticsCalculator.displayLibraryStatistics(books, log);
                        break;
                    case TOTAL_BOOKS_PRESENT:
                        System.out.println("Total number of books present: " + bookStatisticsCalculator.getTotalBooks(books));
                        break;
                    case CURRENTLY_BORROWED_BOOKS_COUNT:
                        System.out.println("Number of currently borrowed books: " + bookStatisticsCalculator.calculateCurrentlyBorrowedBooksCount(log));
                        break;
                    case LIST_BORROWED_BOOK_TITLES:
                        System.out.println("List of titles of all borrowed books:");
                        List<String> borrowedTitles = bookStatisticsCalculator.getAllBorrowedBookTitles(log, books);
                        for (String title : borrowedTitles) {
                            System.out.println(title);
                        }
                        break;
                    case EXIT:
                        return;
                    default:
                        System.out.println("Invalid selection. Please try again.");
                        break;
                }
            } else {
                System.out.println("Invalid selection. Please try again.");
            }
        }
    }
//----------------------------------------------------------------------------------------------------------

    public enum AnalyzerOption {
        BORROWING_TRENDS_PER_MONTH(1),
        BORROWING_TRENDS_PER_QUARTER(2),
        BORROWING_TRENDS_PER_YEAR(3),
        GENRE_TRENDS(4),
        AUTHOR_TRENDS(5),
        MOST_BORROWED_BOOKS(6),
        EXIT(7);

        private final int value;

        AnalyzerOption(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static AnalyzerOption fromValue(int value) {
            for (AnalyzerOption option : values()) {
                if (option.value == value) {
                    return option;
                }
            }
            return null; // Or throw an exception if value is invalid
        }
    }

    public void analyzer() {
        while (true) {
            printMenu2();
            int analyzerChoice = ScannerUtils.getIntInput("Choose an option:");

            AnalyzerOption option = AnalyzerOption.fromValue(analyzerChoice);
            if (option == null) {
                System.out.println("Invalid selection. Please try again.");
                continue;
            }

            switch (option) {
                case BORROWING_TRENDS_PER_MONTH:
                    borrowingTrendAnalyzer.analyzeBorrowingTrendsPerMonth(log);
                    break;
                case BORROWING_TRENDS_PER_QUARTER:
                    borrowingTrendAnalyzer.analyzeBorrowingTrendsPerQuarter(log);
                    break;
                case BORROWING_TRENDS_PER_YEAR:
                    System.out.println("Enter the year to analyze : ");
                    int year = sc.nextInt();
                    borrowingTrendAnalyzer.analyzeBorrowingTrendsPerYear(log, year);
                    break;
                case GENRE_TRENDS:
                    genreTrendAnalyzer.analyzeGenreTrends(books, log);
                    break;
                case AUTHOR_TRENDS:
                    authorTrendAnalyzer.analyzeAuthorTrends(books, log);
                    break;
                case MOST_BORROWED_BOOKS:
                    System.out.println("Display Top - ");
                    int limit = sc.nextInt();
                    mostBorrowedBooks.analyzeMostBorrowedBooks(books, log, limit);
                    break;
                case EXIT:
                    return;
            }
        }
    }

//------------------------------------------------------------------------------------------------------
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