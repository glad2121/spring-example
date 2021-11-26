<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="f" uri="/WEB-INF/tld/functions.tld" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Greeting</title>
</head>
<body>
<h1>Greeting</h1>
<p>Hello, ${name}!</p>
<p>${f:number(1234567, "#,###")}</p>
<p>${f:warekiText("20211023")}</p>
</body>
</html>
