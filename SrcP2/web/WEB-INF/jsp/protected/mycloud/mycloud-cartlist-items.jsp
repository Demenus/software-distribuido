<%@ page import="ub.chennegrin.controllers.MyCloudController" %>
<%@ page import="ub.chennegrin.model.shop.Product" %>
<%@ page import="ub.chennegrin.model.shop.CartList" %>
<% CartList mycloud_cartList = (CartList) request.getSession().getAttribute("CartList"); %>
<% for (Product p : mycloud_cartList.getSelectedProducts()) { %>
<!-- Shopping Cart Items -->
<table id="prod_<% out.print(p.getId()); %>" class="shopping-cart cartlist-table" style="display: none;">
  <!-- Shopping Cart Item -->
  <tr>
    <td class="image">
      <img src="http://placehold.it/320x150" alt=""/>
    </td>
    <td class="cart-item-title"><b><% out.print(p.getName()); %></b></td>
    <td>
      <div class="feature">Description: <b><% out.print(p.getDesc()); %></b></div>
    </td>
    <!-- Shopping Cart Item Price -->
    <td class="price"><% out.print(p.getPrice()); %>EUR</td>
    <!-- Shopping Cart Item Actions -->
    <td class="actions">
      <div class="button-group inline" role="group">
        <button id="btntrash_<% out.print(p.getId()); %>" class="btn btn-xs btn-grey"><i class="glyphicon glyphicon-trash"></i></button>
        <button id="btnbuy_<% out.print(p.getId()); %>" class="btn btn-success btn-buy"><i class="fa fa-shopping-cart"></i> Buy</button>
      </div>
    </td>
  </tr>
  <!-- End Shopping Cart Item -->
</table>
<!-- End Shopping Cart Items -->
<% } %>


