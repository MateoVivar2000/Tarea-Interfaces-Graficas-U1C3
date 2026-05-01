//H
package vista;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import controlador.logica_ventana;
import javax.swing.GroupLayout.Alignment;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;
import java.awt.event.ActionEvent;
import javax.swing.LayoutStyle.ComponentPlacement;

public class ventana extends JFrame {

	public JPanel contentPane;
	public JTextField txt_nombres, txt_telefono, txt_email, txt_buscar;
	public JCheckBox chb_favorito;
	public JComboBox<String> cmb_categoria;
	public JButton btn_add, btn_modificar, btn_eliminar, btn_exportar;
	public JList lst_contactos; 
	public JTable tabla_contactos; 
	public DefaultTableModel modelo_tabla;
	public JProgressBar barra_progreso; 
	public JPopupMenu menu_contextual; 

	public ventana() {
		setBackground(new Color(240, 240, 240));
		setTitle("GESTIÓN DE CONTACTOS - UPS");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setBounds(100, 100, 874, 624);
		
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 51, 102));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout());

		// PUNTO 1: JTabbedPane 
		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.setBackground(new Color(255, 204, 0));
		
		// --- PESTAÑA 1: CONTACTOS (Todo en uno) ---
		JPanel panelContactos = new JPanel();
		panelContactos.setForeground(new Color(255, 204, 0));
		panelContactos.setBackground(new Color(255, 255, 255));

		// JTable Integrada 
		modelo_tabla = new DefaultTableModel(new Object[]{"NOMBRE", "TELÉFONO", "EMAIL", "CATEGORÍA"}, 0);

		// --- PESTAÑA 2: ESTADÍSTICAS ---
		JPanel PnEstadisticas = new JPanel();

		btn_exportar = new JButton("EXPORTAR CONTACTOS A CSV");

		barra_progreso = new JProgressBar(0, 100);
		barra_progreso.setStringPainted(true);

		tabbedPane.addTab("Contactos", panelContactos);
				
				// Búsqueda 
				JLabel lblBusq = new JLabel("BUSCAR CONTACTO:");
				lblBusq.setFont(new Font("Tahoma", Font.BOLD, 13));
				
				txt_buscar = new JTextField();
				
						// Formulario de Registro
						JLabel lblNom = new JLabel("NOMBRES:");
						lblNom.setFont(new Font("Tahoma", Font.BOLD, 13));
				
				txt_nombres = new JTextField();
				
						// Botones
						btn_add = new JButton("");
						btn_add.setIcon(new ImageIcon(ventana.class.getResource("/IMAGENES/contacts_1060405 (1).png")));
						btn_add.setBackground(new Color(255, 204, 0));
						btn_add.setForeground(new Color(0, 0, 0));
						btn_add.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
							}
						});
						
								btn_modificar = new JButton("");
								btn_modificar.setBackground(new Color(255, 204, 2));
								btn_modificar.setIcon(new ImageIcon(ventana.class.getResource("/IMAGENES/profile_9967510.png")));
								btn_modificar.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
									}
								});
				
						btn_eliminar = new JButton("");
						btn_eliminar.setBackground(new Color(255, 204, 2));
						btn_eliminar.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
							}
						});
						int ancho = btn_eliminar.getWidth();
						int alto = btn_eliminar.getHeight();
						btn_eliminar.setIcon(new ImageIcon(ventana.class.getResource("/IMAGENES/garbage_5853731.png")));
						
		
				JLabel lblTel = new JLabel("TELÉFONO:");
				lblTel.setFont(new Font("Tahoma", Font.BOLD, 13));
		
		txt_telefono = new JTextField();
		
				JLabel lblEmail = new JLabel("EMAIL:");
				lblEmail.setFont(new Font("Tahoma", Font.BOLD, 13));
		
		txt_email = new JTextField();
		tabla_contactos = new JTable(modelo_tabla);
		
		JScrollPane scrTabla = new JScrollPane(tabla_contactos);
		
		JLabel lb_Categoria = new JLabel("CATEGORIA");
		lb_Categoria.setFont(new Font("Tahoma", Font.BOLD, 13));
		
		cmb_categoria = new JComboBox();
		cmb_categoria.setModel(new DefaultComboBoxModel(new String[] {"Familia", "Amigos", "Trabajo", "Favoritos"}));
		cmb_categoria.setToolTipText("");
		GroupLayout gl_panelContactos = new GroupLayout(panelContactos);
		gl_panelContactos.setHorizontalGroup(
			gl_panelContactos.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelContactos.createSequentialGroup()
					.addGroup(gl_panelContactos.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panelContactos.createSequentialGroup()
							.addComponent(lblBusq)
							.addGap(5)
							.addComponent(txt_buscar, GroupLayout.PREFERRED_SIZE, 567, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panelContactos.createSequentialGroup()
							.addGap(29)
							.addGroup(gl_panelContactos.createParallelGroup(Alignment.TRAILING)
								.addComponent(lblTel, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblNom, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panelContactos.createParallelGroup(Alignment.LEADING)
								.addComponent(txt_nombres, GroupLayout.PREFERRED_SIZE, 360, GroupLayout.PREFERRED_SIZE)
								.addComponent(txt_telefono, GroupLayout.PREFERRED_SIZE, 360, GroupLayout.PREFERRED_SIZE))
							.addGap(10)
							.addComponent(btn_add)
							.addGap(5)
							.addComponent(btn_modificar, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btn_eliminar))
						.addComponent(scrTabla, GroupLayout.PREFERRED_SIZE, 839, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panelContactos.createSequentialGroup()
							.addGap(29)
							.addGroup(gl_panelContactos.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panelContactos.createSequentialGroup()
									.addComponent(lblEmail, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(txt_email, GroupLayout.PREFERRED_SIZE, 360, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_panelContactos.createSequentialGroup()
									.addComponent(lb_Categoria)
									.addGap(18)
									.addComponent(cmb_categoria, GroupLayout.PREFERRED_SIZE, 108, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED, 254, Short.MAX_VALUE)))
							.addGap(6)))
					.addContainerGap())
		);
		gl_panelContactos.setVerticalGroup(
			gl_panelContactos.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelContactos.createSequentialGroup()
					.addGroup(gl_panelContactos.createParallelGroup(Alignment.LEADING)
						.addComponent(lblBusq, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
						.addComponent(txt_buscar, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
					.addGap(5)
					.addGroup(gl_panelContactos.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panelContactos.createSequentialGroup()
							.addGroup(gl_panelContactos.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panelContactos.createSequentialGroup()
									.addGroup(gl_panelContactos.createParallelGroup(Alignment.BASELINE)
										.addComponent(lblNom, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
										.addComponent(txt_nombres, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
									.addGap(5)
									.addGroup(gl_panelContactos.createParallelGroup(Alignment.BASELINE)
										.addComponent(lblTel)
										.addComponent(txt_telefono, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)))
								.addComponent(btn_add, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panelContactos.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblEmail, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
								.addComponent(txt_email, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_panelContactos.createParallelGroup(Alignment.BASELINE)
								.addComponent(lb_Categoria, GroupLayout.PREFERRED_SIZE, 12, GroupLayout.PREFERRED_SIZE)
								.addComponent(cmb_categoria, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_panelContactos.createParallelGroup(Alignment.LEADING)
							.addComponent(btn_modificar, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
							.addComponent(btn_eliminar, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)))
					.addGap(30)
					.addComponent(scrTabla, GroupLayout.PREFERRED_SIZE, 384, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		panelContactos.setLayout(gl_panelContactos);
		tabbedPane.addTab("Estadísticas", PnEstadisticas);
		GroupLayout gl_PnEstadisticas = new GroupLayout(PnEstadisticas);
		gl_PnEstadisticas.setHorizontalGroup(
			gl_PnEstadisticas.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_PnEstadisticas.createSequentialGroup()
					.addGroup(gl_PnEstadisticas.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_PnEstadisticas.createSequentialGroup()
							.addGap(109)
							.addComponent(barra_progreso, GroupLayout.PREFERRED_SIZE, 600, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_PnEstadisticas.createSequentialGroup()
							.addGap(251)
							.addComponent(btn_exportar, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(140, Short.MAX_VALUE))
		);
		gl_PnEstadisticas.setVerticalGroup(
			gl_PnEstadisticas.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_PnEstadisticas.createSequentialGroup()
					.addGap(112)
					.addComponent(btn_exportar, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(barra_progreso, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE))
		);
		PnEstadisticas.setLayout(gl_PnEstadisticas);
		contentPane.add(tabbedPane, BorderLayout.CENTER);

		// Menú Contextual 
		menu_contextual = new JPopupMenu();
		menu_contextual.add(new JMenuItem("Eliminar registro"));

		// Controlador bajo patrón MVC [cite: 29]
		new logica_ventana(this);
	}
	public void actualizarTextos() {
	    // Asegúrate de que los nombres (lblNombre, btn_add) coincidan con los tuyos
	    setTitle(Lenguaje.get("titulo"));
	    lblNombre.setText(Lenguaje.get("eti_nombre")); 
	    btn_add.setText(Lenguaje.get("btn_agregar"));
	    btn_modificar.setText(Lenguaje.get("btn_editar"));
	    btn_eliminar.setText(Lenguaje.get("btn_borrar"));
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