<%@ page import="ub.chennegrin.model.shop.CartList" %>
<%@ page import="ub.chennegrin.model.users.User" %>
<nav id="mainNav" class="navbar navbar-default navbar-fixed-top affix">
  <div class="container-fluid">
    <!-- Brand and toggle get grouped for better mobile display -->
    <div class="navbar-header">
      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a class="navbar-brand page-scroll" href="/llibreria">Media Cloud</a>
    </div>

    <% User user_header = (User) request.getAttribute("User"); %>
    <% CartList cartList_header = (CartList) request.getSession().getAttribute("CartList"); %>
    <!-- Collect the nav links, forms, and other content for toggling -->
    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
      <ul class="nav navbar-nav navbar-right">
        <li>
          <a class="page-scroll" href="/llibreria/cataleg">Catalog</a>
        </li>
        <% if (user_header == null) { %>
        <li>
          <a class="page-scroll" href="/login">Login</a>
        </li>
        <% } else { %>
        <li>
          <a class="page-scroll" href="/cartlist"><span id="numCartItems"><% out.print(cartList_header.getNumCartsElements()); %></span> <i class="fa fa-shopping-cart"></i></a>
        </li>
        <li>
          <a class="page-scroll" href="/llibreria/protegit/llista">My Cloud</a>
        </li>
        <li>
          <a class="page-scroll dropdown" href="#"><% out.print(user_header.getUsername()); %> (<span id="user-currency-nav"><% out.print(user_header.getCurrency()); %></span>EUR)</a>
        </li>
        <li>
          <a class="page-scroll" href="/logout">Logout</a>
        </li>
        <% }%>
      </ul>
    </div>
    <!-- /.navbar-collapse -->
  </div>
  <!-- /.container-fluid -->
</nav>