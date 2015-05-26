<%@ page import="ub.chennegrin.controllers.CatalegController" %>
<%@ page import="ub.chennegrin.model.shop.Product" %>
<div class="row">

  <% CatalegController controller = (CatalegController) request.getAttribute("Controller"); %>
  <% User user_product_list = (User) request.getAttribute("User"); %>
  <% for (Product p : controller.getAllProducts()) { %>
  <div class="col-sm-4 col-lg-4 col-md-4">
    <div class="thumbnail">
      <img src="http://placehold.it/320x150" alt="">
      <div class="caption">
        <h4 class="pull-right"><% out.write(String.valueOf(p.getPrice())); %>EUR</h4>
        <h4><a href="#"><% out.write(p.getName()); %></a>
        </h4>
        <p><% out.write(p.getDesc()); %></p>
      </div>
      <div class="ratings">
        <% if (p.getReviews() == 1) { %>
        <p class="pull-right"><% out.write(String.valueOf(p.getReviews())); %> review</p>
        <% } else { %>
        <p class="pull-right"><% out.write(String.valueOf(p.getReviews())); %> reviews</p>
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
          <% } else { %>
          <button class="btn-success">Add to cart</button>
          <% } %>
        <% } %>
      </div>
    </div>
  </div>
  <% } %>
</div>