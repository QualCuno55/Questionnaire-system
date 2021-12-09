package Servlets;

import DAO.AnswerDAO;
import DAO.QuestionDAO;
import DAO.TopicDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/create")
public class CreateServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext servletContext = getServletContext();

        //Проверка роли

        if (request.getSession().getAttribute("role")!= null) {
            if(! request.getSession().getAttribute("role").equals("Admin")){
                RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher("/falseRole");
                requestDispatcher.forward(request, response);
            }

            } else if (request.getSession().getAttribute("role")== null){

            RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher("/falseRole");
            requestDispatcher.forward(request, response);
        }

        TopicDAO topicDAO = new TopicDAO();
        QuestionDAO questionDAO = new QuestionDAO();
        AnswerDAO answerDAO = new AnswerDAO();

        //Получение данных из формы

        if (request.getParameter("download") != null) {
            int a = Integer.parseInt(request.getParameter("counter"));
            String name = request.getParameter("label");
            String desc = request.getParameter("description");

            int topicId = topicDAO.add(name, desc);

            for (int i = 1; i <= a; i++) {

                String ques = request.getParameter("questionText" + i);
                String ansType = request.getParameter("answersType" + i);
                String[] answers = request.getParameterValues("answer" + i);


                int questionId = questionDAO.add(ques, topicId, ansType);

                for (String s : answers) {

                    answerDAO.add(s, questionId);
                }
            }

            // Выход

            switch (String.valueOf(request.getSession().getAttribute("role"))) {
                case ("User"): {
                    RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher("/userHome");
                    requestDispatcher.forward(request, response);
                }
                case ("Admin"): {
                    RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher("/adminHome");
                    requestDispatcher.forward(request, response);
                }
                case ("null"): {
                    RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher("/home");
                    requestDispatcher.forward(request, response);
                }
            }
        }


        RequestDispatcher view = request.getRequestDispatcher("WEB-INF/Create.jsp");
        view.forward(request, response);
    }


}




