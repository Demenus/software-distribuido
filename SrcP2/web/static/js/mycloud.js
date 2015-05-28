/**
 * Created by aaron on 27/05/2015.
 */

$(".list-group-item").on("click", function(){
    $(".list-group-item.active").removeClass("active");
    $(this).addClass("active");
    if ($(this).attr("id") == "select-purchased") {
        var cartlist_select = $(".cartlist-table");
        if (cartlist_select.length > 0) {
            cartlist_select.hide("slow", function(){
                $(".purchased-table").show("slow");
            });
        } else {
            $(".purchased-table").show("slow");
        }
    } else {
        var purchased_select = $(".purchased-table");
        if (purchased_select.length > 0) {
            purchased_select.hide("slow", function(){
                $(".cartlist-table").show("slow");
            });
        } else {
            $(".cartlist-table").show("slow");
        }
    }
});

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

var removeCartFunction = function (data) {
    if (data["result"] == "ok") {
        $("#numCartItems").text(data["numElems"]);
        $("#prod_"+btnId).hide("slow", function () {
            this.remove();
        });
    }
};

var buyCartFunction = function(data) {
    if (data["result"] == "ok") {
        var productId = data["productId"];
        $("#numCartItems").text(data["numElems"]);
        $("#prod_"+productId).hide("slow", function () {
            //var elem = $($(this).html());
            $("#purchase-success").show().delay(3000).fadeOut("slow")
            var elem = $($("<div />").append($(this).clone()).html());
            this.remove();
            var currency_nav = $("#user-currency-nav");
            var creditStr = currency_nav.text();
            var credit = parseFloat(creditStr);
            credit = credit - parseFloat(data["productPrice"]);
            currency_nav.text(credit.toFixed(2));
            elem.removeClass("cartlist-table").addClass("purchased-table");
            elem.find(".actions").empty();
            elem.find(".price").remove();
            elem.find(".actions").append('<a class="btn btn-success" href="/products/?productid='+productId+'"><span class="glyphicon glyphicon-download-alt" aria-hidden="true"></span> Download</a>');
            $("#purchased-div").prepend(elem);

        });
    }
};

var buyClick = function() {
    var productId = $(this).attr("id").split("_")[1];
    $.ajax({
        url: "/cartlist",
        method: "post",
        data: {"action": "buy", "productId": productId},
        dataType: "json",
        success: buyCartFunction
    });
};

$(document).ready(function() {
    $(".btn-grey").click(removeClick);
    $(".btn-buy").click(buyClick);
});
