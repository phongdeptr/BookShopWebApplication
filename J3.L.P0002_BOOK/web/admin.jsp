<%-- 
    Document   : admin
    Created on : May 20, 2021, 5:24:56 PM
    Author     : ADMIN
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <title>Bootstrap CRUD Data Table for Database with Modal Form</title>
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto|Varela+Round">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
        <%@include file="css.jspf"%>
        <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
        <style>
            body {
                color: #566787;
                background: #f5f5f5;
                font-family: 'Varela Round', sans-serif;
                font-size: 13px;
            }
            .table-responsive {
                margin: 30px 0;
            }
            .table-wrapper {
                background: #fff;
                padding: 20px 25px;
                border-radius: 3px;
                min-width: 1000px;
                box-shadow: 0 1px 1px rgba(0,0,0,.05);
            }
            .table-title {        
                padding-bottom: 15px;
                background: #435d7d;
                color: #fff;
                padding: 16px 30px;
                min-width: 100%;
                margin: -20px -25px 10px;
                border-radius: 3px 3px 0 0;
            }
            .table-title h2 {
                margin: 5px 0 0;
                font-size: 24px;
            }
            .table-title .btn-group {
                float: right;
            }
            .table-title .btn {
                color: #fff;
                float: right;
                font-size: 13px;
                border: none;
                min-width: 50px;
                border-radius: 2px;
                border: none;
                outline: none !important;
                margin-left: 10px;
            }
            .table-title .btn i {
                float: left;
                font-size: 21px;
                margin-right: 5px;
            }
            .table-title .btn span {
                float: left;
                margin-top: 2px;
            }
            table.table tr th, table.table tr td {
                border-color: #e9e9e9;
                padding: 12px 15px;
                vertical-align: middle;
            }
            table.table tr th:first-child {
                width: 60px;
            }
            table.table tr th:last-child {
                width: 100px;
            }
            table.table-striped tbody tr:nth-of-type(odd) {
                background-color: #fcfcfc;
            }
            table.table-striped.table-hover tbody tr:hover {
                background: #f5f5f5;
            }
            table.table th i {
                font-size: 13px;
                margin: 0 5px;
                cursor: pointer;
            }	
            table.table td:last-child i {
                opacity: 0.9;
                font-size: 22px;
                margin: 0 5px;
            }
            table.table td a {
                font-weight: bold;
                color: #566787;
                display: inline-block;
                text-decoration: none;
                outline: none !important;
            }
            table.table td a:hover {
                color: #2196F3;
            }
            table.table td a.edit {
                color: #FFC107;
            }
            table.table td a.delete {
                color: #F44336;
            }
            table.table td i {
                font-size: 19px;
            }
            table.table .avatar {
                border-radius: 50%;
                vertical-align: middle;
                margin-right: 10px;
            }
            .pagination {
                float: right;
                margin: 0 0 5px;
            }
            .pagination li a {
                border: none;
                font-size: 13px;
                min-width: 30px;
                min-height: 30px;
                color: #999;
                margin: 0 2px;
                line-height: 30px;
                border-radius: 2px !important;
                text-align: center;
                padding: 0 6px;
            }
            .pagination li a:hover {
                color: #666;
            }	
            .pagination li.active a, .pagination li.active a.page-link {
                background: #03A9F4;
            }
            .pagination li.active a:hover {        
                background: #0397d6;
            }
            .pagination li.disabled i {
                color: #ccc;
            }
            .pagination li i {
                font-size: 16px;
                padding-top: 6px
            }
            .hint-text {
                float: left;
                margin-top: 10px;
                font-size: 13px;
            }    
            /* Custom checkbox */
            .custom-checkbox {
                position: relative;
            }
            .custom-checkbox input[type="checkbox"] {    
                opacity: 0;
                position: absolute;
                margin: 5px 0 0 3px;
                z-index: 9;
            }
            .custom-checkbox label:before{
                width: 18px;
                height: 18px;
            }
            .custom-checkbox label:before {
                content: '';
                margin-right: 10px;
                display: inline-block;
                vertical-align: text-top;
                background: white;
                border: 1px solid #bbb;
                border-radius: 2px;
                box-sizing: border-box;
                z-index: 2;
            }
            .custom-checkbox input[type="checkbox"]:checked + label:after {
                content: '';
                position: absolute;
                left: 6px;
                top: 3px;
                width: 6px;
                height: 11px;
                border: solid #000;
                border-width: 0 3px 3px 0;
                transform: inherit;
                z-index: 3;
                transform: rotateZ(45deg);
            }
            .custom-checkbox input[type="checkbox"]:checked + label:before {
                border-color: #03A9F4;
                background: #03A9F4;
            }
            .custom-checkbox input[type="checkbox"]:checked + label:after {
                border-color: #fff;
            }
            .custom-checkbox input[type="checkbox"]:disabled + label:before {
                color: #b8b8b8;
                cursor: auto;
                box-shadow: none;
                background: #ddd;
            }
            /* Modal styles */
            .modal .modal-dialog {
                max-width: 400px;
            }
            .modal .modal-header, .modal .modal-body, .modal .modal-footer {
                padding: 20px 30px;
            }
            .modal .modal-content {
                border-radius: 3px;
                font-size: 14px;
            }
            .modal .modal-footer {
                background: #ecf0f1;
                border-radius: 0 0 3px 3px;
            }
            .modal .modal-title {
                display: inline-block;
            }
            .modal .form-control {
                border-radius: 2px;
                box-shadow: none;
                border-color: #dddddd;
            }
            .modal textarea.form-control {
                resize: vertical;
            }
            .modal .btn {
                border-radius: 2px;
                min-width: 100px;
            }	
            .modal form label {
                font-weight: normal;
            }	
        </style>
    </head>
    <body>
        <c:if test="${sessionScope.LOGIN_USER == null}">
            <c:redirect url="login.jsp"></c:redirect>
        </c:if>
        <c:if test="${sessionScope.LOGIN_USER != null && sessionScope.LOGIN_USER.isAdmin == false}">
            <c:redirect url="home.jsp"></c:redirect>
        </c:if>
        <div class="container-xl">
            <div class="table-responsive">
                <div class="table-wrapper">
                    <div class="table-title">
                        <div class="row">
                            <div class="col-sm-6">
                                <h2>Manage <b>Book</b></h2>
                                <c:if test="${sessionScope.LOGIN_USER != null && sessionScope.LOGIN_USER.isAdmin == true}">
                                    <p>Welcome Admin</p>
                                </c:if>
                            </div>
                            <div class="col-sm-7" style="float: right; position: relative; right: 12px;">
                                <a href="#searchModal" class="btn btn-success" data-toggle="modal">Search</a>
                                <a href="#addDiscountModal" class="btn btn-success" data-toggle="modal"><i class="material-icons">&#xE147;</i><span>Add discount</span></a>
                                <a href="MainController?action=managebook" class="btn btn-success">Show All Book</a>
                                <a href="LogoutController" class="btn btn-success">Logout</a>
                                <a href="#addBookModal" class="btn btn-success" data-toggle="modal"><i class="material-icons">&#xE147;</i> <span>Add New Book</span></a>						
                            </div>
                        </div>
                    </div>
                    <div style="overflow-x:auto;">
                        <table class="table table-striped table-hover">
                            <thead>
                                <tr>
                                    <th>ISBN number</th>
                                    <th>Title</th>
                                    <th>Author</th>
                                    <th>Category</th>
                                    <th>Price</th>
                                    <th>Quantity</th>
                                    <th>Import Date</th>
                                    <th>Description</th>
                                    <th>Image name</th>
                                    <th>status</th>
                                    <th>
                                        Update
                                    </th>
                                    <th>Delete</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="book" items="${requestScope.SEARCH_BOOK_LIST}">
                                    <c:set var="id" value="${book.bookID}"/>
                                    <tr>
                                <form id="${id}">
                                    <input type="hidden" value = "${book.bookID}" name= "bookID"/>
                                    <input type="hidden" value = "updatebook" name= "action"/>
                                    <td><div class="col-xs-2"><input class="form-group" value = "${book.ISBN}" name="ISBN"/></div></td>
                                    <td><div class="col-xs-2"><input class="form-group" value = "${book.bookTitle}" name="title"/></div></td>
                                    <td><div class="col-xs-2"><input class="form-group" value ="${book.author}" name="author"/></div></td>
                                    <td><div class="col-xs-2"><input class="form-group" value ="${book.category}" name="category"></div></td>
                                    <td><div class="col-xs-1"><input class="form-group" type="number" value="${book.price}" name="price"/></div></td>
                                    <td><div class="col-xs-1"><input class="form-group" type="number" value="${book.quantity}" name="quantity"/></div></td>
                                    <td><div class="col-xs-2"><input class="form-group" type="date" value ="${book.importDate}" name="importDate"/></div></td>
                                    <td><div class="col-xs-2"><textarea type="text" value ="${book.description}" name="description"></textarea></div></td>
                                    <td><div class="col-xs-2">${book.image}<div></div></td>    
                                    <td>
                                        <c:if test="${book.status}">
                                            Active
                                        </c:if>
                                        <c:if test="${!book.status}">
                                            Inactive
                                        </c:if>
                                    </td>
                                </form>
                                <td>
                                    <button type="button" onclick="UpdateBook('${id}')">
                                        <i class="material-icons" title="Edit">&#xE254;</i>
                                    </button> 
                                </td>
                                <td>
                                    <button type="button" href="#deleteEmployeeModal" onclick="getDeleteID('${id}')" class="delete" data-toggle="modal">
                                        <i class="material-icons" data-toggle="tooltip" title="Delete">&#xE872;</i>
                                    </button>
                                </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!--Add Modal-->
    <!-- Edit Modal HTML -->
    <div id="addBookModal" class="modal fade">
        <div class="modal-dialog">
            <div class="modal-content">
                <form id="addform" method="POST" action="AddController" enctype="multipart/form-data">
                    <div class="modal-header">						
                        <h4 class="modal-title">Add Book</h4>
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    </div>
                    <div class="modal-body">					
                        <div class="form-group">
                            <label>ISBN number</label>
                            <p id = "ISBN-error"></p>
                            <input type="text" name="ISBN" class="form-control" required>
                        </div>
                        <div class="form-group">
                            <label>Title</label>
                            <p id = "title-error"></p>
                            <input type="text" class="form-control" name ="bookTitle" required>
                        </div>
                        <div class="form-group">
                            <label>Author</label>
                            <p id = "author-error"></p>
                            <input type="text" class="form-control" name="author" required></textarea>
                        </div>
                        <div class="form-group">
                            <label>Price</label>
                            <p id = "price-error"></p>
                            <input type="number" min="1" class="form-control" name="price" required></textarea>
                        </div>
                        <div class="form-group">
                            <label>Quantity</label>
                            <p id = "quantity-error"></p>
                            <input type="number" name="quantity" min="0" class="form-control" required>
                        </div>					
                        <div class = "form-group">
                            <label>Category</label>
                            <p id = "category-error"></p>
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
                        <button type="submit" class="btn btn-success" form="addform">Add New</button>
                </form>
            </div>
        </div>
    </div>
