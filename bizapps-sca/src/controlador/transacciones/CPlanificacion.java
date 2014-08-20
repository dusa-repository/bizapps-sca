package controlador.transacciones;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import modelo.seguridad.Arbol;

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
	private Media media;

	private CArbol cArbol = new CArbol();

	@Override
	public void inicializar() throws IOException {
		
		contenido = (Include) divPlanificacion.getParent();
		Tabbox tabox = (Tabbox) divPlanificacion.getParent().getParent().getParent()
				.getParent();
		tabBox = tabox;
		tab = (Tab) tabox.getTabs().getLastChild();
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
				txtArchivoPlanificacion.setText("");
				txtArchivoPlanificacion
						.setPlaceholder("Ningún archivo seleccionado");
				txtArchivoPlanificacion.setStyle("color:black !important;");
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
	 */
	@Listen("onUpload = #btnSeleccionarArchivo")
	public void subirArchivo(UploadEvent event) {

		media = event.getMedia();
		if (media != null) {

			if (media.getContentType().equals("application/vnd.ms-excel")
					|| media.getContentType()
							.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))

			{

				txtArchivoPlanificacion.setValue(media.getName());

			} else {
				Messagebox.show(media.getName()
						+ " No es un tipo de archivo valido!", "Error",
						Messagebox.OK, Messagebox.ERROR);
			}
		}
	}

}
