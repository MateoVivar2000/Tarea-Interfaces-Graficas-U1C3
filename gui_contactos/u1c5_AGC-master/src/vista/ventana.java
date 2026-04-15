package vista;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import controlador.logica_ventana;

public class ventana extends JFrame {

	public JPanel contentPane;
	public JTextField txt_nombres, txt_telefono, txt_email, txt_buscar;
	public JCheckBox chb_favorito;
	public JComboBox<String> cmb_categoria;
	public JButton btn_add, btn_modificar, btn_eliminar, btn_exportar;
	public JList lst_contactos; 
	public JTable tabla_contactos; // [cite: 26]
	public DefaultTableModel modelo_tabla;
	public JProgressBar barra_progreso; // [cite: 28]
	public JPopupMenu menu_contextual; // [cite: 24]

	public ventana() {
		setTitle("GESTIÓN DE CONTACTOS - UPS");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setBounds(100, 100, 1050, 800);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout());

		// PUNTO 1: JTabbedPane [cite: 22]
		JTabbedPane tabbedPane = new JTabbedPane();
		
		// --- PESTAÑA 1: CONTACTOS (Todo en uno) ---
		JPanel panelContactos = new JPanel();
		panelContactos.setLayout(null);
		
		// Búsqueda [cite: 23]
		JLabel lblBusq = new JLabel("BUSCAR CONTACTO:");
		lblBusq.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblBusq.setBounds(25, 20, 150, 25);
		panelContactos.add(lblBusq);
		
		txt_buscar = new JTextField();
		txt_buscar.setBounds(180, 20, 820, 25);
		panelContactos.add(txt_buscar);

		// Formulario de Registro
		JLabel lblNom = new JLabel("NOMBRES:");
		lblNom.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNom.setBounds(25, 60, 100, 25);
		panelContactos.add(lblNom);
		
		txt_nombres = new JTextField();
		txt_nombres.setBounds(130, 55, 420, 30);
		panelContactos.add(txt_nombres);

		JLabel lblTel = new JLabel("TELÉFONO:");
		lblTel.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblTel.setBounds(25, 100, 100, 25);
		panelContactos.add(lblTel);
		
		txt_telefono = new JTextField();
		txt_telefono.setBounds(130, 95, 420, 30);
		panelContactos.add(txt_telefono);

		JLabel lblEmail = new JLabel("EMAIL:");
		lblEmail.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblEmail.setBounds(25, 140, 100, 25);
		panelContactos.add(lblEmail);
		
		txt_email = new JTextField();
		txt_email.setBounds(130, 135, 420, 30);
		panelContactos.add(txt_email);

		// Botones
		btn_add = new JButton("AGREGAR");
		btn_add.setBounds(600, 75, 120, 55);
		panelContactos.add(btn_add);

		btn_modificar = new JButton("MODIFICAR");
		btn_modificar.setBounds(730, 75, 120, 55);
		panelContactos.add(btn_modificar);

		btn_eliminar = new JButton("ELIMINAR");
		btn_eliminar.setBounds(860, 75, 120, 55);
		panelContactos.add(btn_eliminar);

		// JTable Integrada [cite: 26]
		modelo_tabla = new DefaultTableModel(new Object[]{"NOMBRE", "TELÉFONO", "EMAIL", "CATEGORÍA"}, 0);
		tabla_contactos = new JTable(modelo_tabla);
		JScrollPane scrTabla = new JScrollPane(tabla_contactos);
		scrTabla.setBounds(25, 190, 975, 520);
		panelContactos.add(scrTabla);

		// --- PESTAÑA 2: ESTADÍSTICAS ---
		JPanel panelStats = new JPanel();
		panelStats.setLayout(null);

		btn_exportar = new JButton("EXPORTAR CONTACTOS A CSV"); // [cite: 27]
		btn_exportar.setBounds(350, 100, 300, 50);
		panelStats.add(btn_exportar);

		barra_progreso = new JProgressBar(0, 100); // [cite: 28]
		barra_progreso.setBounds(200, 180, 600, 40);
		barra_progreso.setStringPainted(true);
		panelStats.add(barra_progreso);

		tabbedPane.addTab("Contactos", panelContactos);
		tabbedPane.addTab("Estadísticas", panelStats);
		contentPane.add(tabbedPane, BorderLayout.CENTER);

		// Menú Contextual [cite: 24]
		menu_contextual = new JPopupMenu();
		menu_contextual.add(new JMenuItem("Eliminar registro"));

		// Controlador bajo patrón MVC [cite: 29]
		new logica_ventana(this);
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				ventana frame = new ventana();
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
			} catch (Exception e) { e.printStackTrace(); }
		});
	}
}