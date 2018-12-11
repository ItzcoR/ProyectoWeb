function letraNumVal(e) {//funcion para validar la entrada en los cuadros de texto
    tecla = document.all ? e.keyCode : e.which;//en tecla se guarda el evento
    if (tecla === 8 || tecla === 32)
        return true;
    patron = /[a-z]|[A-Z]|á|é|í|ó|ú|Á|É|Í|Ó|Ú|[0-9]/;//limites
    te = String.fromCharCode(tecla);//obtiene la letra que se esta presionando
    return patron.test(te);//busca la letra de la entrada en los limites dados
}
function letraVal(e) {
    tecla = document.all ? e.keyCode : e.which;
    if (tecla === 8 || tecla === 32)
        return true;
    patron = /[a-z]|[A-Z]|á|é|í|ó|ú|Á|É|Í|Ó|Ú/;
    te = String.fromCharCode(tecla);
    return patron.test(te);
}



var pos = 0, examen, examen_status, pregunta, opcion, opciones, opA, opB, opC, correcto = 0;
var preguntas = [
    [ "Cuanto es 10 + 4?", "12", "14", "16", "B" ],
	[ "Cuanto es 20 - 9?", "7", "13", "11", "C" ],
	[ "Cuanto es 7 x 3?", "21", "24", "25", "A" ],
	[ "Cuanto es 8 / 2?", "10", "2", "4", "C" ]
];
function _(x){
	return document.getElementById(x);
}
function renderPregunta(){
	examen = _("examen");
	if(pos >= preguntas.length){
		examen.innerHTML = "<h2>Tuviste correctas "+correcto+" de "+preguntas.length+" preguntas</h2>";
		_("examen_status").innerHTML = "examen Completed";
		pos = 0;
		correcto = 0;
		return false;
	}
	_("examen_status").innerHTML = "Pregunta "+(pos+1)+" de "+preguntas.length;
	pregunta = preguntas[pos][0];
	opA = preguntas[pos][1];
	opB = preguntas[pos][2];
	opC = preguntas[pos][3];
	examen.innerHTML = "<h3>"+pregunta+"</h3>";
	examen.innerHTML += "<input type='radio' name='opciones' value='A'> "+opA+"<br>";
	examen.innerHTML += "<input type='radio' name='opciones' value='B'> "+opB+"<br>";
	examen.innerHTML += "<input type='radio' name='opciones' value='C'> "+opC+"<br><br>";
	examen.innerHTML += "<button onclick='checkAnswer()'>Siguiente</button>";//Aqui es donde se envian  las respuestas
}
function checkAnswer(){
	opciones = document.getElementsByName("opciones");
	for(var i=0; i<opciones.length; i++){
		if(opciones[i].checked){
			opcion = opciones[i].value;
		}
	}
	if(opcion == preguntas[pos][4]){
		correcto++;
	}
	pos++;
	renderPregunta();
}
window.addEventListener("load", renderPregunta, false);
