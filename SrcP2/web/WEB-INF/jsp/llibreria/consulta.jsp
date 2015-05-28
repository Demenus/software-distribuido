<%@include file="../common/header2.jsp"%>
<body>
<!--[if lt IE 7]>
<p class="chromeframe">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> or <a href="http://www.google.com/chromeframe/?redirect=true">activate Google Chrome Frame</a> to improve your experience.</p>
<![endif]-->

<%@include file="../common/main-nav.jsp" %>

<!-- Page Title -->
<section class="bg-info">
  <div class="my-section my-section-breadcrumb">
    <div class="container">
      <div class="row">
        <div class="col-md-8">
          <h1>Consulta</h1>
        </div>
      </div>
    </div>
  </div>
</section>

<div class="row">
  <div class="col-md-12">
    <button id="update-btn" class="btn btn-info pull-right">Search web-apis</button>
    <input id="search-product" class="pull-right" type="text">
  </div>
</div>

<!-- Javascripts -->
<!-- jQuery -->
<script src="/static/js/jquery.js"></script>

<!-- Bootstrap Core JavaScript -->
<script src="/static/js/bootstrap.min.js"></script>

<script type="text/javascript" src="/static/js/async.js"></script>

<script src="/static/js/consulta.js"></script>

</body>
</html>