/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import dbutils.DBUtils;
import dto.BookDTO;
import dto.CartDTO;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ADMIN
 */
public class DAO_TEST {

    public boolean createOrder(CartDTO cart, float total, String userID, String discountCode) {
        boolean isOrder = false;
        String sql = "INSERT INTO tblOrders(orderID, total,userID) VALUES(?,?,?)";
        Connection con = null;
        PreparedStatement stm = null;
        PreparedStatement stmUpdateBook = null;
        PreparedStatement stmOrderDetail = null;
        PreparedStatement stmDiscount = null;
        ResultSet rs = null;
        Long curretTime = Calendar.getInstance().getTimeInMillis();
        String orderID = curretTime.toString();
        con = dbutils.DBUtils.getConnection();
        try {
            con.setAutoCommit(false);
            stm = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stm.setString(1, orderID);
            stm.setFloat(2, total);
            stm.setString(3, userID);
            int rowEffect = stm.executeUpdate();
            rs = stm.getGeneratedKeys();
            if (rs.next()) {

            }
            if (rowEffect == 1) {
                String sqlPiviot = "INSERT INTO "
                        + "tblOrderDetails(orderID, bookID, price, quantity)"
                        + "VALUES(?,?,?,?)";
                for (Map.Entry<BookDTO, Integer> en : cart.getCart().entrySet()) {
                    BookDTO key = en.getKey();
                    Integer value = en.getValue();
                    key.getBookID();
                    stmOrderDetail = con.prepareStatement(sqlPiviot);
                    stmOrderDetail.setString(1, orderID);
                    stmOrderDetail.setString(2, key.getBookID());
                    stmOrderDetail.setFloat(3, key.getPrice());
                    stmOrderDetail.setInt(4, value);
                    stmOrderDetail.executeUpdate();
                }
                String sqlUpdateBook = "UPDATE tblBooks SET tblBooks.quantity = ?"
                        + " where bookID = ?";
                for (Map.Entry<BookDTO, Integer> en : cart.getCart().entrySet()) {
                    BookDTO key = en.getKey();
                    Integer value = en.getValue();
                    stmUpdateBook = con.prepareStatement(sqlUpdateBook);
                    stmUpdateBook.setInt(1, key.getQuantity() - value);
                    stmUpdateBook.setString(2, key.getBookID());
                    stmUpdateBook.executeUpdate();
                }

            }
            if (discountCode != null) {
                String sqlDiscount = "INSERT INTO [dbo].[tblDiscountUsage]\n"
                        + "("
                        + "  [discountCode]\n"
                        + ", [userID]"
                        + ", [orderID])\n"
                        + "VALUES(?,?,?)";
                stmDiscount = con.prepareStatement(sqlDiscount);
                stmDiscount.setString(1, discountCode);
                stmDiscount.setString(2, userID);
                stmDiscount.setString(3, orderID);
                stmDiscount.executeUpdate();
            }
            con.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                con.rollback();
            } catch (SQLException ex) {
                System.out.println(ex.getCause());
            }
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmDiscount != null) {
                    stmDiscount.close();
                }
                if (stmUpdateBook != null) {
                    stmUpdateBook.close();
                }
                if (stmOrderDetail != null) {
                    stmOrderDetail.close();
                }
                if (stm != null) {
                    stm.close();
                }
            } catch (SQLException sQLException) {

            }
        }
        return isOrder;
    }

    public float getDiscount(String discountCode) {
        String sql = "SELECT [percent] from tblDiscounts where discountCode = ?";
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        float result = 0;
        try {
            con = DBUtils.getConnection();
            stm = con.prepareStatement(sql);
            stm.setString(1, discountCode);
            rs = stm.executeQuery();
            if (rs.next()) {
                result = rs.getFloat("percent");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public BookDTO getBookInformation(String bookID) {
        BookDTO result = null;
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        String sql = "SELECT [ISBN]\n"
                + "      ,[bookID]\n"
                + "      ,[bookTitle]\n"
                + "      ,[price]\n"
                + "      ,[quantity]\n"
                + "      ,[importDate]\n"
                + "      ,[imageName]\n"
                + "      ,[category]\n"
                + "      ,[author]\n"
                + "      ,[status]\n"
                + "  FROM [BOOK_DB].[dbo].[tblBooks] where bookID = ?";
        try {
            con = DBUtils.getConnection();
            stm = con.prepareStatement(sql);
            stm.setString(1, bookID);
            rs = stm.executeQuery();
            if (rs.next()) {
                String ISBN = rs.getString("ISBN");
                String title = rs.getString("bookTitle");
                String author = rs.getString("author");
                String category = rs.getString("category");
                float price = rs.getFloat("price");
                int quantity = rs.getInt("quantity");
                Date importDate = rs.getDate("importDate");
                String image = rs.getString("imageName");
                if (!image.matches("(.[a-z]{1,})")) {
                    image = image + ".jpg";
                }
                boolean status = rs.getBoolean("status");
                result = new BookDTO(bookID, ISBN, title, author, price, quantity, importDate, status, author, image);
                result.setCategory(category);
            }
        } catch (SQLException ex) {
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stm != null) {
                    stm.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
            }
        }
        return result;
    }

}

class TEST {

    public static void main(String[] args) {
        CartDTO cart = new CartDTO();
        DAO_TEST dao = new DAO_TEST();
        HashMap<BookDTO, Integer> currCart = new HashMap<>();
        BookDTO dto = new BookDTO();
        dto.setBookID("BOOK000003");
        dto.setPrice(Float.parseFloat("16.02"));
        BookDTO dto_2 = new BookDTO();
        dto_2.setBookID("BOOK000004");
        dto_2.setPrice(Float.parseFloat("46.85"));
        dto.setQuantity(50);
        dto_2.setQuantity(50);

        currCart.put(dto, 1);
        currCart.put(dto_2, 2);
        cart.setCart(currCart);
        float total = 0;
        for (Map.Entry<BookDTO, Integer> entry : currCart.entrySet()) {
            BookDTO key = entry.getKey();
            Integer value = entry.getValue();
            total = total + value * key.getPrice();
        }
        System.out.println(total);
        System.out.println(dao.getDiscount("BOOK300000"));
        dao.createOrder(cart, total - total * dao.getDiscount("BOOK300000")/100, "user02", "BOOK300000");
    }
}
