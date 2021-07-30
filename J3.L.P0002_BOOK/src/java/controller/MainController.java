/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ADMIN
 */
public class MainController extends HttpServlet {

    private static final String ADD_CART = "AddCartController";
    private static final String REMOVE_CART = "RemoveCartController";
    private static final String UDATE_CART = "UpdateCartController";
    private static final String VIEW_CART = "ViewCartController";
    private static final String ADD_NEW_BOOK = "AddController";
    private static final String UPDATE_BOOK = "UpdateController";
    private static final String DELETE_BOOK = "DeleteController";
    private static final String CHECKOUT = "CheckoutController";
    private static final String SEARCH = "SearchController";
    private static final String LOGIN = "LoginController";
    private static final String LOAD_BOOK = "LoadProductDetail";
    private static final String LOGOUT = "LogoutController";
    private static final String DEFAULT = "index.jsp";

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
        String action = request.getParameter("action");
        String url = DEFAULT;
        if (action != null) {
            if (action.equals("addcart")) {
                url = ADD_CART;
            }
            if (action.equals("removecart")) {
                url = REMOVE_CART;
            }
            if (action.equals("updatecart")) {
                url = UDATE_CART;
            }
            if (action.equals("addnewbook")) {
                url = ADD_NEW_BOOK;
            }
            if (action.equals("deletebook")) {
                url = DELETE_BOOK;
            }
            if (action.equals("updatebook")) {
                url = UPDATE_BOOK;
            }
            if (action.equals("search")) {
                url = SEARCH;
            }
            if (action.equals("viewcart")) {
                url = VIEW_CART;
            }
            if (action.equals("checkout")) {
                url = CHECKOUT;
            }
            if (action.equals("managebook")) {
                url = "ManageBook";
            }
            if (action.equals("creatediscount")) {
                url = "CreateDiscountCode";
            }
            if (action.equals("loaddetail")) {
                url = "LoadProductDetail";
            }
            if (action.equals("searchorder")) {
                url = "SearchOrderController";
            }
        }
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
