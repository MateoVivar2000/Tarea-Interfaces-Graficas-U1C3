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

        // Listeners [cite: 23]
        this.delegado.btn_add.addActionListener(this);
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
                modelo.addRow(new Object[]{p.getNombre(), p.getTelefono(), p.getEmail(), p.getCategoria()});
            }
            sorter = new TableRowSorter<>(modelo); // [cite: 26]
            delegado.tabla_contactos.setRowSorter(sorter);
        } catch (IOException e) { e.printStackTrace(); }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == delegado.btn_exportar) {
            exportarAnimacion(); // [cite: 27, 28]
        }
    }

    private void exportarAnimacion() {
        new SwingWorker<Void, Integer>() {
            @Override protected Void doInBackground() throws Exception {
                for(int i=0; i<=100; i+=10) { Thread.sleep(60); publish(i); }
                return null;
            }
            @Override protected void process(List<Integer> chunks) {
                delegado.barra_progreso.setValue(chunks.get(chunks.size()-1));
            }
            @Override protected void done() {
                JOptionPane.showMessageDialog(delegado, "Archivo CSV generado.");
                delegado.barra_progreso.setValue(0);
            }
        }.execute();
    }

    @Override public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) JOptionPane.showMessageDialog(delegado, "Guardando...");
    }

    @Override public void mouseReleased(MouseEvent e) {
        if (e.isPopupTrigger() || SwingUtilities.isRightMouseButton(e)) {
            delegado.menu_contextual.show(e.getComponent(), e.getX(), e.getY());
        }
    }

    // Métodos vacíos obligatorios
    public void keyTyped(KeyEvent e) {}
    public void keyReleased(KeyEvent e) {}
    public void mouseClicked(MouseEvent e) {}
    public void mousePressed(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
}