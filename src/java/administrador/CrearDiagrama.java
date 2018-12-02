package administrador;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CrearDiagrama extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Crear Pregunta True or False</title>"); 
         
            out.println("<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js\"></script>");
            out.println("</head>");
            out.println("<body>");  //  out.println("");   out.println('');  <input type="reset"> 
            out.println("<form action='trueFalse' method='get'>");
            out.println("<div>");
            out.println("<table>");
                out.println("<tr>");
                    out.println("<td> ID de la pregunta </td><td><input type='text' name='id' required /></td>");
                out.println("</tr>");
                out.println("<tr>");
                    out.println("<td> Texto de la pregunta </td><td><input type='text' name='texto' required /></td>");
                out.println("</tr>");
                out.println("<tr>");
                    out.println("<td> Valor de la pregunta </td><td><input type='text' name='pond' required /></td>");
                out.println("</tr>");
                out.println("<tr>");
                    out.println("<td> Respuesta </td><td><select name=\"res\">\n" +
"                                          <option value=\"V\">Verdadero</option>\n" +
"                                          <option value=\"F\">Falso</option>\n" +
"                                          </select>");
                out.println("</tr>");
                out.println("<tr>");
                    out.println("<td> Agregar archivo </td><td><input type='file' name='archivo' /></td>");
                out.println("</tr>");
            out.println("</table");
            out.println("</div");
            out.println("<div>");
            out.println("<input type=\"reset\">");
            out.println("<input type=\"submit\" value=\"Crear\">");
            out.println("<input type=\"button\" value=\"Cancelar\" onclick=\"document.location='Maestro'\">");
            out.println("</div>");
            out.println("</form>");
            out.println("</body>");
            out.println("</html>");
        }
    }
    
}
