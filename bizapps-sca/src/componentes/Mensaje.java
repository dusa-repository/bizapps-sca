package componentes;

import org.zkoss.zul.Messagebox;

public class Mensaje {
	public static String guardado = "Registro Guardado Exitosamente.";
	public static String claveUsada = "La Clave ha sido Usada por Otro Registro.";
	public static String camposVacios = "Debe Llenar Todos los Campos Requeridos.";
	public static String errorEnReporte = "Ha ocurrido un error originando el reporte";
	public static String noSeleccionoItem = "No ha seleccionado ningun Item";
	public static String noHayRegistros = "No se Encontraron Registros";
	public static String editarSoloUno = "Solo puede Editar un Item a la vez, "
			+ "Seleccione un (1) solo Item y Repita la Operacion";
	public static String eliminarSoloUno = "Solo puede Eliminar un Registro a la vez, "
			+ "Seleccione un (1) solo Registro y Repita la Operacion";
	public static String deseaEliminar = "¿Desea Eliminar el Registro?";
	public static String eliminado = "Registro Eliminado Exitosamente";
	public static String fichaoEmpleado = "La ficha del empleado definido por el usuario no existe";
	public static String estaEditando = "No ha culminado la Edicion, ¿Desea Continuar Editando?";
	public static String noSeleccionoRegistro = "No ha seleccionado ningun Registro";
	public static String exportar = "¿Desea exportar los datos de la lista a formato CSV?";
	public static String enUso = "La interfaz esta siendo usada";
	public static String correoInvalido = "Formato de Correo No Valido";
	public static String contrasennasInvalidas = "Las contraseñas no coinciden.";
	public static String usuarioNoRegistrado = "El Usuario no Esta Registrado.";
	public static String correoNoConcuerda = "El Correo no Concuerda con los Datos del Usuario.";
	public static String contrasennasNoCoinciden = "Las Contraseñas no Coinciden.";
	public static String eliminacionFallida = "No puede eliminar este grupo";
	public static String noPermitido = "El tipo de archivo que ha seleccionado no esta permitido, solo archivos con extension .jpeg y .png son permitidos";
	public static String tamanioMuyGrande = "El archivo que ha seleccionado excede el tamaño maximo establecido (100 KB)";
	public static String noEliminar = "El Registro no se puede Eliminar, Esta siendo Usado";
	public static String telefonoInvalido = "Formato de Telefono No Valido";
	public static String cedulaInvalida = "Formato de Cedula No Valido";
	public static String llenarListas = "Debe Llenar Todos los Campos de la Listas";
	public static String formatoImagenNoValido = "Formato de Imagen no Valido";
	public static String seleccioneFuncionalidades = "Seleccione las Funcionalidades";
	public static String cedulaNoExiste = "El Numero de Cedula que Ingreso No esta asociado a Ningun Usuario";
	public static String reinicioContrasenna = "Se envio un Correo Indicando los datos del Usuario";
	public static String loginUsado = "El Login no esta Disponible, esta siendo usado por otro Usuario";
	public static String datosImportados = "Datos del archivo importados correctamente";

	public void mensajeInformacion(String msj) {
		Messagebox.show(msj, "Informacion", Messagebox.OK,
				Messagebox.INFORMATION);
	}

	public void mensajeAlerta(String msj) {
		Messagebox.show(msj, "Alerta", Messagebox.OK, Messagebox.EXCLAMATION);
	}

	public void mensajeError(String msj) {
		Messagebox.show(msj, "Error", Messagebox.OK, Messagebox.ERROR);
	}
}