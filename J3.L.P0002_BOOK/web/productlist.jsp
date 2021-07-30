<%-- 
    Document   : login
    Created on : May 14, 2021, 9:23:04 PM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<!DOCTYPE html>
<!--[if IE 7]><html class="ie ie7"><![endif]-->
<!--[if IE 8]><html class="ie ie8"><![endif]-->
<!--[if IE 9]><html class="ie ie9"><![endif]-->
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="format-detection" content="telephone=no">
        <meta name="apple-mobile-web-app-capable" content="yes">
        <link href="apple-touch-icon.png" rel="apple-touch-icon">
        <link href="favicon.png" rel="icon">
        <meta name="author" content="Nghia Minh Luong">
        <meta name="keywords" content="Default Description">
        <meta name="description" content="Default keyword">
        <title>Sky - Product Listing 2</title>
        <%@include file="css.jspf"%>
    </head>
    <!--[if IE 7]><body class="ie7 lt-ie8 lt-ie9 lt-ie10"><![endif]-->
    <!--[if IE 8]><body class="ie8 lt-ie9 lt-ie10"><![endif]-->
    <!--[if IE 9]><body class="ie9 lt-ie10"><![endif]-->
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
            <div class="ps-products-wrap inverse pt-80 pb-80">
                <div class="ps-products" data-mh="product-listing">
                    <div class="ps-product-action">
                    </div>
                    <div class="ps-product__columns">
                        <c:forEach var="book" items="${requestScope.SEARCH_BOOK_LIST}">
                            <div class="ps-product__column">
                                <div class="ps-shoe mb-30">
                                    <div class="ps-shoe__thumbnail">
                                        <img width="300" height="300" src="images/${book.image}" alt=""><a class="ps-shoe__overlay" href="LoadProductDetail?bookID=${book.bookID}"></a>
                                    </div>
                                    <div class="ps-shoe__content">
                                        <div class="ps-shoe__detail">
                                            <p>Title: <a class="ps-shoe__name" href="MainController?action=loaddetail&bookID=${book.bookID}">${book.bookTitle}</a></p>
                                            <p class="ps-shoe__categories">Category: <a href="SearchController?category=${book.category}">${book.category}</a></p></br>
                                            <span>Price: ${book.price} </span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </div>
                <div class="ps-sidebar" data-mh="product-listing">
                    <form action="SearchController" method="get" id="myform">              
                        <aside class="ps-widget--sidebar ps-widget--filter">
                            <div class="ps-widget__header">
                                <h3>Advance Search</h3>
                            </div>
                            <input class="form-control" type="text" placeholder="Search Product…" value="${sessionScope.LAST_SEARCH}" name="keyword">      
                            <div class="ps-widget__header">
                                <h3>Category</h3>
                            </div>
                            <select name="category" class="ps-select" tabindex="-98">
                            </select>
                        </aside>
                        <aside class="ps-widget--sidebar ps-widget--filter">
                            <div class="ps-widget__header">
                                <h3>Price</h3>
                            </div>
                            <div class="form-group">

                                <div class="ps-widget__content">
                                    <p>From Price: <span id="rangeval-min"></span></p>
                                    <input class="my_min_price" type="range" name="minprice" min="1" step="1" max="1000"  oninput="setPriceRange()" value="${requestScope.LAST_MIN_PRICE}"/>
                                    <p>To Price: <span id="rangeval-max"></span></p>
                                    <input class="my_max_price" type="range" name="maxprice" min="10" step="1" max="1000" oninput="$('#rangeval-max').html($(this).val())" onclick="setPriceRange()"value="${requestScope.LAST_MAX_PRICE}"/>
                                </div>
                            </div>
                        </aside>
                        <input type="submit" class="btn-search" value="SEARCH">
                    </form>
                </div>
            </div>
            <%@include file="footer.jspf" %>
        </main>
        <!-- JS Library-->
        <%@include file="script.jspf" %>

    </body>
</html>
