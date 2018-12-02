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

public class Administrador extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session=request.getSession();
        String id=(String) session.getAttribute("id");
        String tipo=(String) session.getAttribute("tipo");
        String xml=request.getRealPath("WEB-INF\\practica.xml");
        int aux=tamano(xml);
        String[][] usuarios=new String[3][aux];
        usuarios=nombre(xml);
        try (PrintWriter out = response.getWriter()) {
            if(id!=null)
            {    
            out.println("<!DOCTYPE html>");
            out.println("<html style='height:100%; width:100%; margin:0px;'>");
            out.println("<head>");
            out.println("<title>Servlet Adm</title>"); 
            out.println("<meta charset=\"utf-8\">\n" +
            "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
            "<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js\"></script>\n" +
            "<script src=\"https://cdn.jsdelivr.net/npm/vue/dist/vue.js\"></script>");
            out.println("</head>");
            out.println("<body >");
            out.println("<div >\n" +
            "<h2>Bienvenido "+tipo+": "+id+" "+aux+"  </h2>           \n" +
            "<table>\n" +
            "   <thead>\n" +
            "     <tr>\n" +
            "       <th>Nombre</th>\n" +
            "       <th>Tipo</th>\n" +
            "       <th>Acciones</th>\n" +
            "     </tr>\n" +
            "   </thead>\n" +
            "   <tbody>\n" +
            "     <tr>\n" +
            "       <td>");
            for(int i=0;i<aux;i++){
                out.println("<h5>"+usuarios[0][i]+"</h5>"); //Nombre
                out.println("<hr>");
            }
            out.println("</td>\n" +
            "<td>");        
            for(int i=0;i<aux;i++){
                out.println("<h5>"+usuarios[2][i]+"</h5>"); //Tipo
                out.println("<hr>");
            }
            out.println("</td>\n" +
            "<td>");
            for(int i=0;i<aux;i++){
                out.println("<button ><a href='modificar1?nombre="+usuarios[1][i]+"&tipo="+usuarios[2][i]+"'>Modificar</a></button>");
                out.println("<button ><a href='eliminar?nombre="+usuarios[1][i]+"&tipo="+usuarios[2][i]+"'>Eliminar</a></button>");
                out.println("<hr>");    
            }
            
            out.println("</td>\n" +
            "      </tr>\n" +
            "    </tbody>\n" +
            "  </table>\n" +
            "</div>");
            out.println("<div class=\"row\">\n" +
            "  <div></div>\n" +
            "  <div></div>\n" +
            "  <div><button><a href='nuevoUsuario.html'>Nuevo usuario</a></button></div>\n" +
            "  <div><button><a href='CrearGrupo'>Crear Grupos</a></button></div>\n" +
            "  <div></div>\n" +
            "  <div></div>\n" +
            "</div>");
            out.println("<div class=\"row\">\n" +
            "  <div></div>\n" +
            "  <div><button ><a c href='cerrar'>Cerrar Sesion</a></button></div>\n" +
            "</div>");
            out.println("</body>");
            out.println("</html>");
            }
        }
    }
    
        public int tamano(String direc) //Esta funcion nos regresa el numero de nietos del tag raiz
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
                Element hijo=(Element)hijos.get(i);
                List nietos=hijo.getChildren();
                for(int j=0;j<nietos.size();j++)
                {
                   aux++;
                }
            }
        } catch (JDOMException | IOException ex) {
            Logger.getLogger(Administrador.class.getName()).log(Level.SEVERE, null, ex);//ver los errores
        }
        return aux;
    }
    
    public String[][] nombre(String direc)
    {
        int aux=tamano(direc);
        int aux2=0;
        String[][] nombU=new String[3][aux];
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
            List hijos=raiz.getChildren();//Los hijos son los tags que definen el tipo de usuario
            for(int i=0;i<hijos.size();i++)
            {
                Element hijo=(Element)hijos.get(i);//Se ob tiene cada hijo mientras se recorre el for
                List nietos=hijo.getChildren();//Se obtienen los hijos de cada tag hijo (Nietos del elemento raiz)
                for(int j=0;j<nietos.size();j++)
                {
                    Element nieto=(Element)nietos.get(j);//Se obtiene cada nieto de forma individual mientras se recorre el for
                    nombU[0][aux2]=nieto.getAttributeValue("nombre");   //Obtenemos el nombre del usuario el cual es un atributo
                    nombU[1][aux2]=nieto.getAttributeValue("id");       //En esta posicion del arreglo se agrega el id unico de cada Usuario
                    nombU[2][aux2]=hijo.getName();  //Esta posicion del arreglo guarda el tipo de usuario el cual esta definido por el tag hijo
                    aux2++;
                }
            }
        } catch (JDOMException | IOException ex) {
            Logger.getLogger(Administrador.class.getName()).log(Level.SEVERE, null, ex);
        }
        return nombU;
    }
}

