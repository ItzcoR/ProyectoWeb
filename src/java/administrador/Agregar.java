
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
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

public class Agregar extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String xml=request.getRealPath("WEB-INF\\practica.xml");
        String usuario=request.getParameter("id");
        String contra=request.getParameter("password");
        String nom=request.getParameter("nombre");
        String tipo=request.getParameter("io");
        String res=agregar(xml,nom,usuario,contra,tipo);
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html style='height:100%; width:100%; margin:0px;'>");
            out.println("<head>");
            out.println("<title>Servlet add</title>"); 
            out.println("<meta charset=\"utf-8\">\n" +
            "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
            "<link rel=\"stylesheet\" type=\"text/css\" href=\"estilos.css\" media=\"screen\"/>\n" +
            "<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css\">\n" +
            "<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js\"></script>\n" +
            "<script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js\"></script>");
            out.println("</head>");
            out.println("<body class='colorful'>");
            out.println("<h1 class='blanco'>"+res+" </h1>");
            out.println("<div class=\"row\">\n" +
            "  <div class=\"col-sm-8\"></div>\n" +
            "  <div class=\"col-sm-4\"><a class='blanco' href='Administrador'>Regresar</a></div>\n" +
            "</div>");
            out.println("</body>");
            out.println("</html>");
        }
    }
    public String agregar(String ruta,String nom,String usuario,String contra,String tipo)
    {
        String res="";
        int aux=0;
        try{
            /*SAXBuilder se encarga de cargar el archivo XML del disco o de un String */
            SAXBuilder builder=new SAXBuilder();
            //Forma de abriri el archivo
            File xmlFile = new File(ruta);
            /*Almacenamos el xml cargado en builder en un documento*/
            Document bd_xml=builder.build(xmlFile);
            //Elemento raiz
            Element raiz=bd_xml.getRootElement();
            //Se almacenan los hijos en una lista
            List hijos=raiz.getChildren();
            //Objeto que escribe en el archivo xml
            XMLOutputter xmlOutput = new XMLOutputter();
            //Formato en el que se va a escribir
            xmlOutput.setFormat(Format.getPrettyFormat());
            for(int i=0;i<hijos.size();i++)
            {
                Element hijo=(Element)hijos.get(i);
                List nietos=hijo.getChildren();
                for(int j=0;j<nietos.size();j++)
                {
                    //Elemento nieto
                    Element nieto=(Element)nietos.get(j);
                    String nombre=nieto.getAttributeValue("id");
                    if(nombre.equals(usuario))
                    {
                        aux++;   
                        break;
                    }
                }
            }
            if(aux==1)
            {
                res="El usuario ya existe";
            }
            else
            {   
                if(tipo.equals("Alumno"))
                {
                    Element hij=raiz.getChild(tipo);
                    Element nuevo=new Element("Alum");
                    nuevo.setAttribute("password",contra);
                    nuevo.setAttribute("nombre",nom);
                    nuevo.setAttribute("id",usuario);
                    hij.addContent(nuevo);
                }
                
                else if(tipo.equals("Maestro"))
                {
                    Element hij=raiz.getChild(tipo);
                    Element nuevo=new Element("Maes");
                    nuevo.setAttribute("password",contra);
                    nuevo.setAttribute("nombre",nom);
                    nuevo.setAttribute("id",usuario);
                    hij.addContent(nuevo);
                }
                else if(tipo.equals("Administrador"))
                {
                    Element hij=raiz.getChild(tipo);
                    Element nuevo=new Element("Adm");
                    nuevo.setAttribute("password",contra);
                    nuevo.setAttribute("nombre",nom);
                    nuevo.setAttribute("id",usuario);
                    hij.addContent(nuevo);
                }
                res="Usuario creado con exito";
            }
        //Se escribe el documento bd_xml en el archivo XML
        xmlOutput.output(bd_xml,new FileWriter(ruta));
       
        } catch (JDOMException | IOException ex) {
            Logger.getLogger(Agregar.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }
}
