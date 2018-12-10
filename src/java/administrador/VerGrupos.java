package administrador;

import java.io.File;
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
public class VerGrupos extends HttpServlet {

     @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session=request.getSession();
        /*String id=(String) session.getAttribute("id");
        String tipo=(String) session.getAttribute("tipo");*/
        String xml=request.getRealPath("WEB-INF\\practica2.xml");
        int aux=tamano(xml);
        String[] usuarios=new String[aux];
        usuarios=nombre(xml);
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html style='height:100%; width:100%; margin:0px;'>");
            out.println("<head>");
            out.println("<title>Servlet Adm</title>"); 
            out.println("<meta charset=\"utf-8\">\n" +
            "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
            "<link rel=\"stylesheet\" type=\"text/css\" href=\"estilos.css\" media=\"screen\"/>\n" +
            "<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css\">\n" +
            "<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js\"></script>\n" +
            "<script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js\"></script>");
            out.println("<script type =\"text/javascript\">");
            out.println("$(document).ready(function () {\n" +
"            $('#submitLogin').click(function (event) {\n" +
"                var nombreVar = $('#txtUsuario').val();\n" +
"                var apellidoVar = $('#txtPassword').val();\n" +
"\n" +
"                $.post('eliminar', {\n" +
"                    nombre: nombreVar,\n" +
"                    tipo: apellidoVar\n" +
"\n" +
"                }, function (responseText) {\n" +
"\n" +
"                    $('#texto').html(responseText);\n" +
"\n" +
"                });\n" +
"            });\n" +
"        });");
            out.println("</script>");
            out.println("</head>");
            out.println("<body class='colorful'>");
            out.println("<div class=\"container\">\n" +
            "<h2 class='blanco'>Bienvenido: </h2>           \n" +
            "<table class=\"table\">\n" +
            "   <thead>\n" +
            "     <tr class='blanco'>\n" +
            "       <th>Grupo</th>\n" +
            "       <th>Accion</th>\n" +
            "     </tr>\n" +
            "   </thead>\n" +
            "   <tbody>\n" +
            "     <tr>\n" +
            "       <td>");
            for(int i=0;i<aux;i++){
                out.println("<h5 id='txtUsuario' class='blanco'>"+usuarios[i]+"</h5>");
                out.println("<hr>");
            }
            out.println("</td>\n" +
            "");        
            /*for(int i=0;i<aux;i++){
                out.println("<h5 id='txtPassword' class='blanco'>"+usuarios[2][i]+"</h5>");
                out.println("<hr>");
            }*/
            out.println("\n" +
            "<td>");
            for(int i=0;i<aux;i++){
                out.println("<button class='btn-info'><a class='blanco' href='ModGrupo?id="+usuarios[i]+"'>Modificar</a></button>");
                out.println("<button class='btn-info'><a class='blanco' href='EliminarGrupo?id="+usuarios[i]+"'>Eliminar</a></button>");
                out.println("<hr>");    
            }
            
            out.println("</td>\n" +
            "      </tr>\n" +
            "    </tbody>\n" +
            "  </table>\n" +
            "</div>");
            out.println("<div class=\"row\">\n" +
            "  <div class=\"col-sm-1\"></div>\n" +
            "  <div class=\"col-sm-1\"></div>\n" +
            "  <div class=\"col-sm-4\"><button class='btn-info'><a class='blanco' href='Administrador'>Cancelar</a></button></div>\n" +
            
            "</div>");
            
            out.println("<div id=\"tabla\">\n" +
"        <p id=\"texto\" style=\"color:red;\"></p>\n" +
"    </div>");
            /////////////////////////////////////////////////////////////////////////////////////////////////////////
            // Footer
            /////////////////////////////////////////////////////////////////////////////////////////////////////////
            out.println(
                    "<script src=\"js/vendor/modernizr-3.6.0.min.js\"></script>\n" +
                    "<script src=\"https://code.jquery.com/jquery-3.3.1.min.js\" integrity=\"sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8=\" crossorigin=\"anonymous\"></script>\n" +
                    "<script>window.jQuery || document.write('<script src=\"js/vendor/jquery-3.3.1.min.js\"></script>')</script>\n"+
                    "<script src=\"js/plugins.js\"></script>\n" +
                    "<script src=\"js/main.js\"></script>\n" +

                    "<!-- Google Analytics: change UA-XXXXX-Y to be your site's ID. -->\n" +
                    "<script > \n "+
                    "  window.ga = function () { ga.q.push(arguments) }; ga.q = []; ga.l = +new Date;\n" +
                    "  ga('create', 'UA-XXXXX-Y', 'auto'); ga('send', 'pageview')\n" +
                    "</script>\n" +
                    " <script src=\"https://www.google-analytics.com/analytics.js\" async defer></script>\n" +
                    "</body>\n" +

                    "</html>");
        }
    }
public int tamano(String direc)
    {
        int aux=0;
        try{
            /*SAXBuilder se encarga de cargar el archivo XML del disco o de un String */
            SAXBuilder builder=new SAXBuilder();
            //Forma de abriri el archivo
            
            File xmlFile = new File(direc);
            /*Almacenamos el xml cargado en builder en un documento*/
            Document bd_xml=builder.build(xmlFile);
            //Elemento raiz
            Element raiz=bd_xml.getRootElement();
            //Se almacenan los hijos en una lista
            List hijos=raiz.getChildren();
            for(int i=0;i<hijos.size();i++)
            {
                
                   aux++;
                
            }
        } catch (JDOMException | IOException ex) {
            Logger.getLogger(VerGrupos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return aux;
    }
    
    public String[] nombre(String direc)
    {
        int aux=tamano(direc);
        int aux2=0;
        String[] nombU=new String[aux];
        try{
            /*SAXBuilder se encarga de cargar el archivo XML del disco o de un String */
            SAXBuilder builder=new SAXBuilder();
            //Forma de abriri el archivo
            File xmlFile = new File(direc);
            /*Almacenamos el xml cargado en builder en un documento*/
            Document bd_xml=builder.build(xmlFile);
            //Elemento raiz
            Element raiz=bd_xml.getRootElement();
            //Se almacenan los hijos en una lista
            List hijos=raiz.getChildren();
            for(int i=0;i<hijos.size();i++)
            {
                Element hijo=(Element)hijos.get(i);
                
                    nombU[aux2]=hijo.getAttributeValue("id");
                    aux2++;
                
            }
        } catch (JDOMException | IOException ex) {
            Logger.getLogger(VerGrupos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return nombU;
    }
}
