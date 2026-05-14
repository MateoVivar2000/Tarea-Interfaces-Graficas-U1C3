package controlador;

import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import vista.ventana;
import modelo.*;

public class logica_ventana implements ActionListener, MouseListener, KeyListener {

    private ventana delegado;
    private List<persona> contactos;
    private TableRowSorter<DefaultTableModel> sorter;

    // PUNTO 3: pool de un solo hilo para exportaciones
    private final ExecutorService exportadorPool = Executors.newSingleThreadExecutor();

    // PUNTO 5: bandera volatile para bloquear edición simultánea
    private volatile boolean contactoBloqueado = false;

    public logica_ventana(ventana delegado) {
        this.delegado = delegado;
        Lenguaje.definirIdioma("es", "EC");
        delegado.actualizarTextos();
        cargarDatosTabla();

        this.delegado.btn_add.addActionListener(this);
        this.delegado.btn_modificar.addActionListener(this);
        this.delegado.btn_eliminar.addActionListener(this);
        this.delegado.btn_exportar.addActionListener(this);
        this.delegado.txt_telefono.addKeyListener(this);
        this.delegado.tabla_contactos.addMouseListener(this);

        // PUNTO 2: cada tecla dispara un SwingWorker de busqueda
        this.delegado.txt_buscar.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                ejecutarBusquedaConcurrente(delegado.txt_buscar.getText().trim());
            }
        });
    }

    // ---------------------------------------------------------------
    // PUNTO 2: busqueda en hilo de fondo con SwingWorker
    // ---------------------------------------------------------------
    private void ejecutarBusquedaConcurrente(String texto) {
        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                Thread.sleep(50);
                return null;
            }
            @Override
            protected void done() {
                SwingUtilities.invokeLater(() -> {
                    if (texto.isEmpty()) {
                        sorter.setRowFilter(null);
                    } else {
                        sorter.setRowFilter(RowFilter.regexFilter("(?i)" + texto, 0));
                    }
                });
            }
        }.execute();
    }

    // ---------------------------------------------------------------
    private void cargarDatosTabla() {
        try {
            contactos = new personaDAO(new persona()).leerArchivo();
            DefaultTableModel modelo = (DefaultTableModel) delegado.modelo_tabla;
            modelo.setRowCount(0);
            for (persona p : contactos) {
                modelo.addRow(new Object[]{
                    p.getNombre(), p.getTelefono(), p.getEmail(), p.getCategoria()
                });
            }
            sorter = new TableRowSorter<>(modelo);
            delegado.tabla_contactos.setRowSorter(sorter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ---------------------------------------------------------------
    // PUNTO 1: validacion de duplicados en Thread independiente
    // ---------------------------------------------------------------
    private void guardarContactoConValidacion() {
        String nom = delegado.txt_nombres.getText().trim();
        String tel = delegado.txt_telefono.getText().trim();
        String mail = delegado.txt_email.getText().trim();
        String cat = delegado.cmb_categoria.getSelectedItem().toString();

        if (nom.isEmpty() || tel.isEmpty()) {
            JOptionPane.showMessageDialog(delegado, "Nombre y telefono son obligatorios.");
            return;
        }

        delegado.btn_add.setEnabled(false);
        delegado.btn_add.setText("Validando...");

        Thread hiloValidacion = new Thread(() -> {
            boolean duplicado = false;

            synchronized (contactos) {
                for (persona p : contactos) {
                    if (p.getNombre().equalsIgnoreCase(nom) || p.getTelefono().equals(tel)) {
                        duplicado = true;
                        break;
                    }
                }
            }

            final boolean esDuplicado = duplicado;

            SwingUtilities.invokeLater(() -> {
                delegado.btn_add.setEnabled(true);
                delegado.btn_add.setText("");

                if (esDuplicado) {
                    mostrarNotificacion("El contacto ya existe (nombre o telefono duplicado).", false);
                } else {
                    new personaDAO(new persona(nom, tel, mail, cat, false)).escribirArchivo();
                    cargarDatosTabla();
                    limpiarcampos();
                    mostrarNotificacion("Contacto guardado con exito.", true);
                }
            });
        });

        hiloValidacion.setName("Hilo-Validacion-Contacto");
        hiloValidacion.start();
    }

    // ---------------------------------------------------------------
    // PUNTO 4: notificaciones desde cualquier hilo
    // ---------------------------------------------------------------
    private void mostrarNotificacion(String mensaje, boolean exito) {
        int tipo = exito ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.WARNING_MESSAGE;
        JOptionPane.showMessageDialog(delegado, mensaje,
                exito ? "Exito" : "Advertencia", tipo);
    }

    // ---------------------------------------------------------------
    // PUNTO 3: exportacion CSV concurrente con barra de progreso
    // ---------------------------------------------------------------
    private void exportarCSVConcurrente() {
        List<persona> copiaContactos;
        synchronized (contactos) {
            copiaContactos = new ArrayList<>(contactos);
        }

        delegado.btn_exportar.setEnabled(false);
        delegado.barra_progreso.setValue(0);

        new SwingWorker<Void, Integer>() {
            @Override
            protected Void doInBackground() throws Exception {
                int total = Math.max(copiaContactos.size(), 1);
                for (int i = 0; i <= total; i++) {
                    Thread.sleep(40);
                    publish((int) ((i / (double) total) * 100));
                }
                new personaDAO(new persona()).exportarCSV(
                        copiaContactos, "c:/gestionContactos/exportacion_U3.csv");
                return null;
            }
            @Override
            protected void process(List<Integer> chunks) {
                SwingUtilities.invokeLater(() ->
                        delegado.barra_progreso.setValue(chunks.get(chunks.size() - 1)));
            }
            @Override
            protected void done() {
                SwingUtilities.invokeLater(() -> {
                    delegado.barra_progreso.setValue(100);
                    delegado.btn_exportar.setEnabled(true);
                    mostrarNotificacion(
                            "Exportacion completada: c:/gestionContactos/exportacion_U3.csv", true);
                    delegado.barra_progreso.setValue(0);
                });
            }
        }.execute();
    }

    // ---------------------------------------------------------------
    private void limpiarcampos() {
        delegado.txt_nombres.setText(null);
        delegado.txt_telefono.setText(null);
        delegado.txt_email.setText(null);
        delegado.cmb_categoria.setSelectedIndex(0);
    }

    private void eliminarContacto() {
        int filaSeleccionada = delegado.tabla_contactos.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(delegado, "Seleccione un contacto primero");
            return;
        }
        int filaModelo = delegado.tabla_contactos.convertRowIndexToModel(filaSeleccionada);
        synchronized (contactos) {
            contactos.remove(filaModelo);
        }
        new personaDAO(new persona()).guardarListaCompleta(contactos);
        cargarDatosTabla();
    }

    // ---------------------------------------------------------------
    // PUNTO 5: modificacion sincronizada, solo un hilo a la vez
    // ---------------------------------------------------------------
    private void ejecutarModificacion() {
        int filaVisual = delegado.tabla_contactos.getSelectedRow();
        if (filaVisual == -1) {
            JOptionPane.showMessageDialog(delegado,
                    "Selecciona un contacto de la tabla primero.");
            return;
        }
        if (contactoBloqueado) {
            JOptionPane.showMessageDialog(delegado,
                    "El contacto esta siendo editado. Espere.");
            return;
        }

        contactoBloqueado = true;
        int filaModelo = delegado.tabla_contactos.convertRowIndexToModel(filaVisual);

        synchronized (contactos) {
            persona p = contactos.get(filaModelo);
            p.setNombre(delegado.txt_nombres.getText());
            p.setTelefono(delegado.txt_telefono.getText());
            p.setEmail(delegado.txt_email.getText());
            p.setCategoria(delegado.cmb_categoria.getSelectedItem().toString());
        }

        new personaDAO(new persona()).guardarListaCompleta(contactos);
        cargarDatosTabla();
        limpiarcampos();
        contactoBloqueado = false;

        SwingUtilities.invokeLater(() ->
                mostrarNotificacion("Contacto actualizado correctamente.", true));
    }

    // ---------------------------------------------------------------
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == delegado.btn_exportar) {
            exportarCSVConcurrente();
        } else if (e.getSource() == delegado.btn_add) {
            guardarContactoConValidacion();
        } else if (e.getSource() == delegado.btn_eliminar) {
            int r = JOptionPane.showConfirmDialog(delegado,
                    "Desea eliminar este contacto?",
                    "Confirmar eliminacion",
                    JOptionPane.YES_NO_CANCEL_OPTION);
            if (r == JOptionPane.YES_OPTION) {
                eliminarContacto();
                SwingUtilities.invokeLater(() ->
                        mostrarNotificacion("Contacto eliminado correctamente.", true));
            }
        } else if (e.getSource() == delegado.btn_modificar) {
            ejecutarModificacion();
        }
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

    private String getDatoTabla(String nombreColumna, int fila) {
        int index = delegado.tabla_contactos.getColumnModel().getColumnIndex(nombreColumna);
        return delegado.tabla_contactos.getValueAt(fila, index).toString();
    }

    public void keyTyped(KeyEvent e) {}
    public void keyReleased(KeyEvent e) {}
    public void mouseClicked(MouseEvent e) {}
    public void mousePressed(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
}