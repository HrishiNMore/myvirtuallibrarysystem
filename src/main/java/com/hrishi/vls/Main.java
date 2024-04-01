package com.hrishi.vls;

import com.hrishi.vls.models.Library;

import com.hrishi.vls.operations.*;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Library lib=new Library();

        boolean exit = false;
        BookSearcher searcher = new BookSearcher(lib);



        while (!exit) {
            System.out.println("==================================================================");
            System.out.println("==============  Welcome to virtual library system  ===============");
            System.out.println("==================================================================");
            System.out.println("1. Search for a book            \t2.Borrow a book");
            System.out.println("3. Return a book                \t4.View Library Inventory");
            System.out.println("5. View Transaction Log         \t6.Upload Books");
            System.out.println("7. Books Statistics Overview    \t8.Analyzers");
            System.out.println("9. Exit");
            System.out.println("==================================================================");
            System.out.print("Choose an option: ");
            Scanner scanner = new Scanner(System.in);
            int choice = scanner.nextInt();
            System.out.println("==================================================================");



            switch (choice) {
                case 1:
                    lib.searchBook();
                    break;
                case 2:

                    lib.borrowBook();
//
                    break;
                case 3:


                    lib.returnBook();
                    break;
                case 4:

                    lib.bookInventory();
                    break;
                case 5:

                    lib.viewLog();
                    break;
                case 6:

                    lib.UploadBook("src/main/resources/dataset.csv");
                    break;
                case 7:
                    lib.showStatistics();
                    break;
                case 8:

                   lib.analyzer();
                    break;
                case 9:
//

                    System.out.println("==================================================================");
                    System.out.println("\tThank You for Visitng our library.Please visit again ");
                    System.out.println("==================================================================");
                    exit = true;
            }
        }
    }
    }
