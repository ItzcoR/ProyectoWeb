package administrador;

import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;


public class eliminarExamen extends HttpServlet{
 @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        String xml=request.getRealPath("WEB-INF\\ProtoExam.xml");
        String idExam=request.getParameter("idExamen");
        HttpSession session=request.getSession();
        //String[] idpreg=(String [])session.getAttribute("idPreguntas");
        //int npregs=idpreg.length;
        
       eliminarDelExamen(xml,idExam);
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Creacion de Examen</title>"); 
            out.println("<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js\"></script>");
            out.println("<script src=\"https://cdn.jsdelivr.net/npm/vue/dist/vue.js\"></script>");
            out.println("</head>");
            out.println("<body>");  //  out.println("");   out.println('');
            out.println("<h1 >Eliminado: "+idExam+"</h1>");
           // out.println("<h1 >"+resultado+"</h1>");
            out.println(" <div ><a  href='Maestro'>Regresar</a></div>\n" +
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
    
     public void eliminarDelExamen(String ruta,String idExamen)
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
            //Se almacenan los hijos en una lista
            List hijos=raiz.getChildren();
            //Objeto que escribe en el archivo xml
            XMLOutputter xmlOutput = new XMLOutputter();
            //Formato en el que se va a escribir
            xmlOutput.setFormat(Format.getPrettyFormat());
            for(int i=0;i<hijos.size();i++)
            {
                Element hijo=(Element)hijos.get(i);
                String id=hijo.getAttributeValue("id");
                if(id.equals(idExamen))
                {
                    hijos.remove(i);  
                    break;
                }
                
            }
        //Se reescribe el documento bd_xml en el archivo XML
        xmlOutput.output(bd_xml,new FileWriter(ruta));
       
        } catch (JDOMException | IOException ex) {
            Logger.getLogger(Agregar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
