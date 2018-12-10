
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

public class modificarPregunta extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String xml=request.getRealPath("WEB-INF\\ProtoToF.xml");
        String idPregunta=request.getParameter("id");
        String tipoPregunta=request.getParameter("tipo");
        HttpSession session=request.getSession();
        session.setAttribute("id",idPregunta);
        session.setAttribute("tipo",tipoPregunta);
        //String nombreExamen=request.getParameter("nombreExamen");
        String[] Preset=new String[3];
        Preset=getValuesPreguntaTOF(xml,idPregunta);
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
            out.println("<h1 class='blanco'>Modificar Pregunta ID: "+idPregunta+" del Tipo: "+tipoPregunta+"</h1>");
            if (tipoPregunta.equals("ToF")) {    
                    out.println("<form name='VoF' action='modiTrueFalse' method='get'>");                    
                        out.println("<table>");
                            /*out.println("<tr>");
                                out.println("<td> ID de la pregunta </td><td><input type='text' name='id' value="+idPregunta+" required /></td>");
                            out.println("</tr>");*/
                            out.println("<tr>");
                                out.println("<td> Texto de la pregunta </td><td><input type='text' name='texto' value='"+Preset[2]+"' required /></td>");
                            out.println("</tr>");
                            out.println("<tr>");
                                out.println("<td> Valor de la pregunta </td><td><input type='text' name='pond' value='"+Preset[1]+"' required /></td>");
                            out.println("</tr>");
                            out.println("<tr>");
                                out.println("<td> Respuesta </td><td><select name=\"res\" >" );    //selected='selected'
                                if (Preset[0].equals("V")) {
                                    out.println("<option selected='selected' value=\"V\">Verdadero</option>");
                                    out.println("<option value=\"F\">Falso</option>");
                                }
                                else {
                                    out.println("<option value=\"V\">Verdadero</option>");
                                    out.println("<option selected='selected' value=\"F\">Falso</option>");
                                }
                                out.println("</select>");
                                
                            out.println("</tr>");
                         /*out.println("<tr>");
                                out.println("<td> Agregar archivo </td><td><input type='file' name='archivo' /></td>");
                            out.println("</tr>");*/
                        out.println("</table");
                        out.println("<input type=\"reset\">");
                        out.println("<input type=\"submit\" value=\"Modificar\">");
                        out.println("<input type=\"button\" value=\"Cancelar\" onclick=\"document.location='Maestro'\">");
            
                    out.println("</form>");
                }
                else if (tipoPregunta.equals("HS")) {
                   /* out.println("<form name='VoF' action='modiHS' method='get'>");                    
                        out.println("<table>");
                            out.println("<tr>");
                                out.println("<td> ID de la pregunta </td><td><input type='text' name='id' value="+idPregunta+" required /></td>");
                            out.println("</tr>");
                            out.println("<tr>");
                                out.println("<td> Texto de la pregunta </td><td><input type='text' name='texto' value='"+Preset[2]+"' required /></td>");
                            out.println("</tr>");
                            out.println("<tr>");
                                out.println("<td> Valor de la pregunta </td><td><input type='text' name='pond' value='"+Preset[1]+"' required /></td>");
                            out.println("</tr>");
                            out.println("<tr>");
                                out.println("<td> Respuesta </td><td><select name=\"res\" >" );    //selected='selected'
                                if (Preset[0]=="V") {
                                    out.println("<option selected='selected' value=\"V\">Verdadero</option>");
                                    out.println("<option value=\"F\">Falso</option>");
                                }
                                else {
                                    out.println("<option value=\"V\">Verdadero</option>");
                                    out.println("<option selected='selected' value=\"F\">Falso</option>");
                                }
                                out.println("</select>");
                                
                            out.println("</tr>");
                         /*out.println("<tr>");
                                out.println("<td> Agregar archivo </td><td><input type='file' name='archivo' /></td>");
                            out.println("</tr>");
                        out.println("</table");
                        out.println("<input type=\"reset\">");
                        out.println("<input type=\"submit\" value=\"Crear\">");
                        out.println("<input type=\"button\" value=\"Cancelar\" onclick=\"document.location='Maestro'\">");
            
                    out.println("</form>");*/
                }
            out.println("<div class=\"row\">\n" +
            "  <div class=\"col-sm-8\"></div>\n" +
            "  <div class=\"col-sm-4\"><a class='blanco' href='Maestro'>Regresar</a></div>\n" +
            "</div>");
            out.println("</body>");
            out.println("</html>");
        }
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
        xmlOutput.output(bd_xml,new FileWriter(direc));
        } catch (JDOMException | IOException ex) {
            Logger.getLogger(eliminard.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Valores;
    }
}
