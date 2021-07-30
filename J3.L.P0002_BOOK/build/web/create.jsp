<%-- 
    Document   : create
    Created on : May 20, 2021, 8:50:38 PM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto|Varela+Round">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
        <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
        <style>
            .modal .modal-content {
                border-radius: 3px;
                font-size: 14px;
            }
            body {
                color: #566787;
                background: #f5f5f5;
                font-family: 'Varela Round', sans-serif;
                font-size: 13px;
            }
        </style>
    </head>
    <body>
        <form action="MainController" method="POST" enctype="multipart/form-data" id="addForm">
            <div class="modal-header">						
                <h4 class="modal-title">Add Book</h4>
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            </div>
            <div class="modal-body">					
                <div class="form-group">
                    <label>ISBN number</label>
                    <input type="text" name="ISBN" class="form-control" placeholder="ISBN is 10 - 13 digits" required>
                    <p id="ISBN-error">${requestScope.CREATE_ERROR.ISBN_ERROR}</p>
                </div>
                <div class="form-group">
                    <label>Title</label>
                    <input type="text" class="form-control" name ="bookTitle" required>
                    <p id="title-error">${requestScope.CREATE_ERROR.titleErr}</p>
                </div>
                <div class="form-group">
                    <label>Author</label>
                    <input type="text" class="form-control" name="author" required>
                    <p id="author-error">${requestScope.CREATE_ERROR.authorErr}</p>

                </div>
                <div class="form-group">
                    <label>Price</label>
                    <input type="number" min="0" class="form-control" name="price" required>
                    <p id="price-error">${requestScope.CREATE_ERROR.priceErr}</p>
                </div>
                <div class="form-group">
                    <label>Quantity</label>
                    <input type="number" name="quantity" min="1" class="form-control" required>
                    <p id="quantity-error">${requestScope.CREATE_ERROR.quantityErr}</p>
                </div>					
                <div class = "form-group">
                    <label>Category</label>
                    <select class = "form-control ps-select" name="category">

                    </select>
                </div>
                <div class = "form-group">
                    <label>Image</label>
                    <p id = "category-error"></p>
                    <input class = "form-control" name="file" type="file">.
                </div>
                <input class = "form-control" name="action" type="hidden" value="addnewbook">.
            </div>
            <div class="modal-footer">
                <input type="button" class="btn btn-default" data-dismiss="modal" value="Cancel">
                <input type="submit" class="btn btn-success">
            </div>
        </form>
        <script type="text/javascript" src="js/myscript.js" ></script>
    </body>
</html>
