package controlador;

import java.util.Locale;
import java.util.ResourceBundle;

public class Lenguaje {
    private static ResourceBundle bundle;

    public static void definirIdioma(String idioma, String pais) {
        Locale local = new Locale(idioma, pais);
        // "idiomas.mensajes" si están en esa carpeta, o solo "mensajes" si están en src
        bundle = ResourceBundle.getBundle("idiomas.mensajes", local);
    }

    public static String get(String llave) {
        return bundle.getString(llave);
    }
}