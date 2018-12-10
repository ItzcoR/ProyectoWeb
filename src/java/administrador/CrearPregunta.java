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
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

public class CrearPregunta extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Crear Pregunta True or False</title>"); 

            
            out.println("</head>");
            out.println("<body>");  //  out.println("");   out.println('');  <input type="reset"> 
            
            out.println("<div id='app'>");     
                out.println("<select name=\"tipoPreg\" v-model=\"selected\" options='opciones' required>\n" +
                "<option value=\"\" selected></option>\n" +
                "<option value=\"ToF\">Verdadero o Falso</option>\n" +
                "<option value=\"HotS\">HotSpot</option>\n" +
                "</select>");
                
                out.println("<template v-if=\"selected == 'ToF'\">");
                    out.println("<form name='VoF' action='trueFalse' method='get'>");
                    
                        out.println("<table>");
                            out.println("<tr>");
                                out.println("<td> ID de la pregunta </td><td><input type='text' name='id' required /></td>");
                            out.println("</tr>");
                            out.println("<tr>");
                                out.println("<td> Texto de la pregunta </td><td><input type='text' name='texto' required /></td>");
                            out.println("</tr>");
                            out.println("<tr>");
                                out.println("<td> Valor de la pregunta </td><td><input type='text' name='pond' required /></td>");
                            out.println("</tr>");
                            out.println("<tr>");
                                out.println("<td> Respuesta </td><td><select name=\"res\">\n" +
                                "<option value=\"V\">Verdadero</option>\n" +
                                "<option value=\"F\">Falso</option>\n" +
                                "</select>");
                            out.println("</tr>");
                         /*out.println("<tr>");
                                out.println("<td> Agregar archivo </td><td><input type='file' name='archivo' /></td>");
                            out.println("</tr>");*/
                        out.println("</table");
                        out.println("<input type=\"reset\">");
                        out.println("<input type=\"submit\" value=\"Crear\">");
                        out.println("<input type=\"button\" value=\"Cancelar\" onclick=\"document.location='Maestro'\">");
            
                    out.println("</form>");
                out.println("</template>");
                    
                out.println("<template v-if=\"selected == 'HotS'\">");
                    out.println("<form name='HotS' action='HotS' method='get'>");
                    
                        out.println("<table>");
                            out.println("<tr>");
                                out.println("<td> ID de la pregunta </td><td><input type='text' name='id' onkeypress=\"return letraNumVal(event)\" required /></td>");
                            out.println("</tr>");
                            out.println("<tr>");
                                out.println("<td> Texto de la pregunta </td><td><input type='text' name='texto' onkeypress=\"return letraNumVal(event)\" required /></td>");
                            out.println("</tr>");
                            out.println("<tr>");
                                out.println("<td> Valor de la pregunta </td><td><input type='text' name='pond' onkeypress=\"return numVal(event)\" required /></td>");
                            out.println("</tr>");
                            out.println("<tr>");
                            out.println("<td> Opciones </td>");
                            out.println("</tr>");
                            /*out.println("<tr>");
                                out.println("<td> Agregar archivo </td><td><input type='file' name='archivo' /></td>");
                            out.println("</tr>");*/
                        out.println("</table");
                        out.println("<input type=\"reset\">");
                        out.println("<input type=\"submit\" value=\"Crear\">");
                        out.println("<input type=\"button\" value=\"Cancelar\" onclick=\"document.location='Maestro'\">");
                            
                        out.println("</form>");
                out.println("</template>");
            out.println("</div>");
            out.println("<script src=\"https://cdn.jsdelivr.net/npm/vue/dist/vue.js\"></script>");
            out.println("<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js\"></script>");
            out.println("<script>");
            out.println("var app=new Vue({\n" +
"      el: '#app',\n" +
"      data: {\n" +
"               selected: ''\n" +
                //"opciones= [\n"+
                    //"{text: 'Verdadero o Falso', value: 'ToF'},\n"+
                    //"{text: 'HotSpot', value: 'HotS'},\n"+
"             }\n" +
"       });"); 
            out.println("</script"); 
            out.println("</body>");
            out.println("</html>");
        }
    }
    public int tamano(String direc)//Esta funcion regresa el numero de hijos del elemento raiz
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
    /*public String[][] preguntas(String direc)
    {
        int aux=tamano(direc);
        String[][] nombU=new String[4][aux];    //Se crea un arreglo bidimensional que contendra los elementos de cada pregunta
        
        try{
            SAXBuilder se encarga de cargar el archivo XML del disco o de un String 
            SAXBuilder builder=new SAXBuilder();
            //Forma de abriri el archivo
            File xmlFile = new File(direc);
            //Almacenamos el xml cargado en builder en un documento
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
                nombU[3][i]=hijo.getName();//En la cuarta posicion se guarda el nombre del tag, el cual nos indica el tipo de pregunta
            }
        } catch (JDOMException | IOException ex) {
            Logger.getLogger(Maestro.class.getName()).log(Level.SEVERE, null, ex);
        }
        return nombU;
    }*/
}
