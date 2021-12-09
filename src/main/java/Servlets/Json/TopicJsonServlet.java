package Servlets.Json;

import DAO.AnswerDAO;
import DAO.QuestionDAO;
import DAO.TopicDAO;
import Models.Question;
import Models.Topic;
import Models.UsersAnswers;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@WebServlet("/get-topic")
public class TopicJsonServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        QuestionDAO questionDAO = new QuestionDAO();
        TopicDAO topicDAO = new TopicDAO();
        AnswerDAO answerDAO = new AnswerDAO();

        UsersAnswers usersAnswers = new UsersAnswers();

        ArrayList JSON = new ArrayList();


        if (request.getParameter("id") != null) {
            usersAnswers.setId(Integer.parseInt(request.getParameter("id")));
        }


        Topic topic = topicDAO.select(usersAnswers.getId());


        ArrayList<Integer> questionsId = new ArrayList<>();

        List<ArrayList> listOfAnswers = answerDAO.selectAnswersForQuestion(questionsId);
        List<Question> questions = questionDAO.select(usersAnswers.getId());
        ArrayList<Map> listForAnswers = new ArrayList<>();

        for (Question q : questions) {
            questionsId.add(Integer.parseInt(q.getId()));
        }


        Map<String, ArrayList> answers = new TreeMap<>();

        for (int i = 0; i < listOfAnswers.size(); i++) {
            answers.put(Integer.toString(questionsId.get(i)), listOfAnswers.get(i));
        }


        listForAnswers.add(answers);


        JSON.add(topic);
        JSON.add(questions);
        JSON.add(listForAnswers);


        PrintWriter out = response.getWriter();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("payload", JSON);
        out.print(jsonObject);


    }
}

