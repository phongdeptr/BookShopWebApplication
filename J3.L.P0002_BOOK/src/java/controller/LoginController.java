/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import daos.Dao;
import dto.BookDTO;
import dto.CategoryDTO;
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
public class LoginController extends HttpServlet {

    private static final String ADMIN = "admin.jsp";
    private static final String NON_ADMIN = "index.jsp";
    private static final String UNAUTHORIZE = "login.jsp";

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

        String url = "login.jsp";
        Dao dao = new Dao();
        String userID = request.getParameter("username");
        String password = request.getParameter("password");
        HttpSession session = request.getSession();
        try {
            if (userID == null || userID.isEmpty()) {
                url = "login.jsp";
            } else if (password == null || password.isEmpty()) {
                url = "login.jsp";
            } else {
                UserDTO checkLogin = dao.checkLogin(userID, password);
                if (checkLogin != null && checkLogin.isIsAdmin()) {
                    url = "admin.jsp";
                    session.setAttribute("LOGIN_USER", checkLogin);
                } else if (checkLogin != null && !checkLogin.isIsAdmin()) {
                    url = "index.jsp";
                    session.setAttribute("LOGIN_USER", checkLogin);
                } else {
                    url = "login.jsp";
                }

            }
        } catch (Exception e) {
        }
        response.sendRedirect(url);
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
