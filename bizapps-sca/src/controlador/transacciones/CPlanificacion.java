package controlador.transacciones;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import java.io.File;

import jxl.*;
import jxl.read.biff.BiffException;

import modelo.maestros.Molinete;
import modelo.seguridad.Arbol;
import modelo.transacciones.PlanificacionSemanal;

import org.zkoss.io.Files;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Include;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Textbox;

import arbol.CArbol;

import componentes.Botonera;
import componentes.Catalogo;
import componentes.Mensaje;
import controlador.maestros.CGenerico;

public class CPlanificacion extends CGenerico {

	private static final long serialVersionUID = 84966503677381446L;

	@Wire
	private Div divPlanificacion;
	@Wire
	private Button btnSeleccionarArchivo;
	@Wire
	private Textbox txtArchivoPlanificacion;
	@Wire
	private Button btnSubirArchivo;
	@Wire
	private Div botoneraPlanificacion;
	@Wire
	private Media mediaPlanificacion;
	private static SimpleDateFormat formatoFecha = new SimpleDateFormat(
			"dd/MM/yyyy");
	DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT);
	DateFormat df1 = DateFormat.getDateInstance(DateFormat.MEDIUM);
	List<String> erroresGenerados = new ArrayList<String>();
	private int idRow = 0;
	File archivoPlanificacion;
	int fila = 0;
	int filaEvaluada = 0;
	int filaInvalida = 0;
	int lineasEvaluadas = 0;
	int lineasValidas = 0;
	int lineasInvalidas = 0;
	int errores = 0;

	private CArbol cArbol = new CArbol();

	@Override
	public void inicializar() throws IOException {

		HashMap<String, Object> map = (HashMap<String, Object>) Sessions
				.getCurrent().getAttribute("mapaGeneral");
		if (map != null) {
			if (map.get("tabsGenerales") != null) {
				tabs = (List<Tab>) map.get("tabsGenerales");
				System.out.println(tabs.size());
				map.clear();
				map = null;
			}
		}

		txtArchivoPlanificacion.setPlaceholder("Ningún archivo seleccionado");
		txtArchivoPlanificacion.setStyle("color:black !important;");
		Botonera botonera = new Botonera() {

			@Override
			public void salir() {
				cerrarVentana(divPlanificacion, "Planificacion Semanal", tabs);
			}

			@Override
			public void limpiar() {
				limpiarCampos();
			}

			@Override
			public void guardar() {

			}

			@Override
			public void eliminar() {

			}
		};
		botonera.getChildren().get(0).setVisible(false);
		botonera.getChildren().get(1).setVisible(false);
		botoneraPlanificacion.appendChild(botonera);

	}

	/**
	 * Metodo que permite validar que el documento posee una extension adecuada
	 * asi como tambien permite subirlo a la vista
	 * 
	 * @param event
	 *            archivo subido al sistema por el usuario
	 * @throws IOException
	 */
	@Listen("onUpload = #btnSeleccionarArchivo")
	public void seleccionarArchivo(UploadEvent event) throws IOException {

		mediaPlanificacion = event.getMedia();
		if (mediaPlanificacion != null) {

			if (mediaPlanificacion.getContentType().equals(
					"application/vnd.ms-excel"))

			{
				txtArchivoPlanificacion.setValue(mediaPlanificacion.getName());

				// Copiar archivo excel en el directorio C:\files\
				Files.copy(
						new File("C:\\files\\" + mediaPlanificacion.getName()),
						mediaPlanificacion.getStreamData());

			} else {
				Messagebox
						.show(mediaPlanificacion.getName()
								+ " No es un tipo de archivo valido!, el archivo debe tener la extensión .xls",
								"Error", Messagebox.OK, Messagebox.ERROR);
			}
		}
	}

	@Listen("onClick = #btnSubirArchivo")
	public void subirArchivo() throws BiffException, IOException,
			ParseException {

		if (mediaPlanificacion != null) {

			// Pasamos el excel que vamos a leer
			Workbook workbook = Workbook.getWorkbook(mediaPlanificacion
					.getStreamData());
			// Seleccionamos la hoja que vamos a leer
			Sheet sheet = workbook.getSheet(0);

			int loteUpload = (int) (Math.random() * (99999 - 11111)) + 11111;
			int semana = Integer.parseInt(sheet.getCell(22, 2).getContents());
			String tipoTurno = "D";
			String fichaJefe = sheet.getCell(22, 4).getContents();
			String idUsuario = nombreUsuarioSesion();
			String fichaUsuario = usuarioSesion(idUsuario).getFicha();
			errores = 0;

			// VALIDACION SEMANA
			if (isNumeric(String.valueOf(semana))) {
				if (semana < 1 || semana > 52) {
					errores = errores + 1;
				}
			} else {
				errores = errores + 1;
			}

			// VALIDACION FICHA JEFE
			if (servicioUsuario.buscarPorFicha(fichaJefe) == null) {
				errores = errores + 1;
			}

			while (servicioPlanificacionSemanal.buscarPorLoteUpload(loteUpload)
					.size() != 0) {
				loteUpload = (int) (Math.random() * (99999 - 11111)) + 11111;
			}

			// Metodo para recorrer el archivo excel y verificar que no existan
			// errores
			while (fila < sheet.getRows()) {
				if (isNumeric(sheet.getCell(0, fila).getContents())) {

					for (int columna = 0; columna < 7; columna++) {

						String idTurno = sheet.getCell(3, fila).getContents();
						String idPermiso = "";

						if (sheet.getCell(4 + columna * 2, fila).getContents() == " ") {
							idPermiso = sheet.getCell(4 + columna * 2, fila)
									.getContents();
						} else {

							if (sheet.getCell(5 + columna * 2, fila)
									.getContents() == " ") {
								idPermiso = sheet
										.getCell(5 + columna * 2, fila)
										.getContents();
							} else {

								idPermiso = "";
							}
						}

						// VALIDACION ID_TURNO

						if (servicioTurno.buscar(idTurno) == null) {
							errores = errores + 1;
						}

						// VALIDACION ID_PERMISO

						if (idPermiso != "") {

							if (servicioTipoAusentismo.buscar(idPermiso) == null) {
								errores = errores + 1;

							}

						}

					}

					if (errores == 0) {
						lineasValidas = lineasValidas + 1;
					} else {
						lineasInvalidas = lineasInvalidas + 1;
					}
					fila++;
					filaEvaluada++;

				} else {
					filaEvaluada++;
					filaInvalida++;
					fila++;
				
				}

			}
			
			
			if(lineasInvalidas > 0){
					
				
			}else{
				
				//Insercion de los datos en la base de datos
				
				fila = 0;
				filaEvaluada = 0;
				filaInvalida = 0;
				lineasEvaluadas = 0;
				lineasValidas = 0;
				lineasInvalidas = 0;
				errores = 0;
				
				// Metodo para recorrer el archivo excel y verificar que no existan
				// errores
				while (fila < sheet.getRows()) {
					if (isNumeric(sheet.getCell(0, fila).getContents())) {

						for (int columna = 0; columna < 7; columna++) {

							String ficha = sheet.getCell(1, fila).getContents();
							String nombre = sheet.getCell(2, fila).getContents();
							Date fechaTurno = df.parse(sheet.getCell(
									4 + columna * 2, 7).getContents());
							String fecha2 = df1.format(fechaTurno);
							String idTurno = sheet.getCell(3, fila).getContents();
							String diaSemana = sheet.getCell(4 + columna * 2, 8)
									.getContents();
							String cuadrilla = sheet.getCell(18, fila)
									.getContents();
							String idPermiso = "";

							if (sheet.getCell(4 + columna * 2, fila).getContents() == " ") {
								idPermiso = sheet.getCell(4 + columna * 2, fila)
										.getContents();
							} else {

								if (sheet.getCell(5 + columna * 2, fila)
										.getContents() == " ") {
									idPermiso = sheet
											.getCell(5 + columna * 2, fila)
											.getContents();
								} else {

									idPermiso = "";
								}
							}

							// VALIDACION ID_TURNO

							if (servicioTurno.buscar(idTurno) == null) {
								errores = errores + 1;
							}

							// VALIDACION ID_PERMISO

							if (idPermiso != "") {

								if (servicioTipoAusentismo.buscar(idPermiso) == null) {
									errores = errores + 1;

								}

							}
							
							
							PlanificacionSemanal planificacion = new PlanificacionSemanal(idRow, loteUpload, ficha,
									nombre, fechaTurno, semana, idTurno,
									diaSemana, tipoTurno,cuadrilla,
									idPermiso, fichaJefe, idUsuario,
									fechaHora, horaAuditoria, fichaUsuario);
							servicioPlanificacionSemanal.guardar(planificacion);

						}

						if (errores == 0) {
							lineasValidas = lineasValidas + 1;
						} else {
							lineasInvalidas = lineasInvalidas + 1;
						}
						fila++;
						filaEvaluada++;

					} else {
						filaEvaluada++;
						filaInvalida++;
						fila++;
					
					}

				}
				
				msj.mensajeInformacion(Mensaje.guardado);
				limpiarCampos();
					
			}
			

		} else {

			Messagebox.show("Debe seleccionar un archivo", "Advertencia",
					Messagebox.OK, Messagebox.EXCLAMATION);

		}

		
	}

	private void limpiarCampos() {
		idRow = 0;
		txtArchivoPlanificacion.setText("");
		txtArchivoPlanificacion.setPlaceholder("Ningún archivo seleccionado");
		txtArchivoPlanificacion.setStyle("color:black !important;");

	}

	// Metodo para validar si un dato es numerico o no
	private static boolean isNumeric(String cadena) {
		try {
			Integer.parseInt(cadena);
			return true;
		} catch (NumberFormatException nfe) {
			return false;
		}
	}

}
