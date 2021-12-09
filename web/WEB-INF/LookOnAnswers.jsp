<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <style>
        body {
            font-family: sans-serif;
            font-size: 20px;
        }

        body div {
            width: 900px;
            padding: 20px;
            margin: 30px auto;
            background-color: #b6ffa1;
            border-radius: 10px;
        }

        body p {
            width: 900px;
            margin: 10px auto;
            border-radius: 10px;
        }
    </style>
</head>
<body>
<form action="${pageContext.request.contextPath}/lookOnAnswers">
    <div>
        <p> Выбор пользователя:
            <select multiple size="3" name="multiSelectUser">
                <c:forEach items="${array}" var="item">
                    <option>${item}</option>
                </c:forEach>
            </select>
        </p>
        <input type="submit" name="selectUser" value="Выбрать">
        <p> Выбор опроса:
            <select multiple size="3" name="multiSelectTopic">
                <c:forEach items="${arrayOfTopics}" var="item">
                    <option>${item}</option>
                </c:forEach>
            </select>
        </p>
        <a><input type="submit" name="selectTopic" value="Выбрать"></a>
    </div>
    <c:forEach items="${arrayOfResults}" var="item">

        <c:forEach items="${item}" var="item1">
            <p>${item1.key}</p>
            <p>${item1.value}</p><br>
        </c:forEach>

    </c:forEach>
<input type="submit" name="back", value="Вернуться на главную">
</form>
</body>
</html>
