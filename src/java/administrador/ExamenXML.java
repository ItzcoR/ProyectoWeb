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


public class ExamenXML extends HttpServlet{
     @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        String xml=request.getRealPath("WEB-INF\\ProtoExam.xml");
        String idExam=request.getParameter("id");
        String nombreExam=request.getParameter("nombre");
        HttpSession session=request.getSession();
        String[] idpreg=(String [])session.getAttribute("idPreguntas");
        int npregs=idpreg.length;
        
        String resultado=agregarAlExamen(xml,nombreExam,idExam,idpreg);
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Creacion de Examen</title>"); 
            out.println("<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js\"></script>");
            out.println("<script src=\"https://cdn.jsdelivr.net/npm/vue/dist/vue.js\"></script>");
            out.println("</head>");
            out.println("<body>");  //  out.println("");   out.println('');
            out.println("<h1 >"+resultado+"</h1>");
            out.println(" <div ><a  href='Maestro'>Regresar</a></div>\n" +
            "</div>");
            out.println("</body>");
            out.println("</html>");
        }
    }
    
     public String agregarAlExamen(String ruta,String nombreExamen,String idExamen,String[] idpreg)
    {
        String res="";
        int aux=0;
        int npreguntas=idpreg.length;
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
                if(id.equals(idExamen))//Si encuentra un elemento con el mismo id rompemos el ciclo
                {
                    aux++;   
                    break;
                }
                
            }
            if(aux==1)//Si esta bandera esta activa es porque el id ya existe por lo que regresamos una cadena de error
            {
                res="El id del Examen ya existe";
            }
            else    //En caso contrario creamos un nuevo elemento con los datos dados y lo agregamos al archivo
            {   
                Element nuevo=new Element("Exa");            
                nuevo.setAttribute("id",idExamen);
                nuevo.setAttribute("nombre",nombreExamen);
                raiz.addContent(nuevo);
                for (int j=0;j<npreguntas ;j++ ) {  //En este for agregamos todas las preguntas seleccionadas anteriormente al examen
                    Element preguntaExamen= new Element("Preg");
                    preguntaExamen.setAttribute("id",idpreg[j]);
                    nuevo.addContent(preguntaExamen);
                }
                res="Examen creado con exito";
            }
        //Se escribe el documento bd_xml en el archivo XML
        xmlOutput.output(bd_xml,new FileWriter(ruta));
       
        } catch (JDOMException | IOException ex) {
            Logger.getLogger(Agregar.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }
}