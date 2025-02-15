import java.io.*;
import java.util.*;

public class clase {

    public static void main(String[] args) {
        // Instanciamos un objeto de la clase Archivo
        Archivo archivo = new Archivo();

        // Leemos el archivo y obtenemos los lexemas
        String[] lexemas = archivo.leerArchivoTxt("C:\\Users\\jdvil\\OneDrive\\Escritorio\\7. SEMESTRE\\COPILADORES\\MODULO 1\\codigotxt\\archivo.txt");

        // Imprimimos la tabla con los datos del archivo
        imprimirTabla(lexemas);
    }

    public static void imprimirTabla(String[] lexemas) {
        // Encabezados de la tabla
        String[] encabezados = {"Nombre", "Tipo", "Ambito", "Visibilidad", "Tamaño", "Posicion", "Rol"};
        System.out.println(String.join(" | ", encabezados));

        // Procesamos cada lexema y lo clasificamos
        int posicion = 0; // Para la columna "Posicion"
        for (String palabra : lexemas) {
            // Limpiamos caracteres no deseados
            palabra = palabra.replaceAll("[^a-zA-Z0-9=+\\*//*()\\-]", "");

            // Si el lexema está vacío, lo ignoramos
            if (palabra.isEmpty()) {
                continue;
            }

            // Determinamos el tipo de lexema
            String tipo = "";
            String visibilidad = "";
            int tamano = 0;

            if (palabra.equals("int") || palabra.equals("double") || palabra.equals("string") || 
                palabra.equals("public") || palabra.equals("private") || palabra.equals("class")) {
                tipo = "Palabra reservada";
            } else if (palabra.equals("=")) {
                tipo = "Operador de asignación";
            } else if (palabra.matches("[0-9]+")) {
                tipo = "Número";
            } else if (palabra.matches("[a-zA-Z][a-zA-Z0-9]*")) {
                tipo = "Identificador";
            } else if (palabra.equals("+") || palabra.equals("-") || palabra.equals("*") || palabra.equals("/")) {
                tipo = "Operador aritmético";
            } else if (palabra.equals("(") || palabra.equals(")")) {
                tipo = "Símbolo de agrupación";
            } else if (palabra.equals(";") || palabra.equals("{") || palabra.equals("}")) {
                tipo = "Símbolo especial";
            } else {
                tipo = "No reconocido";
            }

            // Asignamos visibilidad y tamaño (simplificado)
            if (palabra.equals("public") || palabra.equals("private")) {
                visibilidad = palabra;
            } else {
                visibilidad = "N/A";
            }

            switch (tipo) {
                case "int":
                    tamano = 4;
                    break;
                case "double":
                    tamano = 8;
                    break;
                case "String":
                    tamano = 6; // Tamaño arbitrario para String
                    break;
                default:
                    tamano = 0; // Desconocido
            }

            // Creamos la fila de la tabla
            String[] fila = {palabra, tipo, "Global", visibilidad, String.valueOf(tamano), String.valueOf(posicion), ""};
            System.out.println(String.join(" | ", fila));

            posicion++; // Incrementamos la posición
        }
    }
}

class Archivo {
    public String[] leerArchivoTxt(String direccion) {
        String texto = "";

        try (BufferedReader bf = new BufferedReader(new FileReader(direccion))) {
            StringBuilder varibleTemporal = new StringBuilder();
            String bfRead;

            while ((bfRead = bf.readLine()) != null) {
                varibleTemporal.append(bfRead).append(" ");
            }

            texto = varibleTemporal.toString();
        } catch (Exception e) {
            System.err.println("No se encontró el archivo");
        }

        return texto.split("(?=[-+*/=;(){}])|(?<=[-+*/=()])|\\s+");
    }
}