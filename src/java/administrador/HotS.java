package administrador;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
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
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class HotS extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String xml=request.getRealPath("WEB-INF\\ProtoToF.xml");
        String id="";
        String texto="";
        String pond="";
        String imagen="";
        String res="";
        String nOpciones="";
        int auxOpc=0;
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        
    if (isMultipart) {
        FileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
         try {
        List items = upload.parseRequest(request);
        Iterator iterator = items.iterator();
        while (iterator.hasNext()) {
            FileItem item = (FileItem) iterator.next();
            if (item.isFormField()){
                String name = item.getFieldName();
                System.out.println(name);
                if(name.equals("id")){
                     id=item.getString();
                    System.out.println(id);
                }
                else if(name.equals("texto")){
                     texto=item.getString();
                    System.out.println(texto);
                }
                else if(name.equals("pond")){
                     pond=item.getString();
                    System.out.println(pond);
                }
                else if(name.equals("res")){
                     res=item.getString();
                    System.out.println(res);
                }
                else if(name.equals("nOpciones")){
                     nOpciones=item.getString();
                    System.out.println(nOpciones);
                }
                //String value = item.getString();
                //System.out.println(value);
            }
            else {
                String fileName = item.getName();
                //System.out.println("");
                String root = getServletContext().getRealPath("/");
                File path = new File(root + "/uploads");
                if (!path.exists()) {
                    boolean status = path.mkdirs();
                }

                File uploadedFile = new File(path + "/" + fileName);
                imagen="uploads/" + fileName;
                System.out.println(uploadedFile.getAbsolutePath());
                item.write(uploadedFile);
            }
        }
    } catch (FileUploadException e) {
        e.printStackTrace();
    } catch (Exception e) {
        e.printStackTrace();
    }
        
        
        String resultado=agregarHotS(xml,id,texto,res,pond,imagen);
        HttpSession session=request.getSession();
        session.setAttribute("id",id);
        session.setAttribute("texto",texto);
        session.setAttribute("pond",pond);
        session.setAttribute("imagen",imagen);
        session.setAttribute("resultado",resultado);
        session.setAttribute("nOpciones",nOpciones);
        auxOpc=Integer.parseInt(nOpciones);
        //session.setAttribute("tipo",tipo);
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
                    "<legend>Pregunta Creada; Añadir HotSpots </legend>");
                        out.println("<h1>Id Pregunta"+id+"</h1>");          //        out.println("");
                        //out.println("<h1>"+texto+"</h1>");
                        //out.println("<h1>"+pond+"</h1>");
                        out.println("<h1>Ruta de la imagen"+imagen+"</h1>");
                        out.println("<h1>Numero de Opciones: "+nOpciones+"</h1>");
                        out.println("<h1>"+resultado+"</h1>");
                        out.println("<img src='"+imagen+"'>");
                    out.println("<form name='Opciones' action='Mapear' method='get'>");
                        
                        for (int i=0;i<auxOpc ;i++ ) {
                           out.println("<label>Opcion ID:</label>\n"+
                              "<input type='text' name='idOpcion' required autocomplete=\"off\" placeholder=\"Escribe el ID\">");
                            out.println("<label>Coordenada X:</label>\n"+
                              "<input type='text' name='coordX' required autocomplete=\"off\" onkeypress=\"return numVal(event)\" placeholder=\"Coordena X\">");
                            out.println("<label>Coordenada Y:</label>\n"+
                              "<input type='text' name='coordY' required autocomplete=\"off\" onkeypress=\"return numVal(event)\" placeholder=\"Coordena Y\">");
                            out.println("<label>Radio:</label>\n"+
                              "<input type='text' name='radio' required autocomplete=\"off\" onkeypress=\"return numVal(event)\" placeholder=\"Radio\">"); 
                            out.println("<br/>");
                        }
                            
                        out.println("<input type=\"reset\">");
                        out.println("<input type=\"submit\" value=\"Crear\">");
                        out.println("<input type=\"button\" value=\"Cancelar\" onclick=\"document.location='Maestro'\">");
                        
                    out.println("</form>");
                    out.println("</div>\n" +
                "</div>");  


        ////////////////////////////////////////////////////////////////////////////////////////////
        // ------------  FOOTER  -------------------------------------------------------------------
        ///////////////////////////////////////////////////////////////////////////////////////////               
        out.println("</body>");
        out.println("</html>");  
        }
    }
    }
    public String agregarHotS(String ruta,String id,String texto,String res,String pond,String urlImagen)
    {
        String respuesta="";
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
                    aux++;   
                    break;
                 }
         
            }
            if(aux==1)
            {
                respuesta="Este id ya existe";
            }
            else
            { 
                Element nuevo=new Element("HotSpot");
                nuevo.setAttribute("id",id);
                nuevo.setAttribute("res",res);
                nuevo.setAttribute("pond",pond);
                nuevo.setAttribute("src",urlImagen);
                nuevo.setText(texto);
                raiz.addContent(nuevo);
                respuesta="Pregunta agregada exitosamente";
            } 
      xmlOutput.output(bd_xml,new FileWriter(ruta));
       
        } catch (JDOMException | IOException ex) {
            Logger.getLogger(Agregar.class.getName()).log(Level.SEVERE, null, ex);
        }
        return respuesta;
    }
}