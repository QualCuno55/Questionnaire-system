<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <style><%@include file="CSS/TopicStyle.css" %> </style>
</head>
<body>
<form class="poll" action="${pageContext.request.contextPath}/topic"></form>


<script>  // найденный скрипт
const pollForm = document.querySelector(".poll");

fetch('${pageContext.request.contextPath}/get-topic?id=' + ${topicId})
    .then(response => response.json())
    .then(json => {
        let poll = json.payload;
        const labelHTML = "<h1 class=\"pollLabel\">" + poll[0].name + "</h1>";
        const textHTML = "<p class=\"pollText\">" + poll[0].description + "</p>";
        const submitHTML = "<input class=\"submit\" type=\"submit\" name='submit'>";

        let questionsHTML = "<div class=\"PollQuestions\">";

        for (const [questionIndex, question] of poll[1].entries()) {
            questionsHTML += "<div class=\"question\"><h2 class=\"questionLabel\">" +
                "Вопрос №" + (questionIndex + 1) + "</h2><p class=\"questionText\">" +
                question.question + "</p><ul class=\"questionAnswers\">";

            for (const answer of poll[2][0][question.id]) {
                questionsHTML += "<li class=\"questionAnswer\"><input type=\"" +
                    question["questionType"] + "\" value=\"" + answer + "\" name=\"answerType"
                    + (questionIndex + 1) + "\" />" + answer + "</li>";
            }

            questionsHTML += "</ul></div>";
        }

        questionsHTML += "</div>";

        pollForm.insertAdjacentHTML("beforeend", labelHTML + textHTML + questionsHTML + submitHTML);
    });

</script>
</body>
</html>
