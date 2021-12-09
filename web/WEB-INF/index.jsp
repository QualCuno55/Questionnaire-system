<%@ page contentType="text/html;charset=UTF-8"
         language="java"

%>
<html>
<head>
    <style><%@include file="CSS/Home.css" %> </style>
</head>

<body>

<header class="header">
    <form class="loginForm" action="${pageContext.request.contextPath}/home">
        <p class="loginText">Пожалуйста, войдите</p>
        <div class="loginInputs">
            <input class="loginTextInput" type="text" name="log" required placeholder="Введите логин">
            <input class="loginTextInput" type="password" name="pass" required placeholder="Введите пароль">
            <input type="submit" onclick="alerted()" name="loginEnter" value="Войти">
        </div>
        <p class="loginText smallText">Нет аккаунта? <span class="registerLink">Зарегистрируйтесь!</span></p>
    </form>
</header>

<div class="polls">
    <section class="pollsGallery"></section>
</div>

<form class="registerForm" action="${pageContext.request.contextPath}/home">
    <input class="registerTextInput" type="text" name="addName" required placeholder="Как вас зовут?">
    <input class="registerTextInput" type="text" name="addLog" required placeholder="Придумайте логин">
    <input class="registerTextInput" type="password" name="addPass" required placeholder="Придумайте пароль">
    <input class="registerSubmit" type="submit" name="registerEnter" value="Зарегистрироваться">
    <button class="registerCancel">Я передумал</button>
</form>


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
                poll.id + "\"></a><a class=\"pollEditLink\" href=\"" +
                editUrl + "?id=" + poll.id + "\"></a></div>";

            pollsGallery.insertAdjacentHTML("beforeend", cardHTML);
        }
    });

const registerForm = document.querySelector(".registerForm");
const registerLink = document.querySelector(".registerLink");
const registerSubmit = document.querySelector(".registerSubmit");
const registerCancel = document.querySelector(".registerCancel");

registerLink.addEventListener("click", () => {
    registerForm.classList.toggle("active");
})
registerSubmit.addEventListener("click", () => {
    registerForm.classList.toggle("active");
})
registerCancel.addEventListener("click", () => {
    registerForm.classList.toggle("active");
})


</script>

</body>


</html>
