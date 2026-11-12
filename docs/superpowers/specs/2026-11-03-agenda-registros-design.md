# Agenda: registros y apariencia oscura

- **Objetivo:** permitir insertar, buscar, eliminar y limpiar registros de estudiantes, y listarlos en una tabla minimalista.
- **Enfoque elegido:** guardar temporalmente los datos en un `DefaultTableModel` dentro de la ventana Swing. Se descarta persistencia en archivo porque no fue solicitada.
- **Alternativas consideradas:** tabla en memoria (elegida por simplicidad) o almacenamiento persistente (añade alcance y dependencias de archivo).
- **Reglas:** cédula única; insertar requiere todos los campos; buscar y eliminar consultan por cédula; limpiar vacía el formulario.
- **Diseño:** tema oscuro tipo vidrio esmerilado simulado con colores y transparencias Swing, tabla sin líneas de cuadrícula. El desenfoque real del escritorio depende de APIs nativas externas y queda fuera de alcance.
- **Límite:** los registros se pierden al cerrar la aplicación.
