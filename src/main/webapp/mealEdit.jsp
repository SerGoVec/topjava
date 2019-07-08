<%--
  Created by IntelliJ IDEA.
  User: SerGo
  Date: 07.07.2019
  Time: 19:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Meal</title>
    <style>
        dl{
            background: none repeat scroll 0 0 #FAFAFA;
            margin: 8px 0;
            padding: 0;
        }
        dt{
            display: inline-block;
            width: 170px;
        }
        dd{
            display: inline-block;
            margin-left: 8px;
            vertical-align: top;
        }
    </style>
</head>
<body>
<%-- section -  раздел документа --%>
<section>
    <h2><a href="">Home</a></h2>
    <h3>Edit meal</h3>
    <hr>
    <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.Meal" scope="request"></jsp:useBean>
    <%-- по action попадаем в сервлет meals и передаем данные по post --%>
    <form action="meals" method="post">
        <%--скрытое поле id--%>
        <input type="hidden" name="id" value="${meal.id}">
        <%-- input type="datetime-local" value... - плюшки html 5 --%>
        <dl>
            <dt>DateTime:</dt>
            <dd><input type="datetime-local" value="${meal.dateTime}" name="dateTime"></dd>
        </dl>
        <dl>
            <dt>Description:</dt>
            <dd><input type="text" value="${meal.description}" size="50" name="description"></dd>
        </dl>
        <dl>
            <dt>Calories:</dt>
            <dd><input type="number" value="${meal.calories}" name="calories"></dd>
        </dl>

        <%-- после submit данные пойдут meals (MealServlet) по post (doPost)--%>
        <button type="submit">Save</button>
        <%--откат истории назад--%>
        <button onclick="window.history.back()">Cancel</button>
    </form>
</section>
</body>
</html>
