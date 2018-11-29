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


public class modificard extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
         String xml=request.getRealPath("WEB-INF\\canvas.xml");
        String idc=request.getParameter("idc");
        String canv=recuperar(xml,idc);
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Crear Diagrama</title>"); 
            out.println("<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js\"></script>");
            out.println("<script src='fabric.js'></script>");
            out.println("<script src='http://code.responsivevoice.org/responsivevoice.js'></script>");
            out.println("</head>");
            out.println("<body>");
            out.println("<div style='width:1200px;height:80px;float:center;border:1px solid #A6C9E2'>");
            out.println("<input value='Salvar' type='button' onClick='salvar()'/>|");
            out.println("<a href='Maestro'>Menu Profesor</a>|");
            out.println("<a href='cerrar'>Cerrar Session</a>");
            out.println("</div>");
            
            out.println("<div style='width:310px;height:550px;margin-right:10px;float:right;border:1px solid #A6C9E2'>");
            out.println("<div style='width:300px;height:150px'>");
            out.println("<h3>Figuras</h3>");
            out.println("<ul>");
            out.println("<li>");
            out.println("COLOR:<input type='text' id='color1'/>");
            out.println("<input type='button' value='Circulo' onClick='crearCirculo()' />");
            out.println("</li>");
            out.println("<li>");
            out.println("COLOR:<input type='text' id='color2'/>");
            out.println("<button onClick='crearCuadri()'>Cuadrilatero</button>");
            out.println("</li>");
            out.println("<li>");
            out.println("COLOR:<input type='text' id='color3'/>");
            out.println("<button onClick='crearTria()'>Triangulo</button>");
            out.println("</li>");
            out.println("</ul>");
            out.println("</div>");
            
            out.println("<div style='width:300px;height:80px'>");
            out.println("<h3>Texto</h3>");
            out.println("<button id='add' type='button'>Añadir</button>");
            out.println("</div>");
            
            out.println("<div style='width:300px;height:310px'>");
            out.println("<h3>Multimedia</h3>");
            out.println("<div >");
            out.println("<input id='file' type ='file' size ='45' /> <br />");
            out.println("</div>");
            
            out.println("</div>");
            out.println("</div>");
            
            out.println("<div id='canvas-container' style='width:850px;height:550px;float:left'>");
            out.println("<canvas id='canvas' width='850' height='550' style='border:1px solid #000000;'></canvas>");
            out.println("</div>");
            
            out.println("<script>");
            out.println("var canvas = new fabric.Canvas('canvas');");
            out.println("canvas.loadFromJSON("+canv+");");
            out.println("var nombre='"+idc+"';");
            out.println("canvas.uniScaleTransform = true;");
            
            out.println("canvas.on('mouse:up', function(evn) {"//funcion para eliminar, si jalamos una imagen abajo de
                    + " var x = evn.e.offsetX;"                 //estas coordenadas la eliminamos
                    + "var y = evn.e.offsetY;"
                    + "if(x>830 && y>530){"
                    + "canvas.remove(selectedObject);"
                    + "salvar();}});");
                    out.println("canvas.on('object:selected', function(evn) {"
                    + "selectedObject = evn.target;});");//target-sirve para decirnos que evento acciono el evento
            
            out.println("function crearCirculo(){");
            out.println("var color=document.getElementById('color1').value;");//obtener el color de nuestro circulo
            out.println("var circle = new fabric.Circle({radius:50,fill:color,left:100,top:100,stroke : 'black',\n" +
                        "strokeWidth : 1});");//Crear circulo 
            out.println("canvas.add(circle);");//agregar el circulo al canvas
            out.println("salvar();");//serializar el canvas y guardarlos como string en una variable
            out.println("}");
            
             out.println("function crearCuadri(){");
            out.println("var color=document.getElementById('color2').value;");
            out.println("var cua = new fabric.Rect({width:50,height:50,fill:color,left:100,top:100,stroke : 'black',\n" +
                        "strokeWidth : 1});");
            out.println("canvas.add(cua);");
            out.println("salvar();");
            out.println("}");
            
            
            out.println("function crearTria(){");
            out.println("var color=document.getElementById('color3').value;");
            out.println("var tri = new fabric.Triangle({width:50,height:50,fill:color,left:100,top:100,stroke : 'black',\n" +
                        "strokeWidth : 1});");
            out.println("canvas.add(tri);");
            out.println("salvar();");
            out.println("}");
            
            out.println("function salvar(){");
            out.println("var canvasstring = JSON.stringify(canvas);");
            out.println("$.ajax({type:'post',url:'SalvarDiagrama',data:{'canv':canvasstring,'nombre':nombre}});}");
            
            
              out.println("var appObject = function() {"
                    + "return { __canvas: canvas,__tmpgroup: {},"
                    + "addText: function() {var newID = (new Date()).getTime().toString().substr(5);"//Sirve crear id
                    + "var text = new fabric.IText('escribe aqui', {"
                    + "fontFamily: 'arial black',"
                    + "left: 500,top: 500,myid: newID,objecttype: 'text'});"
                    + "this.__canvas.add(text);},"//Se agrega texto al canvas
                    + "}}");
            
            
            out.println("$(document).ready(function() {\n" +
                        "var app = appObject();\n" +
                        "$('#add').click(function() {\n" +
                        "app.addText();\n" +
                        "salvar();"+
                        "});\n" +
                        "})\n" +
                        "$(\"canvas\").click(function(e){\n" +
                        "var txt = canvas.getActiveObject().text;\n" +
                        "Decir(txt);\n" +
                        "});\n" +
                        "function Decir(say){\n" +
                        "var voicelist = responsiveVoice.getVoices();\n" +
                        "responsiveVoice.speak(say,\"Spanish Latin American Female\");\n" +
                        "}");
            
            
             out.println("document.getElementById('file').addEventListener(\"change\", function (e) {\n" +
                         "var file = e.target.files[0];\n"+
                         "var reader = new FileReader();\n" +
                         "reader.onload = function (f) {"+
                         "var data = f.target.result;"
                       + "fabric.Image.fromURL(data, function (img) {"+
                         "var oImg = img.set({left: 0, top: 0, angle: 0,width:300, height:300}).scale(0.9);\n" +
                         " img.set({scaleX:0.5,scaleY:0.5,left:0,top:0 });\n" +
                         "canvas.add(oImg).renderAll();"
                        + "var a = canvas.setActiveObject(oImg);\n"
                        + "var dataURL = canvas.toDataURL({format: 'png', quality: 0.8});" +
                         "});\n" +
                         "};\n" +
                         "reader.readAsDataURL(file);\n" +
                         "salvar();});");
                         
            out.println("</script>");
            out.println("</body>");
            out.println("</html>");
        }
}

   public String recuperar(String xml,String c)
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