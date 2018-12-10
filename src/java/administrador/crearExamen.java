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
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Creacion de Examen</title>"); 
            out.println("<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js\"></script>");
            out.println("<script src=\"https://cdn.jsdelivr.net/npm/vue/dist/vue.js\"></script>");
            out.println("<script type =\"text/javascript\">\n" +
"            function letraNumVal(e) {\n" +
"                tecla = document.all ? e.keyCode : e.which;\n" +
"                if (tecla === 8 || tecla === 32)\n" +
"                    return true;\n" +
"                patron = /[a-z]|[A-Z]|á|é|í|ó|ú|Á|É|Í|Ó|Ú|[0-9]/;\n" +
"                te = String.fromCharCode(tecla);\n" +
"                return patron.test(te);\n" +
"            }\n" +
"            function numVal(e) {\n" +
"                tecla = document.all ? e.keyCode : e.which;\n" +
"                if (tecla === 8 || tecla === 32)\n" +
"                    return true;\n" +
"                patron = /[0-9]/;\n" +
"                te = String.fromCharCode(tecla);\n" +
"                return patron.test(te);\n" +
"            }" );
            out.println("</script>"); 
            out.println("</head>");
            out.println("<body>");  //  out.println("");   out.println('');
            out.println("<h1 >Estas son las preguntas Seleccionadas </h1>");
            for(int i=0;i<npregs;i++){
                out.println("<h2>Pregunta "+idpreg[i]+" agregada</h2>");
            }
            out.println("<form action='ExamenXML' method='get'>");
                out.println("<h1 >Proporciona un nombre y un ID, para el examen. El ID debe ser único</h1>");
                out.println("ID");
                out.println("<input type='text' name='id' onkeypress=\"return letraNumVal(event)\" required />");
                out.println("Nombre");
                out.println("<input type='text' name='nombre' onkeypress=\"return letraNumVal(event)\" required />");
            
                out.println("<input type=\"reset\">");
                out.println("<input type=\"submit\" value=\"Crear\">");
            out.println("</form>");
            out.println(" <div ><a  href='Maestro'>Regresar</a></div>\n" +
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
