<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Meals</title>
    <style>
        .red {
            color: red;
        }

        .green {
            color: green;
        }
    </style>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<p></p>
<a href="edit.jsp">Add meal</a>
<p></p>
<table border="1" cellpadding="4" cellspacing="0">
    <tr>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
        <th></th>
        <th></th>
    </tr>
    <c:forEach items="${meals}" var="meal">
        <tr class="<c:out value='${meal.isExcess() ? "red" : "green"}' />">
            <td>${formater.format(meal.getDateTime())}</td>
            <td>${meal.getDescription()}</td>
            <td>${meal.getCalories()}</td>
            <td></td>
            <td><a href="meals?uuid=${meal.getUUID()}&action=delete">Delete</a> </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
