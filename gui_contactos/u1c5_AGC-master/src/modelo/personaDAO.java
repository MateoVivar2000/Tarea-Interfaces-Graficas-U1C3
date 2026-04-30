package modelo;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

//Definición de la clase pública "personaDAO"
public class personaDAO {
	
	private File archivo; 
	private persona persona; 
	
	public personaDAO(persona persona) {
		this.persona = persona; 
		archivo = new File("c:/gestionContactos"); 
		prepararArchivo();
	}
	
	private void prepararArchivo() {
		if (!archivo.exists()) { 
			archivo.mkdir();
		}
		
		archivo = new File(archivo.getAbsolutePath(), "datosContactos.csv");
		if (!archivo.exists()) { 
			try {
				archivo.createNewFile();
				// PUNTO 3: Encabezado para el archivo CSV
				escribir("NOMBRE;TELEFONO;EMAIL;CATEGORIA;FAVORITO");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void escribirArchivo() {
		escribir(persona.datosContacto());
	}

	private void escribir(String contenido) {
		try (FileWriter fw = new FileWriter(archivo, true);
			 PrintWriter pw = new PrintWriter(fw)) {
			pw.println(contenido);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<persona> leerArchivo() throws IOException {
		List<persona> lista = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
			String linea;
			boolean saltarEncabezado = true;
			while ((linea = br.readLine()) != null) {
				if (saltarEncabezado) { saltarEncabezado = false; continue; }
				String[] d = linea.split(";");
				if (d.length >= 5) {
					lista.add(new persona(d[0], d[1], d[2], d[3], Boolean.parseBoolean(d[4])));
				}
			}
		}
		return lista;
	}
	public void guardarListaCompleta(List<persona> lista) {
	    try (FileWriter fw = new FileWriter(archivo, false);
	         PrintWriter pw = new PrintWriter(fw)) {
	        pw.println("NOMBRE;TELEFONO;EMAIL;CATEGORIA;FAVORITO");
	        for (persona p : lista) {
	            pw.println(p.datosContacto());
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
}