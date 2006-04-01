<%
response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
String newLocn = "yui";
response.setHeader("Location",newLocn);
%>

<html>
<body>
<h2>Hello World!</h2>
</body>
</html>
