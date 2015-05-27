<%@ page import="ub.chennegrin.controllers.MyCloudController" %>
<%@ page import="ub.chennegrin.model.shop.Product" %>
<section class="bg-info">
<% MyCloudController controller = (MyCloudController) request.getAttribute("Controller"); %>
<% for (Product product : controller.getPurchasedProducts(request)) { %>
<div class="row">
<div class="col-md-12">
<!-- Shopping Cart Items -->
<table class="shopping-cart">
  <!-- Shopping Cart Item -->
  <tr>
    <!-- Shopping Cart Item Image -->
    <td class="image">
      <img src="http://placehold.it/320x150" alt="">
        <div class="col-xs-6 col-md-3">
            <% if (product.getType().equalsIgnoreCase("Video2")) { %>
            <img src="static/img/video.png" alt="Video">
            <% }%>
            <% if (product.getType().equalsIgnoreCase("Audio2")) { %>
            <img src="static/img/audio.png" alt="Audio">
            <% }%>
            <% if (product.getType().equalsIgnoreCase("Book2")) { %>
            <img src="static/img/book.png" alt="Book">
            <% }%>
        </div>
    <td class="cart-item-title"><b><% out.print(product.getName()); %></b></td>
    <td>
      <div class="feature">Description: <b><% out.print(product.getDesc()); %></b></div>
    </td>
    <!-- Shopping Cart Item Actions -->
    <td class="actions">
      <button class="btn btn-success"><span class="glyphicon glyphicon-download-alt" aria-hidden="true"></span> Download</button>
    </td>
  </tr>
</table>
<!-- End Shopping Cart Items -->

<% } %>
</section>


