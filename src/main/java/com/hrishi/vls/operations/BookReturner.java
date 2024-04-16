package com.hrishi.vls.operations;

import com.hrishi.vls.models.Book;
import com.hrishi.vls.models.Library;
import com.hrishi.vls.models.TransactionLog;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class BookReturner {


    private Library library;
    private TransactionLog lg;

    static Scanner sc = new Scanner(System.in);


    public void ReturnBook(List<Book> books, List<TransactionLog> log) {
        System.out.println("Enter your UserID to return the book: ");
        int userID = sc.nextInt();
        System.out.println("Enter ISBN of the Book you want to return: ");
        String isbn = sc.next();

        boolean foundLog = false;

        for (TransactionLog l : log) {
            if (l.getUserId() == userID && l.getISBN().equalsIgnoreCase(isbn)) {
                foundLog = true;

                for (Book bk : books) {
                    if (bk.getISBN().equalsIgnoreCase(isbn)) {
                        System.out.println("The book you want to return is : " + bk.getTitle());
                        System.out.println("Confirm to proceed(Y/N) :");
                        String confirmation = sc.next();

                        if (confirmation.equalsIgnoreCase("y")) {
                            bk.setNoOfCopies(bk.getNoOfCopies() + 1);
                            if (bk.getStatus().equalsIgnoreCase("out of stock")) {
                                bk.setStatus("Available");
                            }
                            l.setReturned("Yes");
                            l.setReturnDate(LocalDate.now());
                            System.out.println("Book returned successfully");
                            return; // Fail fast after successful return
                        } else {
                            System.out.println("Thank You");
                            return; // Fail fast if confirmation is not 'y'
                        }
                    }
                }
            }
        }

        if (!foundLog) {
            System.out.println("No log record Exists");
        }
    }


}
