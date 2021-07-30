package daos;

import dto.BookDTO;
import dto.UserDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import dbutils.DBUtils;
import dto.CartDTO;
import dto.CategoryDTO;
import dto.OrderDTO;
import dto.OrderItem;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author ADMIN
 */
public class Dao {

    public UserDTO checkLoginByGoogle(String userID, String userName) {
        UserDTO user = null;
        String sql = "SELECT userID, fullName, isAdmin FROM tblUsers where userID = ?";
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBUtils.getConnection();
            if (con != null) {
                stm = con.prepareStatement(sql);
                stm.setString(1, userID);
                rs = stm.executeQuery();
                if (rs.next()) {
                    boolean isAdmin = rs.getBoolean("isAdmin");
                    String fullName = rs.getString("fullName");
                    user = new UserDTO(userID, isAdmin);
                    user.setFullName(fullName);
                } else {
                    System.out.println("RS is null in checkLogin");
                    sql = "INSERT INTO tblUsers(userID, fullName) VALUES (?, ?)";
                    stm = con.prepareStatement(sql);
                    stm.setString(1, userID);
                    stm.setString(2, userName);
                    stm.executeUpdate();
                    UserDTO dto = new UserDTO();
                    dto.setUserID(userID);
                    dto.setIsAdmin(false);
                    dto.setFullName(userName);
                    return dto;
                }
            }
        } catch (SQLException e) {
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
        return user;
    }

    public UserDTO checkLogin(String userID, String password) {
        UserDTO user = null;
        String sql = "SELECT userID, fullName, isAdmin FROM tblUsers where userID = ? and password = ?";
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBUtils.getConnection();
            if (con != null) {
                stm = con.prepareStatement(sql);
                stm.setString(1, userID);
                stm.setString(2, password);
                rs = stm.executeQuery();
                if (rs != null) {
                    if (rs.next()) {
                        boolean isAdmin = rs.getBoolean("isAdmin");
                        String fullName = rs.getString("fullName");
                        user = new UserDTO(userID, isAdmin);
                        user.setFullName(fullName);
                    }
                }
            }
        } catch (SQLException e) {

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
        return user;
    }

    public List<BookDTO> findBooks(String keyword, float minPrice, float maxPrice, String category, int flag) {
        List<BookDTO> books = null;
        PreparedStatement stm = null;
        Connection con = null;
        ResultSet rs = null;
        String sql = "SELECT [ISBN]\n"
                + "      ,[bookID]\n"
                + "      ,[bookTitle]\n"
                + "      ,[price]\n"
                + "      ,[quantity]\n"
                + "      ,[importDate]\n"
                + "      ,[imageName]\n"
                + "      ,[author]\n"
                + "      ,[status]\n"
                + "      ,[description]\n"
                + "      ,[category]\n"
                + "      ,[categoryIcon]\n"
                + "  FROM tblBooks"
                + " where status = ? AND bookTitle LIKE ?";
        con = DBUtils.getConnection();
        try {
            if (flag == 1) {
                stm = con.prepareStatement(sql);
                stm.setBoolean(1, true);
                stm.setString(2, "%" + keyword + "%");
            }
            if (flag == 2) {
                sql = sql + " AND category = ?";
                stm = con.prepareStatement(sql);
                stm.setBoolean(1, true);
                stm.setString(2, "%" + keyword + "%");
                stm.setString(3, category);
            }
            if (flag == 3) {
                sql = sql + " and price >= ? and price <= ?";
                stm = con.prepareStatement(sql);
                stm.setFloat(3, minPrice);
                stm.setFloat(4, maxPrice);
                stm.setString(2, "%" + keyword + "%");
                stm.setBoolean(1, true);

            }
            if (flag == 4) {
                sql = sql + " and price >= ? and price <= ? and category = ?";
                stm = con.prepareStatement(sql);
                stm.setFloat(3, minPrice);
                stm.setFloat(4, maxPrice);
                stm.setString(5, category);
                stm.setString(2, "%" + keyword + "%");
                stm.setBoolean(1, true);
            }
            if (stm != null) {
                rs = stm.executeQuery();
                while (rs != null && rs.next()) {
                    String bookID = rs.getString("bookID");
                    String ISBN = rs.getString("ISBN");
                    String title = rs.getString("bookTitle");
                    String author = rs.getString("author");
                    float price = rs.getFloat("price");
                    String bookImage = rs.getString("imageName");
                    String bookCategory = rs.getString("category");
                    int quantity = rs.getInt("quantity");
                    boolean status = rs.getBoolean("status");
                    Date importDate = rs.getDate("importDate");
                    BookDTO dto = new BookDTO();
                    dto.setISBN(ISBN);
                    dto.setBookID(bookID);
                    dto.setBookTitle(title);
                    dto.setAuthor(author);
                    dto.setImportDate(importDate);
                    dto.setCategory(bookCategory);
                    dto.setImage(bookImage);
                    dto.setStatus(status);
                    dto.setPrice(price);
                    dto.setQuantity(quantity);
                    System.out.println(dto.getISBN());
                    if (books == null) {
                        books = new ArrayList<>();
                    }
                    books.add(dto);
                }
            }
        } catch (SQLException sqlex) {
        }
        return books;
    }

