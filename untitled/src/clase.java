import java.io.*;
//import java.util.*;
public class clase {

    public static void main(String[] args) {

        //INSTANCIAMOS UN OBJETO
        Archivo archivo = new Archivo();

        //IASIGNAMOS LA RUTA A LA VARIBLE LEXEMA
        String[] lexemas = archivo.leerArchivoTxt("C:\\Users\\danie\\OneDrive\\Escritorio\\Nueva carpeta (2)\\AnalizadorLexico\\archivo.txt");

        System.out.println("Lexemas encontrados:");
        for (String lexema : lexemas) {
            // Limpiamos caracteres no deseados
            lexema = lexema.replaceAll("[^a-zA-Z0-9=+\\*//*()\\-]", "");

            // Imprimimos solo si no quedó vacío
            if (!lexema.isEmpty()) {
                System.out.println(lexema);
            }
        }


        //USAMOS UN FOR MEJORADO PARA ASIGNAR SU TIPO DE LEXEMA Y LO IMPRIME
        for (String palabra : lexemas) {
            if (palabra.equals("\t")){
                System.out.println("Lexema: [TABULADOR] -> Tabulador");
            } else
            if (palabra.equals(" ")) {
                System.out.println("Lexema: [ESPACIO] -> Espacio en blanco");
            } else if (palabra.equals("int") || palabra.equals("double") || palabra.equals("string") || palabra.equals("public") || palabra.equals("private") || palabra.equals("class")) {
                System.out.println("Lexema: " + palabra + " -> Palabra reservada");
            } else if (palabra.equals("=")) {
                System.out.println("Lexema: " + palabra + " -> Operador de asignación");
            } else if (palabra.matches("[0-9]+")) {  // Coincide con números
                System.out.println("Lexema: " + palabra + " -> Número");
            } else if (palabra.matches("[a-zA-Z][a-zA-Z0-9]*")) {  // Coincide con identificadores
                System.out.println("Lexema: " + palabra + " -> Identificador");
            } else if (palabra.equals("+") || palabra.equals("-") || palabra.equals("*") || palabra.equals("/")) {
                System.out.println("Lexema: " + palabra + " -> Operador aritmético");
            } else if (palabra.equals("(") || palabra.equals(")")) {
                System.out.println("Lexema: " + palabra + " -> Símbolo de agrupación");
            } else if(palabra.equals(";") || palabra.equals("{") || palabra.equals("}")){
                System.out.println("Lexema: " + palabra + " -> Símbolo especial");
            }
            else {
                System.out.println("Lexema: " + palabra + " -> No reconocido");
            }
        }

    }
}

class Archivo{  //CREAMOS LA CLASE ARCHIVO
    public String[] leerArchivoTxt(String direccion){ //CREAMOS UN METODO TIPO STRING ARRAY

        String texto = ""; //CREAMOS UNA VARIABLE PARA MAS ADELANTE RETURN

        try{ //CREAMOS UN TRY CATCH POR SEGURIDAD
            BufferedReader bf = new BufferedReader(new FileReader(direccion)); // USAMOS FUNCIONES ESPECIALES DE JAVA
            // USAMOS FILEREADER PARA LEER EL ARCHIVO PERO ES POCO EFICIENTE
            // LO COMBINAMOS CON BUFFEREDREADER QUE LEE BLOQUEES GRNADES DE DATOS

            //String varibleTemporal = "";

            //CREAMOS UNA VARIABLE TEMPORAL TIPO STRINGBUILDER PARA MODIFICAR LA CADENA DE TEXTO
            StringBuilder varibleTemporal = new StringBuilder();
            String bfRead;

            //LEE LINEA POR LINEA DEL ARCHIVO Y SE LA ASIGNA A "BFREAD"
            // EL BUCLE TERMINA CUANDO ENCUENTRE UN IGUAL
            while ((bfRead = bf.readLine()) != null) {
                varibleTemporal.append(bfRead).append(" "); // AÑADE UN ESPACIO PARA OBTENER LOS LEXEMAS
                // varibleTemporal = varibleTemporal + bfRead;
            }

            // SE ASIGANA A TEXTO
            texto = varibleTemporal.toString(); // LO COMBERTIMOS EN STRING
        }
        catch(Exception e){
            System.err.println("Archivo no encontrado");
        }
        //return texto.split("\\s+");
        //return texto.split("(?=[;(){}])|\\s+");
        //return texto.split("(?=[-+*/=;(){}])|(?<=[-+*/=])|\\\\s+");
        return texto.split("(?=[-+*/%=!<>&|(){}\\[\\],;.:\"'@# ])|(?<=[-+*/%=!<>&|(){}\\[\\],;.:\"'@# ])|\\s"); // USAMOS UNA EXPRESION REGULAR
        // DIVIDIMOS LA CADENA DE TEXTO EN PARETES MAS PEQUEÑAS
        //LO RETORNAMOS

    }
}
