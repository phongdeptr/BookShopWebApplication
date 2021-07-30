/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import daos.Dao;
import dto.UserDTO;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
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
@WebServlet(name = "CreateDiscountCode", urlPatterns = {"/CreateDiscountCode"})
public class CreateDiscountCode extends HttpServlet {

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
        String discountCode = request.getParameter("discountCode");
        String percent = request.getParameter("percent");
        String date = request.getParameter("expire_date");
        Dao dao = new Dao();
        float discountValue;
        String result = "";
        HttpSession session = request.getSession();
        UserDTO user = (UserDTO) session.getAttribute("LOGIN_USER");
        if (user == null || !user.isIsAdmin()) {
            response.sendRedirect("login.jsp");
            return;
        }
        try {
            String error = "";
            if (!discountCode.matches("(BOOK)([0-9]{6,6})")) {
                error = error + "Discount invalid format\n";
            }
            LocalDate expireDate = LocalDate.parse(date);
            LocalDate now = LocalDate.now();
            LocalDate expireContraint = now.plusDays(30);
            if (expireDate.isBefore(LocalDate.now()) || !expireDate.isAfter(expireContraint)) {
                error = error + "Error: expire date must be greater than current Date and must greater than 30 day from create days\n";
            }
            if (error.isEmpty()) {
                discountValue = Float.parseFloat(percent);
                if (dao.createDiscount(discountCode, discountValue, Date.valueOf(date))) {
                    result = "SUCCESS";
                } else {
                    error = "Discount Code is duplicate\n";
                    result = error;
                }
            } else {
                result = error;
            }
        } catch (NumberFormatException e) {
            result = "Percent is not number";
        }
        response.getWriter().write(result);
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
