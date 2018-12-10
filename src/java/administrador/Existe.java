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
import org.jdom.*;
import org.jdom.input.SAXBuilder;



public class Existe extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String usuario = request.getParameter("id");
        String contra = request.getParameter("password");
        String nomb = request.getParameter("nombre");
        String xml = request.getRealPath("WEB-INF\\practica.xml");
        String existe = verificar(xml, usuario, contra);
        HttpSession session = request.getSession();
        session.setAttribute("id", usuario);
        session.setAttribute("tipo", existe);
        session.setAttribute("nombre", nomb);
        try (PrintWriter out = response.getWriter()) {

            if (existe.equals("")) {
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
                    " </head>\n" +

                    "<body>\n" +
                    "<!--[if lte IE 9]>\n" +
                    " <p class=\"browserupgrade\">You are using an <strong>outdated</strong> browser. Please <a href=\"https://browsehappy.com/\">upgrade your browser</a> to improve your experience and security.</p>\n" +
                    "  <![endif]-->\n" +

                    "<div class=\"contenedor_barra\">\n" +
                    "<h1>Sistema evaluador</h1>\n" +
                    " </div>");





                out.println(
                    "<div class=\"bg_amarillo contenedor sombra\">\n" +
                    "<legend>\n" +
                    "    Log in <span>Todos los campos son obligatorios</span>\n" +
                    "</legend>\n" +
                    
                    "<div class=\"campo enviar\">\n" +
                    "    <input type=\"submit\" class=\"btn-info\" value=\"Iniciar sesion\">\n" +
                    "</div>\n" +


                    "</div>");



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
                else if (existe.equals("Administrador")) {
                    //Permite redigir a otro archivo o servlet y mandar los parametros 
                    response.sendRedirect("Administrador");
                } else if (existe.equals("Maestro")) {
                    //Permite redigir a otro archivo o servlet y mandar los parametros 
                    response.sendRedirect("Maestro");
                } else if (existe.equals("Alumno")) {
                    //Permite redigir a otro archivo o servlet y mandar los parametros 
                    response.sendRedirect("Alumno");
                }
            }
        }

        public String verificar(String xml, String usuario, String contra) {
            String res = "";
            try {
                /*SAXBuilder se encarga de cargar el archivo XML del disco o de un String */
                SAXBuilder builder = new SAXBuilder();
                //Forma de abrir el archivo
                File xmlFile = new File(xml);
                /*Almacenamos el xml cargado en builder en un documento*/
                Document bd_xml = builder.build(xmlFile);
                /*Indica el elemento raiz que se guardo en la variable Document*/
                Element raiz = bd_xml.getRootElement();
                //Lista con los nodos hijos 
                List hijos = raiz.getChildren();
                for (int i = 0; i < hijos.size(); i++) {
                    Element hijo = (Element) hijos.get(i);
                    List nietos = hijo.getChildren();
                    String tipo = hijo.getName();
                    for (int j = 0; j < nietos.size(); j++) {
                        Element nieto = (Element) nietos.get(j);
                        //Valor que tiene el atributo numero
                        String nombre = nieto.getAttributeValue("id");
                        String pass = nieto.getAttributeValue("password");
                        if (nombre.equals(usuario) && pass.equals(contra)) {
                            res = tipo;
                            break;
                        }
                    }
                }
                /*NUEVOS ELEMENTOS
            Element raiz=new Element("ROOT");
            Element hoja = new Element("hoja");*/
            } catch (JDOMException | IOException ex) {
                Logger.getLogger(Existe.class.getName()).log(Level.SEVERE, null, ex);
            }


            return res;
        }
    }