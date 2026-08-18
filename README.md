# Clínica Odontológica

Sistema de gestión (pacientes, odontólogos, agenda de citas, tratamientos,
insumos y facturación) construido sobre Spring Boot 4.1.0 + Thymeleaf +
Bootstrap 5.

## Requisitos

- JDK 25+
- MySQL corriendo en `localhost:3306`

## Cómo levantarlo

1. Ajusta usuario/contraseña de MySQL en `src/main/resources/application.properties`
   si no usas `root/root`.
2. Desde la raíz del proyecto:

3. Abre `http://localhost:8080`.

Con `spring.jpa.hibernate.ddl-auto=update`, Hibernate reconcilia las
entidades contra las tablas de la base de datos — las
recrea en caso de que no existan, no las borra.

## Qué incluye

- **Entidades JPA** para las 8 tablas del MER y/o el script DB sql (`paciente`, `odontologo`,
  `cita`, `tratamiento`, `insumo`, `cita_tratamiento`, `cita_insumo`,
  `factura`), con validación (`jakarta.validation`) en los campos
  obligatorios.
- **CRUD completo con vistas** para Paciente, Odontólogo, Tratamiento,
  Insumo y Cita.
- **`cita_tratamiento` y `cita_insumo` no tienen pantalla propia en el
  menú.** Se gestionan desde `/citas/detalle/{id}`: ahí se agrega o se quitan los
  tratamientos aplicados y insumos usados en esa cita puntual. Quitar un
  insumo restaura el stock automáticamente.
- **Facturación**: desde el detalle de una cita se genera la factura (se
  calcula el total sumando los tratamientos aplicados); en el listado de
  facturas se puede marcarla como pagada.
- **UI responsive**: sidebar fija en escritorio, menú tipo offcanvas en
  móvil/tablet, tablas con columnas que se ocultan progresivamente en
  pantallas chicas, formularios con validación Bootstrap + validación de
  servidor.

## Decisiones de diseño que vale la pena que conozcas

- **Relaciones unidireccionales.** `Paciente` y `Odontologo` no tienen una
  lista `List<Cita> citas`. Con Lombok `@Data`, una relación bidireccional
  genera `equals`/`hashCode`/`toString` que se llaman entre sí y explotan
  en `StackOverflowError` en cuanto Hibernate intenta loguear o comparar
  una entidad. Si en algún punto necesitas "las citas de un paciente",
  usa `citaRepository.findByPaciente(paciente)` — ya está en el
  repositorio.
- **Los `@ManyToOne` de `Cita` (`paciente`, `odontologo`) se enlazan al
  `<select>` del formulario vía un `Converter<String, Paciente>` /
  `Converter<String, Odontologo>` (carpeta `config/`).** Es lo que le
  permite a Spring convertir el id que llega del formulario en la
  entidad completa sin que se tenga que escribir ese código a mano en el
  controlador.
