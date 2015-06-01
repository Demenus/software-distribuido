<%@ page import="ub.chennegrin.controllers.MyCloudController" %>
<%@ page import="ub.chennegrin.model.shop.Product" %>
<% MyCloudController controller_purchased = (MyCloudController) request.getAttribute("Controller"); %>
<% for (Product p : controller_purchased.getPurchasedProducts(request)) { %>
<!-- Shopping Cart Items -->
<table class="shopping-cart purchased-table">
  <!-- Shopping Cart Item -->
  <tr>
    <!-- Shopping Cart Item Image -->
    <td class="image">
      <img src="http://placehold.it/320x150" alt=""/>
    </td>
    <td class="cart-item-title"><b><% out.print(p.getName()); %></b></td>
    <td>
      <div class="feature">Description: <b><% out.print(p.getDesc()); %></b></div>
    </td>
    <!-- Shopping Cart Item Actions -->
    <td class="actions">
      <a class="btn btn-success" href="/products/?productid=<% out.print(p.getId()); %>"><span class="glyphicon glyphicon-download-alt" aria-hidden="true"></span> Download</a>
    </td>
  </tr>
</table>
<!-- End Shopping Cart Items -->

<% } %>


