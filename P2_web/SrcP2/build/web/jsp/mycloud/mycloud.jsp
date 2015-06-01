<%@include file="../common/header2.jsp"%>
<body>
<!--[if lt IE 7]>
<p class="chromeframe">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> or <a href="http://www.google.com/chromeframe/?redirect=true">activate Google Chrome Frame</a> to improve your experience.</p>
<![endif]-->

<%@include file="../common/main-nav.jsp" %>

<!-- Page Title -->
<section class="bg-black">
  <div class="my-section my-section-breadcrumb">
    <div class="container">
      <div class="row">
        <div class="col-md-12">
          <h1>Shopping Cart</h1>
        </div>
      </div>
    </div>
  </div>
</section>

<%@ page import="ub.chennegrin.controllers.MyCloudController" %>
<%@ page import="ub.chennegrin.model.users.User" %>
    
    <% MyCloudController controller = (MyCloudController) request.getAttribute("Controller"); %>
    <% User user_product_list = (User) request.getAttribute("User"); %>
    <% for (int purchased : user_product_list.getPurchased()) { %>
    <div class="row">
      <div class="col-md-12">
        <!-- Shopping Cart Items -->
        <table class="shopping-cart">
          <!-- Shopping Cart Item -->
          <% if (user_product_list != null) { %>
          <% if (user_product_list.hasPurchased(purchased)) { %>
          <% if (controller.getProductTypeById(purchased).equals("Video")) { %>
            <tr>
              <!-- Shopping Cart Item Image -->
              <td class="image">
                  <div class="row">
                    <div class="col-xs-6 col-md-3">
                      <a href="#" class="thumbnail">
                        <img src="static/img/video.png" alt="Item Name">
                      </a>
                    </div>
                  </div>
              <td>
                <div class="cart-item-title"><a href="page-product-details.html"><% out.write("Product ID: "+purchased); %></a></div>
                <div class="feature color">
                  Description: <span class="color-white"></span>
                </div>
              </td>
              <!-- Shopping Cart Item Actions -->
              <td class="actions">
                  <span class="glyphicon glyphicon-download-alt" aria-hidden="true"></span>
              </td>
            </tr>
          <% }else if(controller.getProductTypeById(purchased).equals("Audio")){ %>
                    <tr>
              <!-- Shopping Cart Item Image -->
              <td class="image">
                  <div class="row">
                    <div class="col-xs-6 col-md-3">
                      <a href="#" class="thumbnail">
                        <img src="static/img/audio.jpg" alt="Item Name">
                      </a>
                    </div>
                  </div>
              <td>
                <div class="cart-item-title"><a href="page-product-details.html"><% out.write("Name: "+purchased); %></a></div>
                <div class="feature color">
                  Description: <span class="color-white"></span>
                </div>
              </td>
              <!-- Shopping Cart Item Actions -->
              <td class="actions">
                  <span class="glyphicon glyphicon-download-alt" aria-hidden="true"></span>
              </td>
            </tr>
           <% }else{%>
                    <tr>
              <!-- Shopping Cart Item Image -->
              <td class="image">
                  <div class="row">
                    <div class="col-xs-6 col-md-3">
                      <a href="#" class="thumbnail">
                        <img src="static/img/book.jpg" alt="Item Name">
                      </a>
                    </div>
                  </div>
              <td>
                <div class="cart-item-title"><a href="page-product-details.html"><% out.write("Product ID: "+purchased); %></a></div>
                <div class="feature color">
                  Description: <span class="color-white"></span>
                </div>
              </td>
              <!-- Shopping Cart Item Actions -->
              <td class="actions">
                  <span class="glyphicon glyphicon-download-alt" aria-hidden="true"></span>
              </td>
            </tr>
           <% }%>
          
        <% } %>
        <% } %>
          
         
        </table>
        <!-- End Shopping Cart Items -->
      </div>
     </div>
      <% } %>


