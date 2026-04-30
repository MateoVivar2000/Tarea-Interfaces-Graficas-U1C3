package controlador;

import java.awt.event.*;
import java.io.IOException;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import vista.ventana;
import modelo.*;

public class logica_ventana implements ActionListener, MouseListener, KeyListener {
	private ventana delegado;
	private List<persona> contactos;
	private TableRowSorter<DefaultTableModel> sorter;

	public logica_ventana(ventana delegado) {
		this.delegado = delegado;
		cargarDatosTabla();

		// Listeners
		this.delegado.btn_add.addActionListener(this);
		this.delegado.btn_modificar.addActionListener(this);
		this.delegado.btn_eliminar.addActionListener(this);
		this.delegado.btn_exportar.addActionListener(this);
		this.delegado.txt_telefono.addKeyListener(this);
		this.delegado.tabla_contactos.addMouseListener(this);

		// Búsqueda dinámica
		this.delegado.txt_buscar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				String texto = delegado.txt_buscar.getText();
				if (texto.trim().length() == 0) {
					sorter.setRowFilter(null);
				} else {
					sorter.setRowFilter(RowFilter.regexFilter("(?i)" + texto, 0));
				}
			}
		});
	}

	private void cargarDatosTabla() {
		try {
			contactos = new personaDAO(new persona()).leerArchivo();
			DefaultTableModel modelo = (DefaultTableModel) delegado.modelo_tabla;

			modelo.setRowCount(0);
			for (persona p : contactos) {
				modelo.addRow(new Object[] { p.getNombre(), p.getTelefono(), p.getEmail(), p.getCategoria() });
			}
			sorter = new TableRowSorter<>(modelo);
			delegado.tabla_contactos.setRowSorter(sorter);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void obtenerValoresyCrear() {
		String nom = delegado.txt_nombres.getText();
		String tel = delegado.txt_telefono.getText();
		String mail = delegado.txt_email.getText();
		String cat = delegado.cmb_categoria.getSelectedItem().toString();
		persona nueva = new persona(nom, tel, mail, cat, false);
		personaDAO dao = new personaDAO(nueva);
		dao.escribirArchivo();
	}

	public void elimianDatos() {

	}

	public void limpiarcampos() {
		delegado.txt_nombres.setText(null);
		delegado.txt_telefono.setText(null);
		delegado.txt_email.setText(null);
		delegado.cmb_categoria.setSelectedItem(-1);
	}

	private void eliminarContacto() {
		int filaSeleccionada = delegado.tabla_contactos.getSelectedRow();
		if (filaSeleccionada == -1) {
			JOptionPane.showMessageDialog(delegado, "Seleccione un contacto primero");
			return;
		}
		int filaModelo = delegado.tabla_contactos.convertRowIndexToModel(filaSeleccionada);
		contactos.remove(filaModelo);
		new personaDAO(new persona()).guardarListaCompleta(contactos);
		cargarDatosTabla();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == delegado.btn_exportar) {
			exportarAnimacion();
		} else if (e.getSource() == delegado.btn_add) {
			obtenerValoresyCrear();
			cargarDatosTabla();
			JOptionPane.showMessageDialog(delegado, "Contacto agregado correctamente", "Exito",
					JOptionPane.INFORMATION_MESSAGE);
			limpiarcampos();
		} else if (e.getSource() == delegado.btn_eliminar) {
			int respuesta = JOptionPane.showConfirmDialog(delegado, "¿Desea eliminar este contacto?",
					"Confirmar eliminación", JOptionPane.YES_NO_CANCEL_OPTION);
			if (respuesta == JOptionPane.YES_OPTION) {
				eliminarContacto();
				JOptionPane.showMessageDialog(delegado, "Contacto Eliminado");
			}
		}
	}

	private void exportarAnimacion() {
		new SwingWorker<Void, Integer>() {
			@Override
			protected Void doInBackground() throws Exception {
				for (int i = 0; i <= 100; i += 10) {
					Thread.sleep(60);
					publish(i);
				}
				return null;
			}

			@Override
			protected void process(List<Integer> chunks) {
				delegado.barra_progreso.setValue(chunks.get(chunks.size() - 1));
			}

			@Override
			protected void done() {
				JOptionPane.showMessageDialog(delegado, "Archivo CSV generado.");
				delegado.barra_progreso.setValue(0);
			}
		}.execute();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER)
			JOptionPane.showMessageDialog(delegado, "Guardando...");
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (e.isPopupTrigger() || SwingUtilities.isRightMouseButton(e)) {
			delegado.menu_contextual.show(e.getComponent(), e.getX(), e.getY());
			int fila = delegado.tabla_contactos.getSelectedRow();
			if (fila != -1) {

				delegado.txt_nombres.setText(getDatoTabla("NOMBRE", fila));
				delegado.txt_telefono.setText(getDatoTabla("TELÉFONO", fila));
				delegado.txt_email.setText(getDatoTabla("EMAIL", fila));
				delegado.cmb_categoria.setSelectedItem(getDatoTabla("CATEGORÍA", fila));
			}
		}
	}

	// Método auxiliar para buscar el índice de la columna por su nombre
	private String getDatoTabla(String nombreColumna, int fila) {
		int index = delegado.tabla_contactos.getColumnModel().getColumnIndex(nombreColumna);
		return delegado.tabla_contactos.getValueAt(fila, index).toString();
	}


	// Métodos vacíos obligatorios
	public void keyTyped(KeyEvent e) {
	}

	public void keyReleased(KeyEvent e) {
	}

	public void mouseClicked(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}
}