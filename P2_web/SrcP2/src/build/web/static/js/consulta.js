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
                crossDomain: true,
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
                crossDomain: true,
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
                crossDomain: true,
                success: addProduct,
                error: function() {},
                complete: function() {cb(null);}
            });
        }
    ], function() {
        callback(null);
    });

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
    console.log(products);
};

var onLlibreriesReceived = function(data, status) {
    var ips = data.split("\n");
    async.forEachOf(ips, requestService, sortProducts);
};

var searchClick = function() {
    search_item_name = $("#search-product").val();
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


