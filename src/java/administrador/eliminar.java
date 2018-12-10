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

public class eliminar extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String xml=request.getRealPath("WEB-INF\\practica.xml");
        String nombre=request.getParameter("nombre");
        String nodo=request.getParameter("tipo");
        borrar(xml,nodo,nombre);
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

                    
                    out.println("<div class=\"bg_blanco contenedor sombra\">\n"+
                    "<div class =\"contenedor_alertas\">\n"+
                    "<legend>\n"+
                     " Usuario eliminado correctacmente\n"+
                    "</legend>\n"+
                    
                    "<div class=\"campo enviar\">\n" +
                                "   <a class=\"btn-info btn_rosa\" href='Administrador'>Regresar</a>\n" +
                                "</div>\n" +
                                "</div>\n" +
                    "</div>");
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
    public void borrar(String ruta,String nodo,String nombre)
    {
        
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
            //Objeto que escribe en el archivo xml
            XMLOutputter xmlOutput = new XMLOutputter();
            //Formato en el que se va a escribir
            xmlOutput.setFormat(Format.getPrettyFormat());
            
            for(int i=0;i<nietos.size();i++)
            {
                Element nieto=(Element) nietos.get(i);
                String usua=nieto.getAttributeValue("id");
                if(usua.equals(nombre))
                {
                    nietos.remove(i);
                    break;
                }
            }
            xmlOutput.output(bd_xml,new FileWriter(ruta));
        } catch (JDOMException | IOException ex) {
            Logger.getLogger(eliminar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
