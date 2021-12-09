package Servlets;

import DAO.*;
import Models.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;


@WebServlet("/topic")
public class TopicServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext servletContext = getServletContext();


        // Проверка на логин юзера
        if (request.getSession().getAttribute("role") != null) {
            int id;
            if (request.getSession().getAttribute("topicId") != null) {
                id = (int) request.getSession().getAttribute("topicId");
            } else {
                id = Integer.parseInt(request.getParameter("id"));
            }

            UsersAnswersDAO usersAnswersDAO = new UsersAnswersDAO();

            // Проверка на повторное прохождение

            if (usersAnswersDAO.check(String.valueOf(request.getSession().getAttribute("name")), id)) {
                UsersDAO usersDAO = new UsersDAO();
                User user = usersDAO.select(String.valueOf(request.getSession().getAttribute("login")),
                        String.valueOf(request.getSession().getAttribute("password")));

                int topicId = 0;

                int userId = user.getId();

                // Выгрузка ID опроса из сессии

                if (request.getSession().getAttribute("topicId") != null) {
                    topicId = (int) request.getSession().getAttribute("topicId");
                }


                if (request.getParameter("id") != null) {

                    topicId = Integer.parseInt(request.getParameter("id"));
                    request.getSession().setAttribute("topicId", topicId);

                }


                if (request.getParameter("submit") != null) {
                    TopicDAO topicDAO = new TopicDAO();
                    AnswerDAO answerDAO = new AnswerDAO();

                    // Удаление старых ответов

                    usersAnswersDAO.delete(topicId, userId);

                    // Добавление ответов в бд

                    for (int i = 0; i <= topicDAO.count(topicId); i++) {
                        if (request.getParameter("answerType" + i) != null) {
                            String[] massOfAnswers = request.getParameterValues("answerType" + i);
                            Map<Integer, ArrayList> mapOfId = answerDAO.getAllId(massOfAnswers);
                            usersAnswersDAO.add(mapOfId, topicId, userId);

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
            } else {

                UsersDAO usersDAO = new UsersDAO();

                User user = usersDAO.select(String.valueOf(request.getSession().getAttribute("login")),
                        String.valueOf(request.getSession().getAttribute("password")));
                int userId = user.getId();

                int topicId = 0;

                if (request.getSession().getAttribute("topicId") != null) {
                    topicId = (int) request.getSession().getAttribute("topicId");
                }


                if (request.getParameter("id") != null) {

                    topicId = Integer.parseInt(request.getParameter("id"));
                    request.getSession().setAttribute("topicId", topicId);

                }


                if (request.getParameter("submit") != null) {
                    TopicDAO topicDAO = new TopicDAO();
                    AnswerDAO answerDAO = new AnswerDAO();

                    // Добавление ответов в бд
                    for (int i = 0; i <= topicDAO.count(topicId); i++) {
                        if (request.getParameter("answerType" + i) != null) {
                            String[] massOfAnswers = request.getParameterValues("answerType" + i);
                            Map<Integer, ArrayList> mapOfId = answerDAO.getAllId(massOfAnswers);
                            usersAnswersDAO.add(mapOfId, topicId, userId);
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


            }
            RequestDispatcher view = request.getRequestDispatcher("WEB-INF/Topic.jsp");
            view.forward(request, response);
        } else {

            RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher("/falseRole");
            requestDispatcher.forward(request, response);

        }
    }

}








