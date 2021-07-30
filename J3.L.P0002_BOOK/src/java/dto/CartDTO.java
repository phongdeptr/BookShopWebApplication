/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import java.io.Serializable;
import java.util.HashMap;

/**
 *
 * @author ADMIN
 */
public class CartDTO implements Serializable {

    private HashMap<BookDTO, Integer> cart;

    public CartDTO() {
    }

    public CartDTO(HashMap<BookDTO, Integer> cart) {
        this.cart = cart;
    }
    public HashMap<BookDTO, Integer> getCart() {
        return cart;
    }

    public void setCart(HashMap<BookDTO, Integer> cart) {
        this.cart = cart;
    }

    public void addToCart(BookDTO item, int quantity) {
        int currQuantity = 0;
        if (cart != null) {
            boolean isExist = cart.containsKey(item);
            if (isExist) {
                currQuantity = cart.get(item);
                currQuantity = currQuantity + quantity;
                cart.put(item, quantity);
            } else {
                cart.put(item, quantity);
            }
        } else {
            cart = new HashMap<>();
            cart.put(item, quantity);
        }
    }

    public String updateCart(BookDTO item, int quantity) {
        String result = null;
        int currQuantity = 0;
        if (cart.containsKey(item)) {
            cart.put(item, quantity);
            result = "SUCCESS";
        } else {
            result = "Error: item is not exist in cart";
        }
        return result;
    }

    public String removeFromCart(BookDTO item) {
        String message = "";
        if (cart == null) {
            message = "No book in cart to remove";
        }
        if (cart != null && cart.size() > 0) {
            if (cart.containsKey(item)) {
                cart.remove(item);
                message = "SUCCESS";
            } else {
                message = "No book in cart to remove";
            }
        }
        return message;
    }
}
