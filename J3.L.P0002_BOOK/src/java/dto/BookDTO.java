/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author ADMIN
 */
public class BookDTO {
    private String bookID;
    private String ISBN;
    private String bookTitle;
    private String author;
    private float price;
    private int quantity;
    private Date importDate;
    private boolean status;
    private String description;
    private String category;
    private String image;
    
    public BookDTO() {
    }

    public BookDTO(String bookID,String ISBN, String bookTitle, String author, float price, String image) {
        this.bookID = bookID;
        this.ISBN = ISBN;
        this.bookTitle = bookTitle;
        this.author = author;
        this.price = price;
        this.image = image;
    }

    public BookDTO(String bookID, String bookTitle, String author, float price, int quantity, boolean status, String description, String category, String image) {
        this.bookID = bookID;
        this.bookTitle = bookTitle;
        this.author = author;
        this.price = price;
        this.quantity = quantity;
        this.status = status;
        this.description = description;
        this.category = category;
        this.image = image;
    }
    
    
    
    public BookDTO(String bookID, String ISBN, String bookTitle, String author, float price, int quantity, Date importDate, boolean status, String category, String image) {
        this.bookID = bookID;
        this.ISBN = ISBN;
        this.bookTitle = bookTitle;
        this.author = author;
        this.price = price;
        this.quantity = quantity;
        this.importDate = importDate;
        this.status = status;
        this.category = category;
        this.image = image;
    }

    public BookDTO(String bookID, String ISBN, String bookTitle, String author, float price, int quantity, Date importDate, boolean status, String description, String category, String image) {
        this.bookID = bookID;
        this.ISBN = ISBN;
        this.bookTitle = bookTitle;
        this.author = author;
        this.price = price;
        this.quantity = quantity;
        this.importDate = importDate;
        this.status = status;
        this.description = description;
        this.category = category;
        this.image = image;
    }
    
    

    public String getBookID() {
        return bookID;
    }

    public void setBookID(String bookID) {
        this.bookID = bookID;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Date getImportDate() {
        return importDate;
    }

    public void setImportDate(Date importDate) {
        this.importDate = importDate;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }    

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 17 * hash + Objects.hashCode(this.bookID);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final BookDTO other = (BookDTO) obj;
        if (!Objects.equals(this.bookID, other.bookID)) {
            return false;
        }
        return true;
    }
    
    
}
