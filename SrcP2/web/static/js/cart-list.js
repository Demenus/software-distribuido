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
        var price = parseFloat(priceStr.substring(0, priceStr.length - 3));
        products = products + price;
    }).promise().done(function() {
        $("#total-products").text("-"+products.toFixed(2)+"EUR");
        var creditStr = $("#user-credit").text();
        var credit = parseFloat(creditStr.substring(0, creditStr.length - 3));
        var total = credit - products;
        if (total < 0) {
            $("#form-buy").hide();
            $("#total-eur").text("Not enough credit");
        } else {
            $("#total-eur").text(total+"EUR");
        }
    });
};

var btnId;

var removeClick = function() {
    btnId = this.id.split("_")[1];
    $.ajax({
        url: "/llibreria/cataleg",
        method: "post",
        data: {"action": "remove", "productId": btnId},
        dataType: "json",
        success: removeCartFunction
    });
};

var removeCartFunction = function (data) {
    if (data["result"] == "ok") {
        $("#numCartItems").text(data["numElems"]);
        $("#prod_"+btnId).hide("slow", function () {
            this.remove();
        });
        noElements();
        updateTotal();
    }
};

$(document).ready(function(){
    noElements();
    updateTotal();
});

$(".btn-grey").click(removeClick);
