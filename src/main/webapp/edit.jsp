<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${title}</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h3>${title}</h3>
<form method="post" action="meals" enctype="application/x-www-form-urlencoded">
    <input type="hidden" name="id" value="${meal.id}">
    <p>DataTime: <input type="datetime-local" name="localDate" size=55 value="${time}"></p>
    <p>Description: <input type="text" name="description" size=55 value="${meal.description}"></p>
    <p>Calories: <input type="text" name="calories" size=55 value="${meal.calories}"></p>
    <button type="submit">Save</button>
    <button type="button" onclick="goBack()">Back</button>
    <script>
        function goBack() {
            window.history.back();
            event.preventDefault();
        }
    </script>
</form>
</body>
</html>
