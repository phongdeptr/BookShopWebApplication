/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import daos.Dao;
import dto.UserDTO;
import google.GooglePOJO;
import google.GoogleUtils;
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
public class LoginGoogleServlet extends HttpServlet {

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
        String code = request.getParameter("code");
        String url = UNAUTHORIZE;
        Dao dao = new Dao();
        try {
            if (code == null || code.isEmpty()) {
                request.getRequestDispatcher(url).forward(request, response);
            } else {
                String accessToken = GoogleUtils.getToken(code);
                GooglePOJO gpojo = GoogleUtils.getUserInfo(accessToken);
                System.out.println("ID1: " + gpojo.getId() + "name: " + gpojo.getName());
                HttpSession session = request.getSession();
                UserDTO dto = dao.checkLoginByGoogle(gpojo.getId(), gpojo.getEmail());
                System.out.println(dto);
                session.setAttribute("LOGIN_USER", dto);
                if (dto != null) {
                    if (dto.isIsAdmin()) {
                        url = ADMIN;
                        // request.getRequestDispatcher(url).forward(request, response);
                    } else {
                        url = NON_ADMIN;
                        List<String> loadDiscountCode = dao.loadDiscountCode();
                        session.setAttribute("DISCOUNT_LIST", loadDiscountCode);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error " + e.getCause().toString());
        }
        System.out.println(url);
        request.getRequestDispatcher(url).forward(request, response);
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
