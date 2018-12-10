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
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;


public class modificarExamen extends HttpServlet{
     @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        String xmlExam=request.getRealPath("WEB-INF\\ProtoExam.xml");
        String xmlPreg=request.getRealPath("WEB-INF\\ProtoToF.xml");
        String idExam=request.getParameter("idExamen");
        //String texto=request.getParameter("nombre");
        String nombreExam=request.getParameter("nombreExamen");
        int cantPregs=numPregs(xmlExam,idExam);
        HttpSession session=request.getSession();
        session.setAttribute("idViejo",idExam);
        session.setAttribute("nombre",nombreExam);
        String[] idpreg=getIDPreguntasDeExamen(xmlExam,idExam);
        String[][] valuesPregs=new String[4][cantPregs];
        valuesPregs=getValuesDePreguntas(xmlPreg,idpreg);
        //String[] resultado=getPreguntas(xmlExam,idExam,idpreg);
        int valorExamen=getValorExamen(xmlPreg,idpreg);
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Modificacion de Examen</title>"); 
            out.println("<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js\"></script>");
            out.println("<script src=\"https://cdn.jsdelivr.net/npm/vue/dist/vue.js\"></script>");
            out.println("<script type =\"text/javascript\">\n" +
"            function letraNumVal(e) {\n" +
"                tecla = document.all ? e.keyCode : e.which;\n" +
"                if (tecla === 8 || tecla === 32)\n" +
"                    return true;\n" +
"                patron = /[a-z]|[A-Z]|á|é|í|ó|ú|Á|É|Í|Ó|Ú|[0-9]/;\n" +
"                te = String.fromCharCode(tecla);\n" +
"                return patron.test(te);\n" +
"            }\n" +
"            function numVal(e) {\n" +
"                tecla = document.all ? e.keyCode : e.which;\n" +
"                if (tecla === 8 || tecla === 32)\n" +
"                    return true;\n" +
"                patron = /[0-9]/;\n" +
"                te = String.fromCharCode(tecla);\n" +
"                return patron.test(te);\n" +
"            }" );
            out.println("</script>"); 
            out.println("</head>");
            out.println("<body>");  //  out.println("");   out.println('');
            out.println("<div>");
                out.println("<div>");
                    out.println("<h1 >Modificando el Examen con nombre: "+nombreExam+"</h1>");
                    
                    out.println("<h1 >numero de Preguntas en el examen: "+cantPregs+"</h1>");
                    out.println("<h1 >Total de puntos en el examen: "+valorExamen+"</h1>");
                    out.println("<form name='EditarPreguntas' action='modExm' method='get'>");
                    out.println("<table id='listadoPregunatas'>");
                    out.println("   <thead>\n" +
                                "     <tr>\n" +
                                "       <th>ID Pregunta</th>\n" +
                                "       <th>Pregunta</th>\n" +
                                "       <th>Tipo</th>\n" +
                                "       <th>Respuesta</th>\n" +
                                "       <th>Valor</th>\n" +
                                "       <th>Acciones</th>\n" +
                                "       <th>valor</th>\n" +
                                "     </tr>\n" +
                                "     </thead>\n" +
                                "     <tbody>");
                    for(int i=0;i<cantPregs;i++)
                    {   
                        out.println("<tr>");
                        out.println("<td>");
                        out.println(idpreg[i]);    //out.println("<>");   out.println("");  out.println(''); 
                        out.println("</td>");
                        out.println("<td>");
                        out.println(valuesPregs[1][i]);//Texto Pregunta
                        out.println("</td>");
                        out.println("<td>");
                        out.println(valuesPregs[2][i]);  //Tipo out.println("<>");   out.println("");  out.println(''); 
                        out.println("</td>");
                        out.println("<td>");
                        out.println(valuesPregs[0][i]);    // Respuesta out.println("<>");   out.println("");  out.println(''); 
                        out.println("</td>");
                        out.println("<td>");
                        out.println(valuesPregs[3][i]);    // Valor out.println("<>");   out.println("");  out.println(''); 
                        out.println("</td>");
                        out.println("<td>");
                         out.println("<a class=\"btn_ver btn\" href='verPregunta?id="+idpreg[i]+"&tipo="+valuesPregs[2][i]+"'><i class=\"far fa-eye\"></i></a>");
                         out.println("<a class=\"btn_borrar btn\" href='eliminarDeExamen?id="+idpreg[i]+"&tipo="+valuesPregs[2][i]+"'><i class=\"fas fa-trash-alt\"></i></a>");
                         out.println("<a class=\"btn_editar btn\" href='modificarPregunta?id="+idpreg[i]+"&tipo="+valuesPregs[2][i]+"'><i class=\"fas fa-pen-square\"></i></i></a>");
                        out.println("</td>");
                         /* out.println("<td class=\"checkbox\">");
                        out.println(
                        "<label class=\"switch\">\n" +
                        "<input type=\"checkbox\"name='agregarExamen' value="+idpreg[i]+">\n" +
                        "<span class=\"slider round\"></span>\n" +
                        "</label>"); */                          
                        out.println("</td>");                           
                        out.println("</tr>");
                    }
        
