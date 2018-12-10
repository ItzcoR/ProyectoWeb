package administrador;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class Alumno extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session=request.getSession();
        String id=(String) session.getAttribute("id");
        String tipo=(String) session.getAttribute("tipo");
        if(id!=null)
        {
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html style='height:100%; width:100%; margin:0px;'>");
            out.println("<head>");
            out.println("<title>Servlet Alumno</title>"); 
            out.println("<meta charset=\"utf-8\">\n" +
            "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
            "<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js\"></script>\n" +
            "<script src=\"https://cdn.jsdelivr.net/npm/vue/dist/vue.js\"></script>");
            out.println("</head>");
            out.println("<body >");
            out.println("<h1>Bienvenido "+tipo+": "+id+"</h1>");
            out.println("<div>\n" +
            "  <div ></div>\n" +
            "  <div ><a href='cerrar'>Cerrar Sesion</a></div>\n" +
            "</div>");


            
                /////////////////////////////////////////////////////////////////////////////////////////////////////////
                // Footer
                /////////////////////////////////////////////////////////////////////////////////////////////////////////
                out.println(
                        "<script src=\"js/vendor/modernizr-3.6.0.min.js\"></script>\n" +
                        "<script src=\"https://code.jquery.com/jquery-3.3.1.min.js\" integrity=\"sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8=\" crossorigin=\"anonymous\"></script>\n" +
                        "<script>window.jQuery || document.write('<script src=\"js/vendor/jquery-3.3.1.min.js\"><\/script>')</script>\n" +
                        "<script src=\"js/plugins.js\"></script>\n" +
                        "<script src=\"js/main.js\"></script>\n" +

                        "<!-- Google Analytics: change UA-XXXXX-Y to be your site's ID. -->\n" +
                        <
                        script > \n "+
                        "  window.ga = function () { ga.q.push(arguments) }; ga.q = []; ga.l = +new Date;\n" +
                        "  ga('create', 'UA-XXXXX-Y', 'auto'); ga('send', 'pageview')\n" +
                        "</script>\n" +
                        " <script src=\"https://www.google-analytics.com/analytics.js\" async defer></script>\n" +
                        "</body>\n" +

                        "</html>");
        }
        }
    }
}
