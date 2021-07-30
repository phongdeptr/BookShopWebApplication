<%-- 
    Document   : index.jsp
    Created on : May 18, 2021, 8:00:59 PM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Home page</title>
        <%@include file="css.jspf" %>
        <!--HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries-->
        <!--WARNING: Respond.js doesn't work if you view the page via file://-->
        <!--[if lt IE 9]><script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script><script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script><![endif]-->
    </head>
    <body>
        <%@include file="header.jspf"%>
        <div class="header-services">
            <div class="ps-services owl-slider" data-owl-auto="true" data-owl-loop="true" data-owl-speed="7000" data-owl-gap="0" data-owl-nav="true" data-owl-dots="false" data-owl-item="1" data-owl-item-xs="1" data-owl-item-sm="1" data-owl-item-md="1" data-owl-item-lg="1" data-owl-duration="1000" data-owl-mousedrag="on">
                <p class="ps-service"><i class="ps-icon-delivery"></i><strong>Free delivery</strong>: Get free standard delivery on every order with Sky Store</p>
                <p class="ps-service"><i class="ps-icon-delivery"></i><strong>Free delivery</strong>: Get free standard delivery on every order with Sky Store</p>
                <p class="ps-service"><i class="ps-icon-delivery"></i><strong>Free delivery</strong>: Get free standard delivery on every order with Sky Store</p>
            </div>
        </div>
        <main>

        </main>
        <div class="ps-container">
            <div class="ps-section__header mb-50">
                <h3 class="ps-section__title" data-mask="Promotion">- Our Category</h3>
            </div>
            <div class="ps-section__content pb-50">
                <div class="masonry-wrapper" data-col-md="3" data-col-sm="2" data-col-xs="1" data-gap="30" data-radio="100%">
                    <div class="ps-masonry" style="position: relative; height: 459.976px;">
                        <div class="grid-sizer"></div>
                        <p>Click Category to see book</p>
                        <p>Enter and click search to buy a book</p>
                        <ul>
                            <c:forEach var="discountInfo" items="${sessionScope.DISCOUNT_LIST}">
                                <li>
                                    ${discountInfo}
                                </li>
                            </c:forEach>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
        <%@include file="script.jspf" %>
    </body>
</html>
