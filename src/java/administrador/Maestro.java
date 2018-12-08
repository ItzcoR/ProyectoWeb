
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
        //String[] =new String[aux];
        //=diagrama(xml);
        String[][] pregs=new String[4][aux];
        pregs=preguntas(xml);
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
            
            out.println("<form action='crearExamen' method='get'>");
            
            out.println(
                "<div class=\"bg_blanco contenedor sombra contactos\">\n"+
                "<h2>Bienvenido profesor: "+id+"</h2>\n" +
                    "<p  class=\"total_contactos\"><span></span> Contactos</p>\n"+
                   
                    "<div class=\"contenedor_contactos\">\n"+
                    
                        "<div class=\"contenedor_tabla\">\n"+
                            
                        "<table class=\"listado_contactos maestro\" id=\"listado_contactos\">\n" +
                        
                        "   <thead>\n" +
                        "     <tr>\n" +
                        "       <th>ID Pregunta</th>\n" +
                        "       <th>Pregunta</th>\n" +
                        "       <th>Tipo</th>\n" +
                        "       <th>Acciones</th>\n" +
                        "       <th>¿Agregar al examen?</th>\n" +
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
                            out.println(pregs[3][i]);
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
                        "<input type=\"submit\" value=\"Agregar a un Examen\">\n" +
                        "<input type=\"reset\">\n" +
                        "<a href='cerrar'>Cerrar Sesion</a>\n" +
                        "<a href='CrearPregunta'>Crear Pregunta</a>\n" +
                        "</div>\n"+
                "</div>"
               
            );

            out.println("</form>");


                
                out.println("<script>\n" +
                "var countChecked = function() {\n" +
                "  var n = $( \"input:checked\" ).length;\n" +
                "  $( \"p\" ).text( n +  \" Preguntas seleccionadas\" );\n" +
                "};\n" +
                "countChecked();\n" +
                " \n" +
                "$( \"input[type=checkbox]\" ).on( \"click\", countChecked );\n" +
                "</script>");
               
                out.println("</body>");
                out.println("</html>");
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
}
