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


@WebServlet("/topicEdit")
public class TopicEditServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext servletContext = getServletContext();
        // Проверка роли
        if (request.getSession().getAttribute("role") != null) {
            if (!request.getSession().getAttribute("role").equals("Admin")) {
                RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher("/falseRole");
                requestDispatcher.forward(request, response);
            }

        } else if (request.getSession().getAttribute("role") == null) {

            RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher("/falseRole");
            requestDispatcher.forward(request, response);
        }

        if (request.getParameter("id") != null) {
            if (request.getSession() != null) {
                request.getSession().setAttribute("id", request.getParameter("id"));
            }
        }


        if (request.getSession() != null) {
            request.setAttribute("topicId", request.getSession().getAttribute("id"));
        }

        QuestionDAO questionDAO = new QuestionDAO();
        TopicDAO topicDAO = new TopicDAO();
        AnswerDAO answerDAO = new AnswerDAO();

        //  Удаление опроса
        if (request.getParameter("removeButton") != null) {
            topicDAO.delete(Integer.parseInt((String) request.getSession().getAttribute("id")));

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

        //  Создание измененного опроса
        if (request.getParameter("download") != null) {


             int count = questionDAO.count(Integer.parseInt((String) request.getSession().getAttribute("id")));

            String name = request.getParameter("pollLabel");
            String desc = request.getParameter("pollText");

            int topicId1 = topicDAO.add(name, desc);

            for (int i = 1; i <= count; i++) {
                String ansType = "";
                String ques = request.getParameter("questionText" + i);
                if (request.getParameter("isMulti" + i) != null) {
                    ansType = "checkbox";
                } else {
                    ansType = "radio";
                }
                String[] answers = request.getParameterValues("answer" + i);


                int questionId = questionDAO.add(ques, topicId1, ansType);

                for (String s : answers) {

                    answerDAO.add(s, questionId);
                }
            }
             //  Удаление старого опроса
            topicDAO.delete(Integer.parseInt((String) request.getSession().getAttribute("id")));


            //  Переход на главную
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


        RequestDispatcher view = request.getRequestDispatcher("WEB-INF/TopicEdit.jsp");
        view.forward(request, response);
    }
}
