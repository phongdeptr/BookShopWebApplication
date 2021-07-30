<%-- 
    Document   : orderdetail
    Created on : May 27, 2021, 11:39:13 PM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@include file="css.jspf"%>
    </head>
    <body>
        <%@include file="header.jspf" %>
        <table  class="table ps-cart__table">
            <thead>
                <tr>
                    <td>
                        Name
                    </td>
                    <td>
                        Price
                    </td>
                    <td>
                        Quantity
                    </td>
                    <td>belong to Order</td>
                </tr>

            </thead>
            <tbody>
                <c:forEach var="orderDetail" items="${requestScope.ORDER_DETAIL}">
                    <tr>
                        <td>${orderDetail.itemName}</td>
                        <td>${orderDetail.price}</td>
                        <td>${orderDetail.quantity}</td>
                        <td>${sessionScope.CURRENT_ORDER}</td>
                    </tr>    
                </c:forEach>
            </tbody>
        </table>

        <%@include file="script.jspf" %>
    </body>
</html>
