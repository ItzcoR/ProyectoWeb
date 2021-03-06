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
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

public class ver extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String nomC=request.getParameter("id");
        String xml=request.getRealPath("WEB-INF\\canvas.xml");
        String res=ver(xml,nomC);
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
            out.println("<div style='width:1200px;height:80px;float:center;border:1px solid #A6C9E2'>");
            out.println("<a href='Maestro'>Menu Profesor</a>|");
            out.println("<a href='cerrar'>Cerrar Session</a>");
            out.println("</div>");
            
            out.println("<canvas id='c'width='850' height='550' style='border:1px solid #000000'></canvas>");
            out.println("<script>");
            out.println("var canvas = new fabric.Canvas('c');");
            out.println("canvas.loadFromJSON("+res+");");
            out.println("$(\"canvas\").click(function(e){\n" +
                        "var txt = canvas.getActiveObject().text;\n" +
                        "Decir(txt);\n" +
                        "});\n" +  
                        "function Decir(say){\n" +
                        "var voicelist = responsiveVoice.getVoices();\n" +
                        "responsiveVoice.speak(say,\"Spanish Latin American Female\");\n" +
                        "};");
            out.println("</script>");
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
    
    public String ver(String xml,String c)
    {
        String res="";
        int aux=0;
        try {
            SAXBuilder builder = new SAXBuilder();
            File xmlFile= new File(xml);
            Document bd_xml=builder.build(xmlFile);
            Element raiz=bd_xml.getRootElement();
            List hijos=raiz.getChildren();
            for(int i=0;i<hijos.size();i++)
            {
                Element hijo=(Element)hijos.get(i);
                String canv=hijo.getAttributeValue("id");
                if(c.equals(canv))
                {
                  aux=i;  
                }
            }
            Element ver=(Element)hijos.get(aux);
            res=ver.getText();
            return res;
        } catch (JDOMException | IOException ex) {
            Logger.getLogger(ver.class.getName()).log(Level.SEVERE, null, ex);
        }
      return res;
    }
}
