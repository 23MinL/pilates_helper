<%--
  Created by IntelliJ IDEA.
  User: morgan
  Date: 25. 3. 12.
  Time: 오후 12:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <p>결과 페이지</p>
    <% if (session.getAttribute("answer") != null) { %>
        <p>question: <%= session.getAttribute("question")%> </p>
        <p>answer: <%= session.getAttribute("answer")%> </p>
        <p>thinking: <%= session.getAttribute("thinking")%> </p>
        <p>reasoning: <%= session.getAttribute("reasoning")%> </p>
        <div>
            <%= session.getAttribute("image")%>">
<%--            <img alt="<%= session.getAttribute("answer")%>" src="<%= session.getAttribute("image")%>">--%>
        </div>
    <% } %>
</body>
</html>
