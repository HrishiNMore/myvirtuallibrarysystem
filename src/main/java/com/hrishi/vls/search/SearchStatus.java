package com.hrishi.vls.search;

import com.hrishi.vls.models.Book;

public class SearchStatus {


    public void showStatus(Book bk) {

        System.out.println("Here is the details of the book: \nTitle : " + bk.getTitle() + "\n"
                + "Author : " + bk.getAuthor() + "\n" + "ISBN : " + bk.getISBN() + "\n"
                + "Genre : " + bk.getGenre() + "\n"
                + "Publication date : " + bk.getPublication_Date());
        if (bk.getNoOfCopies() < 1) {
            bk.setStatus("Out of stock");
            System.out.println("Current availability status : " + bk.getStatus());
        } else {
            bk.setStatus("Available");
            System.out.println("Current availability status : " + bk.getStatus());
        }
    }


}
