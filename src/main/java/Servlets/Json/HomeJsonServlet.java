package Servlets.Json;

import DAO.TopicDAO;
import Models.Topic;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@WebServlet("/get-polls")
public class HomeJsonServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        PrintWriter out = response.getWriter();

        JSONObject jsonObject = new JSONObject();


        TopicDAO topicDAO = new TopicDAO();

        List<Topic> topics = topicDAO.selectAll();  // Плитка на главной


        jsonObject.put("polls", topics);

        out.print(jsonObject);


    }

}
