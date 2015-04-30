<%
  if (request.getParameter("logoff") != null) {
    session.invalidate();
  }
%>

<html>
<head>
<title>P�gina p�blica</title>
</head>
<body bgcolor="white">

P�gina p�blica per a la que no es necessita autentificaci�.

<br/>
<br/>

Tornar a la p�gina <a href="../index.jsp">inicial</a> de l'exemple.

</body>
</html>
