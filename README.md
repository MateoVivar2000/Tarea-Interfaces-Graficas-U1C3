#Gestión de Contactos - Programación de Interfaces
Este proyecto es una aplicación de escritorio desarrollada en **Java** con la librería **Swing**. El sistema permite gestionar una lista de contactos personal, aplicando conceptos avanzados de manejo de eventos y el patrón de diseño **MVC (Modelo-Vista-Controlador)**, conforme a los requerimientos de la Universidad Politécnica Salesiana.

## Funcionalidades Principales
Cumpliendo con la **Tarea #1: Manejo de Eventos**, el proyecto integra:

1.  **Interfaz Multi-pestaña (`JTabbedPane`)**:
    * **Contactos**: Formulario de registro (Nombres, Teléfono, Email, Categoría, Favorito), búsqueda dinámica y tabla principal en un solo espacio.
    * **Estadísticas**: Panel dedicado para procesos de exportación y monitoreo con barra de progreso.
2.  **Manejo de Eventos Avanzados**:
    * **Eventos de Teclado**: Atajos como la tecla `ENTER` en los campos de texto para procesar el guardado rápidamente.
    * **Búsqueda Dinámica**: Filtrado en tiempo real de la `JTable` mediante el evento `KeyReleased` en el campo de búsqueda.
    * **Eventos de Mouse**: Implementación de un `JPopupMenu` (clic derecho) sobre la tabla para eliminar registros de forma intuitiva.
3.  **Componentes de Optimización (Swing Avanzado)**:
    * **JTable**: Visualización organizada de datos con capacidad de ordenamiento por columnas.
    * **JProgressBar**: Indicador visual que muestra el progreso real durante la exportación de datos a CSV.
    * **Exportación a CSV**: Funcionalidad para persistir la información en un archivo plano compatible con Excel.

## 🏗️ Arquitectura MVC
El código está estructurado en paquetes para facilitar la mantenibilidad:
- **`vista`**: Contiene la clase `ventana.java` (Interfaz Gráfica).
- **`controlador`**: Contiene `logica_ventana.java` (Gestión de eventos y lógica de negocio).
- **`modelo`**: Contiene las entidades de datos y el acceso a archivos (DAO).

## 🛠️ Instalación y Uso
1.  Clona el repositorio:
    ```bash
    git clone [https://github.com/Programacion-Interfaces-Graficas/u1c5_AGC.git](https://github.com/Programacion-Interfaces-Graficas/u1c5_AGC.git)
    ```
2.  Importa el proyecto en tu IDE preferido (Eclipse, NetBeans o IntelliJ).
3.  Asegúrate de tener instalado el **JDK 11** o superior.
4.  Ejecuta la clase `ventana.java` ubicada en el paquete `vista`.
