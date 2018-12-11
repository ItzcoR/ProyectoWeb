package administrador;

import java.io.File;
import java.io.FileWriter;
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

public class verSiguiente extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String xmlExam=request.getRealPath("WEB-INF\\ProtoExam.xml");
        String xmlPreg=request.getRealPath("WEB-INF\\ProtoToF.xml");
        //String nodo=request.getParameter("tipo");
        HttpSession session=request.getSession();
        String idExam=(String) session.getAttribute("idExam");
        String nombre=(String) session.getAttribute("nombre");
        int indicepreg=(int) session.getAttribute("indicepreg");
        String[] idpregs=(String []) session.getAttribute("idpregs");
        int calificacion=(int) session.getAttribute("calificacion");
        int cantPregs=numPregs(xmlExam,idExam);
        String[] ValoresHotS=new String[4];
        //ValoresHotS=getValuesPreguntaHotS(xmlPreg,idpregs[indicepreg]);
        String[][] valuesPregs=new String[4][cantPregs];    
        valuesPregs=getValuesDePreguntas(xmlPreg,idpregs);
        String tipo=valuesPregs[2][indicepreg];
        
        //indicepreg++;
        session.setAttribute("idExam",idExam);
        session.setAttribute("nombre",nombre);
        session.setAttribute("indicepreg",indicepreg);
        session.setAttribute("idpregs",idpregs);
        session.setAttribute("calificacion",calificacion);
        
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
                "<div class=\"contenedor bg_amarillo sombra\">\n"+
                    "<div class=\"contenedor_alertas\">\n"+
                    "<legend> </legend>\n"+
                    "</div>\n"+
                "</div>"
                );
                out.println("<h1 class='blanco'>Ver Pregunta ID: "+idpregs[indicepreg]+" Tipo : "+tipo+"</h1>");
            
             if (tipo.equals("ToF")) {
                 out.println("<h1>"+valuesPregs[1][indicepreg]+"");
                 out.println("<form action='evaluar' method='get'>\n" +
"  <input type=\"radio\" name=\"opcion\" value=\"V\"> Verdadero<br>\n" +
"  <input type=\"radio\" name=\"opcion\" value=\"F\"> Falso<br>\n" +
"  <input type=\"submit\" value=\"Submit\">\n" +
"</form>");
             }
             else if (tipo.equals("HotSpot")) {  //out.println("");
                 int nOps=numOpciones(xmlPreg,idpregs[indicepreg]);
                 ValoresHotS=getValuesPreguntaHotS(xmlPreg,idpregs[indicepreg]);
                 String[][] ValoresOpciones=new String[4][nOps];
                 ValoresOpciones=ObtenerOpciones(xmlPreg,idpregs[indicepreg]);
                 out.println("<h1 class='blanco'>Pregunta: "+ValoresHotS[3]+"</h1>");
                 out.println("<img src='"+ValoresHotS[2]+"' usemap='#mapa'>");
                 out.println("<map name=\"mapa\">");
                 
                 for(int i=0; i<nOps;i++ ){
                   out.println("<area shape=\"circle\" coords='"+ValoresOpciones[1][i]+","+ValoresOpciones[2][i]+","+ValoresOpciones[3][i]+"'  href='evaluar?ev="+ValoresOpciones[0][i]+"&id="+idpregs[indicepreg]+"&res="+ValoresHotS[0]+"&pond="+ValoresHotS[1]+"'>");
                    
                }
                 out.println("</map>");


        ////////////////////////////////////////////////////////////////////////////////////////////
        // ------------  FOOTER  -------------------------------------------------------------------
        ///////////////////////////////////////////////////////////////////////////////////////////               
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
}