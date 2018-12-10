
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
        String xmlExamen=request.getRealPath("WEB-INF\\ProtoExam.xml");
        
        int aux=tamano(xml);
        int aux2=tamano(xmlExamen);
        //String[] =new String[aux];
        //=diagrama(xml);
        String[][] pregs=new String[4][aux];
        pregs=preguntas(xml);
        String[][] examenes=new String[2][aux2];
        examenes=getExamenes(xmlExamen);
        HttpSession session=request.getSession();
        String id=(String) session.getAttribute("id");
        String tipo=(String) session.getAttribute("tipo");
        if(id!=null)
        {
        try (PrintWriter out = response.getWriter()) {


            
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Adm</title>"); 
            out.println("<meta charset=\"utf-8\">\n" +
            "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
            "<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js\"></script>\n" +
            "<script src=\"https://cdn.jsdelivr.net/npm/vue/dist/vue.js\"></script>");
            out.println("<link rel=\"stylesheet\" href=\"css/normalize.css\">");
            out.println("<link rel=\"stylesheet\" href=\"css/main.css\">");
            out.println("<link rel=\"stylesheet\" href=\"https://use.fontawesome.com/releases/v5.5.0/css/all.css\" integrity=\"sha384-B4dIYHKNBt8Bc12p+WXckhzcICo0wtJAoU8YZTY5qE0Id1GSseTk6S+L3BlXeVIU\" crossorigin=\"anonymous\">");
            out.println("</head>");
            out.println("<body >");
                 out.println("<div class=\"contenedor_barra\">\n"+
            "<h1>Sistema evaluador</h1>\n"+
            "</div>");


            out.println("<div id='appselect'>\n"+
            
            "<div class=\"bg_blanco contenedor sombra contactos\">\n"+
            "<legend>\n"+
            " Seleccione si desea ver preguntas o Examenes<span>La creación de examenes es en la tabla de pregunatas</span>\n"+
            "</legend>");
            
                out.println("<div class=\"rounded\">");
                out.println("<select name=\"tipoTabla\" v-model=\"selected\"  class=\"selector rounded\">\n" +
                "<option value=\"Pregs\">Ver Preguntas</option>\n" +
                "<option value=\"Examen\">Ver Examenes</option>\n" +
                "</select>");
                out.println("</div>");
                
                out.println("<template v-if=\"selected == 'Pregs' \">");
            out.println("<form action='crearExamen' method='get'>");
            
            out.println(
                "<h2 class=\"espacio\">Bienvenido profesor: "+id+"</h2>\n" +
                   
                    "<div class=\"contenedor_contactos\">\n"+
                    
                        "<div class=\"contenedor_tabla\">\n"+
                            
                        "<table class=\"listado_contactos maestro\" id=\"listado_contactos\">\n" +
                        
                        "   <thead>\n" +
                        "     <tr>\n" +
                        "       <th>ID Pregunta</th>\n" +
                        "       <th>Pregunta</th>\n" +
                        "       <th>Tipo</th>\n" +
                        "       <th>Acciones</th>\n" +
                        "       <th>¿Agregar a un examen?</th>\n" +
                        "     </tr>\n" +
                        "     </thead>\n" +
                        "     <tbody>");

                        for(int i=0;i<aux;i++)
                        {   
                            out.println("<tr>");
                            out.println("<td>");
                            out.println(pregs[0][i]);    //out.println("<>");   out.println("");  out.println(''); 
                            out.println("</td>");
                            out.println("<td>");
                            out.println(pregs[2][i]);
                            out.println("</td>");
                            out.println("<td>");
                            out.println(pregs[3][i]);        //out.println("<>");   out.println("");  out.println(''); 
                            out.println("</td>");
                            out.println("<td>");
                            out.println("<a class=\"btn_ver btn\" href='ver?id="+pregs[0][i]+"'><i class=\"far fa-eye\"></i></a>");
                            out.println("<a class=\"btn_borrar btn\" href='eliminard?idc="+pregs[0][i]+"'><i class=\"fas fa-trash-alt\"></i></a>");
                            out.println("<a class=\"btn_editar btn\" href='modificard?idc="+pregs[0][i]+"'><i class=\"fas fa-pen-square\"></i></i></a>");
                            out.println("</td>");
                            out.println("<td class=\"checkbox\">");
                            out.println(
                            "<label class=\"switch\">\n" +
                            "<input type=\"checkbox\"name='agregarExamen' value="+pregs[0][i]+">\n" +
                            "<span class=\"slider round\"></span>\n" +
                            "</label>");

                            out.println("</td>");
                            out.println("</tr>");
                        }


                       out.println( "</tbody>\n" +
                       "</table>\n" +
                        "</div>\n" +
                        "</div>\n" +
                        "<div class=\"contenedor_botones\">\n"+
                        "<input class=\"btn btn_existe btn_admin\" type=\"submit\" value=\"Agregar a un Examen\">\n" +
                        "<input class=\"btn btn_existe btn_admin\" type=\"reset\">\n" +
                        "<a class=\"btn btn_existe btn_admin\" href='cerrar'>Cerrar Sesion</a>\n" +
                        "<a class=\"btn btn_existe btn_admin\" href='CrearPregunta'>Crear Pregunta</a>\n" +
                        "</div>\n"+
                "</div>"
               
            );

            out.println("</form>");
            out.println("</template>"); //out.println("<>");   out.println("");  out.println(''); 

            out.println("<template v-if=\"selected == 'Examen'\">");
            out.println("<h2  class=\"espacio\">Bienvenido profesor: "+id+"</h2>\n"+
            "<div class=\"contenedor_contactos\">\n"+
                    
                        "<div class=\"contenedor_tabla\">");
            out.println("<table id='listaExamnes' class=\"listado_contactos maestro\">");
                out.println("<thead>\n" +
                        "     <tr>\n" +
                        "       <th>ID</th>\n" +
                        "       <th>Nombre</th>\n" +
                        "       <th>Acciones</th>\n" +
                        "     </tr>\n" +
                        "   </thead>\n" +
                        "   <tbody>\n");
                for(int i=0;i<aux2;i++){
                    out.println("<tr > <td>" +examenes[0][i]+"</td>" ); //ID ""
                    out.println("<td>"+examenes[1][i]+"</td>");//Nombre
                    out.println("<td>");
                    out.println("<a class=\"btn_ver btn\" href='verExamen?id="+examenes[0][i]+"&nombreExamen="+examenes[1][i]+"'><i class=\"far fa-eye\"></i></a>");
                    out.println("<a class=\"btn_borrar btn\" href='eliminarExamen?idExamen="+examenes[0][i]+"'><i class=\"fas fa-trash-alt\"></i></a>");
                    out.println("<a class=\"btn_editar btn\" href='modificarExamen?idExamen="+examenes[0][i]+"&nombreExamen="+examenes[1][i]+"'><i class=\"fas fa-pen-square\"></i></i></a>");
                    }
            out.println("</table>");
            out.println("</div>");
            out.println("</div>");
            out.println("<div class=\"contenedor_botones\">\n"+
                        // "<input class=\"btn btn_existe btn_admin\" type=\"submit\" value=\"Agregar a un Examen\">\n" +
                        // "<input class=\"btn btn_existe btn_admin\" type=\"reset\">\n" +
                        "<a class=\"btn btn_existe btn_admin\" href='cerrar'>Cerrar Sesion</a>\n" +
                        // "<a class=\"btn btn_existe btn_admin\" href='CrearPregunta'>Crear Pregunta</a>\n" +
                        "</div>");

            out.println("</template>");
            out.println("</div>");
            out.println("<script>\n" +
                "var countChecked = function() {\n" +
                "  var n = $( \"input:checked\" ).length;\n" +
                "  $( \"p\" ).text( n +  \" Preguntas seleccionadas\" );\n" +
                "};\n" +
                "countChecked();\n" +
                " \n" +
                "$( \"input[type=checkbox]\" ).on( \"click\", countChecked );\n" +
                "</script>");
            out.println("<script>");
            out.println("var app2=new Vue({\n" +
            "      el: '#appselect',\n" +
            "      data: {\n" +
            "               selected: 'Pregs'\n" +
                            //"opciones= [\n"+
                                //"{text: 'Verdadero o Falso', value: 'ToF'},\n"+
                                //"{text: 'HotSpot', value: 'HotS'},\n"+
            "             }\n" +
            "       });"); 
            out.println("</script"); 
                
               
                /////////////////////////////////////////////////////////////////////////////////////////////////////////
                // Footer
                /////////////////////////////////////////////////////////////////////////////////////////////////////////
                out.println(
                        "<script src=\"js/vendor/modernizr-3.6.0.min.js\"></script>\n" +
                        "<script src=\"https://code.jquery.com/jquery-3.3.1.min.js\" integrity=\"sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8=\" crossorigin=\"anonymous\"></script>\n" +
                        "<script>window.jQuery || document.write('<script src=\"js/vendor/jquery-3.3.1.min.js\"><\/script>')</script>\n" +
                        "<script src=\"js/plugins.js\"></script>\n" +
                        "<script src=\"js/main.js\"></script>\n" +

                        "<!-- Google Analytics: change UA-XXXXX-Y to be your site's ID. -->\n" +
                        <
                        script > \n "+
                        "  window.ga = function () { ga.q.push(arguments) }; ga.q = []; ga.l = +new Date;\n" +
                        "  ga('create', 'UA-XXXXX-Y', 'auto'); ga('send', 'pageview')\n" +
                        "</script>\n" +
                        " <script src=\"https://www.google-analytics.com/analytics.js\" async defer></script>\n" +
                        "</body>\n" +

                        "</html>");
            }
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
     
     public String[][] preguntas(String direc)
    {
        int aux=tamano(direc);
        String[][] nombU=new String[4][aux];    //Se crea un arreglo bidimensional que contendra los elementos de cada pregunta
        
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
                nombU[3][i]=hijo.getName();//En la cuarta posicion se guarda el nombre del tag, el cual nos indica el tipo de pregunta
            }
        } catch (JDOMException | IOException ex) {
            Logger.getLogger(Maestro.class.getName()).log(Level.SEVERE, null, ex);
        }
        return nombU;
    }
    public String[][] getExamenes(String direc)
    {
        int aux=tamano(direc);
        String[][] nombU=new String[2][aux];    //Se crea un arreglo bidimensional que contendra los elementos de cada pregunta
        
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
                nombU[1][i]=hijo.getAttributeValue("nombre");  //En la Posicion 1 estara  la respuesta 
            }
        } catch (JDOMException | IOException ex) {
            Logger.getLogger(Maestro.class.getName()).log(Level.SEVERE, null, ex);
        }
        return nombU;
    }

}
