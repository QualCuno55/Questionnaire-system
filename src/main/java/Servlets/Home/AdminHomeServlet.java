package Servlets.Home;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/adminHome")
public class AdminHomeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext servletContext = getServletContext();
        request.setAttribute("name",String.valueOf(request.getSession().getAttribute("name")));

        // Разлог

        if(request.getParameter("logout")!=null){
            request.getSession().invalidate();
            RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher("/home");
            requestDispatcher.forward(request, response);
        }

        // Просмотр ответов

        if(request.getParameter("look")!=null){
            RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher("/lookOnAnswers");
            requestDispatcher.forward(request, response);
        }

        //  Создание опроса

        if(request.getParameter("create")!=null){
            RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher("/create");
            requestDispatcher.forward(request, response);
        }
        RequestDispatcher view = request.getRequestDispatcher("WEB-INF/HomeFromAdmins.jsp");
        view.forward(request, response);
    }
}
