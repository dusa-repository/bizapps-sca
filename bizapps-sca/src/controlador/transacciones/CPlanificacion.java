package controlador.transacciones;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import java.io.File;

import jxl.*;
import jxl.read.biff.BiffException;

import modelo.maestros.Molinete;
import modelo.seguridad.Arbol;

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
	File archivoPlanificacion;
	long lineasEvaluadas;
	long lineasValidas;
	long lineasInvalidas;

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
	public void subirArchivo() throws BiffException, IOException {

		if (mediaPlanificacion != null) {

			// Pasamos el excel que vamos a leer
			Workbook workbook = Workbook.getWorkbook(mediaPlanificacion.getStreamData());
			// Seleccionamos la hoja que vamos a leer
			Sheet sheet = workbook.getSheet(0);

			// Definimos las columnas que debe tener el archivo de excel
			String id;
			String descripcion;
			String usuario = nombreUsuarioSesion();

			// Recorremos las filas del archivo de excel
			for (int fila = 0; fila < sheet.getRows(); fila++) {

				// sheet.getColumns() Total de columnas que debe tener el
				// archivo de excel

				id = sheet.getCell(0, fila).getContents();
				descripcion = sheet.getCell(1, fila).getContents();

				Molinete molinete = new Molinete(id, descripcion, fechaHora,
						horaAuditoria, usuario);
				servicioMolinete.guardar(molinete);

			}

			msj.mensajeInformacion(Mensaje.datosImportados);
			limpiarCampos();

		} else {

			Messagebox.show("Debe seleccionar un archivo", "Advertencia",
					Messagebox.OK, Messagebox.EXCLAMATION);

		}

	}

	private void limpiarCampos() {
		txtArchivoPlanificacion.setText("");
		txtArchivoPlanificacion.setPlaceholder("Ningún archivo seleccionado");
		txtArchivoPlanificacion.setStyle("color:black !important;");

	}
}
