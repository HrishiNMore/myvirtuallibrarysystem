package com.hrishi.vls.search;

import com.hrishi.vls.models.Book;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Publication_DateSearchStrategy implements SearchStrategy {
    @Override
    public List<Book> search(List<Book> books, String key) {
        List<Book> result = new ArrayList<>();

        for (Book book : books) {
            LocalDate localDate = book.getPublication_Date();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String dateString = localDate.format(formatter);
            if (dateString.equalsIgnoreCase(key)) {
                result.add(book);
            }
        }
        return result;

    }
}
