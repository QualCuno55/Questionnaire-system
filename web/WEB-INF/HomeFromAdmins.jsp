<%@ page contentType="text/html;charset=UTF-8"
         language="java"

%>
<html>
<head>
    <style><%@include file="CSS/Home.css" %> </style>
    <style><%@include file="CSS/HomeFromAdmins.css" %> </style>
</head>

<body>

<header class="header">
    <form class="loginForm" action="${pageContext.request.contextPath}/adminHome">
        <p class="loginText">Здраствуйте, ${name}</p>
        <div class="loginInputs">
            <input class="logoutButton" type="submit" name="logout" value="Выйти">
            <input class="viewAnswersButton" type="submit" value="Посмотреть ответы пользователей" name="look">
            <input class="createButton" type="submit" value="Создать опрос" name="create">
        </div>
    </form>
</header>

<div class="polls"><section class="pollsGallery"></section></div>



<script>  // найденный скрипт
    const viewUrl = "topic"
    const editUrl = "topicEdit"


    let polls;
    const pollsGallery = document.querySelector(".pollsGallery");

    fetch('${pageContext.request.contextPath}/get-polls')
        .then(response => response.json())
        .then(data => {
            console.log(data);

            polls = data.polls;

            for (const poll of polls) {
                let cardHTML = "<div class=\"pollCard\"><h3 class=\"pollLabel\">" +
                    poll.name + "</h1><p class=\"pollText\">" + poll.description +
                    "</p><a class=\"pollViewLink\" href=\"" + viewUrl + "?id=" +
                    poll.id + "\">Пройти</a><a class=\"pollEditLink\" href=\"" +
                    editUrl + "?id=" + poll.id + "\">Изменить</a></div>";

                pollsGallery.insertAdjacentHTML("beforeend", cardHTML);
            }
        });

</script>

</body>


</html>
