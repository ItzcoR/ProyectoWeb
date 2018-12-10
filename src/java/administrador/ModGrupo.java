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
public class ModGrupo extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String xml=request.getRealPath("WEB-INF\\practica2.xml");
        String nombre=request.getParameter("id");
        HttpSession session=request.getSession();
        String[] res=atributo(xml,nombre);
        session.setAttribute("viejo",nombre);
        String xml2=request.getRealPath("WEB-INF\\practica.xml");
        int auxx=tamano(xml2,"Maestro");
        int auxr=tamano(xml2,"Alumno");
        String[] usuarios=new String[auxx];
        usuarios=maestros(xml2);
        String[] usuarios2=new String[auxr];
        usuarios2=alumno(xml2);
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html style='height:100%; width:100%; margin:0px;'>");
            out.println("<head>");
            out.println("<title>Servlet modificar1</title>");  
            out.println("<script type =\"text/javascript\">\n" +
            "function letraNumVal(e) {\n" +
            "   tecla = document.all ? e.keyCode : e.which;\n" +
            "   if (tecla === 8 || tecla === 32)\n" +
            "           return true;\n" +
            "       patron = /[a-z]|[A-Z]|á|é|í|ó|ú|Á|É|Í|Ó|Ú|[0-9]/;\n" +
            "       te = String.fromCharCode(tecla);\n" +
            "           return patron.test(te);\n" +
            "   }\n" +
            "function letraVal(e) {\n" +
            "   tecla = document.all ? e.keyCode : e.which;\n" +
            "   if (tecla === 8 || tecla === 32)\n" +
            "           return true;\n" +
            "       patron = /[a-z]|[A-Z]|á|é|í|ó|ú|Á|É|Í|Ó|Ú/;\n" +
            "       te = String.fromCharCode(tecla);\n" +
            "           return patron.test(te);\n" +
            "   }\n" +
            "</script>");
            out.println("<meta charset=\"utf-8\">\n" +
            "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
            "<link rel=\"stylesheet\" type=\"text/css\" href=\"estilos.css\" media=\"screen\"/>\n" +
            "<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css\">\n" +
            "<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js\"></script>\n" +
            "<script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js\"></script>");
            out.println("</head>");
            out.println("<body class='colorful'>");
            out.println("<form action='ModGrupo2' method='post'>");
            out.println("<div class=\"container\">\n" +
            "  <h2 class='blanco'>Modificar</h2>           \n" +
            "  <table class=\"table\">\n" +
            "    <thead>\n" +
            "      <tr>\n" +
            "        <td class='blanco'>Grupo:</td><td><input type='text' name='id' onkeypress='return letraNumVal(event)' value="+res[0]+"></td>\n" +
            "      </tr><tr>\n" +
            "        <td class='blanco'>Maestro del Grupo:</td><td><input type='text' name='maestro' onkeypress='return letraVal(event)' value="+res[1]+"></td>\n" +
            "      </tr>\n" +
            "    </thead>\n" +
            "    <tbody>\n" +
            "      <tr>\n" +
            /*"        <td><h4 class='blanco'>Maestro: "+res[3]+"</h4></td><td><select name=\"io\">\n" +
            "                            <option value=\"Alum\">Alumno</option>\n" +
            "                            <option value=\"Maes\">Maestro</option>\n" +
            "                        </select>\n" +
            "        </td>\n" +*/
            "      </tr>\n" +
            "      <tr>\n" +
            "        <td class='blanco'>Alumnos en el Grupo: </td><td><input type='text' name='alumnos' onkeypress='return letraNumVal(event)' value="+res[2]+"></td>\n" +
            "      </tr>\n" +
            /*"      <tr>\n" +
            "        <td class='blanco'>Password: </td><td><input type='text' name='password' onkeypress='return letraNumVal(event)' value="+res[1]+"></td>\n" +
            "      </tr>\n" +*/
            "    </tbody>\n" +
            "  </table>\n" +
            "</div>\n" +
            "");
           
            out.println("<table class=\"table\">\n" +
            "   <thead>\n" +
            "     <tr class='blanco'>\n" +
            "       <th>Maestros Registrados</th>\n" +
            "       <th>Alumnos Registrados</th>\n" +
            "     </tr>\n" +
            "   </thead>\n" +
            "   <tbody>\n" +
            "     <tr>\n" +
            "       <td>");
            for(int i=0;i<auxx;i++){
                out.println("<input type=\"radio\" name=\"io\" value=\""+usuarios[i]+"\"><h5 id='txtUsuario' class='blanco'>"+usuarios[i]+"</h5>");
                out.println("<hr>");
            }
            out.println("</td>\n" +
            "<td>");        
            for(int i=0;i<auxr;i++){
                //out.println("<input type=\"checkbox\" name=\"alum\" value=\""+usuarios2[i]+"\" id=\"id"+i+"\"><h5 id='txtPassword' class='blanco'>"+usuarios2[i]+"</h5>");
                out.println("<h5 id='txtPassword' class='blanco'>"+usuarios2[i]+"</h5>");
                out.println("<hr>");
            }
            out.println("</td>");/*\n" +
            "<td>");
            for(int i=0;i<aux;i++){
                out.println("<a class='blanco' href='modificar1?nombre="+usuarios[1][i]+"&tipo="+usuarios[2][i]+"'>Modificar</a>");
                out.println("<button id='submitLogin'><a class='blanco' href='eliminar?nombre="+usuarios[1][i]+"&tipo="+usuarios[2][i]+"'>Eliminar</a></button>");
                out.println("<hr>");    
            }
            
            out.println("</td>\n" +
            "     */out.println("</tr>\n" +
            "    </tbody>\n" +
            "  </table>");
             out.println("<div class=\"row\">\n" +
            "  <div class=\"col-sm-1\"></div>\n" +
            "  <div class=\"col-sm-4\"><input type=\"submit\" class=\"btn-info\" value=\"Modificar\"></div>\n" +
            "  <div class=\"col-sm-4\"><input type=\"button\" class=\"btn-info\" value=\"Cancelar\" onclick=\"document.location='VerGrupos'\"></div>\n" +
            "</div>");
            out.println("</form>");
            /////////////////////////////////////////////////////////////////////////////////////////////////////////
            // Footer
            /////////////////////////////////////////////////////////////////////////////////////////////////////////
            out.println(
                    "<script src=\"js/vendor/modernizr-3.6.0.min.js\"></script>\n" +
                    "<script src=\"https://code.jquery.com/jquery-3.3.1.min.js\" integrity=\"sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8=\" crossorigin=\"anonymous\"></script>\n" +
                    "<script>window.jQuery || document.write('<script src=\"js/vendor/jquery-3.3.1.min.js\"></script>')</script>\n"+
                    "<script src=\"js/plugins.js\"></script>\n" +
                    "<script src=\"js/main.js\"></script>\n" +

                    "<!-- Google Analytics: change UA-XXXXX-Y to be your site's ID. -->\n" +
                    "<script > \n "+
                    "  window.ga = function () { ga.q.push(arguments) }; ga.q = []; ga.l = +new Date;\n" +
                    "  ga('create', 'UA-XXXXX-Y', 'auto'); ga('send', 'pageview')\n" +
                    "</script>\n" +
                    " <script src=\"https://www.google-analytics.com/analytics.js\" async defer></script>\n" +
                    "</body>\n" +

                    "</html>");
        }
    }
    public String[] atributo(String ruta,String nombre)
    {
        String[] res=new String[3];
        try{
            /*SAXBuilder se encarga de cargar el archivo XML del disco o de un String */
            SAXBuilder builder=new SAXBuilder();
            //Forma de abriri el archivo
            File xmlFile = new File(ruta);
            /*Almacenamos el xml cargado en builder en un documento*/
            Document bd_xml=builder.build(xmlFile);
            //Elemento raiz
            Element raiz=bd_xml.getRootElement();
            //Se almacenan nodo hijo 
             List hijos=raiz.getChildren();
            for(int i=0;i<hijos.size();i++)
            {
                Element hijo=(Element)hijos.get(i);
                
                    String usua=hijo.getAttributeValue("id");
            
                if(usua.equals(nombre))
                {
                    
                    res[0]=hijo.getAttributeValue("id");
                    res[1]=hijo.getAttributeValue("Maestro");
                    res[2]=hijo.getText();
                    
                
            }
            }
            
            
        } catch (JDOMException | IOException ex) {
            Logger.getLogger(modificar1.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }
    public int tamano(String direc,String tipo)
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
            Element hijo=raiz.getChild(tipo);
            List nietos=hijo.getChildren();
            aux=nietos.size();
        } catch (JDOMException | IOException ex) {
            Logger.getLogger(ModGrupo.class.getName()).log(Level.SEVERE, null, ex);
        }
        return aux;
    }
    


    // direc Direcion del la funcion request.getRealPath("WEB-INF\\practica.xml");
    public String[] maestros(String direc)
    {
        int aux=tamano(direc,"Maestro");
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
            Element hijo=raiz.getChild("Maestro");
            List nietos=hijo.getChildren();
            for(int i=0;i<nietos.size();i++)
            {
            	Element nieto=(Element) nietos.get(i);
                nombU[i]=nieto.getAttributeValue("nombre");
            }
        } catch (JDOMException | IOException ex) {
            Logger.getLogger(ModGrupo.class.getName()).log(Level.SEVERE, null, ex);
        }
        return nombU;
    }


        public String[] alumno(String direc)
    {
        int aux=tamano(direc,"Alumno");
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
            Element hijo=raiz.getChild("Alumno");
            List nietos=hijo.getChildren();
            for(int i=0;i<nietos.size();i++)
            {
            	Element nieto=(Element) nietos.get(i);
                nombU[i]=nieto.getAttributeValue("nombre");
            }
        } catch (JDOMException | IOException ex) {
            Logger.getLogger(ModGrupo.class.getName()).log(Level.SEVERE, null, ex);
        }
        return nombU;
    }
}
