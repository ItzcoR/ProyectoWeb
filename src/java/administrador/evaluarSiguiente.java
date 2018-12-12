
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
        String[] Preset=new String[3];  //La uso para recuperar los datos de ToF
        String resultado="";
        String opcion=request.getParameter("opcion");
        String ev=request.getParameter("ev");
        
        String resHotS=request.getParameter("ev");
        String pondHotS="";
        HttpSession session=request.getSession();
        String idPreg=(String)session.getAttribute("idPreg");
        String idExam=(String)session.getAttribute("idExam");
        String tipo=getTipo(xmlPreg,idPreg);
        //session.setAttribute("tipo",tipo);
        int cantPregs=numPregs(xmlExam,idExam);
        //String tipo=(String)session.getAttribute("tipo");
        String res=(String)session.getAttribute("res");
        String[] idpregs=(String []) session.getAttribute("idpregs");
        String[] ValoresHotS=new String[4];
        String[][] valuesPregs=new String[4][cantPregs];
        valuesPregs=getValuesDePreguntas(xmlPreg,idpregs);
        //String[] ToFValores=getValuesPreguntaTOF(xmlPreg,idPreg);
        session.setAttribute("tipo",tipo);
        String pond=(String)session.getAttribute("pond");
        int calificacion=(int)session.getAttribute("calificacion");
        //int cantPregs=(int)session.getAttribute("cantPregs");
        int indicepreg=(int)session.getAttribute("indicepreg");
        int indiceSig=indicepreg+1;
        if(indicepreg>0){int indiceReg=indicepreg-1;}
        int indiceReg=indicepreg;
        String tipoSig="";//valuesPregs[2][indiceSig];
        String tipoReg="";//valuesPregs[2][indiceSig];
        if(indiceSig<cantPregs){
        tipoSig=valuesPregs[2][indiceSig];
        tipoReg=valuesPregs[2][indiceSig];
        }
        if(indiceReg>0&&indiceReg<cantPregs){
            tipoReg=valuesPregs[2][indiceReg];
        }
        
        Preset=getValuesPreguntaTOF(xmlPreg,idPreg); //Valores ToF  Valores[0]=res; Valores[1]=pond Valores[2]=getText();
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
            //out.println("<h1 class='blanco'>Respuesta correcta: "+resHotS+"</h1>");
            //out.println("<h1 class='blanco'>Valor de la Pregunta "+pondHotS+"</h1>");
            out.println("<h1 class='blanco'>Indice de Preguntas en la posicion: "+indicepreg+"</h1>");
            out.println("<h1 class='blanco'>Calificacion: "+calificacion+"</h1>");
            out.println("<h1 class='blanco'>Lista de Preguntas del examen:</h1>");
            indicepreg=indicepreg=indicepreg+1;
            session.setAttribute("indicepreg",indicepreg);
            for (int i=0;i<cantPregs;i++) {
                out.println("<h3>"+idpregs[i]+"</h3>");
            }
            if (indicepreg<=cantPregs) {
                out.println("<h1>Indice menor</h1>");
            
                if (tipo.equals("ToF")) {
                    //String[] ToFValores= getValuesPreguntaTOF(xmlPreg,idPreg);
                    out.println("<h1>"+Preset[0]+"</h1>");
                    if(opcion.equals(Preset[0])){
                        out.println("<h1>Respuesta correcta</h1>");
                        pond=Preset[1];
                        calificacion=calificacion+Integer.parseInt(pond);
                        //int indiceSiguiente=indicepreg+1;
                         out.println("<h1 class='blanco'>Calificacion actual "+calificacion+"</h1>");
                         session.setAttribute("calificacion",calificacion);
                         out.println("<div class=\"row\">\n" +
                "  <div class=\"col-sm-8\"></div>\n" +
                "  <div class=\"col-sm-8\"><a class='blanco' href='verSiguiente?idPreg="+idpregs[indiceReg]+"&tipo="+tipoReg+"'>Regresar dentro de ToF Correcto</a></div>\n" +
                "  <div class=\"col-sm-4\"><a class='blanco' href='verSiguiente?idPreg="+idpregs[indiceSig]+"&tipo="+tipoSig+"'>Siguiente dentro de ToF Correcto</a></div>");
                    }
                    else{
                        out.println("<h1>Incorrecto</h1>");
                        out.println("<h1 class='blanco'>Calificacion actual "+calificacion+"</h1>");
                         session.setAttribute("calificacion",calificacion);
                         out.println("<div class=\"row\">\n" +
                "  <div class=\"col-sm-8\"></div>\n" +
                "  <div class=\"col-sm-8\"><a class='blanco' href='verSiguiente?idPreg="+idpregs[indiceReg]+"&tipo="+tipoReg+"'>Regresar dentro de ToF Equivocado</a></div>\n" +
                "  <div class=\"col-sm-4\"><a class='blanco' href='verSiguiente?idPreg="+idpregs[indiceSig]+"&tipo="+tipoSig+"'>Siguiente dentro de ToF Equivocado</a></div>");
                    }
                     
                 }
                 else if (tipo.equals("HotSpot")) {  //out.println("");
                     ValoresHotS=getValuesPreguntaHotS(xmlPreg,idPreg); //Valores[0]=res;Valores[1]=pond;Valores[2]=src;Valores[3]=.getText();
                    pondHotS=ValoresHotS[1];
                    out.println("<h1 class='blanco'>Valor de la Pregunta "+pondHotS+"</h1>");
                    if(ValoresHotS[0].equals(ev)){
                       resultado="Respuesta correcta";
                       out.println("<h1>"+resultado+"</h1>");
                        calificacion=calificacion+Integer.parseInt(pond);
                         session.setAttribute("calificacion",calificacion);
                       }
                       else{
                            resultado="Respuesta incorrecta";
                       out.println("<h1>"+resultado+"</h1>");
                       }
                 }
                 out.println("<h1 class='blanco'>Calificacion: "+calificacion+"</h1>");
                out.println("<div class=\"row\">\n" +
                "  <div class=\"col-sm-8\"></div>\n" +
                "  <div class=\"col-sm-8\"><a class='blanco' href='verSiguiente?idPreg="+idpregs[indiceReg]+"&tipo="+tipoReg+"'>Regresar fuera de 1 if</a></div>\n" +
                "  <div class=\"col-sm-4\"><a class='blanco' href='verSiguiente?idPreg="+idpregs[indiceSig]+"&tipo="+tipoSig+"'>Siguiente fuera de 1 if</a></div>");
                out.println("<div class=\"row\">\n" +
            "  <div class=\"col-sm-8\"></div>\n" +
            //"  <div class=\"col-sm-4\"><a class='blanco' href='verSiguiente?idPreg="+idpregs[indicepreg+1]+"&tipo="+tipo+"'>Siguiente</a></div>\n" +
            "  <div class=\"col-sm-4\"><a class='blanco' href='index.html'>Regresar al indice</a></div>\n" +
            "</div>");
            out.println("</body>");
            out.println("</html>");
             }
           /*      out.println("<div class=\"row\">\n" +
            "  <div class=\"col-sm-8\"></div>\n" +
            //"  <div class=\"col-sm-4\"><a class='blanco' href='verSiguiente?idPreg="+idpregs[indicepreg+1]+"&tipo="+tipo+"'>Siguiente</a></div>\n" +
            "  <div class=\"col-sm-4\"><a class='blanco' href='index.html'>Regresar al indice</a></div>\n" +
            "</div>");
            out.println("</body>");
            out.println("</html>");*/
        }
        //session.setAttribute("indicepreg",indicepreg);
        session.setAttribute("idpregs",idpregs);
        session.setAttribute("calificacion",calificacion);
    }
    public String getTipo(String direc,String idPreg)
    {
        String name;    
        name = "";
        
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
                if(id.equals(idPreg))//Si la encuentra ponemos a tam a contar el numero de hijos de 
                {  
                    List preguntas=hijo.getChildren();
                    name=hijo.getName();                   
                }
            }
            
        } catch (JDOMException | IOException ex) {
            Logger.getLogger(Maestro.class.getName()).log(Level.SEVERE, null, ex);
        }
        return name;
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
        
       // int n=0;
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
                    System.out.println("ObtenerOpciones encontro el id:  "+id+" bien");
                    List opciones=hijo.getChildren();
                    for (int j=0;j<opciones.size() ;j++ ) {
                        Element opcion=(Element)opciones.get(j);
                        opcionesValues[0][j]=opcion.getAttributeValue("id");
                        opcionesValues[1][j]=opcion.getAttributeValue("coordX");
                        opcionesValues[2][j]=opcion.getAttributeValue("coordY");
                        opcionesValues[3][j]=opcion.getAttributeValue("radio");
                       // n++;
                        System.out.println("ObtenerOpciones coordX:  "+ opcionesValues[1][j]+" bien");
                        System.out.println("ObtenerOpciones radio:  "+ opcionesValues[3][j]+" bien");
                        //System.out.println("ObtenerOpciones n :  "+n+" bien");
                        
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