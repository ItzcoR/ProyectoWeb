
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
        String[] Preset=new String[3];
        String[] ValoresHotS=new String[4];
        //String nombreExamen=request.getParameter("nombreExamen");
        if (tipoPregunta.equals("ToF")) { 
            Preset=getValuesPreguntaTOF(xml,idPregunta);
        }
        else if (tipoPregunta.equals("HotSpot")) {
            ValoresHotS=getValuesPreguntaHotS(xml,idPregunta);
        }
        try (PrintWriter out = response.getWriter()) {
///////////////////////////////////////////////////////////////////////////////////////////
// ------------  HEADER  ------------------------------------------------------------------
///////////////////////////////////////////////////////////////////////////////////////////
out.println("<!DOCTYPE html>");
out.println("<html>");
out.println("<head>");
out.println("<title>Servlet Adm</title>"); 
out.println("<meta charset=\"utf-8\">\n" +
"<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
"<script src=\"js/vendor/modernizr-3.6.0.min.js\"></script>\n" +
"<script src=\"https://code.jquery.com/jquery-3.3.1.min.js\" integrity=\"sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8=\" crossorigin=\"anonymous\"></script>\n" +
"<script src=\"js/plugins.js\"></script>\n" +
"<script src=\"js/main.js\"></script>\n" +
"<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js\"></script>\n" +
"<script src=\"https://cdn.jsdelivr.net/npm/vue/dist/vue.js\"></script>\n" +
"<link rel=\"stylesheet\" href=\"css/normalize.css\">\n" +
"<link rel=\"stylesheet\" href=\"css/main.css\">\n" +
"<link rel=\"stylesheet\" href=\"https://use.fontawesome.com/releases/v5.5.0/css/all.css\" integrity=\"sha384-B4dIYHKNBt8Bc12p+WXckhzcICo0wtJAoU8YZTY5qE0Id1GSseTk6S+L3BlXeVIU\" crossorigin=\"anonymous\">\n" +
"</head>\n" +
"<body>\n" +
"<div class=\"contenedor_barra\">\n"+
"<h1>Sistema evaluador</h1>\n"+
"</div>");



///////////////////////////////////////////////////////////////////////////////////////////
// ------------  CONTENIDO  ---------------------------------------------------------------
///////////////////////////////////////////////////////////////////////////////////////////
            out.println(
                "<div class=\"contenedor bg_amarillo sombra\">"
                +"<div class=\"contenedor_alertas\">"
                
                +"<legend>Modificar Pregunta<span>ID: "+idPregunta+" del Tipo: "+tipoPregunta+"</span></legend>");
                
               


            if (tipoPregunta.equals("ToF")) {    
                    out.println("<form name='VoF' action='modiTrueFalse' method='get'>");                    
                    
                            /*out.println("<tr>");
                                out.println("<td> ID de la pregunta </td><td><input type='text' name='id' value="+idPregunta+" required /></td>");
                            out.println("</tr>");*/
                    out.println(
                    "<div class=\"campos_usuario\">\n"+

              
                "<div class=\"campo_usuario\">\n"+
        
                  "<label for=\"nombre\">Pregunta:</label>\n"+
                  "<input type='text' name='texto' value='"+Preset[2]+"' required autocomplete=\"off\" >\n"+
        
                "</div>\n"+
        
                
        
        
                "<div class=\"campo_usuario\">\n"+
        
                  "<label for=\"empresa\">Valor:</label>\n"+
                  "<input type='number' name='pond' value='"+Preset[1]+"' required autocomplete=\"off\" placeholder=\"Id usuario\">\n"+
        
                "</div>\n"+
        
        
        
                "<div class=\"campo_usuario\">\n"+
        
                  "<label for=\"empresa\">Tipo de usuario:</label>\n"+
                  "<select name=\"res\">");
                  if (Preset[0].equals("V")) {
                    out.println("<option selected='selected' value=\"V\">Verdadero</option>");
                    out.println("<option value=\"F\">Falso</option>");
                }
                else {
                    out.println("<option value=\"V\">Verdadero</option>");
                    out.println("<option selected='selected' value=\"F\">Falso</option>");
                }

                out.println(
                  "</select>\n"+
        
                "</div>\n"+
        
        
              "</div>");
            


            
                
                }
                else if (tipoPregunta.equals("HotSpot")) {
                    int nOps=numOpciones(xml,idPregunta);
                    String[][] ValoresOpciones=new String[4][nOps];
                    ValoresOpciones=ObtenerOpciones(xml,idPregunta);
                    out.println("<legend><span>numero de Opciones: "+nOps+"</span></legend>");
                  
                    out.println("<img src='"+ValoresHotS[2]+"'>");
                    out.println("<form name='HotS' action='modiHs' method='get'>");                    
                        out.println(

                        
                            "<div class=\"campos_usuario\">\n"+
        
                      
                        "<div class=\"campo_usuario\">\n"+
                
                          "<label for=\"nombre\">Valor de la pregunta:</label>\n"+
                          "<input type='number' name='pond' value='"+ValoresHotS[1]+"' required autocomplete=\"off\" >\n"+
                
                        "</div>\n"+
                        "<div class=\"campo_usuario\">\n"+
                
                          "<label for=\"nombre\">Respuesta:</label>\n"+
                          "<input type='text' name=\"res\" value='"+ValoresHotS[0]+"' required autocomplete=\"off\" >\n"+
                
                        "</div>\n"+
                        "</div>\n"+
                        "<hr>");
                  


                            for (int l=0;l<nOps ;l++ ) {
                                out.println(
                                    "<div class=\"campos_usuario\">\n"+
        
                                "<div class=\"campo_usuario\">\n"+
                                "<label for=\"nombre\">ID de la opcion:</label>\n"+
                                "<input type='text' name='idOpcion' value="+ValoresOpciones[0][l]+" required autocomplete=\"off\" >\n"+
                                "</div>\n"+

                                "<div class=\"campo_usuario\">\n"+
                                "<label for=\"nombre\">Cordenada X:</label>\n"+
                                "<input type='number' name='coordX' value="+ValoresOpciones[1][l]+" required autocomplete=\"off\" >\n"+
                                "</div>\n"+


                                "<div class=\"campo_usuario\">\n"+
                                "<label for=\"nombre\">Cordenada Y:</label>\n"+
                                "<input type='number' name='coordY' value="+ValoresOpciones[2][l]+" required autocomplete=\"off\" >\n"+
                                "</div>\n"+


                                "<div class=\"campo_usuario\">\n"+
                                "<label for=\"nombre\">Radio:</label>\n"+
                                "<input type='number' name='radio' value="+ValoresOpciones[3][l]+" required autocomplete=\"off\" >\n"+
                                "</div>\n"+
                                "</div>\n"+
                                "<hr>");

                        
                            
                            }                         
                            
                        

                    
                    // out.println("</div>");
                }
                
                out.println(
                    "<div class=\"contenedor_botones\">\n"+
                    "<div class=\"campo_usuario enviar\">\n"+
                "<input type=\"hidden\" id=\"accion\" value=\"crear\">\n"+
                "<input type=\"submit\" class=\"btn-info\" value=\"Modificar\">\n"+
                "<input  type=\"button\" class=\"btn-info\" value=\"Cancelar\" onclick=\"document.location='Maestro'\">\n"+
                "</div>\n"+
                "</div>");
               
                out.println("</form>");

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
        //xmlOutput.output(bd_xml,new FileWriter(direc));
        } catch (JDOMException | IOException ex) {
            Logger.getLogger(modificarPregunta.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Valores;
    }
    public String[] getValuesPreguntaHotS(String direc,String id)    
    {
        String[] Valores=new String[4];
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
                    Valores[2]=hijo.getAttributeValue("src");
                    Valores[3]=hijo.getText();
                }
            }
            //Se escribe el documento bd_xml en el archivo XML
        //xmlOutput.output(bd_xml,new FileWriter(direc));
        } catch (JDOMException | IOException ex) {
            Logger.getLogger(modificarPregunta.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Valores;
    }
    public String agregarOpciones(String ruta,String id,String[] idopc,String[] coordX,String[] coordY,String[] radios)
    {
        String resultado="";
        int numeroOpciones=coordX.length;
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
                    for (int j=0;j<numeroOpciones ;j++ ) {
                        Element nuevo=new Element("HS");
                        nuevo.setAttribute("id",idopc[j]);
                        nuevo.setAttribute("coordX",coordX[j]);
                        nuevo.setAttribute("coordY",coordY[j]);
                        nuevo.setAttribute("radio",radios[j]);
                        hijo.addContent(nuevo);
                        resultado="Pregunta agregada exitosamente";
                    }
                        
                 }
                
            }
      xmlOutput.output(bd_xml,new FileWriter(ruta));
       
        } catch (JDOMException | IOException ex) {
            Logger.getLogger(Mapear.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultado;
    }
    public int numOpciones(String ruta,String id)
    {
        
        //int numeroOpciones=coordX.length;
        int aux=0;
       // String[][] opciones=new String[4][aux];
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
                    List opciones=hijo.getChildren();
                    aux=opciones.size();
                    return aux;
                        
                 }
                
            }
      //xmlOutput.output(bd_xml,new FileWriter(ruta));
       
        } catch (JDOMException | IOException ex) {
            Logger.getLogger(Mapear.class.getName()).log(Level.SEVERE, null, ex);
        }
        return aux;
    }
    public String[][] ObtenerOpciones(String ruta,String id)
    {
        
        int n=0;
        int aux=numOpciones(ruta,id);
        String[][] opcionesValues=new String[4][aux];
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
                    List opciones=hijo.getChildren();
                    for (int j=0;j<opciones.size() ;j++ ) {
                        Element opcion=(Element)opciones.get(j);
                        opcionesValues[0][n]=opcion.getAttributeValue("id");
                        opcionesValues[1][n]=opcion.getAttributeValue("coordX");
                        opcionesValues[2][n]=opcion.getAttributeValue("coordY");
                        opcionesValues[3][n]=opcion.getAttributeValue("radio");
                        n++;
                    }
                        
                 }
                 
            }
      //xmlOutput.output(bd_xml,new FileWriter(ruta));
       
        } catch (JDOMException | IOException ex) {
            Logger.getLogger(Mapear.class.getName()).log(Level.SEVERE, null, ex);
        }
        return opcionesValues;
    }
}
