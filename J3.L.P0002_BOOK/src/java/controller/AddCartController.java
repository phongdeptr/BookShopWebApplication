/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import daos.Dao;
import dto.BookDTO;
import dto.CartDTO;
import dto.UserDTO;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author ADMIN
 */
public class AddCartController extends HttpServlet {

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
        Dao dao = new Dao();
        BookDTO item = null;
        String bookID = request.getParameter("bookID");
        String quantity = request.getParameter("quantity");
        HttpSession session = request.getSession();
        CartDTO dto = (CartDTO) session.getAttribute("CART");
        UserDTO user = (UserDTO) session.getAttribute("LOGIN_USER");
        String result = "";
        HashMap<BookDTO, Integer> cart = null;
        try {

            if (user == null || user.isIsAdmin()) {
                response.sendRedirect("login.jsp");
                return;
            }
                    item = dao.getBookInformation(bookID);
                    if (item != null && item.isStatus() == true) {
                        System.out.println(item);
                        System.out.println(bookID);
                        if (dto == null) {
                            dto = new CartDTO();
                            cart = new HashMap<>();
                            cart.put(item, Integer.parseInt(quantity));
                            dto.setCart(cart);
                        } else {
                            cart = dto.getCart();
                            if (cart == null) {
                                cart = new HashMap<>();
                            }
                            if (cart.containsKey(item)) {
                                Integer quan = cart.get(item);
                                quan = quan + Integer.parseInt(quantity);
                                cart.replace(item, quan);
                            } else {
                                cart.put(item, 1);
                            }
                            dto.setCart(cart);
                        }
                        session.setAttribute("CART", dto);
                        result = "SUCCESS";
                    } else {
                        result = "Item is not exist";
                    }
        } catch (NumberFormatException e) {
            result = "quantity is not number";
        } finally {
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
