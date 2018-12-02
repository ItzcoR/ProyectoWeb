package administrador;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class crearExamen extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String xml=request.getRealPath("WEB-INF\\ProtoExam.xml");
        //HttpSession session=request.getSession();
        String[] idpreg=request.getParameterValues("agregarExamen");
        int npregs=idpreg.length;
        //String texto=request.getParameter("texto");
        //String res=request.getParameter("res");
        //String pond=request.getParameter("pond");
        //String resultado=agregar(xml,idpreg,texto,res,pond);
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Pregunta Verdadero o Falso</title>"); 
            out.println("<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js\"></script>");
            out.println("<script src=\"https://cdn.jsdelivr.net/npm/vue/dist/vue.js\"></script>");
            out.println("</head>");
            out.println("<body>");  //  out.println("");   out.println('');
            //out.println("<h1 >"+idpreg[0]+" </h1>");
            for(int i=0;i<npregs;i++){
                out.println("<h2>Pregunta "+idpreg[i]+" agregada</h2>");
            }
            out.println(" <div ><a  href='Maestro'>Regresar</a></div>\n" +
            "</div>");
            out.println("</body>");
            out.println("</html>");
        }
    }
}
