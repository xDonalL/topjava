<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>
        <c:if test="${meal.description == null}">
            Add Meal
        </c:if>
        <c:if test="${meal.description != null}">
            Edit Meal
        </c:if>
    </title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h3>
    <c:if test="${meal.description == null}">
        Add Meal
    </c:if>
    <c:if test="${meal.description != null}">
        Edit Meal
    </c:if>
</h3>
<form method="post" action="meals" enctype="application/x-www-form-urlencoded">
    <input type="hidden" name="id" value="${meal.id}">
    <p>DataTime: <input type="datetime-local" name="localDate" size=55 value="${meal.dateTime}"></p>
    <p>Description: <input type="text" name="description" size=55 value="${meal.description}"></p>
    <p>Calories: <input type="number" id="calories" name="calories" size=55 value="${meal.calories}"></p>
    <button type="submit">Save</button>
    <button type="button" onclick="goBack()">Back</button>
    <script>
    </script>
    <script>
        function goBack() {
            window.history.back();
            event.preventDefault();
        }
    </script>
</form>
</body>
</html>
