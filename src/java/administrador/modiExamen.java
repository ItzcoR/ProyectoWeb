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
            out.println("<!DOCTYPE html>");
            out.println("<html style='height:100%; width:100%; margin:0px;'>");
            out.println("<head>");
            out.println("<title>Servlet Maestro</title>"); 
            out.println("<meta charset=\"utf-8\">\n" +
            "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
            "<link rel=\"stylesheet\" type=\"text/css\" href=\"estilos.css\" media=\"screen\"/>\n" +
            "<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css\">\n" +
            "<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js\"></script>\n" +
            "<script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js\"></script>");
            out.println("</head>");
            out.println("<body class='colorful'>");
            out.println("</div>");
                out.println("</div>");
                    out.println("<h1 class='blanco'>Modificando Examen ID: "+idViejo+"</h1>");
                    out.println("<h1 class='blanco'>Nombre nuevo: "+nombreNuevo+"</h1>");
                    out.println("<h1 class='blanco'>Nuevo ID: "+idNuevo+"</h1>");
                    out.println("<h1 class='blanco'>El resultado es:"+resultado+"</h1>");
                    out.println("<div class=\"row\">\n" +
                    "  <div class=\"col-sm-8\"></div>\n" +
                    "  <div class=\"col-sm-4\"><a class='blanco' href='Maestro'>Regresar</a></div>\n" +
                    "</div>");
                out.println("</div>");
            out.println("</div>");
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
