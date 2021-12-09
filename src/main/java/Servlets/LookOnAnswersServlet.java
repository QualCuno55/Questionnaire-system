package Servlets;

import DAO.TopicDAO;
import DAO.UsersAnswersDAO;
import DAO.UsersDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@WebServlet("/lookOnAnswers")
public class LookOnAnswersServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext servletContext = getServletContext();

        //  Проверка роли
        if (request.getSession().getAttribute("role") == null) {
            RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher("/falseRole");
            requestDispatcher.forward(request, response);
        } else if (!request.getSession().getAttribute("role").equals("Admin")) {
            RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher("/falseRole");
            requestDispatcher.forward(request, response);
        } else {


             UsersDAO usersDAO = new UsersDAO();
             TopicDAO topicDAO = new TopicDAO();
             UsersAnswersDAO usersAnswersDAO = new UsersAnswersDAO();

             Set<String> users = usersAnswersDAO.getAllUsers();
            request.setAttribute("array", users);

            if (request.getParameter("selectUser") != null) {
                request.getSession().setAttribute("multiSelectUser", request.getParameter("multiSelectUser"));
            }


             Set<String> topics = usersAnswersDAO.getAllTopicsFromUser((String) request.getSession().getAttribute("multiSelectUser"));

             ArrayList<String> arrayList1 = new ArrayList<>(topics);
            request.setAttribute("arrayOfTopics", arrayList1);


            if (request.getParameter("selectTopic") != null) {

                  Set<Map> listAllQuesAndAnswers = usersAnswersDAO.getAllQuesAndAnswers(
                        topicDAO.getId(request.getParameter("multiSelectTopic")),
                        usersDAO.getId((String) request.getSession().getAttribute("multiSelectUser")));
                request.setAttribute("arrayOfResults", listAllQuesAndAnswers);


            }
            // Выход на главную
            if(request.getParameter("back")!=null){
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


            RequestDispatcher view = request.getRequestDispatcher("WEB-INF/LookOnAnswers.jsp");
            view.forward(request, response);
        }
    }
}
