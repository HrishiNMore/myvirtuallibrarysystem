package com.hrishi.vls;

import java.util.Scanner;

// Enum representing available library functionalities
enum LibraryOption {
    SEARCH_BOOK,
    BORROW_BOOK,
    RETURN_BOOK,
    VIEW_INVENTORY,
    VIEW_LOG,
    UPLOAD_BOOKS,
    VIEW_STATISTICS,
    ANALYZE,
    EXIT
}

// Refactored LibraryCommands class to manage commands associated with each LibraryOption
class LibraryCommands {
    private static LibraryManager lib = new LibraryManager();

    // Method to execute a command based on LibraryOption
    public static void executeCommand(LibraryOption option) {
        switch (option) {
            case SEARCH_BOOK:
                lib.searchBook();
                break;
            case BORROW_BOOK:
                lib.borrowBook();
                break;
            case RETURN_BOOK:
                lib.returnBook();
                break;
            case VIEW_INVENTORY:
                lib.bookInventory();
                break;
            case VIEW_LOG:
                lib.viewLog();
                break;
            case UPLOAD_BOOKS:
                lib.UploadBook("src/main/resources/dataset.csv");
                break;
            case VIEW_STATISTICS:
                lib.showStatistics();
                break;
            case ANALYZE:
                lib.analyzer();
                break;
            case EXIT:
                System.out.println("==================================================================");
                System.out.println("\tThank You for Visiting our library. Please visit again.");
                System.out.println("==================================================================");
                break;
            default:
                System.out.println("Invalid option. Please choose a valid option.");
        }
    }
}

public class VirtualLibrarySystem {

    public static void main(String[] args) {
        boolean exit = false;

        while (!exit) {
            printMenu();
            int choice = getUserChoice();
            System.out.println("==================================================================");

            // Execute command based on user choice
            LibraryOption option = LibraryOption.values()[choice - 1];
            LibraryCommands.executeCommand(option);

            if (option == LibraryOption.EXIT) {
                exit = true;
            }
        }
    }

    private static void printMenu() {
        System.out.println("==================================================================");
        System.out.println("==============  Welcome to virtual library system  ===============");
        System.out.println("==================================================================");
        System.out.println("1. Search for a book            \t2. Borrow a book");
        System.out.println("3. Return a book                \t4. View Library Inventory");
        System.out.println("5. View Transaction Log         \t6. Upload Books");
        System.out.println("7. Books Statistics Overview    \t8. Analyzers");
        System.out.println("9. Exit");
        System.out.println("==================================================================");
        System.out.print("Choose an option: ");
    }

    private static int getUserChoice() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }
}
