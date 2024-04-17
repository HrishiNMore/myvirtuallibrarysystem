package com.hrishi.vls;


import java.util.Scanner;

public class ScannerUtils {

    private static final Scanner scanner = new Scanner(System.in);

    public static int getIntInput(String message) {
        System.out.print(message);
        try {
            return scanner.nextInt();
        } catch (Exception e) {
            System.out.println("Invalid input. Please enter a valid integer.");
            scanner.nextLine(); // Clear the invalid input
            return getIntInput(message); // Recursive call for valid input
        }
    }

    // You can add more utility methods here if needed

    // Don't forget to close the scanner when it's no longer needed
    public static void closeScanner() {
        scanner.close();
    }
}