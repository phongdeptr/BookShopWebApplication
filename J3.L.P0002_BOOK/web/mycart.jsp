<%-- 
    Document   : democart.jsp
    Created on : May 14, 2021, 8:58:11 PM
    Author     : ADMIN
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<html lang="en">
    <head>
        <%@include file="css.jspf" %>
        <title>Cart</title>
    </head>
    <body class="ps-loading">
        <div class="header--sidebar"></div>
        <%@include file="/header.jspf" %>
        <div class="header-services">
            <div class="ps-services owl-slider" data-owl-auto="true" data-owl-loop="true" data-owl-speed="7000" data-owl-gap="0" data-owl-nav="true" data-owl-dots="false" data-owl-item="1" data-owl-item-xs="1" data-owl-item-sm="1" data-owl-item-md="1" data-owl-item-lg="1" data-owl-duration="1000" data-owl-mousedrag="on">
                <p class="ps-service"><i class="ps-icon-delivery"></i><strong>Free delivery</strong>: Get free standard delivery on every order with Sky Store</p>
                <p class="ps-service"><i class="ps-icon-delivery"></i><strong>Free delivery</strong>: Get free standard delivery on every order with Sky Store</p>
                <p class="ps-service"><i class="ps-icon-delivery"></i><strong>Free delivery</strong>: Get free standard delivery on every order with Sky Store</p>
            </div>
        </div>
        <main class="ps-main">
            <div class="ps-content pt-80 pb-80">
                <div class="ps-container">
                    <div class="ps-cart-listing">
                        <table class="table ps-cart__table">
                            <thead>
                                <tr>
                                    <th>All Products</th>
                                    <th>Price</th>
                                    <th>Quantity</th>
                                    <th>Total</th>
                                    <th></th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:set var="mycart" value="${sessionScope.CART.cart}" />
                                <c:set var="total" value="${0}"/>
                                <c:forEach var="entry" items="${mycart}">
                                    <tr id="${entry.key.bookID}">
                                        <td><a class="ps-product__preview" href="LoadProductDetail?bookID=${entry.key.bookID}"><img src="images/${entry.key.image}.jpg" alt="">${entry.key.bookTitle}</a></td>
                                        <td>${entry.key.price}</td>
                                        <td>
                                            <div class="form-group--number">
                                                <p>Quantity: </p>
                                                <button class="minus" onclick="decreaseInput('${entry.key.bookID}')"><span>-</span></button>
                                                <input class="form-control" id="${entry.key.bookID}" oninput="updateItemCart('${entry.key.bookID}')" onchange="updateItemCart('${entry.key.bookID}')" type="number" min="1" value="${entry.value}">
                                                <button class="plus" onclick="increaseInput('${entry.key.bookID}')"><span>+</span></button>
                                            </div>
                                        </td>
                                        <td id="${entry.key.bookID}">${entry.key.price*entry.value}</td>
                                        <td>
                                            <div class="ps-remove" onclick="removeItemCart('${entry.key.bookID}')"></div>
                                        </td>
                                        ${total = total + entry.key.price*entry.value};
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                        <div class="ps-cart__actions">
                            <div class="ps-cart__promotion">
                                <div class="form-group">
                                    <div class="ps-form--icon"><i class="fa fa-angle-right"></i>
                                        <form id="promocode">
                                            <input class="form-control" name ="discountCode" type="text" placeholder="Promo Code">
                                        </form>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <a href="index.jsp"><button class="ps-btn ps-btn--gray">Continue Shopping</button><a/>
                                </div>
                            </div>
                            <div class="ps-cart__total">
                                <h3>Total Price: <span> ${total} $</span></h3><button onclick="checkout()" class="ps-btn">Process to checkout<i class="ps-icon-next"></i></button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <%@include file="footer.jspf" %>
        </main>
        <!-- JS Library-->
        <%@include file="script.jspf" %>
    </body>
</html>