    public List<CategoryDTO> loadCategories() {
        List<CategoryDTO> res = null;
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        String sql = "SELECT Distinct category as categoryName,categoryIcon from tblBooks";
        try {
            con = DBUtils.getConnection();
            stm = con.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                String categoryName = rs.getString("categoryName");
                String image = rs.getString("categoryIcon");
                CategoryDTO dto = new CategoryDTO(categoryName);
                dto.setImageSrc(image);
                if (res == null) {
                    res = new ArrayList<>();
                }
                res.add(dto);
            }
        } catch (SQLException e) {
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
        return res;
    }

    public String createNewBook(String ISBN, String title, String author, float price, int quantity, String imageName, String category) {
        String sql = "INSERT INTO tblBooks(ISBN, bookTitle, author, price , quantity, imageName, category) VALUES (?,?,?,?,?,?,?)";
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBUtils.getConnection();
            if (con != null) {
                stm = con.prepareStatement(sql);
                stm.setString(1, ISBN);
                stm.setString(2, title);
                stm.setString(3, author);
                stm.setFloat(4, price);
                stm.setInt(5, quantity);
                stm.setString(6, imageName);
                stm.setString(7, category);
                int executeUpdate = stm.executeUpdate();
                if (executeUpdate > 0) {
                    return "SUCCESS";
                }
            }
        } catch (SQLException e) {
            return "FAIL";
        } finally {
            try {
                if (stm != null) {
                    stm.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                return "FAIL";
            }
        }
        return "FAIL";
    }

