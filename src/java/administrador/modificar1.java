
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


public class modificar1 extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
         String xml=request.getRealPath("WEB-INF\\practica.xml");
         String nombre=request.getParameter("nombre");
         String nodo=request.getParameter("tipo");
         HttpSession session=request.getSession();
         String[] res=atributo(xml,nodo,nombre);
         session.setAttribute("nodo",nodo);
         session.setAttribute("idv",res[0]);
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html style='height:100%; width:100%; margin:0px;'>");
            out.println("<head>");
            out.println("<title>Servlet modificar1</title>");  
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
            out.println("<meta charset=\"utf-8\">\n" +
            "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
            "<link rel=\"stylesheet\" type=\"text/css\" href=\"estilos.css\" media=\"screen\"/>\n" +
            "<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css\">\n" +
            "<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js\"></script>\n" +
            "<script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js\"></script>");
            out.println("</head>");
            out.println("<body class='colorful'>");
            out.println("<form action='modificacion2' method='post'>");
            out.println("<div class=\"container\">\n" +
            "  <h2 class='blanco'>Modificar</h2>           \n" +
            "  <table class=\"table\">\n" +
            "    <thead>\n" +
            "      <tr>\n" +
            "        <td class='blanco'>Nombre:</td><td><input type='text' name='nombre' onkeypress='return letraVal(event)' value="+res[2]+"></td>\n" +
            "      </tr>\n" +
            "    </thead>\n" +
            "    <tbody>\n" +
            "      <tr>\n" +
            "        <td><h4 class='blanco'>Tipo: "+res[3]+"</h4></td><td><select name=\"io\">\n" +
            "                            <option value=\"Alum\">Alumno</option>\n" +
            "                            <option value=\"Maes\">Maestro</option>\n" +
            "                            <option value=\"Adm\">Administrador</option>\n" +    
            "                        </select>\n" +
            "        </td>\n" +
            "      </tr>\n" +
            "      <tr>\n" +
            "        <td class='blanco'>Usuario: </td><td><input type='text' name='id' onkeypress='return letraNumVal(event)' value="+res[0]+"></td>\n" +
            "      </tr>\n" +
            "      <tr>\n" +
            "        <td class='blanco'>Password: </td><td><input type='text' name='password' onkeypress='return letraNumVal(event)' value="+res[1]+"></td>\n" +
            "      </tr>\n" +
            "    </tbody>\n" +
            "  </table>\n" +
            "</div>\n" +
            "");
            out.println("<div class=\"row\">\n" +
            "  <div class=\"col-sm-1\"></div>\n" +
            "  <div class=\"col-sm-4\"><input type=\"submit\" class=\"btn-info\" value=\"Modificar\"></div>\n" +
            "  <div class=\"col-sm-4\"><input type=\"button\" class=\"btn-info\" value=\"Cancelar\" onclick=\"document.location='Administrador'\"></div>\n" +
            "</div>");
            out.println("</form>");
            out.println("</body>");
            out.println("</html>");
        }
    }
    
    public String[] atributo(String ruta,String nodo,String nombre)
    {
        String[] res=new String[4];
        try{
            /*SAXBuilder se encarga de cargar el archivo XML del disco o de un String */
            SAXBuilder builder=new SAXBuilder();
            //Forma de abriri el archivo
            File xmlFile = new File(ruta);
            /*Almacenamos el xml cargado en builder en un documento*/
            Document bd_xml=builder.build(xmlFile);
            //Elemento raiz
            Element raiz=bd_xml.getRootElement();
            //Se almacenan nodo hijo 
            Element hijo=raiz.getChild(nodo);
            //Lista de nodos nietos
            List nietos=hijo.getChildren();
            for(int i=0;i<nietos.size();i++)
            {
                Element nieto=(Element) nietos.get(i);
                String usua=nieto.getAttributeValue("id");
                if(usua.equals(nombre))
                {
                    
                    res[0]=nieto.getAttributeValue("id");
                    res[1]=nieto.getAttributeValue("password");
                    res[2]=nieto.getAttributeValue("nombre");
                    res[3]=hijo.getName();
                    break;
                }
            }
            
        } catch (JDOMException | IOException ex) {
            Logger.getLogger(modificar1.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }
}
