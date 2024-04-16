package com.hrishi.vls.search;

import com.hrishi.vls.models.Book;
import com.hrishi.vls.models.Library;
import com.hrishi.vls.operations.BookLender;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class BookSearcher {

    private Scanner sc = new Scanner(System.in);
    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private BookLender borrow;
    private List<Book> books;

    public BookSearcher(List<Book> books) {

        this.books = books;
    }


    public void search() {
        if (books.isEmpty()) {
            System.err.println("Library is Empty. Please add books");
            return; // Fail fast if the library is empty
        }

        System.out.println("Search By : ");
        System.out.println("1. Title");
        System.out.println("2. Author");
        System.out.println("3. ISBN");
        System.out.println("4. Genre");
        System.out.println("5. Published Date");
        System.out.println("6. No of Copies");
        System.out.print("Choose an option: ");
        int option = sc.nextInt();
        SearchStrategy searchStrategy = getSearchStrategy(option);
        System.out.println("Enter the search key:");
        sc.nextLine();
        String key = sc.nextLine().trim();
        performSearch(searchStrategy.search(books, key));


    }

    private SearchStrategy getSearchStrategy(int option) {
        switch (option) {
            case 1:
                return new TitleSearchStrategy();
            case 2:
                return new AuthorSearchStrategy();
            case 3:
                return new ISBNSearchStrategy();
            case 4:
                return new GenreSearchStrategy();
            case 5:
                return new Publication_DateSearchStrategy();
            case 6:
                return new NoOfCopiesSearchStrategy();
            // other choices..
            default:
                throw new IllegalArgumentException("Invalid option");
        }
    }

    private void performSearch(List<Book> result) {
        if (result.isEmpty()) {
            System.out.println("Book Not Found");
        } else if (result.size() == 1) {
            Book foundBook = result.get(0);
            System.out.println("Book Found in Library: " + foundBook.getTitle());
            new SearchStatus().showStatus(foundBook);
        } else {
            // Only prompt for filtering if there are multiple results
            for (Book book : result) {
                System.out.println("Title: " + book.getTitle() + " ISBN: " + book.getISBN());
            }
            result = addfilter(result);

            if (result.isEmpty()) {
                System.out.println("No books match the specified criteria.");
            }
        }
    }


    public List<Book> searchByTitle(String title, List<Book> books) {
        List<Book> result = new ArrayList<>();
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                result.add(book);
            }
        }
        return result;
    }

    public List<Book> searchByAuthor(String author, List<Book> books) {
        List<Book> result = new ArrayList<>();
        for (Book book : books) {
            if (book.getAuthor().equalsIgnoreCase(author)) {
                result.add(book);
            }
        }
        return result;
    }

    public List<Book> searchByGenre(String Genre, List<Book> books) {
        List<Book> result = new ArrayList<>();
        for (Book book : books) {
            if (book.getGenre().equalsIgnoreCase(Genre)) {
                result.add(book);
            }
        }
        return result;
    }

    public List<Book> searchByISBN(String isbn, List<Book> books) {
        List<Book> result = new ArrayList<>();
        for (Book book : books) {
            if (book.getISBN().equalsIgnoreCase(isbn)) {
                result.add(book);
            }
        }
        return result;
    }

    public List<Book> searchByDate(LocalDate date, List<Book> books) {
        List<Book> result = new ArrayList<>();
        for (Book book : books) {
            if (book.getPublication_Date().isEqual(date)) {
                result.add(book);
            }
        }
        return result;
    }

    public List<Book> searchByCopies(int copies, List<Book> books) {
        List<Book> result = new ArrayList<>();
        for (Book book : books) {
            if (book.getNoOfCopies() == copies) {
                result.add(book);
            }
        }
        return result;
    }

    public List<Book> addfilter(List<Book> result) {
        System.out.println("Do you want to Filter the result(Y/N) ?:");
        String ch = sc.next();
        if (!ch.equalsIgnoreCase("y")) {
            return result; // If user doesn't want to filter, return the original result
        }
        while (ch.equalsIgnoreCase("y")) {
            System.out.println("Filter By : ");
            System.out.println("1. Title");
            System.out.println("2. Author");
            System.out.println("3. ISBN");
            System.out.println("4. Genre");
            System.out.println("5. Published Date");
            System.out.println("6. No of Copies");
            System.out.print("Choose an option: ");
            int a = sc.nextInt();
            switch (a) {
                case 1:
                    System.out.println("Enter the Title of Book :");
                    String title = sc.next();
                    result = searchByTitle(title, result);
                    break;
                case 2:
                    System.out.println("Enter the Author of Book :");
                    String author = sc.next();
                    result = searchByAuthor(author, result);
                    break;
                case 3:
                    System.out.println("Enter the ISBN of Book :");
                    String isbn = sc.next();
                    result = searchByISBN(isbn, result);
                    break;
                case 4:
                    System.out.println("Enter Genre of Book :");
                    String genre = sc.next();
                    result = searchByGenre(genre, result);
                    break;
                case 5:
                    System.out.println("Enter the Publication Date of Book :");
                    String d = sc.next();
                    LocalDate date = LocalDate.parse(d, formatter);
                    result = searchByDate(date, result);
                    break;
                case 6:
                    System.out.println("Enter the No of copies of book :");
                    int noOfCopies = sc.nextInt();
                    result = searchByCopies(noOfCopies, result);
                    break;
                default:
                    System.out.println("Invalid input");
                    continue;
            }

            if (result.isEmpty()) {
                System.out.println("No books match the specified criteria.");
                return Collections.emptyList();
            } else {
                System.out.println("Filtered Books :");
                for (int i = 0; i < result.size(); i++) {
                    System.out.println((i + 1) + ". " + result.get(i).getTitle());
                }
            }

            System.out.println("Choose a book from the filtered list (Enter the corresponding number): ");
            int bookIndex = sc.nextInt();
            if (bookIndex > 0 && bookIndex <= result.size()) {
                Book selectedBook = result.get(bookIndex - 1);
                new SearchStatus().showStatus(selectedBook);
                return Collections.singletonList(selectedBook); // Return a list containing the selected book
            } else {
                System.out.println("Invalid book selection.");
                return Collections.emptyList();
            }

        }
        return result;
    }
}