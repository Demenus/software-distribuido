/**
 * Created by aaron on 27/05/2015.
 */

var noElements = function() {
    var numElems = parseInt($("#numCartItems").text());
    if (numElems == 0) {
        $("#content-section").hide();
        $("#no-content-section").show();
    } else {
        $("#content-section").show();
        $("#no-content-section").hide();
    }
};

var updateTotal = function() {
    var products = 0.0;
    $(".price").each(function(){
        var priceStr = $(this).text();
        var price = parseFloat(priceStr);
        products = products + price;
    }).promise().done(function() {
        $("#total-products").text("-"+products.toFixed(2));
        var creditStr = $("#user-credit").text();
        var credit = parseFloat(creditStr);
        var total = credit - products;
        if (total < 0) {
            $("#buy-button").hide();
            $("#total-eur").text("Not enough credit");
        } else {
            $("#buy-button").show();
            $("#total-eur").text(total+"EUR");
        }
    });
};

var btnId;

var removeClick = function() {
    btnId = this.id.split("_")[1];
    $.ajax({
        url: "/cartlist",
        method: "post",
        data: {"action": "remove", "productId": btnId},
        dataType: "json",
        success: removeCartFunction
    });
};

var removeAjax = function(productId) {
    $.ajax({
        url: "/cartlist",
        method: "post",
        data: {"action": "remove", "productId": productId},
        dataType: "json",
        success: removeCartFunction
    });
};

var removeCartFunction = function (data) {
    if (data["result"] == "ok") {
        console.log(data);
        $("#numCartItems").text(data["numElems"]);
        $("#prod_"+btnId).hide("slow", function () {
            this.remove();
            noElements();
            updateTotal();
        });
    }
};

var buyCartFunction = function(data) {
    if (data["result"] == "ok") {
        var productId = data["productId"];
        $("#numCartItems").text(data["numElems"]);
        $("#prod_"+productId).hide("slow", function () {
            this.remove();
            noElements();
            updateTotal();
            var currency_nav = $("#user-currency-nav");
            var creditStr = currency_nav.text();
            var credit = parseFloat(creditStr);
            credit = credit - parseFloat(data["productPrice"]);
            currency_nav.text(credit.toFixed(2));
        });
    } else if (data["result"] == "purchased") {
        btnId = data["productId"];
        removeAjax(data["productId"])
    }
};

var buyClick = function() {
    $(".cartlist-item").each(function() {
        var productId = $(this).attr("id").split("_")[1];
        $.ajax({
            url: "/cartlist",
            method: "post",
            data: {"action": "buy", "productId": productId},
            dataType: "json",
            success: buyCartFunction
        });
    });
};

$(document).ready(function(){
    noElements();
    updateTotal();
    $(".btn-grey").click(removeClick);
    $("#buy-button").click(buyClick);
});


