import java.io.*;
public class AnalisisLexcio{

    public static void main(String[] args) {
        Archivo archivo = new Archivo();

        String[] lexemas = archivo.leerArchivoTxt("C:\\Users\\jdvil\\OneDrive\\Escritorio\\7. SEMESTRE\\COPILADORES\\MODULO 1\\codigotxt\\archivo.txt");

        /*System.out.println("Lexemas encontrados:");
        for (String lexema : lexemas) {   
            // Limpiamos caracteres no deseados
            lexema = lexema.replaceAll("[^a-zA-Z0-9=+\\*//*()\\-]", "");

            // Imprimimos solo si no quedó vacío
            if (!lexema.isEmpty()) {
                System.out.println(lexema);
            }
        }
            */

        for (String palabra : lexemas) {
            if (palabra.equals("int") || palabra.equals("double") || palabra.equals("string") || palabra.equals("public") || palabra.equals("private") || palabra.equals("class")) {
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
                System.out.println("Lexema: " + palabra + " -> Símbolo de especial");
            } 
            else {
                System.out.println("Lexema: " + palabra + " -> No reconocido");
            }
        }
        
    }
}

class Archivo{
    public String[] leerArchivoTxt(String direccion){

        String texto = "";

        try{
            BufferedReader bf = new BufferedReader(new FileReader(direccion));
            //String varibleTemporal = "";
            StringBuilder varibleTemporal = new StringBuilder();
            String bfRead;

            while ((bfRead = bf.readLine()) != null) {
                varibleTemporal.append(bfRead).append(" ");
               // varibleTemporal = varibleTemporal + bfRead;
            }

            texto = varibleTemporal.toString();
        }
        catch(Exception e){
            System.err.println("no se encontro el archivo mula");
        }
        //return texto.split("\\s+");
        return texto.split("(?=[;(){}])|\\s+");
    
    }
}