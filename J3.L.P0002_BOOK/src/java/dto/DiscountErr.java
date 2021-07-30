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
public class DiscountErr {

    private String discountCodeErr;
    private String percentErr;
    private String expireDateErr;

    public DiscountErr() {
    }

    public DiscountErr(String discountCodeErr, String percentErr, String expireDateErr) {
        this.discountCodeErr = discountCodeErr;
        this.percentErr = percentErr;
        this.expireDateErr = expireDateErr;
    }
    
    
    public String getDiscountCodeErr() {
        return discountCodeErr;
    }

    public void setDiscountCodeErr(String discountCodeErr) {
        this.discountCodeErr = discountCodeErr;
    }

    public String getPercentErr() {
        return percentErr;
    }

    public void setPercentErr(String percentErr) {
        this.percentErr = percentErr;
    }

    public String getExpireDateErr() {
        return expireDateErr;
    }

    public void setExpireDateErr(String expireDateErr) {
        this.expireDateErr = expireDateErr;
    }
    
    

}
