
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
import org.jdom.*;
import org.jdom.input.SAXBuilder;



public class Existe extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String usuario=request.getParameter("id");
        String contra=request.getParameter("password");
        String nomb=request.getParameter("nombre");
        String xml=request.getRealPath("WEB-INF\\practica.xml");
        String existe= verificar(xml,usuario,contra);
        HttpSession session=request.getSession();
        session.setAttribute("id",usuario);
        session.setAttribute("tipo",existe);
        session.setAttribute("nombre",nomb);
        try (PrintWriter out = response.getWriter()) {
            
            if(existe.equals(""))
            {
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Servlet Adm</title>"); 
                out.println("<meta charset=\"utf-8\">\n" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
                "<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js\"></script>\n" +
                "<script src=\"https://cdn.jsdelivr.net/npm/vue/dist/vue.js\"></script>");
                out.println("<link rel=\"stylesheet\" href=\"css/normalize.css\">");
                out.println("<link rel=\"stylesheet\" href=\"css/main.css\">");
                out.println("<link rel=\"stylesheet\" href=\"https://use.fontawesome.com/releases/v5.5.0/css/all.css\" integrity=\"sha384-B4dIYHKNBt8Bc12p+WXckhzcICo0wtJAoU8YZTY5qE0Id1GSseTk6S+L3BlXeVIU\" crossorigin=\"anonymous\">");
                out.println("</head>");
                out.println("<body >");
                out.println("<div class=\"contenedor_barra\">\n"+
                "<h1>Sistema evaluador</h1>\n"+
                "</div>");
                out.println("<div class=\"bg_blanco contenedor sombra\">\n"+
                "<form id=\"contacto\" action=\"Existe\" method=\"POST\">\n"+
          "<legend>\n"+
           "Usuario o contraseña invalidos <span>Intente de nuevo o contacte al administrador para registrarse o arreglar cualquier asunto reacionado a su cuenta</span>\n"+
          "</legend>\n"+
          "<div class=\"campo enviar\">\n"+
          "<input type=\"hidden\" id=\"accion\" value=\"crear\">\n"+
          "<a class=\"btn btn_admin btn_existe\" href='index.html'>Regresar</a>\n"+
          "</div>\n"+

                "</div>");
                
                out.println("</body>");
                out.println("</html>");
            }
            else if(existe.equals("Administrador"))
            {
                //Permite redigir a otro archivo o servlet y mandar los parametros 
                response.sendRedirect("Administrador");
            }
            else if(existe.equals("Maestro"))
            {
                //Permite redigir a otro archivo o servlet y mandar los parametros 
                response.sendRedirect("Maestro");
            }
            else if(existe.equals("Alumno"))
            {
                //Permite redigir a otro archivo o servlet y mandar los parametros 
                response.sendRedirect("Alumno");
            }
        }
    }
    
    public String verificar(String xml,String usuario,String contra)
    {   
        String res="";
        try{
            /*SAXBuilder se encarga de cargar el archivo XML del disco o de un String */
            SAXBuilder builder=new SAXBuilder();
            //Forma de abrir el archivo
            File xmlFile = new File(xml);
            /*Almacenamos el xml cargado en builder en un documento*/
            Document bd_xml=builder.build(xmlFile);
            /*Indica el elemento raiz que se guardo en la variable Document*/
            Element raiz=bd_xml.getRootElement();
            //Lista con los nodos hijos 
            List hijos = raiz.getChildren();
            for (int i = 0; i < hijos.size(); i++)
            {
                Element hijo = (Element) hijos.get(i);
                List nietos=hijo.getChildren();
                String tipo=hijo.getName();
                for(int j=0;j<nietos.size();j++)
                {
                    Element nieto=(Element) nietos.get(j);
                    //Valor que tiene el atributo numero
                    String nombre = nieto.getAttributeValue("id");
                    String pass=nieto.getAttributeValue("password");
                    if(nombre.equals(usuario)&& pass.equals(contra))
                    {
                        res=tipo;
                        break;
                    }
                }
            }
             /*NUEVOS ELEMENTOS
            Element raiz=new Element("ROOT");
            Element hoja = new Element("hoja");*/
            } catch (JDOMException | IOException ex) {
            Logger.getLogger(Existe.class.getName()).log(Level.SEVERE, null, ex);
        }
        
  
        return res;
    }
}
