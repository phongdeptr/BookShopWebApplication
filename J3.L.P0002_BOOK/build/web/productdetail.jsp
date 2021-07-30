<%-- 
    Document   : productDetail
    Created on : May 16, 2021, 8:00:05 PM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
        <title>Sky - Product Detail</title>
        <!-- Fonts-->
        <%@include file="css.jspf" %>
        <!--HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries-->
        <!--WARNING: Respond.js doesn't work if you view the page via file://-->
        <!--[if lt IE 9]><script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script><script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script><![endif]-->
    </head>
    <body class="ps-loading">
        <div class="header--sidebar"></div>
        <%@include file="header.jspf" %>
        <div class="header-services">
            <div class="ps-services owl-slider" data-owl-auto="true" data-owl-loop="true" data-owl-speed="7000" data-owl-gap="0" data-owl-nav="true" data-owl-dots="false" data-owl-item="1" data-owl-item-xs="1" data-owl-item-sm="1" data-owl-item-md="1" data-owl-item-lg="1" data-owl-duration="1000" data-owl-mousedrag="on">
                <p class="ps-service"><i class="ps-icon-delivery"></i><strong>Free delivery</strong>: Get free standard delivery on every order with Sky Store</p>
                <p class="ps-service"><i class="ps-icon-delivery"></i><strong>Free delivery</strong>: Get free standard delivery on every order with Sky Store</p>
                <p class="ps-service"><i class="ps-icon-delivery"></i><strong>Free delivery</strong>: Get free standard delivery on every order with Sky Store</p>
            </div>
        </div>
        <main class="ps-main">
            <div class="test">
                <div class="container">
                    <div class="row">
                        <div class="col-lg-4 col-md-4 col-sm-4 col-xs-4 ">
                        </div>
                    </div>
                </div>
            </div>
            <c:set var="bookItem" value="${requestScope.PRODUCT_DETAIL}"/>
            <div class="ps-product--detail pt-60">
                <div class="ps-container">
                    <div class="row">
                        <div class="col-lg-10 col-md-12 col-lg-offset-1">
                            <div class="ps-product__thumbnail">
                                <div class="ps-product__image">
                                    <div class="item"><img class="zoom" src="images/${bookItem.image}" alt="" data-zoom-image="images/${bookItem.image}"></div>
                                    <div class="item"><img class="zoom" src="images/${bookItem.image}" alt="" data-zoom-image="images/${bookItem.image}"></div>
                                    <div class="item"><img class="zoom" src="images/${bookItem.image}" alt="" data-zoom-image="images/${bookItem.image}"></div>
                                </div>
                            </div>
                            <div class="ps-product__thumbnail--mobile">
                                <div class="ps-product__main-img"><img src="images/${bookItem.image}" height="300"></div>
                                <div class="ps-product__preview owl-slider" data-owl-auto="true" data-owl-loop="true" data-owl-speed="5000" data-owl-gap="20" data-owl-nav="true" data-owl-dots="false" data-owl-item="3" data-owl-item-xs="3" data-owl-item-sm="3" data-owl-item-md="3" data-owl-item-lg="3" data-owl-duration="1000" data-owl-mousedrag="on"><img src="images/shoe-detail/1.jpg" alt=""><img src="images/shoe-detail/2.jpg" alt=""><img src="images/shoe-detail/3.jpg" alt=""></div>
                            </div>                            <div class="ps-product__info">
                                <h1>${bookItem.bookTitle}</h1>
                                <p class="ps-product__category">${bookItem.category}</p>
                                <h3 class="ps-product__price">£ ${bookItem.price}</h3>
                                <c:choose>
                                    <c:when test="${bookItem.status == true || bookItem.quantity <=0}">
                                        <div class="ps-product__shopping">
                                            <div class="form-group--number">
                                                <button class="minus" onclick="deceaseInputQuantity('${bookItem.bookID}')"><span>-</span></button>
                                                <input class="form-control" min="1" id="${bookItem.bookID}" type="number" min="1" value="1">
                                                <button class="plus" onclick="increaseInputQuantity('${bookItem.bookID}')"><span>+</span></button>
                                            </div>
                                            <button class="ps-btn mb-10" onclick="addCart('${bookItem.bookID}')">Add to cart<i class="ps-icon-next"></i></button>
                                        </div>
                                    </c:when>
                                    <c:otherwise>
                                        <div class="ps-product__shopping"><h3>Book is not available</h3></div>
                                    </c:otherwise>   
                                </c:choose>

                            </div>
                            <div class="clearfix"></div>
                            <div class="ps-product__content mt-50">
                                <ul class="tab-list" role="tablist">
                                    <li class="active"><a href="#tab_01" aria-controls="tab_01" role="tab" data-toggle="tab">Overview</a></li>
                                    <li><a href="#tab_02" aria-controls="tab_02" role="tab" data-toggle="tab">Review</a></li>
                                </ul>
                            </div>
                            <div class="tab-content mb-60">
                                <div class="tab-pane" role="tabpane2" id="tab_02">
                                    <p>${bookItem.description}</p>
                                </div>
                                <div class="tab-pane active" role="tabpane1" id="tab_01">
                                    <p>ISBN number ${bookItem.ISBN}</p>
                                    <p>Author: ${bookItem.author}</p>
                                    <p>Price ${bookItem.price}</p>
                                    <p>Quantity ${bookItem.quantity}</p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <%@include  file="footer.jspf"%>
        </main>
        <!-- JS Library-->
        <%@include file="script.jspf" %>

    </body>
</html>
