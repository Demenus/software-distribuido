<%@include file="../common/header.jsp"%>
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

</body>

</html>