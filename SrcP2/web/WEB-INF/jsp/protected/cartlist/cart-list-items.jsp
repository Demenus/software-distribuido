<%@ page import="ub.chennegrin.model.shop.CartList" %>
<%@ page import="ub.chennegrin.model.shop.Product" %>
<% CartList cart_list_items = (CartList) request.getSession().getAttribute("CartList"); %>
<% User user_list_items = (User) request.getAttribute("User"); %>




    <section id="no-content-section" class="bg-info" style="display: none;">
        <div class="container">
            <div class="row">
                <div class="col-lg-8 col-lg-offset-2 text-center">
                    <h2 class="section-heading">You have not select any product yet</h2>
                    <a href="/llibreria/cataleg" class="btn btn-default btn-xl">Go to catalog!</a>
                </div>
            </div>
        </div>
    </section>

<section id="content-section" class="bg-info" style="display: none;">
<% for (Product p : cart_list_items.getSelectedProducts()) { %>
<div id="prod_<% out.print(p.getId()); %>" class="row">
    <div class="col-md-12">
        <!-- Shopping Cart Items -->
        <table class="shopping-cart">
            <!-- Shopping Cart Item -->
            <tr>
                <td class="cart-item-title"><b><% out.print(p.getName()); %></b></td>
                <td>
                    <div class="feature">Description: <b><% out.print(p.getDesc()); %></b></div>
                </td>
                <!-- Shopping Cart Item Price -->
                <td class="price"><% out.print(p.getPrice()); %>EUR</td>
                <!-- Shopping Cart Item Actions -->
                <td class="actions">
                    <button id="btn_<% out.print(p.getId()); %>" class="btn btn-xs btn-grey"><i class="glyphicon glyphicon-trash"></i></button>
                </td>
            </tr>
            <!-- End Shopping Cart Item -->
        </table>
        <!-- End Shopping Cart Items -->
    </div>
</div>
<% } %>
<div class="row">
    <!-- Shopping Cart Totals -->
    <div class="col-md-4 col-md-offset-8 col-sm-6 col-sm-offset-6">
        <table class="cart-totals">
            <tr>
                <td><b>Your credit</b></td>
                <td id="user-credit"><% out.print(user_list_items.getCurrency()); %>EUR</td>
            </tr>
            <tr>
                <td><b>Selected products</b></td>
                <td id="total-products"></td>
            </tr>
            <tr class="cart-grand-total">
                <td><b>Your credit after shopping</b></td>
                <td><b id="total-eur"></b></td>
            </tr>
        </table>
        <!-- Action Buttons -->
        <div class="pull-right">
            <form method="post" id="form-buy">
                <button type="submit" class="btn btn-success"><i class="glyphicon glyphicon-shopping-cart icon-white"></i> BUY</button>
            </form>
        </div>
    </div>
</div>

</section>