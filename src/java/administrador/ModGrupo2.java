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

public class ModGrupo2 extends HttpServlet {

@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
       String xml=request.getRealPath("WEB-INF\\practica2.xml");
        String id=request.getParameter("id");
        String maestro=request.getParameter("io");
        String alumnos=request.getParameter("alumnos");
        //String tipo=request.getParameter("io");
        HttpSession session=request.getSession();
        String idviejo=(String) session.getAttribute("viejo");
        //String idviejo=(String) session.getAttribute("idv");
        atributo(xml,id,maestro,alumnos,idviejo);
        try (PrintWriter out = response.getWriter()) {

            out.println("<!DOCTYPE html>");
            out.println("<html style='height:100%; width:100%; margin:0px;'>");
            out.println("<head>");
            out.println("<title>Servlet modificacion2</title>");        
            out.println("<meta charset=\"utf-8\">\n" +
            "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
            "<link rel=\"stylesheet\" type=\"text/css\" href=\"estilos.css\" media=\"screen\"/>\n" +
            "<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css\">\n" +
            "<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js\"></script>\n" +
            "<script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js\"></script>");
            out.println("</head>");
            out.println("<body class='colorful'>");
            out.println("<h1 class='blanco'>Usuario Modificado</h1>");
            //out.println("El nuevo usuario: "+usuario+"<br> La contraseña: "+contra+"<br> El nombre: "+nom+"<br> El tipo: "+tipo+" <br>El tipo viejo: "+a+"<br> El viejo usuario: "+idviejo+"</h1>");
            out.println("<div class=\"row\">\n" +
            "  <div class=\"col-sm-8\"></div>\n" +
            "  <div class=\"col-sm-4\"><button class='btn-info'><a class='blanco' href='VerGrupos'>Regresar</a></button></div>\n" +
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
    public void atributo(String ruta,String id,String maestro,String alumnos, String idviejo) 
    {
        int aux=0;
        String res="";
        try{
            /*SAXBuilder se encarga de cargar el archivo XML del disco o de un String */
            SAXBuilder builder=new SAXBuilder();
            //Forma de abriri el archivo
            File xmlFile = new File(ruta);
            /*Almacenamos el xml cargado en builder en un documento*/
            Document bd_xml=builder.build(xmlFile);
            //Elemento raiz
            Element raiz=bd_xml.getRootElement();
            //NODOS HIJOS
            List hijos=raiz.getChildren();
            //metodo que escribe en el archivo xml
            XMLOutputter xmlOutput = new XMLOutputter();
            //Formato en el que se va a escribir
            xmlOutput.setFormat(Format.getPrettyFormat());
            
            //Eliminar
            for(int i=0;i<hijos.size();i++)
            {
                Element hijo=(Element)hijos.get(i);
                
                    String usua=hijo.getAttributeValue("id");
            
                if(usua.equals(idviejo))
                {
                   hijos.remove(i);
                    
                
            }
            }
            for(int i=0;i<hijos.size();i++)
            {
                Element hijo=(Element)hijos.get(i);
                String nombre=hijo.getAttributeValue("id");
                
                    if(nombre.equals(id))
                    {
                        aux++;   
                        break;
                    }
                
            }
            if(aux==1)
            {
                res="El usuario ya existe";
            }
            else
            {   
            Element a=new Element("Grupo");
            a.setAttribute("id", id);
            a.setAttribute("Maestro", maestro);
            a.setText(alumnos);
            raiz.addContent(a);
                res="Usuario modificado con exito";
                xmlOutput.output(bd_xml,new FileWriter(ruta)); 
            } 
        } catch (JDOMException | IOException ex) {
            Logger.getLogger(ModGrupo2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
