package modelo;

//Definición de la clase pública "persona"
public class persona {
	
	// Declaración de variables privadas de la clase "persona"
	private String nombre, telefono, email, categoria;
	private boolean favorito;
	
	// Constructor público de la clase "persona"
	public persona() {
		super(); // Llama al constructor de la clase base (Object)
		this.nombre = "";
		this.telefono = "";
		this.email = "";
		this.categoria = "";
		this.favorito = false;
	}
	// Constructor público de la clase "persona" que incializa todos los campos 
	public persona(String nombre, String telefono, String email, String categoria, boolean favorito) {
		super();
		this.nombre = nombre;
		this.telefono = telefono;
		this.email = email;
		this.categoria = categoria;
		this.favorito = favorito;
	}
	
	// Getters y Setters originales
	public String getNombre() { return nombre; }
	public void setNombre(String nombre) { this.nombre = nombre; }
	public String getTelefono() { return telefono; }
	public void setTelefono(String telefono) { this.telefono = telefono; }
	public String getEmail() { return email; }
	public void setEmail(String email) { this.email = email; }
	public String getCategoria() { return categoria; }
	public void setCategoria(String categoria) { this.categoria = categoria; }
	public boolean isFavorito() { return favorito; }
	public void setFavorito(boolean favorito) { this.favorito = favorito; }
	
	// PUNTO 3: Método para proveer datos a la JTable
	public Object[] toArray() {
		return new Object[]{nombre, telefono, email, categoria, favorito ? "Sí" : "No"};
	}

	// Método para proveer un formato para almacenar en un archivo
	public String datosContacto() {
		return String.format("%s;%s;%s;%s;%s", nombre, telefono, email, categoria, favorito);
	}
	//Método para proveer el formato de los campos que se van a imprimir en la lista
	public String formatoLista() {
		return String.format("%-40s%-40s%-40s%-40s", nombre, telefono, email, categoria);
	}
}