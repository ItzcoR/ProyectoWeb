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
            out.println("<!DOCTYPE html>");
            out.println("<html style='height:100%; width:100%; margin:0px;'>");
            out.println("<head>");
            out.println("<title>Servlet modificacion2</title>");        
            out.println("<meta charset=\"utf-8\">\n" +
            "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
            "<link rel=\"stylesheet\" type=\"text/css\" href=\"estilos.css\" media=\"screen\"/>\n" +
            "<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css\">\n" +
            "<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js\"></script>\n" +
            "<script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js\"></script>");
            out.println("</head>");
            out.println("<body class='colorful'>");
            out.println("<h1 class='blanco'>Usuario Modificado</h1>");
            out.println("El nuevo usuario: "+usuario+"<br> La contraseña: "+contra+"<br> El nombre: "+nom+"<br> El tipo: "+tipo+" <br></h1>");
            out.println("<div class=\"row\">\n" +
            "  <div class=\"col-sm-8\"></div>\n" +
            "  <div class=\"col-sm-4\"><a class='blanco' href='Administrador'>Regresar</a></div>\n" +
            "</div>");
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
