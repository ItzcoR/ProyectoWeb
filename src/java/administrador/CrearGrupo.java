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

public class CrearGrupo extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
         HttpSession session=request.getSession();
        String id=(String) session.getAttribute("id");
        String tipo=(String) session.getAttribute("tipo");
        String xml=request.getRealPath("WEB-INF\\practica.xml");
        int auxx=tamano(xml,"Maestro");
        int auxr=tamano(xml,"Alumno");
        String[] usuarios=new String[auxx];
        usuarios=maestros(xml);
        String[] usuarios2=new String[auxr];
        usuarios2=alumno(xml);
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
            out.println("<script type =\"text/javascript\">\n" +
            "function letraNumVal(e) {\n" +
            "   tecla = document.all ? e.keyCode : e.which;\n" +
            "   if (tecla === 8 || tecla === 32)\n" +
            "           return true;\n" +
            "       patron = /[a-z]|[A-Z]|á|é|í|ó|ú|Á|É|Í|Ó|Ú|[0-9]/;\n" +
            "       te = String.fromCharCode(tecla);\n" +
            "           return patron.test(te);\n" +
            "   }\n" +
            "function letraVal(e) {\n" +
            "   tecla = document.all ? e.keyCode : e.which;\n" +
            "   if (tecla === 8 || tecla === 32)\n" +
            "           return true;\n" +
            "       patron = /[a-z]|[A-Z]|á|é|í|ó|ú|Á|É|Í|Ó|Ú/;\n" +
            "       te = String.fromCharCode(tecla);\n" +
            "           return patron.test(te);\n" +
            "   }\n" +
            "</script>");
            out.println("</head>");
            out.println("<body class='colorful'>");
            out.println("<form action='GuardarGrupo' method='post'>");
            out.println("<div class=\"container\">\n" +
            "<h2 class='blanco'>Crear Grupos</h2>\n" +
            "<p class=\"blanco\">Grupo: </p><input type=\"text\" name=\"id\" onkeypress=\"return letraNumVal(event)\" required>\n"+
            "<table class=\"table\">\n" +
            "   <thead>\n" +
            "     <tr class='blanco'>\n" +
            "       <th>Maestros</th>\n" +
            "       <th>Alumnos</th>\n" +
            "     </tr>\n" +
            "   </thead>\n" +
            "   <tbody>\n" +
            "     <tr>\n" +
            "       <td>");
            for(int i=0;i<auxx;i++){
                out.println("<input type=\"radio\" name=\"io\" value=\""+usuarios[i]+"\"><h5 id='txtUsuario' class='blanco'>"+usuarios[i]+"</h5>");
                out.println("<hr>");
            }
            out.println("</td>\n" +
            "<td>");        
            for(int i=0;i<auxr;i++){
                out.println("<h5 id='txtPassword' class='blanco'>"+usuarios2[i]+"</h5>");
                out.println("<hr>");
            }
            out.println("</td>");
            out.println("</tr>\n" +
            "    </tbody>\n" +
            "  </table>\n" +
            "</div>");
            out.println("<div class=\"row\">\n" +
            "  <div class=\"col-sm-1\"></div>\n" +
            "  <div class=\"col-sm-8\"><p class=\"blanco\">Ingrese los nombres de los alumnos de este grupo, separelos con una coma: </p></div>\n" +
            "  <div class=\"col-sm-1\"></div>\n" +
            "</div>");
            out.println("<div class=\"row form-group\">\n" +
            "  <div class=\"col-sm-1\"></div>\n" +
            "  <div class=\"col-sm-8\"><p class=\"blanco\">Alumnos: </p>  <input type=\"text\" class=\"form-control\" name=\"alum\" id=\"usr\"></div>\n" +
            "  <div class=\"col-sm-1\"></div>\n" +
            "</div>");
            out.println("<div class=\"row\">\n" +
            "  <div class=\"col-sm-1\"></div>\n" +
            "  <div class=\"col-sm-4\"><input type=\"submit\" class=\"btn-info\" value=\"Crear\"></div>\n" +
            "  <div class=\"col-sm-4\"><input type=\"button\" class=\"btn-info\" value=\"Cancelar\" onclick=\"document.location='Administrador'\"></div>\n" +
            "  <div class=\"col-sm-1\"><input type=\"button\" class=\"btn-info\" value=\"Ver Grupos\" onclick=\"document.location='VerGrupos'\"></div>\n" +
            "</div>");
            out.println("</form>");
            out.println("</body>");
            out.println("</html>");
        }
    }
    
    public int tamano(String direc,String tipo)
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
            Element hijo=raiz.getChild(tipo);
            List nietos=hijo.getChildren();
            aux=nietos.size();
        } catch (JDOMException | IOException ex) {
            Logger.getLogger(CrearGrupo.class.getName()).log(Level.SEVERE, null, ex);
        }
        return aux;
    }
    


    // direc Direcion del la funcion request.getRealPath("WEB-INF\\practica.xml");
    public String[] maestros(String direc)
    {
        int aux=tamano(direc,"Maestro");
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
            Element hijo=raiz.getChild("Maestro");
            List nietos=hijo.getChildren();
            for(int i=0;i<nietos.size();i++)
            {
            	Element nieto=(Element) nietos.get(i);
                nombU[i]=nieto.getAttributeValue("nombre");
            }
        } catch (JDOMException | IOException ex) {
            Logger.getLogger(CrearGrupo.class.getName()).log(Level.SEVERE, null, ex);
        }
        return nombU;
    }


        public String[] alumno(String direc)
    {
        int aux=tamano(direc,"Alumno");
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
            Element hijo=raiz.getChild("Alumno");
            List nietos=hijo.getChildren();
            for(int i=0;i<nietos.size();i++)
            {
            	Element nieto=(Element) nietos.get(i);
                nombU[i]=nieto.getAttributeValue("nombre");
            }
        } catch (JDOMException | IOException ex) {
            Logger.getLogger(CrearGrupo.class.getName()).log(Level.SEVERE, null, ex);
        }
        return nombU;
    }
}
