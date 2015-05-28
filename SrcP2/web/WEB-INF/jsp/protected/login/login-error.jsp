<%@include file="../../common/header2.jsp"%>
<body>
<!--[if lt IE 7]>
<p class="chromeframe">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> or <a href="http://www.google.com/chromeframe/?redirect=true">activate Google Chrome Frame</a> to improve your experience.</p>
<![endif]-->

<%@include file="../../common/main-nav.jsp" %>
<section class="bg-black">
  <div class="my-section my-section-breadcrumb">
    <div class="container">
      <div class="row">
        <div class="col-md-12">
          <h1>Login</h1>
        </div>
      </div>
    </div>
  </div>
</section>

<div id="purchase-success" class="row" style="display: none;">
  <div class="col-md-10 col-md-offset-1">
    <div class="row alert alert-danger text-center" role="alert"><h4>Product purchased!</h4></div>
  </div>
</div>


<div class="container">

  <div class="row" id="pwd-container">
    <div class="col-md-4"></div>

    <div class="col-md-4">
      <section class="login-form">
        <form method="post" action="j_security_check">
          <!--<img src="http://i.imgur.com/RcmcLv4.png" class="img-responsive" alt="" />-->
          <input type="text" name="j_username" placeholder="Username" required class="form-control input-lg" />
          <input type="password" name="j_password" class="form-control input-lg" id="password" placeholder="Password" required="" />
          <div class="pwstrength_viewport_progress"></div>
          <button type="submit" name="go" class="btn btn-lg btn-primary btn-block">Sign in</button>

        </form>
      </section>
    </div>

    <div class="col-md-4"></div>


  </div>


</div>

<!-- Javascripts -->
<!-- jQuery -->
<script src="/static/js/jquery.js"></script>

<!-- Bootstrap Core JavaScript -->
<script src="/static/js/bootstrap.min.js"></script>

</body>

</html>