</div>
<c:if test="${requestScope.DELETE_ERROR !=null}">
    <div id="deleteEmployeeModal" class="modal fade" hidden="false">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">						
                    <h4 class="modal-title">Delete BOOK</h4>
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                </div>
                <div class="modal-body">	
                    <p>Are you sure you want to delete this BOOK?</p>

                    <p>Delete Result = ${requestScope.DELETE_ERROR}</p>
                </div>
                <div class="modal-footer">
                    <input type="button" class="btn btn-default" data-dismiss="modal" value="Close">
                </div>
            </div>
        </div>
    </div>
</c:if>


<div id="addDiscountModal" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">						
                <h4 class="modal-title">Add Book Discount</h4>
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            </div>
            <div class="modal-body">
                <form id="adddiscount" method="GET">
                    <div class="form-group">
                        <label>Discount Code</label>
                        <p id = "ISBN-error"></p>
                        <input type="text" name="discountCode" class="form-control" required>
                    </div>
                    <div class="form-group">
                        <label>Percent</label>
                        <p id = "title-error"></p>
                        <input type="number" min="10" max="100" class="form-control" name ="percent" required>
                    </div>
                    <div class="form-group">
                        <label>Date</label>
                        <p id = "author-error"></p>
                        <input type="date" value="2021-09-29" class="form-control" name="expire_date" required>
                    </div>
                    <input class = "form-control" name="action" type="hidden" value="creatediscount">.
                </form>

            </div>
            <div class="modal-footer">
                <input type="button" class="btn btn-default" data-dismiss="modal" value="Cancel">
                <button type="button" onclick="createDiscount()" class="btn btn-success">Add New</button>
            </div>
        </div>
    </div>
