package Servlets.Home;

import DAO.UsersDAO;
import Models.User;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;


@WebServlet("/home")
public class Home extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UsersDAO usersDAO = new UsersDAO();
        User user = new User();

        if (request.getParameter("loginEnter") != null) {

            String login = request.getParameter("log");
            String password = request.getParameter("pass");
            boolean check = usersDAO.check(login, password);

            user = usersDAO.select(login, password);
            String role = user.getRole();
            String name = user.getName();

            // Логин
            if (check) {
                request.getSession().setAttribute("name", name);
                request.getSession().setAttribute("login", login);
                request.getSession().setAttribute("password", password);
                request.getSession().setAttribute("role", role);
                switch (role) {
                    case ("User"): {
                        RequestDispatcher view = request.getRequestDispatcher("WEB-INF/HomeFromUsers.jsp");
                        view.forward(request, response);
                    }
                    case ("Admin"): {
                        RequestDispatcher view = request.getRequestDispatcher("WEB-INF/HomeFromAdmins.jsp");
                        view.forward(request, response);
                    }
                }

            }

            // Регистрация
        } else if (request.getParameter("registerEnter") != null) {
            user.setLogin(request.getParameter("addLog"));
            user.setPass(request.getParameter("addPass"));
            user.setName(request.getParameter("addName"));
            user.setRole("User");
            usersDAO.add(user);
        }

        RequestDispatcher view = request.getRequestDispatcher("WEB-INF/index.jsp");
        view.forward(request, response);
    }
}
