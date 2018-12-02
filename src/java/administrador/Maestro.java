
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


public class Maestro extends HttpServlet {

  @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String xml=request.getRealPath("WEB-INF\\ProtoToF.xml");
        int aux=tamano(xml);
        //String[] diagramas=new String[aux];
        //diagramas=diagrama(xml);
        String[][] pregs=new String[3][aux];
        pregs=preguntas(xml);
        HttpSession session=request.getSession();
        String id=(String) session.getAttribute("id");
        String tipo=(String) session.getAttribute("tipo");
        if(id!=null)
        {
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html style='height:100%; width:100%; margin:0px;'>");
            out.println("<head>");
            out.println("<title>Servlet Maestro</title>"); 
            out.println("<meta charset=\"utf-8\">\n" +
            "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
            "<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js\"></script>\n" +
            "<script src=\"https://cdn.jsdelivr.net/npm/vue/dist/vue.js\"></script>");
            out.println("</head>");
            out.println("<body ");
            out.println("<h1 >Bienvenido "+tipo+": "+id+" GETREALPATH "+xml+"</h1>");
            out.println("<div \n" +
            "  <div ></div>\n" +
            "  <div ><a href='cerrar'>Cerrar Sesion</a></button></div>\n" +
            "</div>");
            out.println("<div>");
            out.print("<a href='CrearDiagrama'>Crear Pregunta</a>");
            out.println("</div>");
            out.println("<div>");
            out.println("<table border='1px solid black'>");
            out.println("<tr>");
            out.println("<td>");
            out.println("<h3>Id de la pregunta</h3>");
            out.println("</td>");
            out.println("<td>");
            out.println("<h3>Pregunta</h3>");
            out.println("</td>");
            out.println("<td>");
            out.println("<h3>Tipo</h3>");
            out.println("</td>");
            out.println("<td>");
            out.println("<h3>Accion</h3>");
            out.println("</td>");
            out.println("</tr>");
            for(int i=0;i<aux;i++)
            {
                out.println("<tr>");
                out.println("<td>");
                out.println("<h4>"+pregs[0][i]+"</h4>");
                out.println("</td>");
                out.println("<td>");
                out.println("<h4>"+pregs[2][i]+"</h4>");
                out.println("</td>");
                out.println("<td>");
                out.println("<h4>True or False</h4>");
                out.println("</td>");
                out.println("<td>");
                out.println("<a href='ver?id="+pregs[0][i]+"'>ver</a>|");
                out.println("<a href='eliminard?idc="+pregs[0][i]+"'>eliminar</a>|");
                out.println("<a href='modificard?idc="+pregs[0][i]+"'>modificar</a>");
                out.println("</td>");
                out.println("</tr>");
            }
            out.println("</table>");
            out.println("</div>");
            out.println("</body>");
            out.println("</html>");
            }
        }
    }
    
    
     public int tamano(String direc)
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
            aux=hijos.size();
            
        } catch (JDOMException | IOException ex) {
            Logger.getLogger(Maestro.class.getName()).log(Level.SEVERE, null, ex);
        }
        return aux;
    }
     
    public String[] diagrama(String direc)
    {
        int aux=tamano(direc);
        String[] nombU=new String[aux];
        
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
                nombU[i]=hijo.getAttributeValue("id");
            }
        } catch (JDOMException | IOException ex) {
            Logger.getLogger(Maestro.class.getName()).log(Level.SEVERE, null, ex);
        }
        return nombU;
    }
     public String[][] preguntas(String direc)
    {
        int aux=tamano(direc);
        String[][] nombU=new String[3][aux];    //Se crea un arreglo bidimensional que contendra los elementos de cada pregunta
        
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
            List hijos=raiz.getChildren();  //EL tag pregunta esta conformado por<Pregunta id="identificador" res="V/F">Texto que apaarecera en la pregunta</Pregunta>
            for(int i=0;i<hijos.size();i++)        
            {
                Element hijo=(Element)hijos.get(i);
                nombU[0][i]=hijo.getAttributeValue("id");   //En la pos 0 tendremos el id de cada pregunta  
                nombU[1][i]=hijo.getAttributeValue("res");  //En la Posicion 1 estara si la respuesta es Verdadera o Falsa
                nombU[2][i]=hijo.getText();//En la ultima posicion estara la pregunta la cual esta como texto entre la pregunta
            }
        } catch (JDOMException | IOException ex) {
            Logger.getLogger(Maestro.class.getName()).log(Level.SEVERE, null, ex);
        }
        return nombU;
    }
}
