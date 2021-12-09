<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <style>
        body, p, h1, h2, h3 {
            margin: 0;
            font-family: sans-serif;
        }

        input {
            display: block;
            box-sizing: border-box;
        }

        .poll {
            width: 900px;
            margin: 30px auto;
            padding: 20px;
            background-color: #2e6b1d;
            border-radius: 10px;
        }

        .pollLabel {
            display: block;
            width: 100%;
            font-size: 25px;
            background-color: #b6ffa1;
            border-radius: 5px;
            border: 0;
            text-align: center;
            margin-bottom: 10px;
            color: black;
            height: 35px;
        }

        .pollText {
            display: block;
            width: 100%;
            text-align: center;
            margin-bottom: 30px;
            font-size: 20px;
            color: black;
            border: 0;
            height: 35px;
            background-color: #b6ffa1;
            font-size: 16px;
            border-radius: 5px;
        }

        .PollQuestions {
            margin-bottom: 25px;

        }

        .question {
            padding: 15px;
            border-radius: 5px;
            background-color: #b6ffa1;
            margin-bottom: 15px;
        }

        .question p {
            display: flex;
            column-gap: 10px;
            margin-bottom: 10px;
        }

        .questionLabel {
            margin-bottom: 10px;
        }

        .questionText {
            width: 100%;
            border-radius: 5px;
            height: 150px;
            resize: none;
            font-size: 16px;
            margin-bottom: 15px;
            padding: 5px;

        }

        .questionAnswers {
            list-style: none;
            padding: 0;
            margin: 0;
        }

        .questionAnswer {
            text-decoration: none;
            display: flex;
            column-gap: 15px;
            margin-bottom: 10px;
        }

        .submit {
            display: block;
            width: 200px;
            height: 35px;
            color: white;
            background-color: #4ea51a;
            border: 1px black solid;
            font-size: 20px;
            margin: 0 auto;
            cursor: pointer;
        }

        .removeButton {
            display: block;
            width: 200px;
            height: 35px;
            color: white;
            background-color: #fc5b5b;
            border: 1px black solid;
            font-size: 20px;
            margin: 40px auto;
            cursor: pointer;
        }
    </style>
</head>
<body>
<form class="poll" action="${pageContext.request.contextPath}/topicEdit">
    <input class="removeButton"type="submit" name="removeButton" value="Удалить">
</form>



<script>   //  найденный скрипт
const pollForm = document.querySelector(".poll");


fetch('${pageContext.request.contextPath}/get-topic?id=' + ${topicId})
    .then(response => response.json())
    .then(json => {
        let poll = json.payload;
        console.log(poll);
        const labelHTML = "<input class=\"pollLabel\" required name=\"pollLabel\" value=\"" + poll[0].name + "\" />";
        const textHTML = "<input class=\"pollText\" required name=\"pollText\" value=\"" + poll[0].description + "\" />";
        const submitHTML = "<input class=\"submit\" type=\"submit\" name='download'>";

        let questionsHTML = "<div class=\"PollQuestions\">";

        for (const [questionIndex, question] of poll[1].entries()) {
            let checked = "";
            if (question["type"] == "checkbox") {
                checked = "checked"
            }
            questionsHTML += "<div class=\"question\"><h2 class=\"questionLabel\">" +
                "Вопрос №" + (questionIndex + 1) + "</h2><textarea class=\"questionText\" required name=\"questionText" +
                (questionIndex + 1) + "\">" + question.question + "</textarea><p>Вопрос с мультивыбором?" +
                "<input class=\"isMulti\" name=\"isMulti" + (questionIndex + 1) + "\" type=\"checkbox\" " +
                checked + "></p><ul class=\"questionAnswers\">";

            for (const answer of poll[2][0][question.id]) {
                questionsHTML += "<li class=\"questionAnswer\"><input required type=\"text\" name=\"answer" + (questionIndex + 1) +
                    "\" value=\"" + answer + "\"></li>";
            }

            questionsHTML += "</ul></div>";
        }

        questionsHTML += "</div>";

        pollForm.insertAdjacentHTML("beforeend", labelHTML + textHTML + questionsHTML + submitHTML);

    });



</script>
</body>
</html>
