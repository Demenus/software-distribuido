<%@include file="../../common/header2.jsp"%>
<body>
<!--[if lt IE 7]>
<p class="chromeframe">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> or <a href="http://www.google.com/chromeframe/?redirect=true">activate Google Chrome Frame</a> to improve your experience.</p>
<![endif]-->

<%@include file="../../common/main-nav.jsp" %>

<!-- Page Title -->
<section class="bg-black">
  <div class="my-section my-section-breadcrumb">
    <div class="container">
      <div class="row">
        <div class="col-md-12">
          <h1>My Cloud</h1>
        </div>
      </div>
    </div>
  </div>
</section>

<div id="purchase-success" class="row" style="display: none;">
  <div class="col-md-10 col-md-offset-1">
    <div class="row alert alert-success text-center" role="alert"><h4>Product purchased!</h4></div>
  </div>
</div>


<div class="section">
  <div class="row">
    <div class="col-md-2">
      <ul id="select-list" class="list-group" style="padding-top: 20px;">
        <li id="select-purchased" class="list-group-item active"><a>Purchased</a></li>
        <li id="select-cartlist" class="list-group-item"><a>Cartlist</a></li>
      </ul>
    </div>
    <div class="col-md-9">
        <div id="purchased-div">
          <%@include file="mycloud-purchased-items.jsp"%>
        </div>
        <div id="cartlist-div">
          <%@include file="mycloud-cartlist-items.jsp"%>
        </div>
    </div>
  </div>
</div>

<!-- Javascripts -->
<!-- jQuery -->
<script src="/static/js/jquery.js"></script>

<!-- Bootstrap Core JavaScript -->
<script src="/static/js/bootstrap.min.js"></script>

<script src="/static/js/mycloud.js"></script>

</body>
</html>