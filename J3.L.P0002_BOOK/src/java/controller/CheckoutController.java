/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import daos.Dao;
import dto.BookDTO;
import dto.CartDTO;
import dto.OrderDTO;
import dto.UserDTO;
import java.io.IOException;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "CheckoutController", urlPatterns = {"/CheckoutController"})
public class CheckoutController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        String discountCode = request.getParameter("discountCode");
        CartDTO cartDTO = (CartDTO) session.getAttribute("CART");
        UserDTO user = (UserDTO) session.getAttribute("LOGIN_USER");
        HashMap<BookDTO, Integer> cart = null;
        Dao dao = new Dao();
        float total = 0;
        float discountPercent = 0;
        LocalTime checkoutCurr = LocalTime.now();
        String result = "ERROR";
        if (user == null || user.isIsAdmin()) {
            response.sendRedirect("login.jsp");
            return;
        }
        if (cartDTO != null) {
            cart = cartDTO.getCart();
            if (cart != null) {
                if (dao.precheckout(cartDTO) != null) {
                    result = "Cart has item is out of stock";
                } else {
                    for (Map.Entry<BookDTO, Integer> entry : cart.entrySet()) {
                        BookDTO key = entry.getKey();
                        BookDTO targetItem = dao.getBookInformation(key.getBookID());
                        total = total + targetItem.getPrice() * cart.get(targetItem);
                    }
                    if (discountCode == null || discountCode.isEmpty()) {
                        discountCode = "";
                        dao.createOrder(cartDTO, total, user.getUserID(), discountCode);
                        session.removeAttribute("CART");
                        result = "SUCCESS";
                    } else {
                        discountPercent = dao.getDiscount(discountCode);
                        if (discountPercent <= 0 || dao.checkDiscountUsage(discountCode, user.getUserID())) {
                            result = "Discount code is used by another order or not exist";
                        } else {
                            if (!dao.checkDiscountAvailable(discountCode, checkoutCurr)) {
                                result = "Discount is expired";
                            } else {
                                total = total - total * (discountPercent / 100);
                                dao.createOrder(cartDTO, total, user.getUserID(), discountCode);
                                session.removeAttribute("CART");
                                result = "SUCCESS";
                            }
                        }
                    }

                }

            } else {
                result = "Cart has nothing to check out";
            }
            List<OrderDTO> ordersHistory = dao.getOrdersHistory(user.getUserID());
            session.setAttribute("ORDER_HISTORY", ordersHistory);
            response.getWriter().write(result);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
