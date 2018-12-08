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
        String nombre="";
        String xml=request.getRealPath("WEB-INF\\practica.xml");
        int aux=tamano(xml);
        String[][] usuarios=new String[3][aux];
        usuarios=ObtenerDatos(xml);
        for (int k=0;k<aux ;k++ ) {
            if(usuarios[1][k].equals(id)){
                nombre=usuarios[0][k];
            }
        }
        
        try (PrintWriter out = response.getWriter()) {
            if(id!=null)
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


            out.println(
                "<div class=\"bg_blanco contenedor sombra contactos\">\n"+
                    "<h2>Bienvenido "+tipo+": "+nombre+"</h2>           \n" +
                    "<p  class=\"total_contactos\"><span>"+aux+"</span> Contactos</p>\n"+
                    "<div class=\"contenedor_contactos\">\n"+
                        "<div class=\"contenedor_tabla\">\n"+
                            
                        "<table class=\"listado_contactos\" id=\"listado_contactos\">\n" +
                        "   <thead>\n" +
                        "     <tr>\n" +
                        "       <th>ID</th>\n" +
                        "       <th>Nombre</th>\n" +
                        "       <th>Tipo</th>\n" +
                        "       <th>Acciones</th>\n" +
                        "     </tr>\n" +
                        "   </thead>\n" +
                        "   <tbody>\n");
                        for(int i=0;i<aux;i++){
                            out.println("<tr > <td>" +usuarios[1][i]+"</td>" ); //ID
                            out.println("<td>"+usuarios[0][i]+"</td>");//Nombre
                            out.println("<td>"+usuarios[2][i]+"</td>");//Tipo
                            out.println("<td><button ><a class=\"btn_editar btn\" href='modificar1?nombre="+usuarios[1][i]+"&tipo="+usuarios[2][i]+"'><i class=\"fas fa-pen-square\"></i></a></button>");//boton para modificar
                out.println("<button ><a class=\"btn_borrar btn\" href='eliminar?nombre="+usuarios[1][i]+"&tipo="+usuarios[2][i]+"'><i class=\"fas fa-trash-alt\"></i></a></button></td>");//boton para eliminar
                 
                        }

                        
            out.println("</tr>\n" +
 
                        "</tbody>\n" +
                        "  </table>\n" +

                        "</div>\n"+
                    "</div>\n"+
                    "<div class=\"contenedor_botones\">\n"+
                    " <a class=\"btn btn_admin\" href='nuevoUsuario.html'>Nuevo usuario</a>\n" +
                    " <a class=\"btn btn_admin\" href='CrearGrupo'>Crear Grupos</a>\n" +
                    " <a class=\"btn btn_admin\" href='cerrar'>Cerrar Sesion</a>\n" +
                    "</div>\n"+
                    

                "</div>"
            );




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
    
    public String[][] ObtenerDatos(String direc)
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
                Element hijo=(Element)hijos.get(i);//Se obtiene cada hijo mientras se recorre el for
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

