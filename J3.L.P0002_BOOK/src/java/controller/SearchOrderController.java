/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import daos.Dao;
import dto.OrderDTO;
import dto.UserDTO;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Date;
import javax.servlet.http.HttpSession;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "SearchOrderController", urlPatterns = {"/SearchOrderController"})
public class SearchOrderController extends HttpServlet {

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
        String title = request.getParameter("title");
        String createDate = request.getParameter("createDate");
        HttpSession session = request.getSession();
        UserDTO user = (UserDTO) session.getAttribute("LOGIN_USER");
        Dao dao = null;
        List<OrderDTO> findOrder = null;
        if(user == null || user.isIsAdmin()){
            response.sendRedirect("login.jsp");
            return;
        }
        if (title == null) {
            title = "";
        }
        if (createDate == null) {
            createDate = "";
        }
        if (!title.isEmpty() && createDate.isEmpty()) {
            dao = new Dao();
            findOrder = dao.findOrder(title, null);

        } else if (title.isEmpty() && !createDate.isEmpty()) {
            try {
                java.util.Date date = new SimpleDateFormat("yyyy-MM-dd").parse(createDate);
                dao = new Dao();
                Date actualDate = new java.sql.Date(date.getTime());
                findOrder = dao.findOrder(title, actualDate);
            } catch (ParseException ex) {
                Logger.getLogger(SearchOrderController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (!title.isEmpty() && !createDate.isEmpty()) {
            try {
                java.util.Date date = new SimpleDateFormat("yyyy-MM-dd").parse(createDate);
                dao = new Dao();
                Date actualDate = new java.sql.Date(date.getTime());
                findOrder = dao.findOrder(title, actualDate);
            } catch (ParseException ex) {
                Logger.getLogger(SearchOrderController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        request.setAttribute("ORDER_HISTORY", findOrder);
        session.setAttribute("LAST_ORDER_SEARCH", title);
        try {
            session.setAttribute("LAST_ORDER_DATE", new SimpleDateFormat("yyyy-MM-dd").parse(createDate));
        } catch (ParseException ex) {
            Logger.getLogger(SearchOrderController.class.getName()).log(Level.SEVERE, null, ex);
        }
        request.setAttribute("ERROR", "Error is empty");
        request.getRequestDispatcher("orderhistory.jsp").forward(request, response);
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
