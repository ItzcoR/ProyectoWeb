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

public class CrearPregunta extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
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

// ------------  True or False -----------------------------------------------------------
out.println("<div class='bg_amarillo sombra contenedor'>");     
out.println("<div class='contenedor_alertas'>");     
            out.println("<div id='app'>");
            out.println("<div class='contenedor_selector'>");     
                out.println("<select class=\"selector rounded\" name=\"tipoPreg\" v-model=\"selected\" options='opciones' required>\n" +
                "<option value=\"ToF\">Verdadero o Falso</option>\n" +
                "<option value=\"HotS\">HotSpot</option>\n" +
                "</select>");
                out.println("</div>");

                out.println("<template v-if=\"selected == 'ToF'\">");
                    out.println("<form name='VoF' action='trueFalse' method='get'>");
                    out.println(
                        "<div class=\"campos_usuario\">\n"+

              
                        "<div class=\"campo_usuario\">\n"+
                
                          "<label>Id:</label>\n"+
                          "<input type='text' name='id' required autocomplete=\"off\"  placeholder=\"Id unico\">\n"+
                
                        "</div>\n"+
                
                        
                        "<div class=\"campo_usuario\">\n"+
                
                          "<label>Pregunta:</label>\n"+
                          "<input type='text' name='texto' required autocomplete=\"off\" placeholder=\"Aqui va tu pregunta ...\">\n"+
                
                        "</div>\n"+
                
                
                
                        "<div class=\"campo_usuario\">\n"+
                
                          "<label>Valor:</label>\n"+
                          "<input type='number' name='pond' onkeypress=\"return numVal(event)\" required placeholder=\"Cuanto vale tu pregunta?\" autocomplete=\"off\">\n"+
                
                        "</div>\n"+

                        "<div class=\"campo_usuario\">\n"+
                
                        "<label>Respuesta:</label>\n"+
                        "<select name=\"res\">\n"+
                        "<option value=\"V\">Verdadero</option>\n" +
                        "<option value=\"F\">Falso</option>\n" +
                        "</select>\n"+
              
                      "</div>\n"+
                        "</div>");



                        out.println("<div class=\"campo_usuario enviar\">");
                        out.println("<input type=\"reset\">");
                        out.println("<input type=\"submit\" value=\"Crear\">");
                        out.println("<input type=\"button\" value=\"Cancelar\" onclick=\"document.location='Maestro'\">");
                        out.println("</div>");

                    out.println("</form>");
                out.println("</template>");
                    

// ------------  Hot Spot ---------------------------------------------------------------

                out.println("<template v-if=\"selected == 'HotS'\">");
                    out.println("<form name='HotS' action='HotS' method='post' enctype=\"multipart/form-data\">");
                    

                    out.println(
                        "<div class=\"campos_usuario\">\n"+

              
                        "<div class=\"campo_usuario\">\n"+
                
                          "<label>Id:</label>\n"+
                          "<input type='text' name='id' onkeypress=\"return letraNumVal(event)\" required autocomplete=\"off\"input type='text' name='pond' onkeypress=\"return numVal(event)\" required placeholder=\"Id unico\">\n"+
                
                        "</div>\n"+
                
                        
                        "<div class=\"campo_usuario\">\n"+
                
                          "<label>Pregunta:</label>\n"+
                          "<input type='text' name='texto' onkeypress=\"return letraNumVal(event)\" required autocomplete=\"off\" placeholder=\"Aqui va tu pregunta ...\">\n"+
                
                        "</div>\n"+
                         
                         "<div class=\"campo_usuario\">\n"+
                
                          "<label>Respuesta:</label>\n"+
                          "<input type='text' name='res' onkeypress=\"return letranumVal(event)\" required placeholder=\"ID de la Opcion correcta\"  autocomplete=\"off\">\n"+
                
                        "</div>\n"+
                
                
                        "<div class=\"campo_usuario\">\n"+
                
                          "<label>Valor:</label>\n"+
                          "<input type='number' name='pond' onkeypress=\"return numVal(event)\" required placeholder=\"Cuanto vale tu pregunta?\"  autocomplete=\"off\">\n"+
                
                        "</div>\n"+

                        "<div class=\"campo_usuario\">\n"+
                
                        "<label>Numero de Opciones:</label>\n"+
                        "<input type='text' name='nOpciones' onkeypress=\"return numVal(event)\" required placeholder=\"Cuantas opciones deseas?\" autocomplete=\"off\">\n"+
                        "</div>\n"+
                        "</div>");
  
                    
                        out.println("<div class=\"campo_usuario enviar\">");
                        out.println("<input type='file' name='file' />");
                        out.println("<input type=\"reset\">");
                        out.println("<input type=\"submit\" value=\"Crear\">");
                        out.println("<input type=\"button\" value=\"Cancelar\" onclick=\"document.location='Maestro'\">");
                        
                        out.println("</div>");
                     out.println("</form>");
               
                out.println("</template>");
            out.println("</div>");




            out.println("<script>");
            out.println("var app=new Vue({\n" +
"      el: '#app',\n" +
"      data: {\n" +
"               selected: 'HotS'\n" +
                //"opciones= [\n"+
                    //"{text: 'Verdadero o Falso', value: 'ToF'},\n"+
                    //"{text: 'HotSpot', value: 'HotS'},\n"+
"             }\n" +
"       });"); 
            out.println("</script>"); 



////////////////////////////////////////////////////////////////////////////////////////////
// ------------  FOOTER  -------------------------------------------------------------------
///////////////////////////////////////////////////////////////////////////////////////////               
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
