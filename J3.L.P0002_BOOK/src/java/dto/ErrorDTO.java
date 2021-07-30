/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

/**
 *
 * @author ADMIN
 */
public class ErrorDTO {
    private String titleErr;
    private String authorErr;
    private String priceErr;
    private String quantityErr;
    private String ISBN_ERROR;
    private String categoryErr;
    public ErrorDTO() {
    }

    public ErrorDTO(String titleErr, String authorErr, String priceErr, String quantityErr, String ISBN_ERROR, String categoryErr) {
        this.titleErr = titleErr;
        this.authorErr = authorErr;
        this.priceErr = priceErr;
        this.quantityErr = quantityErr;
        this.ISBN_ERROR = ISBN_ERROR;
        this.categoryErr = categoryErr;
    }

    public String getTitleErr() {
        return titleErr;
    }

    public void setTitleErr(String titleErr) {
        this.titleErr = titleErr;
    }

    public String getAuthorErr() {
        return authorErr;
    }

    public void setAuthorErr(String authorErr) {
        this.authorErr = authorErr;
    }

    public String getPriceErr() {
        return priceErr;
    }

    public void setPriceErr(String priceErr) {
        this.priceErr = priceErr;
    }

    public String getQuantityErr() {
        return quantityErr;
    }

    public void setQuantityErr(String quantityErr) {
        this.quantityErr = quantityErr;
    }

    public String getISBN_ERROR() {
        return ISBN_ERROR;
    }

    public void setISBN_ERROR(String ISBN_ERROR) {
        this.ISBN_ERROR = ISBN_ERROR;
    }

    public String getCategoryErr() {
        return categoryErr;
    }

    public void setCategoryErr(String categoryErr) {
        this.categoryErr = categoryErr;
    }
}
