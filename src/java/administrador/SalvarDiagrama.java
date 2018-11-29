package administrador;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import static java.lang.System.out;
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
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

public class SalvarDiagrama extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String canvas=request.getParameter("canv");
        String nom=request.getParameter("nombre");
        System.out.println("QQQQQQQQQQQQQQQQQQQQQQQ"+canvas);
        String xml=request.getRealPath("WEB-INF\\canvas.xml");
        int aux=0;
        File fichero=new File(xml);
        if (fichero.isFile())
        {
            try{
                 SAXBuilder builder=new SAXBuilder();
                 Document doc=(Document) builder.build(fichero);
                 Element raiz=doc.getRootElement();
                 List hijos=raiz.getChildren();
                 //Objeto que escribe en el archivo xml
                 XMLOutputter xmlOutput = new XMLOutputter();
                 //Formato en el que se va a escribir
                 xmlOutput.setFormat(Format.getPrettyFormat());
                  for(int i=0;i<hijos.size();i++)
                  {
                      Element hijo=(Element)hijos.get(i);
                      String nombre=hijo.getAttributeValue("id");
                      if(nombre.equals(nom))
                      {
                          hijos.remove(i);
                      }
                  }            
                    Element can = new Element("canvas");
                    can.setText(canvas);
                    can.setAttribute("id",nom);
                    raiz.addContent(can);
                    xmlOutput.output(doc,new FileWriter(xml));
            } catch (JDOMException ex) {
                    Logger.getLogger(SalvarDiagrama.class.getName()).log(Level.SEVERE, null, ex);
                }
        }
    }
}
