/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import daos.Dao;
import dto.BookDTO;
import dto.UserDTO;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author ADMIN
 */
public class SearchController extends HttpServlet {

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
        // response.setContentType("text/html;charset=UTF-8");
        String keyword = request.getParameter("keyword");
        String category = request.getParameter("category");
        String minPrice = request.getParameter("minprice");
        String maxPrice = request.getParameter("maxprice");
        String lastKeyword = keyword;
        String lastMinPrice = minPrice;
        String lastMaxPrice = maxPrice;
        HttpSession session = request.getSession();
        Dao dao = new Dao();
        int option = 1;
        List<BookDTO> bookList = null;
        UserDTO user = (UserDTO) session.getAttribute("LOGIN_USER");
        String url = "login.jsp";
        if (user == null) {
            url = "login.jsp";
        } else if (user.isIsAdmin()) {
            url = "admin.jsp";
        } else if (!user.isIsAdmin()) {
            url = "productlist.jsp";
            try {
                if (keyword == null || keyword.isEmpty()) {
                    keyword = "";
                }
                if (minPrice == null || maxPrice == null) {
                    maxPrice = minPrice = "";
                }
                if (category == null) {
                    category = "";
                }
                if (category.isEmpty() && minPrice.isEmpty() && maxPrice.isEmpty()) {
                    option = 1;
                    bookList = dao.findBooks(keyword, 0, 0, null, option);
                } else if (!category.isEmpty() && minPrice.isEmpty() && maxPrice.isEmpty()) {
                    option = 2;
                    bookList = dao.findBooks(keyword, 0, 0, category, option);
                } else if (category.isEmpty() && !minPrice.isEmpty() && !maxPrice.isEmpty()) {
                    option = 3;
                    bookList = dao.findBooks(keyword, Float.parseFloat(minPrice), Float.parseFloat(maxPrice), category, option);
                } else if (!category.isEmpty() && !minPrice.isEmpty() && !maxPrice.isEmpty()) {
                    option = 4;
                    bookList = dao.findBooks(keyword, Float.parseFloat(minPrice), Float.parseFloat(maxPrice), category, option);
                }
                session.setAttribute("LAST_SEARCH", lastKeyword);
                session.setAttribute("LAST_MIN_PRICE", lastMinPrice);
                session.setAttribute("LAST_MAX_PRICE", lastMaxPrice);
                request.setAttribute("SEARCH_BOOK_LIST", bookList);
                request.getRequestDispatcher(url).forward(request, response);
            } catch (NumberFormatException e) {
            }
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
