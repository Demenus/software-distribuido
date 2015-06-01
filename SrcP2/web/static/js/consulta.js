/**
 * Created by aaron on 28/05/2015.
 */

var search_item_name;
var products = [];

var addProduct = function(data) {
    products.push(data);
};

var requestService = function(item, key, callback) {
    var httpitem = "http://" + item;
    var videoUrl = httpitem+"/API/VIDEO/item/"+search_item_name;
    var audioUrl = httpitem+"/API/AUDIO/item/"+search_item_name;
    var bookUrl = httpitem+"/API/BOOK/item/"+search_item_name;
    async.parallel([
        function(cb) {
            $.ajax({
                url: videoUrl,
                method: "get",
                dataType: "json",
                //crossDomain: true,
                success: addProduct,
                error: function() {},
                complete: function() {cb(null);}
            });
        },
        function(cb) {
            $.ajax({
                url: audioUrl,
                method: "get",
                dataType: "json",
                //crossDomain: true,
                success: addProduct,
                error: function() {},
                complete: function() {cb(null);}
            });
        },
        function(cb) {
            $.ajax({
                url: bookUrl,
                method: "get",
                dataType: "json",
                //crossDomain: true,
                success: addProduct,
                error: function() {},
                complete: function() {cb(null);}
            });
        }
    ], function() {
        callback(null);
    });

};

var createItemFunction = function(p) {
    var s;
    s = "<table class=\"shopping-cart cartlist-table\">";
    s += "<tr>";
    s += "<td class=\"cart-item-title\"><b>"+p["NAME"]+"</b></td>";
    s += "<td>";
    s += "<div class=\"feature\">Description: <b>"+ p["DESC"]+"</b></div>";
    s += "</td>";
    s += "<td class=\"price\">"+p["PRICE"]+ "EUR</td>";
    s += "<td class=\"actions\">";
    s += "<a href=\""+p["LINK"]+"\">"+p["LINK"]+"</a>";
    s += "</td>";
    s += "</tr>";
    s += "</table>";
    return $(s);
};



var sortProducts = function(err) {
    var s = function(p1, p2){
        var keyA = parseFloat(p1["PRICE"]),
            keyB = parseFloat(p2["PRICE"]);
        // Compare the 2 dates
        if(keyA < keyB) return -1;
        if(keyA > keyB) return 1;
        return 0;
    };
    products.sort(s);
    var searchResult = $("#search-result");
    for (var i in products) {
        var p = products[i];
        var div = $("<div class='row'></div>");
        div.append(createItemFunction(p));
        searchResult.append(div);
    }
};

var onLlibreriesReceived = function(data, status) {
    var ips = data.split("\n");
    async.forEachOf(ips, requestService, sortProducts);
};

var searchClick = function() {
    search_item_name = $("#search-product").val();
    $("#search-result").empty();
    products = [];
    $.ajax({
        url: "/static/llibreries.txt",
        method: "get",
        dataType: "text",
        success:onLlibreriesReceived
    });
};

$(document).ready(function() {
    $("#update-btn").click(searchClick);
});


