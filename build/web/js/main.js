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