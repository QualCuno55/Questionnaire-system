<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"
         isELIgnored="false"
%>
<html lang="en" dir="ltr">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Редактор опросников</title>
    <style><%@include file="CSS/CreateStyle.css" %> </style>

</head>

<body>
<div class="wrapper">
    <h1>Новый опрос</h1>

    <form class="makePool" action="${pageContext.request.contextPath}/create">
        <input class="label" name="label" type="text" placeholder="Название анкеты" required/>
        <input class="counter" name="counter" type="number" required value="0">
        <textarea class="description" name="description" placeholder="Описание"></textarea>

        <div class="questions"></div>

        <input class="submit" type="submit" name="download" value="Загрузить форму">
    </form>

    <div class="addQuestion">
        <button class="addQuestionButton">Добавить вопрос!</button>
        <div>
            <span>Число ответов: </span>
            <input class="addQuestionNumber" value="2" type="number" min="2" max="20"/>
        </div>
        <div>
            <span>Мульти выбор: </span>
            <input class="addQuestionMultianswer" type="checkbox"/>
        </div>
    </div>
</div>
<script>  // найденный скрипт
const questionsBlock = document.querySelector(".questions");

const addQuestionButton = document.querySelector(".addQuestionButton");
const addQuestionNumber = document.querySelector(".addQuestionNumber");
const addQuestionMultianswer = document.querySelector(".addQuestionMultianswer");
const counterInput = document.querySelector(".counter");

let counter = 1;

const addQuestion = () => {
    const numOfAnswers = addQuestionNumber.value;
    let isMultiAnswer = addQuestionMultianswer.checked;

    let status;
    let answersType;

    if (isMultiAnswer) {
        status = "(с мультивыбором)";
        answersType = "checkbox";
    } else {
        status = "(без мультивыбора)"
        answersType = "radio";
    }

    let answersHTML = "";
    x = 0;

    while (x < numOfAnswers) {
        answersHTML += "<li><input class=\"answer\" type=\"text\" name=\"answer" + counter + "\" required/></li>";
        x++;
    }

    const questionHTML = "<div class=\"question\"><h3 class=\"questionLabel\">Вопрос " + counter + " " + status +
        "</h3><textarea class=\"questionText\" name=\"questionText" + counter +
        "\" placeholder=\"Текст вопроса\" required></textarea><input name=\"answersType" +
        counter + "\" class=\"answerType\" type=\"text\" value=\"" + answersType +
        "\"><h4>Варианты ответов</h4><ul class=\"answers\">" + answersHTML + "</ul></div>";

    counterInput.value = counter;
    counter++;
    questionsBlock.insertAdjacentHTML("beforeend", questionHTML);
    console.log(counterInput);
    console.log(counterInput.value);
}

addQuestionButton.addEventListener("click", addQuestion);
</script>


</body>

</html>
