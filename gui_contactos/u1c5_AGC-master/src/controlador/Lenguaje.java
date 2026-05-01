package controlador;

import java.util.Locale;
import java.util.ResourceBundle;

public class Lenguaje {
    private static ResourceBundle bundle;

    public static void definirIdioma(String idioma, String pais) {
        Locale local = new Locale(idioma, pais);
        // Si los archivos están en la raíz de src, usa solo "mensajes"
        bundle = ResourceBundle.getBundle("mensajes", local);
    }

    public static String get(String llave) {
        return bundle.getString(llave);
    }
}