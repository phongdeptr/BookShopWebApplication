/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import daos.Dao;
import dto.ErrorDTO;
import dto.UserDTO;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

/**
 *
 * @author ADMIN
 */
public class AddController extends HttpServlet {

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
        String ISBN = request.getParameter("ISBN");
        String title = request.getParameter("bookTitle");
        String author = request.getParameter("author");
        String price = request.getParameter("price");
        String quantity = request.getParameter("quantity");
        String category = request.getParameter("category");
        String quantityErr = null;
        String categoryErr = null;
        String priceErr = null;
        String authorErr = null;
        String ISBN_Err = null;
        String titleErr = null;
        String result = "";
        ErrorDTO err = null;
        Dao dao = new Dao();
        HttpSession session = request.getSession();
        ServletContext addControllerContext = getServletContext();
        String contextPath = addControllerContext.getInitParameter("upload.Location");
        Part image = request.getPart("file");
        boolean error = false;
        String fileName = Paths.get(image.getSubmittedFileName()).getFileName().toString();
        if (fileName.contains(".jpg") || fileName.contains(".png") || fileName.contains("gif") || fileName.contains(".bmp") || fileName.contains(".jpe")) {
            error = false;
        }
        InputStream fileContent = image.getInputStream();
        String storedFileName = contextPath + "\\" + ISBN + ".jpg";
        File f = new File(contextPath + "\\" + ISBN + ".jpg");
        String currPath = f.getAbsolutePath();
        FileOutputStream fos = new FileOutputStream(f);
        byte[] b = new byte[fileContent.available()];
        fileContent.read(b);
        fos.write(b);
        UserDTO user = (UserDTO) session.getAttribute("LOGIN_USER");
        if (user != null && !user.isIsAdmin()) {
            response.sendRedirect("login.jsp");
        }
        if (price == null) {
            priceErr = "Price is empty";
            error = true;
        }
        if (quantity == null) {
            error = true;
            quantityErr = "Quantity must be number and price > 0";
        }
        if (quantity != null
                && !quantity.matches("[0-9]{1,}")) {
            error = true;
            quantityErr = "quantity must be number and quantity > 0";
        }
        if (category == null) {
            error = true;
            categoryErr = "category not fill";
        }
        if (author == null) {
            error = true;
            authorErr = "Author is not fill";
        }
        if (title == null) {
            error = true;
            authorErr = "Author is not fill";

        }
        if (ISBN == null) {
            error = true;
            ISBN_Err = "Author is not fill";
        }

        if (price != null && !price.matches("([0-9]{1,})([,.]{0,1})([0-9]{0,})")) {
            error = true;
            priceErr = "Price must be number and price > 0";
        }

        if (ISBN != null && !ISBN.matches("([0-9]{10,13})") && (ISBN.length() < 10 || ISBN.length() > 13)) {
            error = true;
            ISBN_Err = "ISBN is not valid format or is use by another book";
        } else {
            if (dao.checkDuplicate(ISBN)) {
                error = true;
                ISBN_Err = "Duplicate ISBN for book";
            }
        }

        if (error) {
            err = new ErrorDTO(titleErr, authorErr, priceErr, quantityErr, ISBN_Err, categoryErr);
            request.setAttribute("CREATE_ERROR", err);
            request.getRequestDispatcher("create.jsp").forward(request, response);
        } else {
            result = dao.createNewBook(ISBN, title, author, Float.parseFloat(price), Integer.parseInt(quantity), ISBN + ".jpg", category);
            if (result.equals("SUCCESS")) {
                request.getRequestDispatcher("admin.jsp").forward(request, response);
            } else {
                request.getRequestDispatcher("create.jsp").forward(request, response);
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