    public String deleteBook(String bookID) {
        String sql = "UPDATE  tblBooks SET status = ? where bookID = ?";
        Connection con = null;
        PreparedStatement stm = null;
        try {
            con = DBUtils.getConnection();
            if (con != null) {
                stm = con.prepareCall(sql);
                stm.setBoolean(1, false);
                stm.setString(2, bookID);
                int res = stm.executeUpdate();
                if (res <= 0) {
                    return "Fail";
                }
            }
        } catch (SQLException e) {

        } finally {
            try {
                if (stm != null) {
                    stm.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {

            }
        }
        return "";
    }

    public String updateBook(String bookID, String ISBN, String bookTittle, float price, int quantity, String author, String category, String importDate) throws SQLException {
        String sql = "UPDATE  tblBooks "
                + "SET"
                + " bookTitle = ?"
                + ",price = ?"
                + ",quantity = ?"
                + ",author = ?"
                + ",category =?"
                + ",importDate = ? "
                + ",ISBN = ?"
                + " where bookID = ?";
        Connection con = null;
        PreparedStatement stm = null;
        con = DBUtils.getConnection();
        try {
            if (con != null) {
                stm = con.prepareCall(sql);
                stm.setString(1, bookTittle);
                stm.setFloat(2, price);
                stm.setInt(3, quantity);
                stm.setString(4, author);
                stm.setString(5, category);
                LocalDate tmp = LocalDate.parse(importDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                tmp.toEpochDay();
                Date sqlDate = Date.valueOf(tmp);
                stm.setDate(6, sqlDate);
                stm.setString(7, ISBN);
                stm.setString(8, bookID);
                int res = stm.executeUpdate();
            }
        } catch (SQLException ex) {

        } finally {
            try {
                if (stm != null) {
                    stm.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {

            }
        }
        return null;
    }

    public boolean checkDuplicate(String ISBN) {
        PreparedStatement stm = null;
        Connection con = null;
        ResultSet rs = null;
        boolean result = true;
        String sql = "SELECT [ISBN]\n"
                + "      ,[bookID]\n"
                + "  FROM tblBooks where [ISBN] = ?";
        try {
            con = DBUtils.getConnection();
            stm = con.prepareStatement(sql);
            stm.setString(1, ISBN);
            rs = stm.executeQuery();
            result = rs.next();
        } catch (SQLException e) {

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

    public List<BookDTO> getAllBook() {
        List<BookDTO> books = null;
        PreparedStatement stm = null;
        Connection con = null;
        ResultSet rs = null;
        //getBook
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
                + "      ,[description]\n"
                + "  FROM tblBooks";
        con = DBUtils.getConnection();
        try {
            stm = con.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                if (books == null) {
                    books = new ArrayList<>();
                }
                String ISBN = rs.getString("ISBN");
                String bookID = rs.getString("bookID");
                String title = rs.getString("bookTitle");
                String author = rs.getString("author");
                float price = rs.getFloat("price");
                int quantity = rs.getInt("quantity");
                Date importDate = rs.getDate("importDate");
                String image = rs.getString("imageName");
                String descripton = rs.getString("description");
                String category = rs.getString("category");
                boolean status = rs.getBoolean("status");
                BookDTO dto = new BookDTO(bookID, ISBN, title, author, price, quantity, importDate, status, descripton, category, image);
                dto.setImportDate(importDate);
                dto.setQuantity(quantity);
                dto.setDescription(descripton);
                books.add(dto);
            }
        } catch (SQLException e) {
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
        return books;
    }

    public boolean createDiscount(String code, float percent, Date expiredate) {
        PreparedStatement stm = null;
        Connection con = null;
        String sql = "INSERT INTO tblDiscounts(discountCode, [percent], [expiredDate]) VALUES(?,?,?)";
        boolean isCreate = false;
        try {
            con = DBUtils.getConnection();
            if (con != null) {
                stm = con.prepareStatement(sql);
                stm.setString(1, code);
                stm.setFloat(2, percent);
                stm.setDate(3, expiredate);
                int result = stm.executeUpdate();
                if (result > 0) {
                    isCreate = true;
                }
            }
        } catch (SQLException e) {
            isCreate = false;
        } finally {
            try {
                if (stm != null) {
                    stm.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
            }
        }
        return isCreate;
    }

    public boolean checkDiscountUsage(String discountCode, String userID) {
        PreparedStatement stm = null;
        Connection con = null;
        ResultSet rs = null;
        boolean isUsed = false;
        String sql = "SELECT discountCode from tblDiscountUsage where userID = ? and discountCode = ?";
        try {
            con = DBUtils.getConnection();
            if (con != null) {
                stm = con.prepareStatement(sql);
                stm.setString(1, userID);
                stm.setString(2, discountCode);
                rs = stm.executeQuery();
                if (rs.next()) {
                    isUsed = true;
                }
            }
        } catch (SQLException e) {

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
        return isUsed;
    }

    public List<BookDTO> precheckout(CartDTO cart) {
        HashMap<BookDTO, Integer> cartItem = cart.getCart();
        List<BookDTO> failCheckoutItems = null;
        for (BookDTO book : cartItem.keySet()) {
            BookDTO targetBook = this.getBookInformation(book.getBookID());
            int quantity = cartItem.get(book);
            if (!targetBook.isStatus() || targetBook.getQuantity() < quantity) {
                if (failCheckoutItems == null) {
                    failCheckoutItems = new ArrayList<>();
                }
                failCheckoutItems.add(book);
            }
        }
        return failCheckoutItems;
    }

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
            if (!discountCode.isEmpty()) {
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
                + "  FROM tblBooks where bookID = ?";
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

    public List<OrderDTO> getOrdersHistory(String userID) {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        List<OrderDTO> orderList = null;
        String sql = "SELECT orderID, total,createDate FROM tblOrders where userID = ?";
        try {
            con = DBUtils.getConnection();
            stm = con.prepareStatement(sql);
            stm.setString(1, userID);
            rs = stm.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    String orderID = rs.getString("orderID");
                    float total = rs.getFloat("total");
                    Date createDate = rs.getDate("createDate");
                    float discount = getDiscount(orderID);
                    if (orderList == null) {
                        orderList = new ArrayList<>();
                    }
                    OrderDTO order = new OrderDTO(orderID, createDate.toString(), total);
                    order.setDiscountValue(discount);
                    orderList.add(order);
                }
            }
        } catch (SQLException e) {
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
        return orderList;
    }

    public List<OrderDTO> findOrder(String bookName, Date fromDate) {
        String sql = "SELECT orderID, total, createDate FROM tblOrders "
                + "where orderID in ( SELECT orderID from tblOrderDetails \n"
                + "where tblOrderDetails.bookID IN (SELECT bookID FROM tblBooks where bookTitle LIKE ?))";
        PreparedStatement stm = null;
        ResultSet rs = null;
        Connection con = null;
        List<OrderDTO> orderList = null;
        try {
            if (fromDate != null) {
                sql = sql + " and CAST(createDate as date) = ?";
            }
            con = DBUtils.getConnection();
            stm = con.prepareStatement(sql);
            stm.setString(1, "%" + bookName + "%");
            if (fromDate != null) {
                stm.setDate(2, fromDate);
            }
            rs = stm.executeQuery();
            while (rs.next()) {
                String orderID = rs.getString("orderID");
                float totalBill = rs.getFloat("total");
                Date purchaseDate = rs.getDate("createDate");
                OrderDTO dto = new OrderDTO();
                dto.setCreateDate(purchaseDate.toString());
                dto.setOrderID(orderID);
                dto.setTotal(totalBill);
                if (orderList == null) {
                    orderList = new ArrayList<>();
                }
                orderList.add(dto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
            } catch (SQLException sQLException) {
            }
        }
        return orderList;
    }

    public List<OrderItem> getOrderDetial(String orderID) {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        List<OrderItem> orderList = null;
        String sql = "SELECT "
                + "(SELECT [bookTitle] FROM [tblBooks] where tblBooks.bookID = tblOrderDetails.bookID) as bookName, \n"
                + "price, \n"
                + "quantity\n"
                + "FROM [tblOrderDetails] where orderID = ?";
        try {
            con = DBUtils.getConnection();
            stm = con.prepareStatement(sql);
            stm.setString(1, orderID);
            rs = stm.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    if (orderList == null) {
                        orderList = new ArrayList<>();
                    }
                    String bookName = rs.getString("bookName");
                    float price = rs.getFloat("price");
                    int quantity = rs.getInt("quantity");
                    OrderItem orderItem = new OrderItem(bookName, price, quantity);
                    orderList.add(orderItem);
                }
            }
        } catch (SQLException e) {

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
        return orderList;
    }

    public boolean checkDiscountAvailable(String discountCode, LocalTime todayDate) {
        boolean isAvailble = false;
        String sql = "SELECT expiredDate from tblDiscounts where discountCode = ?";
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBUtils.getConnection();
            if (con != null) {
                stm = con.prepareStatement(sql);
                stm.setString(1, discountCode);
                rs = stm.executeQuery();
                if (rs != null) {
                    if (rs.next()) {
                        isAvailble = rs.getTime("expiredDate").toLocalTime().isAfter(todayDate);
                    }
                }
            }
        } catch (SQLException e) {

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
            } catch (SQLException sQLException) {
            }
        }
        return isAvailble;
    }

    public List<String> loadDiscountCode() {
        List<String> discountContent = null;
        String sql = "SELECT [percent]\n"
                + "      ,[expiredDate]\n"
                + "      ,[discountCode]\n"
                + "  FROM [tblDiscounts]";
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBUtils.getConnection();
            if (con != null) {
                stm = con.prepareStatement(sql);
                rs = stm.executeQuery();
                while (rs.next()) {
                    String target = "Discount Code: " + rs.getString("discountCode") + "\t percent: " + rs.getFloat("percent") + "%\t Expire: " + rs.getDate("expiredDate").toLocalDate() + "\n";
                    if (discountContent == null) {
                        discountContent = new ArrayList<>();
                    }
                    discountContent.add(target);
                }

            }
        } catch (SQLException e) {

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
            } catch (SQLException sQLException) {
            }
        }
        return discountContent;
    }

    public static void main(String[] args) {
        System.out.println(new Dao().loadDiscountCode().toString());
    }

}
