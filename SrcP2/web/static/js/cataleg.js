/**
 * Created by aaron on 25/05/2015.
 */
var btnId;

var addCartFunction = function(data) {
    if (data["result"] == "ok") {
        var $removeCart = $("<i class='fa fa-cart-arrow-down'></i>");
        $("#"+btnId).removeClass("btn-success").addClass("btn-danger").empty().append($removeCart).append(" Remove from cart").off("click").on("click", removeClick);
        $("#numCartItems").text(data["numElems"]);
    } else if (data["result"] == "purchased") {
        $("#"+btnId).removeClass("btn-success").addClass("btn-danger").empty().append("Go to resource");
    }
};

var removeCartFunction = function (data) {
    if (data["result"] == "ok") {
        var $addCart = $("<i class='fa fa-cart-plus'></i>");
        $("#"+btnId).removeClass("btn-danger").addClass("btn-success").empty().append($addCart).append(" Add to cart").off("click").on("click", addClick);
        $("#numCartItems").text(data["numElems"]);
    } else if (data["result"] == "purchased") {
        $("#"+btnId).removeClass("btn-success").addClass("btn-danger").empty().append("Go to resource");
    }
};

var addClick = function() {
    btnId = this.id;
    $.ajax({
        url: "/llibreria/cataleg",
        method: "post",
        data: {"action": "add", "productId": btnId},
        dataType: "json",
        success: addCartFunction
    });
};

var removeClick = function() {
    btnId = this.id;
    $.ajax({
        url: "/llibreria/cataleg",
        method: "post",
        data: {"action": "remove", "productId": btnId},
        dataType: "json",
        success: removeCartFunction
    });
};

$(".btn-success").click(addClick);

$(".btn-danger").click(removeClick);
