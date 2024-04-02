package com.hrishi.vls.search;

import com.hrishi.vls.models.Book;


import java.util.List;
import java.util.Scanner;

public interface SearchStrategy {
    List<Book> search(List<Book> books, String key);
}
