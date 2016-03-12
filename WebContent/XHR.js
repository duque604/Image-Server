/**
 * Created by Alberto Bonsanto on 03/07/2014.
 */
XHR = function () {
    var extFun = new Function();
    var xhr = {};
    var mode = true;//true if async, false if sync.
    var url = "";
    var method = "post";
    var cbf = new Function("callBackFunction");
    var sx='';

    this.setMode = function (m){
        mode = m;
    };

    this.setURL = function (u) {
        url = u;
    };

    this.setMethod = function (m) {
        method = m;
    };
    
    var callBackFunction = function(){ 
    	try{		
        if(xhr.readyState == 4) {
            switch (xhr.status){
                case 200:                	
                    extFun();
                    break;
                case 401:
                    alert("ERROR 401 - Acceso no autorizado");
                    break;
                case 403:
                    alert("ERROR 403 - Acceso prohibido");
                    break;
                case 404:
                    alert("ERROR 404 - Recurso no encontrado");
                    break;
                case 405:
                    alert("ERROR 405 - Metodo no permitido");
                    break;
                case 406:
                    alert("ERROR 406 - No aceptable");
                    break;
                case 500:
                    alert("ERROR 500 - Error Interno");
                    break;
                case 501:
                    alert("ERROR 501 - No implementado");
                    break;
                case 502:
                    alert("ERROR 502 - Pasarela no encontrada");
                    break;
                case 503:
                    alert("ERROR 503 - Servicio no disponible");
                    break;
                case 504:
                    alert("ERROR 504 - Tiempo de espera de pasarela agotado.");
                    break;
                case 505:
                    alert("ERROR 505 - Version de HTTP no soportada");
                    break;
                case 506:
                    alert("ERROR 506 - Variante tambien negocia");
                    break;
                case 507:
                    alert("ERROR 507 - Almacenamiento Insuficiente");
                    break;
                case 509:
                    alert("ERROR 509 - Li­mite de ancho de banda excedido");
                    break;
               
            }
        }            
      }
      catch(e)
      {
    	alert("Error en el servidor");
      }
    };

    this.send = function (val) {
        xhr = window.XMLHttpRequest ? new XMLHttpRequest() : new ActiveXObject("Microsoft.XMLHTTP");
        xhr.onreadystatechange = callBackFunction;
        xhr.open(method, url, mode);
        //xhr.setRequestHeader("Content-type","application/x-www-form-urlencoded");
        xhr.setRequestHeader("Content-type","multipart/form-data");
        xhr.send(val);
    };

    this.setCallBackFun = function (f) {
        extFun = new Function(f);
    };

    this.getResponse = function () {
        return xhr.responseText;
    };
};