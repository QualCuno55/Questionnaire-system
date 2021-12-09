package Servlets.Home;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/userHome")
public class UserHomeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("name", String.valueOf(request.getSession().getAttribute("name")));
        ServletContext servletContext = getServletContext();

        //Разлог

        if (request.getParameter("logout") != null) {
            request.getSession().invalidate();
            RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher("/home");
            requestDispatcher.forward(request, response);
        }
        RequestDispatcher view = request.getRequestDispatcher("WEB-INF/HomeFromUsers.jsp");
        view.forward(request, response);
    }
}
