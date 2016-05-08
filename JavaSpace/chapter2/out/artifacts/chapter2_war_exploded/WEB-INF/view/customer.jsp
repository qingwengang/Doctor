<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="BASE" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title></title>
</head>
<body>
customer list<br>fsdfs
<table>
    <c:forEach var="customer" items="${customerList}">
        <tr>
            <td>${customer.name}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>