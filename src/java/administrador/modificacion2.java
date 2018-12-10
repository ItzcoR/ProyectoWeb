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

public class modificacion2 extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String xml=request.getRealPath("WEB-INF\\practica.xml");
        String usuario=request.getParameter("id");
        String contra=request.getParameter("password");
        String nom=request.getParameter("nombre");
        String tipo=request.getParameter("io");
        HttpSession session=request.getSession();
        String a=(String) session.getAttribute("nodo");
        String idviejo=(String) session.getAttribute("idv");
        atributo(xml,idviejo,usuario,contra,nom,tipo,a);
        try (PrintWriter out = response.getWriter()) {

                /////////////////////////////////////////////////////////////////////////////////////////////////////////
                // Header
                /////////////////////////////////////////////////////////////////////////////////////////////////////////
                out.println(
                    "<!doctype html>\n" +
                    " <html class=\"no-js\" lang=\"\">\n" +

                    "<head>\n" +
                    "<meta charset=\"utf-8\">\n" +
                    "<meta http-equiv=\"x-ua-compatible\" content=\"ie=edge\">\n" +
                    "<title></title>\n" +
                    "<meta name=\"description\" content=\"\">\n" +
                    "   <meta name=\"viewport\" content=\"width=device-width, initial-scale=1, shrink-to-fit=no\">\n" +

                    "<link rel=\"manifest\" href=\"site.webmanifest\">\n" +
                    " <link rel=\"apple-touch-icon\" href=\"icon.png\">\n" +
                    "   <!-- Place favicon.ico in the root directory -->\n" +

                    "<link rel=\"stylesheet\" href=\"css/normalize.css\">\n" +
                    " <link rel=\"stylesheet\" href=\"css/main.css\">\n" +
                    "<link rel=\"stylesheet\" href=\"https://use.fontawesome.com/releases/v5.5.0/css/all.css\" integrity=\"sha384-B4dIYHKNBt8Bc12p+WXckhzcICo0wtJAoU8YZTY5qE0Id1GSseTk6S+L3BlXeVIU\" crossorigin=\"anonymous\">\n"+
                    " </head>\n" +

                    "<body>\n" +
                    "<!--[if lte IE 9]>\n" +
                    " <p class=\"browserupgrade\">You are using an <strong>outdated</strong> browser. Please <a href=\"https://browsehappy.com/\">upgrade your browser</a> to improve your experience and security.</p>\n" +
                    "  <![endif]-->\n" +

                    "<div class=\"contenedor_barra\">\n" +
                    "<h1>Sistema evaluador</h1>\n" +
                    " </div>");


            out.println("<div class=\"bg_blanco contenedor sombra\">\n"+
            "<div class =\"contenedor_alertas\">\n"+
            "<legend>\n"+
             " Usuario modificado correctacmente\n"+
            "</legend>\n"+
            "<div class=\"modif\"><i class=\"fas fa-check\"></i> <span>Usuario: </span>"+usuario+"<br> <i class=\"fas fa-check\"></i> <span>Contraseña:</span> "+contra+"<br><i class=\"fas fa-check\"></i> <span>Nombre:</span> "+nom+"<br><i class=\"fas fa-check\"></i> <span>Tipo:</span> "+tipo+" <br></div>\n"+
            
            "<div class=\"campo enviar\">\n" +
                        "   <a class=\"btn-info btn_rosa\" href='Administrador'>Regresar</a>\n" +
                        "</div>\n" +
                        "</div>\n" +
            "</div>");



////////////////////////////////////////////////////////////////////////////////////////////
// ------------  FOOTER  -------------------------------------------------------------------
///////////////////////////////////////////////////////////////////////////////////////////               
out.println("</body>");
out.println("</html>");  
        }
    }
    public void atributo(String ruta,String idv,String idn,String password,String nombre,String nodon,String nodov) 
    {
        int aux=0;
        String res="";
        try{
            /*SAXBuilder se encarga de cargar el archivo XML del disco o de un String */
            SAXBuilder builder=new SAXBuilder();
            //Forma de abriri el archivo
            File xmlFile = new File(ruta);
            /*Almacenamos el xml cargado en builder en un documento*/
            Document bd_xml=builder.build(xmlFile);
            //Elemento raiz
            Element raiz=bd_xml.getRootElement();
            Element hijoUnico=raiz.getChild(nodov);
            List nietosUnico=hijoUnico.getChildren();
            //NODOS HIJOS
            List hijos=raiz.getChildren();
            //metodo que escribe en el archivo xml
            XMLOutputter xmlOutput = new XMLOutputter();
            //Formato en el que se va a escribir
            xmlOutput.setFormat(Format.getPrettyFormat());
            
            //Eliminar
            for(int i=0;i<nietosUnico.size();i++)
            {
                Element nietoUnico=(Element) nietosUnico.get(i);
                String usua=nietoUnico.getAttributeValue("id");
                if(usua.equals(idv))
                {
                    nietosUnico.remove(i);
                    break;
                }
            }
            
            //BUSCAR ID existente
            for (int i = 0; i < hijos.size(); i++)
            {
                Element hijo = (Element) hijos.get(i);
                List nietos=hijo.getChildren();
                for(int j=0;j<nietos.size();j++)
                {
                    Element nieto=(Element) nietos.get(j);
                    //Valor que tiene el atributo numero
                    String nomb = nieto.getAttributeValue("id");
                    if(nomb.equals(idn))
                    {
                        aux++;
                        System.out.println("%%%%%%%%%%"+aux);
                    }
                }
            }  
            
            if(aux>0)
            {    
                res="El usuario no se pudo modificar, posiblemente el nombre de usuario ya esta ocupado";
            }
            else
            {   
                Element nuevo=new Element(nodon);
                nuevo.setAttribute("id",idn);
                nuevo.setAttribute("nombre",nombre);
                nuevo.setAttribute("password",password);
                if(nodon.equals("Alum"))
                {
                    Element hijoa=raiz.getChild("Alumno");
                    hijoa.addContent(nuevo);
                }
                else
                {
                    Element hijoa=raiz.getChild("Maestro");
                    hijoa.addContent(nuevo);
                }
                res="Usuario modificado con exito";
                xmlOutput.output(bd_xml,new FileWriter(ruta)); 
            } 
        } catch (JDOMException | IOException ex) {
            Logger.getLogger(modificacion2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
