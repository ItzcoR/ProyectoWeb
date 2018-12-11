package administrador;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

public class crearExamen extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        String xml=request.getRealPath("WEB-INF\\ProtoExam.xml");
        HttpSession session=request.getSession();
        String[] idpreg=request.getParameterValues("agregarExamen");
        int npregs=idpreg.length;
        
        session.setAttribute("idPreguntas",idpreg);
        //String resultado=agregarAlExamen(xml,"ExamenPrueba2","idpruebas2",idpreg);
        try (PrintWriter out = response.getWriter()) {
///////////////////////////////////////////////////////////////////////////////////////////
// ------------  HEADER  ------------------------------------------------------------------
///////////////////////////////////////////////////////////////////////////////////////////
out.println("<!DOCTYPE html>");
out.println("<html>");
out.println("<head>");
out.println("<title>Servlet Adm</title>"); 
out.println("<meta charset=\"utf-8\">\n" +
"<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
"<script src=\"js/vendor/modernizr-3.6.0.min.js\"></script>\n" +
"<script src=\"https://code.jquery.com/jquery-3.3.1.min.js\" integrity=\"sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8=\" crossorigin=\"anonymous\"></script>\n" +
"<script src=\"js/plugins.js\"></script>\n" +
"<script src=\"js/main.js\"></script>\n" +
"<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js\"></script>\n" +
"<script src=\"https://cdn.jsdelivr.net/npm/vue/dist/vue.js\"></script>\n" +
"<link rel=\"stylesheet\" href=\"css/normalize.css\">\n" +
"<link rel=\"stylesheet\" href=\"css/main.css\">\n" +
"<link rel=\"stylesheet\" href=\"https://use.fontawesome.com/releases/v5.5.0/css/all.css\" integrity=\"sha384-B4dIYHKNBt8Bc12p+WXckhzcICo0wtJAoU8YZTY5qE0Id1GSseTk6S+L3BlXeVIU\" crossorigin=\"anonymous\">\n" +
"</head>\n" +
"<body>\n" +
"<div class=\"contenedor_barra\">\n"+
"<h1>Sistema evaluador</h1>\n"+
"</div>");



///////////////////////////////////////////////////////////////////////////////////////////
// ------------  CONTENIDO  ---------------------------------------------------------------
///////////////////////////////////////////////////////////////////////////////////////////   
            out.println(
                "<div class=\"contenedor bg_amarillo sombra\">\n"+
                    "<form action='ExamenXML' method='get'>\n"+
                        "<div class=\"contenedor_alertas\">\n"+
                            "<legend>Estas son las preguntas Seleccionadas<span>Proporciona un nombre y un ID, para el examen.El id debe de ser unico</span> </legend>\n"+
                            "<div class=\"campos_usuario\">\n"+

                    
                                "<div class=\"campo_usuario\">\n"+
                        
                                "<label for=\"nombre\">Id:</label>\n"+
                                "<input type='text' name='id' onkeypress=\"return letraNumVal(event)\" required autocomplete=\"off\"/>\n"+
                        
                                "</div>\n"+

                                "<div class=\"campo_usuario\">\n"+
                        
                                "<label for=\"nombre\">Nombre:</label>\n"+
                                "<input type='text' name='nombre' onkeypress=\"return letraNumVal(event)\" required autocomplete=\"off\" />\n"+
                        
                                "</div>\n"+
                            
                            "</div>\n"+


                            "<div class=\"campo_usuario enviar\">\n"+
                                
                                "<input class=\"btn btn-info btn_rosa\" type=\"reset\">\n"+
                                "<input class=\"btn btn-info btn_rosa\" type=\"submit\" value=\"Crear\">\n"+
                                "<a class=\"btn btn-info btn_rosa\" href='Maestro'>Regresar</a>\n"+

                            "</div>\n"+



                        "</div>\n"+
                    "</form>\n"+
                "</div>"
                );



////////////////////////////////////////////////////////////////////////////////////////////
// ------------  FOOTER  -------------------------------------------------------------------
///////////////////////////////////////////////////////////////////////////////////////////               
out.println("</body>");
out.println("</html>");  
        }
    }
}
