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
                /////////////////////////////////////////////////////////////////////////////////////////////////////////
                // Header
                /////////////////////////////////////////////////////////////////////////////////////////////////////////
                out.println(
                    "<!doctype html>\n" +
                    " <html class=\"no-js\" lang=\"\">\n" +

                    "<head>\n" +
                    "<meta charset=\"utf-8\">\n" +
                    "<meta http-equiv=\"x-ua-compatible\" content=\"ie=edge\">\n" +
                    "<title></title>\n" +
                    "<meta name=\"description\" content=\"\">\n" +
                    "   <meta name=\"viewport\" content=\"width=device-width, initial-scale=1, shrink-to-fit=no\">\n" +

                    "<link rel=\"manifest\" href=\"site.webmanifest\">\n" +
                    " <link rel=\"apple-touch-icon\" href=\"icon.png\">\n" +
                    "   <!-- Place favicon.ico in the root directory -->\n" +

                    "<link rel=\"stylesheet\" href=\"css/normalize.css\">\n" +
                    " <link rel=\"stylesheet\" href=\"css/main.css\">\n" +
                    "<link rel=\"stylesheet\" href=\"https://use.fontawesome.com/releases/v5.5.0/css/all.css\" integrity=\"sha384-B4dIYHKNBt8Bc12p+WXckhzcICo0wtJAoU8YZTY5qE0Id1GSseTk6S+L3BlXeVIU\" crossorigin=\"anonymous\">\n"+
                    " </head>\n" +

                    "<body>\n" +
                    "<!--[if lte IE 9]>\n" +
                    " <p class=\"browserupgrade\">You are using an <strong>outdated</strong> browser. Please <a href=\"https://browsehappy.com/\">upgrade your browser</a> to improve your experience and security.</p>\n" +
                    "  <![endif]-->\n" +

                    "<div class=\"contenedor_barra\">\n" +
                    "<h1>Sistema evaluador</h1>\n" +
                    " </div>");

                    
            out.println("<h1 class='blanco'>Usuario Modificado</h1>");
            //out.println("El nuevo usuario: "+usuario+"<br> La contraseña: "+contra+"<br> El nombre: "+nom+"<br> El tipo: "+tipo+" <br>El tipo viejo: "+a+"<br> El viejo usuario: "+idviejo+"</h1>");
            out.println("<div class=\"row\">\n" +
            "  <div class=\"col-sm-8\"></div>\n" +
            "  <div class=\"col-sm-4\"><button class='btn-info'><a class='blanco' href='VerGrupos'>Regresar</a></button></div>\n" +
            "</div>");



////////////////////////////////////////////////////////////////////////////////////////////
// ------------  FOOTER  -------------------------------------------------------------------
///////////////////////////////////////////////////////////////////////////////////////////               
out.println("</body>");
out.println("</html>");  
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