                    out.println( "</tbody>");
                    out.println("</table>");	
                    out.println("</form>");
                    
                    out.println("<form action='modiExamen' method='get'>");
                    out.println("<h1 >Proporciona un nombre y un ID, para el examen. El ID debe ser único</h1>");
                    out.println("ID");
                    out.println("<input type='text' name='idNuevo' onkeypress=\"return letraNumVal(event)\" value='"+idExam+"'required />");
                    out.println("Nombre");
                    out.println("<input type='text' name='nombre' onkeypress=\"return letraNumVal(event)\"value='"+nombreExam+"' required />");
                
                    out.println("<input type=\"reset\">");
                    out.println("<input type=\"submit\" value=\"Crear\">");
                    out.println("</form>");
                    out.println(" <div ><a  href='Maestro'>Regresar</a></div>\n" +
                            "</div>");
                    
                out.println("</div>");
            out.println("</div>");
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
    public int numPregs(String direc,String idExamen)
    {
        int tam=0;    
        
        try{
            //SAXBuilder se encarga de cargar el archivo XML del disco o de un String 
            SAXBuilder builder=new SAXBuilder();
            //Forma de abriri el archivo
            File xmlFile = new File(direc);
            //Almacenamos el xml cargado en builder en un documento
            Document bd_xml=builder.build(xmlFile);
            //Elemento raiz
            Element raiz=bd_xml.getRootElement();
            //Se almacenan los hijos en una lista
            List hijos=raiz.getChildren();  
             for(int i=0;i<hijos.size();i++)
            {
                Element hijo=(Element)hijos.get(i);
                String id=hijo.getAttributeValue("id"); //Buscamos la id que queremos
                if(id.equals(idExamen))//Si la encuentra ponemos a tam a contar el numero de hijos de 
                {  
                    List preguntas=hijo.getChildren();
                    tam=preguntas.size();                    
                }
            }
            
        } catch (JDOMException | IOException ex) {
            Logger.getLogger(Maestro.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tam;
    }
    public String[] getIDPreguntasDeExamen(String direc,String idExamen)
    {
        int tam=numPregs(direc,idExamen);
        String[] ides=new String[tam];    //Se crea un arreglo bidimensional que contendra los elementos de cada pregunta
        
        try{
            //SAXBuilder se encarga de cargar el archivo XML del disco o de un String 
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
                String id=hijo.getAttributeValue("id"); //Checamos si las ids 
                if(id.equals(idExamen))//Si encuentra un elemento con el mismo id rompemos el ciclo
                {  
                    List preguntas=hijo.getChildren();
                    tam=preguntas.size(); 
                    for(int j=0;j<preguntas.size();j++){
                        Element pregunta=(Element) preguntas.get(j); 
                        ides[j]=pregunta.getAttributeValue("id");
                    }
                }
            }
            
        } catch (JDOMException | IOException ex) {
            Logger.getLogger(Maestro.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ides;
    }
    public String[][] getValuesDePreguntas(String direc,String[] idpregs)
    {
        int aux=idpregs.length;
        int n=0;
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
                String idbuscada=hijo.getAttributeValue("id"); //Checamos si las ids 
                if (Arrays.asList(idpregs).contains(idbuscada)) {
                    nombU[0][n]=hijo.getAttributeValue("res");  //En la Posicion 1 estara si la respuesta es Verdadera o Falsa
                    nombU[1][n]=hijo.getText();//En la ultima posicion estara la pregunta la cual esta como texto entre la pregunta
                    nombU[2][n]=hijo.getName();//En la cuarta posicion se guarda el nombre del tag, el cual nos indica el tipo de pregunta
                    nombU[3][n]=hijo.getAttributeValue("pond");
                    n++;
                }
                
                	
            }
        } catch (JDOMException | IOException ex) {
            Logger.getLogger(Maestro.class.getName()).log(Level.SEVERE, null, ex);
        }
        return nombU;
    } 
    public int getValorExamen(String direc,String[] idpregs)
    {
        int aux=tamano(direc);
        int total=0;
        //String[][] nombU=new String[4][aux];    //Se crea un arreglo bidimensional que contendra los elementos de cada pregunta
        
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
                String id=hijo.getAttributeValue("id"); //Checamos si las ids 
                if (Arrays.asList(idpregs).contains(id)) {
                        total=total+Integer.parseInt(hijo.getAttributeValue("pond"));
                }
                	
            }
        } catch (JDOMException | IOException ex) {
            Logger.getLogger(Maestro.class.getName()).log(Level.SEVERE, null, ex);
        }
        return total;
    } 
}