</div>

<!-- Delete Modal HTML -->
<div id="deleteEmployeeModal" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">						
                <h4 class="modal-title">Delete BOOK</h4>
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            </div>
            <div class="modal-body">	
                <p>Are you sure you want to delete this BOOK?</p>
                <form id="deleteform">
                    <input type="hidden" name="action" value="deletebook"/>
                    <input type="hidden" name= "deteleID" value="1" class="delete-input"/>
                </form>
                <p class="text-warning"><small>This action cannot be undone.</small></p>
            </div>
            <div class="modal-footer">
                <button type="button" onclick="deleteBook()" class="btn btn-danger" value="Delete">Delete</button>
                <input type="button" class="btn btn-default" data-dismiss="modal" value="Cancel">
            </div>
        </div>
    </div>
</div>
<div id="searchModal" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">						
                <h4 class="modal-title">Search BOOK</h4>
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            </div>
            <div class="modal-body">	
                <form action="SearchController" method="get">      
                    <div class="form-group">
                        <input class="form-control" type="text" placeholder="Search Product…" value="${requestScope.LAST_KEYWORD}" name="keyword">      
                    </div>
                    <div class="form-group">
                        <h3>Category</h3>
                        <select name="category" class="ps-select form-control" tabindex="-98">
                        </select>
                    </div>
                    <h3>Price</h3>
                    <div class="form-group">
                        <p>From Price: <span id="rangeval-min"></span></p>
                        <input class="my_min_price" type="range" name="minprice" min="1" step="1" max="1000"  oninput="setPriceRange()" value="${requestScope.LAST_MIN_PRICE}"/>
                        <p>To Price: <span id="rangeval-max"></span></p>
                        <input class="my_max_price" type="range" name="maxprice" min="10" step="1" max="1000" oninput="$('#rangeval-max').html($(this).val())" onclick="setPriceRange()"value="${requestScope.LAST_MAX_PRICE}"/>
                    </div>
                    <input type="submit" class="btn-search" value="SEARCH"><
                </form>
            </div>

            </form>
        </div>
    </div>
</div>
<%@include file="script.jspf" %>
</body>
</html>
