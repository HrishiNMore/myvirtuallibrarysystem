package com.hrishi.vls.operations;

import com.hrishi.vls.isbn.ISBNChecker;
import com.hrishi.vls.models.Book;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


import java.util.List;

public class BookUploader {

    private int booksAdded = 0;
    private int booksSkipped = 0;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final ISBNChecker check=new ISBNChecker();
//    private final Library library;



    public void uploadBook(String FILE_PATH, List<Book> books) {
        try {
            processBook(FILE_PATH,books);
            // Display summary after processing books
            System.out.println("Books added: " + booksAdded);
            System.out.println("Books skipped due to duplicate ISBNs: " + booksSkipped);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void processBook(String filepath,List<Book> books) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filepath));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts.length == 6) { // Assuming CSV format: ISBN, Title, Author
                String title = parts[0].trim();
                String author = parts[1].trim();
                String isbn = parts[2].trim();
                String genre = parts[3].trim();
                LocalDate publication_date = LocalDate.parse(parts[4].trim(), formatter);
                int noOfCopies = Integer.parseInt(parts[5].trim());

                Book book = new Book(title, author, isbn, genre, publication_date, noOfCopies);

                // Check uniqueness of ISBN
                if (check.isISBNUnique(book.getISBN(),books)) {
                    books.add(book); // Add book to library's collection
                    System.out.println("Added book: " + book.getTitle());
                    booksAdded++;
                } else {
                    System.out.println("Skipped duplicate ISBN: " + book.getTitle());
                    booksSkipped++;
                }
            }
        }
        reader.close(); // Close the reader after processing
    }
}