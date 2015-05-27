<%@ page import="ub.chennegrin.controllers.CatalegController" %>
<%@ page import="ub.chennegrin.model.shop.Product" %>
<%@ page import="ub.chennegrin.model.shop.CartList" %>
<%@ page import="ub.chennegrin.model.users.User" %>
<div class="row">

  <% CatalegController controller = (CatalegController) request.getAttribute("Controller"); %>
  <% CartList cartList = (CartList) request.getSession().getAttribute("CartList"); %>
  <% User user_product_list = (User) request.getAttribute("User"); %>
  <% for (Product p : controller.getAllProducts()) { %>
  <div class="col-sm-4 col-lg-4 col-md-4 product <% out.print(p.getType()); %>">
    <div class="thumbnail">
      <img src="http://placehold.it/320x150" alt="">
      <div class="caption">
        <h4 class="pull-right"><% out.print(String.valueOf(p.getPrice())); %>EUR</h4>
        <h4><a href="#"><% out.print(p.getName()); %></a>
        </h4>
        <p><% out.print(p.getDesc()); %></p>
      </div>
      <div class="ratings">
        <% if (p.getReviews() == 1) { %>
        <p class="pull-right"><% out.print(String.valueOf(p.getReviews())); %> review</p>
        <% } else { %>
        <p class="pull-right"><% out.print(String.valueOf(p.getReviews())); %> reviews</p>
        <% } %>
        <p>
          <%int n = p.getStarsInt();
            for (int i = 0; i < n; i++) { %>
          <span class="glyphicon glyphicon-star"></span>
          <% } %>
          <% for (int i = 0; i < 5-n; i++) { %>
          <span class="glyphicon glyphicon-star-empty"></span>
          <% } %>
        </p>
        <% if (user_product_list != null) { %>
          <% if (user_product_list.hasPurchased(p.getId())) { %>
          <button class="btn-info">Go to resource</button>
          <% } else if (!cartList.isInCart(p.getId())) { %>
          <button class="btn-success" id="<% out.print(p.getId()); %>"><i class="fa fa-cart-plus"></i> Add to cart</button>
          <% } else { %>
          <button class="btn-danger" id="<% out.print(p.getId()); %>"><i class="fa fa-cart-arrow-down"></i> Remove from cart</button>
          <% }%>
        <% } %>
      </div>
    </div>
  </div>
  <% } %>
</div>