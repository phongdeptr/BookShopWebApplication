/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var categoryList = null;

$(document).ready(function () {
    getCategoryAjax();
    setPriceRange();
    // fillInput();

});
function getCategoryAjax() {
    return $.ajax({
        url: "LoadCategoryController",
        method: "GET",
        cache: false,
        success: function (results) {
            categoryList = JSON.parse(results);
        }
    });
}
getCategoryAjax().done(categoryList, function (result) {
    categoryList = JSON.parse(result);
    console.log(categoryList);
    loadCategoryNav();
    loadCategoryOption();
});

function loadCategoryNav() {
    //getCategoryAjax()
    if (categoryList.length !== 0) {
        $(".mega-item").children().remove();
    }
    console.log(categoryList);
    for (var i = 0, max = categoryList.length; i < max; i++) {
        categoryList[i].categorID;
        categoryList[i].catgoryName;
        var item = createMegaItem(categoryList[i].categoryName, categoryList[i].categoryName);
        $(".mega-item").append(item);
    }
}

function loadCategoryOption() {
    if ($(".ps-select").children() !== null) {
        $(".ps-select").children().remove();
    }
    console.log(categoryList);
    for (var i = 0, max = categoryList.length; i < max; i++) {
        categoryList[i].categorID;
        categoryList[i].catgoryName;
        let myOption = createOption(categoryList[i].categoryName);
        myOption.innerHTML = categoryList[i].categoryName;
        $(".ps-select").append(myOption);
    }
}

function createDiv(divClass, style) {
    var div = document.createElement("DIV");
    div.className = divClass;
    div.style = style;
    return div;
}
function createMegaItemAnchor(href, innerHtml) {
    var anchor = document.createElement("A");
    anchor.href = "SearchController?category=" + href + "&option=1";
    anchor.innerHTML = innerHtml;
    return anchor;
}

function createMegaItem(url, innerHtml) {
    let li = document.createElement("LI");
    let anchor = createMegaItemAnchor(url, innerHtml);
    li.appendChild(anchor);
    return li;
}

function createOption(value) {
    let option = document.createElement("OPTION");
    option.value = value;
    //console.log(option);
    return option;
}


function UpdateBook(ID) {
    let triger = ID;
    let targetString = "form#" + triger.toString();
    console.log("Update button ID: " + targetString);
    let updateForm = $(targetString);
    console.log(updateForm.serializeArray());
    $.ajax({
        url: "MainController",
        method: "POST",
        cache: false,
        data: updateForm.serializeArray(),
        success: function (results) {
            if (results === "success") {

            } else {
                window.location.replace("update.jsp");
            }
        }
    });
}

function setPriceRange() {
    let minInput = $(".my_min_price");
    let maxInput = $(".my_max_price");
    let min_value = minInput.val();
    let max_value = maxInput.val();
    if (Number.parseInt(min_value) > Number.parseInt(max_value)) {
        console.log("Min value: " + min_value);
        max_value = Number.parseInt(min_value) + 50;
        maxInput.val(max_value);
        console.log("Max value: " + max_value);
    }
    $('#rangeval-min').html(min_value);
    $('#rangeval-max').html(max_value);
}



function getDeleteID(deleteID) {
    let inputDelete = $("input.delete-input");
    inputDelete.val(deleteID);
    console.log(inputDelete.val());
}

function  deleteBook() {
    let deleteID = $("input.delete-input").val();
    console.log(deleteID);
    $.ajax({
        url: "MainController",
        method: "GET",
        cache: false,
        data: {deteleID: deleteID, action: "deletebook"},
        success: function (results) {
            if (results === 'success') {
                window.location.reload();
            } else {
                window.location.replace("update.jsp");
            }
        }
    });

}

function addCart(productID) {
    let quantity = $("input#"+productID); 
    
    $.ajax({
        url: "AddCartController",
        method: "GET",
        cache: false,
        data: {
            action: 'addcart',
            bookID: productID, quantity : quantity.val()
        },
        success: function (results) {
            if (results === "SUCCESS") {
                window.alert("Add cart success");
            } else {
                window.alert(results);
            }
        }
    });
}

function updateItemCart(inputID) {
    let itemQuantityInput = $('input#' + inputID);
    let currQuan = itemQuantityInput.val();
    console.log(currQuan);
    $.ajax({
        url: "MainController",
        method: "GET",
        cache: false,
        data: {action: 'updatecart', updateID: inputID, quantity: currQuan},
        success: function (results) {
            if (results === "SUCCESS") {
                window.alert("SUCCESS UPDATE");
                window.location.reload();
            } else {
                window.alert(results);
            }
        }
    });
}

function removeItemCart(id) {
    let res = window.confirm("Do you want to remove this item");
    if (res === true) {
        $.ajax({
            url: "MainController",
            method: "GET",
            cache: false,
            data: {action: 'removecart', removeID: id},
            success: function (results) {
                if (results === "SUCCESS") {
                    window.location.reload();
                } else {
                    window.alert(results);
                }
            }
        });
    }
}


function increaseInput(inputID) {
    let targetInput = $("input#" + inputID);
    let currQuan = targetInput.val();
    currQuan = Number.parseInt(currQuan) + 1;
    targetInput.val(currQuan);
    updateItemCart(inputID);
}

function increaseInputQuantity(inputID) {
    let targetInput = $("input#" + inputID);
    let currQuan = targetInput.val();
    currQuan = Number.parseInt(currQuan) + 1;
    targetInput.val(currQuan);
}
function deceaseInputQuantity(inputID) {
    let targetInput = $("input#" + inputID);
    let currQuan = targetInput.val();
    currQuan = Number.parseInt(currQuan) - 1;
    if(currQuan <= 0 ){
        currQuan = 1;
    }
    targetInput.val(currQuan);
}

function decreaseInput(inputID) {
    let targetInput = $("input#" + inputID);
    let currQuan = targetInput.val();
    currQuan = Number.parseInt(currQuan) - 1;
    targetInput.val(currQuan);
    updateItemCart(inputID);
}

function checkout() {
    let isCheckout = window.confirm("Are you sure to checkout");
    let promocode = $("form#promocode");
    if (isCheckout) {
        $.ajax({
            url: "CheckoutController",
            method: "GET",
            cache: false,
            data: promocode.serializeArray(),
            success: function (result) {
                if (result === "SUCCESS") {
                    window.location.replace("ViewOrderController");
                } else {
                    window.alert(result);
                }
            }
        });
    }
}

function createDiscount() {
    let discountForm = $('form#adddiscount');
    $.ajax({
        url: "CreateDiscountCode",
        method: "GET",
        cache: false,
        data: discountForm.serializeArray(),
        success: function (results) {
            if (results === "SUCCESS") {
                window.location.reload();
            } else {
                window.alert(results);
            }
        }
    });
}