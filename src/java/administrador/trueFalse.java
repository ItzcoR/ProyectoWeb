
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

public class trueFalse extends HttpServlet{
     @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String xml=request.getRealPath("WEB-INF\\ProtoToF.xml");
        //HttpSession session=request.getSession();
        String idpreg=request.getParameter("id");
        String texto=request.getParameter("texto");
        String res=request.getParameter("res");
        String resultado=agregar(xml,idpreg,texto,res);
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Pregunta Verdadero o Falso</title>"); 
            out.println("<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js\"></script>");
            out.println("</head>");
            out.println("<body>");  //  out.println("");   out.println('');
//             out.println("<h1 >"+resultado+" </h1>");
            out.println("<div \n" +
            "  <div ></div>\n" +
            "  <div ><a  href='Maestro'>Regresar</a></div>\n" +
            "</div>");
            out.println("</body>");
            out.println("</html>");
        }
    }
        public String agregar(String ruta,String id,String texto,String res)
    {
        String respuesta="";
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
                String identificador=hijo.getAttributeValue("id");
                if(identificador.equals(id))
                {
                    aux++;   
                    break;
                 }
         
            }
            if(aux==1)
            {
                respuesta="Este id ya existe";
            }
            else
            { 
                Element nuevo=new Element("Pregunta");
                nuevo.setAttribute("id",id);
                nuevo.setAttribute("texto",texto);
                nuevo.setAttribute("res",res);
                raiz.addContent(nuevo);
                respuesta="Pregunta agregada";
            } 
      xmlOutput.output(bd_xml,new FileWriter(ruta));
       
        } catch (JDOMException | IOException ex) {
            Logger.getLogger(Agregar.class.getName()).log(Level.SEVERE, null, ex);
        }
        return respuesta;
    }
}