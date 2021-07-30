/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import daos.Dao;
import dto.BookDTO;
import dto.ErrorDTO;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author ADMIN
 */
public class UpdateController extends HttpServlet {

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
        try {
            String bookID = request.getParameter("bookID");
            String ISBN = request.getParameter("ISBN");
            String title = request.getParameter("title");
            String author = request.getParameter("author");
            String price = request.getParameter("price");
            String quantity = request.getParameter("quantity");
            String importDate = request.getParameter("importDate");
            String description = request.getParameter("description");
            String category = request.getParameter("category");
            String imageName = request.getParameter("imageName");
            String priceErr = null;
            String quantityErr = null;
            String titleError = null;
            String ISBN_Err = null;
            String authorErr = null;
            String catgoryErr = null;
            Dao dao = new Dao();
            HttpSession session = request.getSession();
            RequestDispatcher dispatcher = request.getRequestDispatcher("update.jsp");
            boolean isErr = false;
            if (null == price || !price.matches("([0-9]{1,})([,.]{0,1})([0-9]{1,})")) {
                isErr = true;
                priceErr = "price is not number";
            }
            if ("[^0-9]".matches(quantity)) {
                isErr = true;
                quantityErr = "quantity is not number";
            }
            if ("[^0-9]".matches(category)) {
                isErr = true;
                catgoryErr = "quantity is not number";
            }
            if (null == ISBN || importDate == null) {
                isErr = true;
                ISBN_Err = "ISBN is empty";
            }
            if (title == null) {
                titleError = "title is empty";
                isErr = true;
            }
            if (author == null) {
                isErr = true;
                authorErr = "Author is empty";
            }
            if (ISBN != null) {
                if (ISBN.length() < 10 || ISBN.length() > 13) {
                    if (ISBN.matches("([a-z]{1,})")) {
                        ISBN_Err = "ISBN only contain number digit only";
                    }
                }
            }
            try {
                dao.updateBook(bookID, ISBN, title, Float.parseFloat(price),
                        Integer.parseInt(quantity), author, category, importDate);
                if (session.getAttribute("UPDATE_ERROR") != null) {
                    session.setAttribute("UPDATE_ERROR", null);
                }
                if (session.getAttribute("UPDATE_BOOK") != null) {
                    session.setAttribute("UPDATE_BOOK", null);
                }
            } catch (NumberFormatException | SQLException e) {
                ISBN_Err = "ISBN " + ISBN + " is use by another book";
                isErr = true;
            }
            if (isErr) {
                ErrorDTO err = new ErrorDTO(titleError, authorErr, priceErr, quantityErr, ISBN_Err, catgoryErr);
                BookDTO targetBook = dao.getBookInformation(bookID);
                System.out.println("BOOKID : " + bookID);
                System.out.println(targetBook.toString());
                session.setAttribute("UPDATE_ERROR", err);
                session.setAttribute("UPDATE_BOOK", targetBook);
                response.getWriter().write("fail");
            } else {
                request.setAttribute("SEARCH_BOOK_LIST", dao.getAllBook());
                response.getWriter().write("success");
            }
        } catch (NumberFormatException e) {

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
