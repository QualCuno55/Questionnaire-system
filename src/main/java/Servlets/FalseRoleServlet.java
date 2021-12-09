package Servlets;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/falseRole")
public class FalseRoleServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext servletContext = getServletContext();

        // Выход

        if (request.getParameter("back") != null) {
            HttpSession session = request.getSession();


            switch (String.valueOf(session.getAttribute("role"))) {
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
        RequestDispatcher view = request.getRequestDispatcher("WEB-INF/FalseRole.jsp");
        view.forward(request, response);
    }
}
