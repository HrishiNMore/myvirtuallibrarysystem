package com.hrishi.vls.search;

import com.hrishi.vls.models.Book;

import java.util.ArrayList;
import java.util.List;

public class GenreSearchStrategy implements SearchStrategy {
    @Override
    public List<Book> search(List<Book> books, String key) {
        List<Book> result = new ArrayList<>();
        for (Book book : books) {
            if (book.getGenre().equalsIgnoreCase(key)) {
                result.add(book);
            }
        }
        return result;

    }
}
