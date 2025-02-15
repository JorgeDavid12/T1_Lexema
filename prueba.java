import java.io.*;
import java.util.*;

public class prueba {
    public static void main(String[] args) {
        // Instanciamos un objeto de la clase Archivo
        Archivo archivo = new Archivo();

        // Leemos el archivo y obtenemos los lexemas
        String[] lexemas = archivo.leerArchivoTxt("C:\\Users\\jdvil\\OneDrive\\Escritorio\\7. SEMESTRE\\COPILADORES\\MODULO 1\\codigotxt\\archivo.txt");

        // Imprimimos los lexemas encontrados
        System.out.println("Lexemas encontrados:");
        for (String lexema : lexemas) {
            // Limpiamos caracteres no deseados
            lexema = lexema.replaceAll("[^a-zA-Z0-9=+\\*//*()\\-]", "");

            // Imprimimos solo si no quedó vacío
            if (!lexema.isEmpty()) {
                System.out.println(lexema);
            }
        }

        // Clasificamos los lexemas y los imprimimos
        System.out.println("\nClasificación de lexemas:");
        for (String palabra : lexemas) {
            if (palabra.equals("\t")) {
                System.out.println("Lexema: [TABULADOR] -> Tabulador");
            } else if (palabra.equals(" ")) {
                System.out.println("Lexema: [ESPACIO] -> Espacio en blanco");
            } else if (palabra.equals("int") || palabra.equals("double") || palabra.equals("string") || 
                    palabra.equals("public") || palabra.equals("private") || palabra.equals("class")) {
                System.out.println("Lexema: " + palabra + " -> Palabra reservada");
            } else if (palabra.equals("=")) {
                System.out.println("Lexema: " + palabra + " -> Operador de asignación");
            } else if (palabra.matches("[0-9]+")) {
                System.out.println("Lexema: " + palabra + " -> Número");
            } else if (palabra.matches("[a-zA-Z][a-zA-Z0-9]*")) {
                System.out.println("Lexema: " + palabra + " -> Identificador");
            } else if (palabra.equals("+") || palabra.equals("-") || palabra.equals("*") || palabra.equals("/")) {
                System.out.println("Lexema: " + palabra + " -> Operador aritmético");
            } else if (palabra.equals("(") || palabra.equals(")")) {
                System.out.println("Lexema: " + palabra + " -> Símbolo de agrupación");
            } else if (palabra.equals(";") || palabra.equals("{") || palabra.equals("}")) {
                System.out.println("Lexema: " + palabra + " -> Símbolo especial");
            } else {
                System.out.println("Lexema: " + palabra + " -> No reconocido");
            }
        }

        // Generamos e imprimimos la tabla
        System.out.println("\nTabla de variables:");
        imprimirTabla(lexemas);
    }

    public static void imprimirTabla(String[] lexemas) {
        // Encabezados de la tabla
        String[] encabezados = {"Nombre", "Tipo", "Ambito", "Visibilidad", "Tamaño", "Posicion"};
        System.out.println(String.format("%-10s | %-10s | %-10s | %-10s | %-10s | %-10s", encabezados[0], encabezados[1], encabezados[2], encabezados[3], encabezados[4], encabezados[5]));

        // Procesamos los lexemas para generar la tabla
        int posicion = 0; // Para la columna "Posicion"
        String tipoActual = ""; // Para almacenar el tipo de dato actual
        String visibilidadActual = ""; // Para almacenar la visibilidad actual

        for (String palabra : lexemas) {
            // Limpiamos caracteres no deseados
            palabra = palabra.replaceAll("[^a-zA-Z0-9=+\\*//*()\\-]", "");

            // Si el lexema está vacío, lo ignoramos
            if (palabra.isEmpty()) {
                continue;
            }

            // Si es una palabra reservada, actualizamos el tipo o la visibilidad
            if (palabra.equals("public") || palabra.equals("private")) {
                visibilidadActual = palabra;
            } else if (palabra.equals("int") || palabra.equals("double") || palabra.equals("string") || palabra.equals("class")) {
                tipoActual = palabra;
            }

            // Si es un identificador, lo agregamos a la tabla
            if (palabra.matches("[a-zA-Z][a-zA-Z0-9]*") && !palabra.equals("public") && 
                !palabra.equals("private") && !palabra.equals("int") && !palabra.equals("double") && 
                !palabra.equals("string") && !palabra.equals("class")) {
                // Creamos la fila de la tabla
                String nombre = palabra;
                String tipo = tipoActual;
                String ambito = "Global";
                String visibilidad = visibilidadActual.isEmpty() ? "N/A" : visibilidadActual;
                int tamano = palabra.length();
                String posicionStr = String.valueOf(posicion);

                // Imprimimos la fila con formato alineado
                System.out.println(String.format("%-10s | %-10s | %-10s | %-10s | %-10s | %-10s", nombre, tipo, ambito, visibilidad, tamano, posicionStr));

                posicion++; // Incrementamos la posición
            }
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
            System.err.println("Archivo no encontrado");
        }

        return texto.split("(?=[-+*/%=!<>&|(){}\\[\\],;.:\"'@# ])|(?<=[-+*/%=!<>&|(){}\\[\\],;.:\"'@# ])|\\s");
    }
}