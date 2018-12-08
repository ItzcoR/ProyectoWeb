
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
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet modificar1</title>");   
//           hay que agregar el script para verificar valores
            out.println("<meta charset=\"utf-8\">\n" +
            "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
            "<link rel=\"stylesheet\" type=\"text/css\" href=\"css/main.css\" media=\"screen\"/>\n" +
            "<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js\"></script>\n");
            out.println("</head>");
            out.println("<body>");


            out.println("<div class=\"contenedor_barra\">\n"+
            "<h1>Sistema evaluador</h1>\n"+
            "</div>\n"+
        
        
          "<div class=\"bg_amarillo contenedor sombra\">\n"+
        
            "<form id=\"contacto\" class=\"modificar_1\" action=\"modificacion2\" method=\"POST\">\n"+
              "<legend>\n"+
                "Editar usuario <span>Todos los campos son obligatorios</span>\n"+
              "</legend>\n"+


              "<legend>\n"+
              "Tipo: "+res[3]+" \n"+
            "</legend>\n"+
        
              "<div class=\"campos_usuario\">\n"+

              
                "<div class=\"campo_usuario\">\n"+
        
                  "<label for=\"nombre\">Nombre usuario:</label>\n"+
                  "<input input type=\"text\" name=\"nombre\" onkeypress=\"return letraVal(event)\" required autocomplete=\"off\" value=\""+res[2]+"\">\n"+
        
                "</div>\n"+
        
                "<div class=\"campo_usuario\">\n"+
        
                  "<label for=\"empresa\">Tipo de usuario:</label>\n"+
                  "<select name=\"io\">\n"+
                    "<option value=\"Alumno\">Alumno</option>\n"+
                    "<option value=\"Maestro\">Maestro</option>\n"+
                    "<option value=\"Administrador\">Administrador</option>\n"+
                  "</select>\n"+
        
                "</div>\n"+
        
        
                "<div class=\"campo_usuario\">\n"+
        
                  "<label for=\"empresa\">Usuario:</label>\n"+
                  "<input input type=\"text\" value=\""+res[0]+"\" name=\"id\" onkeypress=\"return letraNumVal(event)\" required autocomplete=\"off\" placeholder=\"Id usuario\">\n"+
        
                "</div>\n"+
        
        
        
                "<div class=\"campo_usuario\">\n"+
        
                  "<label for=\"empresa\">Password:</label>\n"+
                  "<input type=\"text\" name=\"password\" onkeypress=\"return letraNumVal(event)\" required value=\""+res[1]+"\">\n"+
        
                "</div>\n"+
        
        
              "</div>\n"+
              "<div class=\"campo_usuario enviar\">\n"+
                "<input type=\"hidden\" id=\"accion\" value=\"crear\">\n"+
                "<input type=\"submit\" class=\"btn-info\" value=\"Modificar\">\n"+
                "<input  type=\"button\" class=\"btn-info\" value=\"Cancelar\" onclick=\"document.location='Administrador'\">\n"+
              "</div>\n"+
        
            "</form>\n"+
          "</div>");


          out.println("<script src=\"js/vendor/modernizr-3.6.0.min.js\"></script>\n"+
          "<script src=\"https://code.jquery.com/jquery-3.3.1.min.js\" integrity=\"sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8=\" crossorigin=\"anonymous\"></script>\n"+
          "<script src=\"js/plugins.js\"></script>\n"+
          "<script src=\"js/main.js\"></script>");

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
