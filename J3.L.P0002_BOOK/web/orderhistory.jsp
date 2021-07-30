<%-- 
    Document   : orderhistory
    Created on : May 27, 2021, 11:26:44 PM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@include file="css.jspf" %>
    </head>
    <body>
        <%@include file="header.jspf" %>
        <div class="col-lg-3">          
            <h3 style="margin-left: auto; margin-right: auto; width: 50%">Order history</h3>
        </div>
        <div class="col-lg-9">
            <form action="MainController" method="GET">
                <div class="form-group row" style="margin-left: 20px; margin-right: 50px; width: 50%">
                    <label for="title_fields" class="col-sm-2 col-form-label">Title</label>
                    <div class="col-sm-10">
                        <input type="text" name="title" value="${sessionScope.LAST_ORDER_SEARCH}"  class="form-control" id="title_fields">
                    </div>
                </div>
                <div class="form-group row" style="margin-left: 20px; margin-right: 50px; width: 50%">
                    <label for="createDate" class="col-sm-2 col-form-label">Create Date</label>
                    <div class="col-sm-10">
                        <input type="date" value="${sessionScope.LAST_ORDER_DATE}" class="form-control" name="createDate" id="createDate">
                    </div>
                </div>
                <input type="hidden" name="action" value="searchorder" class="form-control" id="createDate">
                <div class ="form-group row">
                    <button style="margin-left: 147px; width: 140px" type="submit"class="btn btn-primary">Search Order</button>
                </div>
            </form>
        </div>    
        <table  class="table ps-cart__table">
            <thead>
                <tr>
                    <td>
                        order ID
                    </td>
                    <td>
                        Checkout Date
                    </td>
                    <td>
                        total(After discount)
                    </td>
                </tr>

            </thead>
            <tbody>
                <c:forEach var="order" items="${requestScope.ORDER_HISTORY}">
                    <tr>
                        <th>${order.orderID}</th>
                        <th>${order.createDate}</th>
                        <th>${order.total}</th>
                        <th><a href="ViewOrderDetail?orderID=${order.orderID}">View Detail</a></th>
                    </tr>    
                </c:forEach>
            </tbody>
        </table>
    </ul>
    <%@include file="script.jspf"%>
</body>
</html>
