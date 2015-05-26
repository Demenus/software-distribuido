<%@ page import="ub.chennegrin.model.users.User" %>
<%@include file="header.jsp" %>

<header>
  <div class="header-content">
    <div class="header-content-inner">
      <h1>All the media you want in one place</h1>
      <hr>
      <p>Start using Media Cloud for high quality media just in one place</p>
      <% User user = (User) request.getAttribute("User"); %>
      <% if (user == null) { %>
        <a href="/login" class="btn btn-primary btn-xl page-scroll">Log in now</a>
      <% } %>
    </div>
  </div>
</header>

<section class="bg-primary" id="about">
  <div class="container">
    <div class="row">
      <div class="col-lg-8 col-lg-offset-2 text-center">
        <h2 class="section-heading">We've got what you need!</h2>
        <hr class="light">
        <p class="text-faded">Check out our cataleg and find what you love and discover new things to love</p>
        <a href="/llibreria/cataleg" class="btn btn-default btn-xl">Go to catalog!</a>
      </div>
    </div>
  </div>
</section>

<section id="services" class="bg-info">
  <div class="container">
    <div class="row">
      <div class="col-lg-12 text-center">
        <h2 class="section-heading">At Your Service</h2>
        <hr class="primary">
      </div>
    </div>
  </div>
  <div class="container">
    <div class="row">
      <div class="col-lg-3 col-md-6 text-center">
        <div class="service-box">
          <i class="fa fa-4x fa-diamond wow bounceIn text-primary"></i>
          <h3>Amazing collection</h3>
          <p class="text-muted">Our media collection will shock you!</p>
        </div>
      </div>
      <div class="col-lg-3 col-md-6 text-center">
        <div class="service-box">
          <i class="fa fa-4x fa-paper-plane wow bounceIn text-primary" data-wow-delay=".1s"></i>
          <h3>Ready to download</h3>
          <p class="text-muted">Fast and secure</p>
        </div>
      </div>
      <div class="col-lg-3 col-md-6 text-center">
        <div class="service-box">
          <i class="fa fa-4x fa-newspaper-o wow bounceIn text-primary" data-wow-delay=".2s"></i>
          <h3>Up to Date</h3>
          <p class="text-muted">Fresh content every week</p>
        </div>
      </div>
      <div class="col-lg-3 col-md-6 text-center">
        <div class="service-box">
          <i class="fa fa-4x fa-heart wow bounceIn text-primary" data-wow-delay=".3s"></i>
          <h3>Made with Love</h3>
          <p class="text-muted">Our team develops this service with love!</p>
        </div>
      </div>
    </div>
  </div>
</section>

<section id="contact" class="bg-info">
  <div class="container">
    <div class="row">
      <div class="col-lg-8 col-lg-offset-2 text-center">
        <h2 class="section-heading">Let's Get In Touch!</h2>
        <hr class="primary">
        <p>Do you have some request or have you found some problem? Give us a call or send us an email and we will get back to you as soon as possible!</p>
      </div>
      <div class="col-lg-4 col-lg-offset-2 text-center">
        <i class="fa fa-phone fa-3x wow bounceIn"></i>
        <p>123-456-678</p>
      </div>
      <div class="col-lg-4 text-center">
        <i class="fa fa-envelope-o fa-3x wow bounceIn" data-wow-delay=".1s"></i>
        <p><a href="mailto:support@mediacloud.com">support@mediacloud.com</a></p>
      </div>
    </div>
  </div>
</section>

<!-- jQuery -->
<script src="/static/js/jquery.js"></script>

<!-- Bootstrap Core JavaScript -->
<script src="/static/js/bootstrap.min.js"></script>

<!-- Plugin JavaScript -->
<script src="/static/js/jquery.easing.min.js"></script>
<script src="/static/js/jquery.fittext.js"></script>
<script src="/static/js/wow.min.js"></script>

<!-- Custom Theme JavaScript -->
<script src="/static/js/creative.js"></script>

</body>

</html>
