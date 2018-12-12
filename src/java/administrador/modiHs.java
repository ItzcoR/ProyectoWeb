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

public class modiHs extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String xml=request.getRealPath("WEB-INF\\ProtoToF.xml");
        HttpSession session=request.getSession();
        String idPregunta=(String) session.getAttribute("id");
        String[] idOpciones=request.getParameterValues("idOpcion");
        int nIDs=idOpciones.length;
        String[] coordenadasX=request.getParameterValues("coordX");
        int numCoordX=coordenadasX.length;
        String[] coordenadasY=request.getParameterValues("coordY");
        int numCoordY=coordenadasY.length;
        String[] radios=request.getParameterValues("radio");    
        int numRadio=radios.length;
        String texto=request.getParameter("texto");
        String pond=request.getParameter("pond");
        String res=request.getParameter("res");
        //borrar(xml,nodo,nombre);
        String resultado="";
        resultado=modificarHotS(xml,idPregunta,pond,res,idOpciones,coordenadasX,coordenadasY,radios);
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
                "<div class=\"contenedor bg_blanco sombra\">"
              +      "<div class=\"contenedor_alertas\">"
              +          "<legend>"+resultado+"<span>"
              +      "ID: "+idPregunta+"</span></legend>"
              + "<div class=\"campo enviar\">"
              +"   <a class=\"btn-info btn_rosa\" href='Administrador'>Regresar</a>"
              +"</div>"
              +      "</div>"

            +"</div>");


        ////////////////////////////////////////////////////////////////////////////////////////////
        // ------------  FOOTER  -------------------------------------------------------------------
        ///////////////////////////////////////////////////////////////////////////////////////////               
        out.println("</body>");
        out.println("</html>");  
        }
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
    public String modificarHotS(String ruta,String id, String pond,String res,String[] idopc,String[] coordX,String[] coordY,String[] radios)
    {
        String resultado="";
        int numeroOpciones=numOpciones(ruta,id);
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
            int p=0;
            for(int i=0;i<hijos.size();i++)
            {
                Element hijo=(Element)hijos.get(i);
                String identificador=hijo.getAttributeValue("id");
                
                if(identificador.equals(id))
                {
                    hijo.setAttribute("res",res);
                    hijo.setAttribute("pond",pond);
                    //hijo.setText(texto);
                    List opciones=hijo.getChildren();
                    res="Lleque aqui";
                    for (int j=0;j<opciones.size() ;j++ ) {
                        
                        Element opcion=(Element)opciones.get(j);
                        opcion.setAttribute("id",idopc[j]);
                        opcion.setAttribute("coordX",coordX[j]);
                        opcion.setAttribute("coordY",coordY[j]);
                        opcion.setAttribute("radio",radios[j]);
                        resultado="Pregunta modificada exitosamente";
                        p++;
                    }
                        
                 }
                
            }
      xmlOutput.output(bd_xml,new FileWriter(ruta));
       
        } catch (JDOMException | IOException ex) {
            Logger.getLogger(Mapear.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultado;
    }
    
}