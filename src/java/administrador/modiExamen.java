/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package administrador;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
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

/**
 *
 * @author itz
 */
public class modiExamen extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String xml=request.getRealPath("WEB-INF\\ProtoExam.xml");
        String[] Preset=new String[3];
        String resultado="";
        String idNuevo=request.getParameter("idNuevo");
        String nombreNuevo=request.getParameter("nombre");
        HttpSession session=request.getSession();
        String idViejo=(String) session.getAttribute("idViejo");
        String nombreViejo=(String) session.getAttribute("nombre");
        //Preset=getValuesPreguntaTOF(xml,id);
        resultado=modificarElExamen(xml,idViejo,idNuevo,nombreNuevo);
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

                /////////////////////////////////////////////////////////////////////////////////////////////////////////
                // Contenido
                /////////////////////////////////////////////////////////////////////////////////////////////////////////
            out.println("<div class=\"bg_blanco contenedor sombra\">\n"+
            "<div class =\"contenedor_alertas\">\n"+
            "<legend>\n"+
             " Modificando examen ID:"+idViejo+"\n"+
            "</legend>\n"+
            "<div class=\"modif\"><i class=\"fas fa-check\"></i> <span>Nombre nuevo: </span>"+nombreNuevo+"<br> <i class=\"fas fa-check\"></i> <span>Nuevo ID:</span> "+idNuevo+"<br></div>\n"+
            
            "<div class=\"campo enviar\">\n" +
                        "   <a class=\"btn-info btn_rosa\" href='Maestro'>Regresar</a>\n" +
                        "</div>\n" +
                        "</div>\n" +
            "</div>");



////////////////////////////////////////////////////////////////////////////////////////////
// ------------  FOOTER  -------------------------------------------------------------------
///////////////////////////////////////////////////////////////////////////////////////////               
out.println("</body>");
out.println("</html>"); 
        }
    }
         public String modificarElExamen(String ruta,String idviejo,String idNuevo,String texto)
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
                String id=hijo.getAttributeValue("id"); //Checamos si las ids 
                if(id.equals(idviejo))//Si encuentra un elemento con el mismo id rompemos el ciclo
                {
                   hijo.setAttribute("id",idNuevo);
                   hijo.setAttribute("nombre",texto); 
                   res="Examen Modificado";
                }
                
            }
           
        //Se escribe el documento bd_xml en el archivo XML
        xmlOutput.output(bd_xml,new FileWriter(ruta));
       
        } catch (JDOMException | IOException ex) {
            Logger.getLogger(Agregar.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }
}
