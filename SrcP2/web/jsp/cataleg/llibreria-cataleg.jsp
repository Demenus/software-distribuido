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

<section class="bg-info">
  <div class="row">
    <div class="col-md-8 col-md-offset-2">
      <div class="row text-center">
        <h2 class="section-heading">Check out the new content!</h2>
      </div>
      <div class="row carousel-holder">
        <div class="col-md-12">
          <div id="carousel-example-generic" class="carousel slide" data-ride="carousel">
            <ol class="carousel-indicators">
              <li data-target="#carousel-example-generic" data-slide-to="0" class="active"></li>
              <li data-target="#carousel-example-generic" data-slide-to="1"></li>
              <li data-target="#carousel-example-generic" data-slide-to="2"></li>
            </ol>
            <div class="carousel-inner">
              <div class="item active">
                <img class="slide-image" src="http://placehold.it/800x300" alt="">
              </div>
              <div class="item">
                <img class="slide-image" src="http://placehold.it/800x300" alt="">
              </div>
              <div class="item">
                <img class="slide-image" src="http://placehold.it/800x300" alt="">
              </div>
            </div>
            <a class="left carousel-control" href="#carousel-example-generic" data-slide="prev">
              <span class="glyphicon glyphicon-chevron-left"></span>
            </a>
            <a class="right carousel-control" href="#carousel-example-generic" data-slide="next">
              <span class="glyphicon glyphicon-chevron-right"></span>
            </a>
          </div>
        </div>
      </div>
    </div>
  </div>
</section>

<section class="bg-info">
  <div class="row">
    <div class="col-md-8 col-md-offset-2">

      <%@include file="product-list.jsp" %>
    </div>

  </div>

  </section>
  <!-- /.container -->


  <!-- /.container -->

  <!-- jQuery -->
  <script src="/static/js/jquery.js"></script>

  <!-- Bootstrap Core JavaScript -->
  <script src="/static/js/bootstrap.min.js"></script>

  <script src="/static/js/cataleg.js"></script>

  </body>

</html>
