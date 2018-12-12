
package administrador;

import java.io.File;
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


public class evaluarSiguiente extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String xmlPreg=request.getRealPath("WEB-INF\\ProtoToF.xml");
        String xmlExam=request.getRealPath("WEB-INF\\ProtoExam.xml");
        String[] Preset=new String[3];
        String resultado="";
        String opcion=request.getParameter("opcion");
        String ev=request.getParameter("ev");
        String resHotS=request.getParameter("ev");
        String pondHotS=request.getParameter("ev");
        HttpSession session=request.getSession();
        String idPreg=(String)session.getAttribute("idPreg");
        String idExam=(String)session.getAttribute("idExam");
        String tipo=(String)session.getAttribute("tipo");
        String res=(String)session.getAttribute("res");
        String[] idpregs=(String []) session.getAttribute("idpregs");
        String pond=(String)session.getAttribute("pond");
        int calificacion=(int)session.getAttribute("calificacion");
        int cantPregs=(int)session.getAttribute("cantPregs");
        int indicepreg=(int)session.getAttribute("indicepreg");
        Preset=getValuesPreguntaTOF(xmlPreg,idPreg);
        //resultado=evaluarTOF(xml,id,opcion);
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
            out.println("<h1 class='blanco'>Evaluando Pregunta ID: "+idPreg+" Tipo : "+tipo+"</h1>");
            out.println("<h1 class='blanco'>Opcion elegida: "+opcion+"</h1>");
            out.println("<h1 class='blanco'>ID del HotSpot: "+ev+"</h1>");
            out.println("<h1 class='blanco'>Respuesta correcta: "+resHotS+"</h1>");
            out.println("<h1 class='blanco'>Valor de la Pregunta "+pondHotS+"</h1>");
            out.println("<h1 class='blanco'>Indice de Preguntas en la posicion: "+indicepreg+"</h1>");
            out.println("<h1 class='blanco'>Calificacion: "+calificacion+"</h1>");
            if (indicepreg<cantPregs) {
                
            
                if (tipo.equals("ToF")) {
                     resultado=evaluarTOF(xmlPreg,idPreg,opcion);
                     out.println("<h1>"+resultado+"</h1>");
                      out.println("<div class=\"row\">\n" +
                "  <div class=\"col-sm-8\"></div>\n" +
                "  <div class=\"col-sm-4\"><a class='blanco' href='verSiguiente?idPreg="+idpregs[indicepreg=indicepreg+1]+"&tipo="+tipo+"'>Siguiente</a></div>");
                     if (resultado.equals("Respuesta correcta")) {
                         calificacion=calificacion+Integer.parseInt(pond);
                         out.println("<h1 class='blanco'>Calificacion actual "+calificacion+"</h1>");
                         session.setAttribute("calificacion",calificacion);
                     }
                     else{
                         out.println("<h1 class='blanco'>Eso es incorrecta Calificacion actual "+calificacion+"</h1>");
                         out.println("<div class=\"row\">\n" +
                "  <div class=\"col-sm-8\"></div>\n" +
                "  <div class=\"col-sm-4\"><a class='blanco' href='verSiguiente?idPreg="+idpregs[indicepreg=indicepreg+1]+"&tipo="+tipo+"'>Siguiente</a></div>");
                     }
                     
                 }
                 else if (tipo.equals("HotSpot")) {  //out.println("");
                     if(resHotS.equals(ev)){
                         resultado="Respuesta correcta";
                         out.println("<h1>"+resultado+"</h1>");
                         calificacion=calificacion+Integer.parseInt(pond);
                         session.setAttribute("calificacion",calificacion);
                       }
                 }
                out.println("<div class=\"row\">\n" +
                "  <div class=\"col-sm-8\"></div>\n" +
                "  <div class=\"col-sm-4\"><a class='blanco' href='verSiguiente?idPreg="+idpregs[indicepreg=indicepreg+1]+"&tipo="+tipo+"'>Siguiente</a></div>");
                out.println("<div class=\"row\">\n" +
            "  <div class=\"col-sm-8\"></div>\n" +
            //"  <div class=\"col-sm-4\"><a class='blanco' href='verSiguiente?idPreg="+idpregs[indicepreg+1]+"&tipo="+tipo+"'>Siguiente</a></div>\n" +
            "  <div class=\"col-sm-4\"><a class='blanco' href='index.html'>Regresar al indice</a></div>\n" +
            "</div>");
            out.println("</body>");
            out.println("</html>");
             }
                 out.println("<div class=\"row\">\n" +
            "  <div class=\"col-sm-8\"></div>\n" +
            //"  <div class=\"col-sm-4\"><a class='blanco' href='verSiguiente?idPreg="+idpregs[indicepreg+1]+"&tipo="+tipo+"'>Siguiente</a></div>\n" +
            "  <div class=\"col-sm-4\"><a class='blanco' href='index.html'>Regresar al indice</a></div>\n" +
            "</div>");
            out.println("</body>");
            out.println("</html>");
        }
        session.setAttribute("indicepreg",indicepreg+1);
        session.setAttribute("idpregs",idpregs);
        session.setAttribute("calificacion",calificacion);
    }
    public String[] getValuesPreguntaTOF(String direc,String id)    
    {
        String[] Valores=new String[3];
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
            XMLOutputter xmlOutput = new XMLOutputter();
            //Formato en el que se va a escribir
            xmlOutput.setFormat(Format.getPrettyFormat());
            for(int i=0;i<hijos.size();i++)
            {
                Element hijo=(Element)hijos.get(i);
                String iden=hijo.getAttributeValue("id");
                if(iden.equals(id))
                {
                    Valores[0]=hijo.getAttributeValue("res");
                    Valores[1]=hijo.getAttributeValue("pond");
                    Valores[2]=hijo.getText();
                }
            }
            //Se escribe el documento bd_xml en el archivo XML
        //xmlOutput.output(bd_xml,new FileWriter(direc));
        } catch (JDOMException | IOException ex) {
            Logger.getLogger(eliminard.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Valores;
    }
    public String evaluarTOF(String direc,String id,String opcion)    
    {
        String response="";
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
            XMLOutputter xmlOutput = new XMLOutputter();
            //Formato en el que se va a escribir
            xmlOutput.setFormat(Format.getPrettyFormat());
            for(int i=0;i<hijos.size();i++)
            {
                Element hijo=(Element)hijos.get(i);
                String iden=hijo.getAttributeValue("id");
                if(iden.equals(id))
                {
                    response="Se encontro el id";
                    hijo.getAttributeValue("res");
                   if(hijo.getAttributeValue("res").equals(opcion)){
                       response="Respuesta correcta";
                   }
                   else{
                       response="Respuesta incorrecta";
                   } 
                }
                
            }
            //Se escribe el documento bd_xml en el archivo XML
        //xmlOutput.output(bd_xml,new FileWriter(direc));
        } catch (JDOMException | IOException ex) {
            Logger.getLogger(eliminard.class.getName()).log(Level.SEVERE, null, ex);
        }
        return response;
    }
    public String evaluarHotS(String direc,String id,String opcion)    
    {
        String response="";
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
            XMLOutputter xmlOutput = new XMLOutputter();
            //Formato en el que se va a escribir
            xmlOutput.setFormat(Format.getPrettyFormat());
            for(int i=0;i<hijos.size();i++)
            {
                Element hijo=(Element)hijos.get(i);
                String iden=hijo.getAttributeValue("id");
                if(iden.equals(id))
                {
                    response="Se encontro el id";
                    hijo.getAttributeValue("res");
                   if(hijo.getAttributeValue("res").equals(opcion)){
                       response="Respuesta correcta";
                       break;
                   }
                   else{
                       response="Respuesta incorrecta";
                   }
                }
                
            }
            //Se escribe el documento bd_xml en el archivo XML
        //xmlOutput.output(bd_xml,new FileWriter(direc));
        } catch (JDOMException | IOException ex) {
            Logger.getLogger(eliminard.class.getName()).log(Level.SEVERE, null, ex);
        }
        return response;
    }
}