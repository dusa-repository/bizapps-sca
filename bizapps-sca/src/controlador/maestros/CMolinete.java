package controlador.maestros;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

import modelo.maestros.Molinete;

import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Div;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Textbox;

import componentes.Botonera;
import componentes.Catalogo;
import componentes.Mensaje;

public class CMolinete extends CGenerico {

	@Wire
	private Div divMolinete;
	@Wire
	private Textbox txtCodigoMolinete;
	@Wire
	private Textbox txtDescripcionMolinete;
	@Wire
	private Button btnBuscarMolinete;
	@Wire
	private Div botoneraMolinete;
	@Wire
	private Div catalogoMolinete;
	private String idMolinete;

	Catalogo<Molinete> catalogo;

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
		txtCodigoMolinete.setFocus(true);

		Botonera botonera = new Botonera() {

			@Override
			public void salir() {
				cerrarVentana(divMolinete, "Molinete", tabs);

			}

			@Override
			public void limpiar() {
				txtCodigoMolinete.setValue("");
				txtDescripcionMolinete.setValue("");
				idMolinete = "";
				txtCodigoMolinete.setFocus(true);

			}

			@Override
			public void guardar() {
				if (validar()) {
					String id = txtCodigoMolinete.getValue();
					String descripcion = txtDescripcionMolinete.getValue();
					String usuario = nombreUsuarioSesion();
					Molinete molinete = new Molinete(id, descripcion,
							fechaHora, horaAuditoria, usuario);
					servicioMolinete.guardar(molinete);
					msj.mensajeInformacion(Mensaje.guardado);
					limpiar();
				}

			}

			@Override
			public void eliminar() {
				if (txtCodigoMolinete.getText().compareTo("") != 0) {
					Messagebox.show("¿Esta Seguro de Eliminar el Molinete?",
							"Alerta", Messagebox.OK | Messagebox.CANCEL,
							Messagebox.QUESTION,
							new org.zkoss.zk.ui.event.EventListener<Event>() {
								public void onEvent(Event evt)
										throws InterruptedException {
									if (evt.getName().equals("onOK")) {

										Molinete molinete = servicioMolinete
												.buscar(idMolinete);
										servicioMolinete.eliminar(molinete);
										limpiar();
										msj.mensajeInformacion(Mensaje.eliminado);

									}
								}
							});
				} else {
					msj.mensajeAlerta(Mensaje.noSeleccionoRegistro);
				}

			}

			@Override
			public void reporte() {
				// TODO Auto-generated method stub
				
			}
		};

		botonera.getChildren().get(3).setVisible(false);
		botoneraMolinete.appendChild(botonera);
	}

	/* Permite validar que todos los campos esten completos */
	public boolean validar() {
		if (txtCodigoMolinete.getText().compareTo("") == 0) {
			msj.mensajeError(Mensaje.camposVacios);
			return false;
		} else
			return true;
	}

	/* Muestra el catalogo de los molinetes */
	@Listen("onClick = #btnBuscarMolinete")
	public void mostrarCatalogo() {
		final List<Molinete> molinetes = servicioMolinete.buscarTodos();
		catalogo = new Catalogo<Molinete>(catalogoMolinete, "Catalogo de Molinetes",
				molinetes, "Código", "Descripción") {

			@Override
			protected List<Molinete> buscar(String valor, String combo) {
				switch (combo) {
				case "Código":
					return servicioMolinete.filtroCodigo(valor);
				case "Descripción":
					return servicioMolinete.filtroDescripcion(valor);
				default:
					return molinetes;
				}
			}

			@Override
			protected String[] crearRegistros(Molinete molinete) {
				String[] registros = new String[2];
				registros[0] = molinete.getId();
				registros[1] = molinete.getDescripcion();
				return registros;
			}

			
		};
		catalogo.setParent(catalogoMolinete);
		catalogo.doModal();
	}

	/* Permite la seleccion de un item del catalogo */
	@Listen("onSeleccion = #catalogoMolinete")
	public void seleccinar() {
		Molinete molinete = catalogo.objetoSeleccionadoDelCatalogo();
		llenarCampos(molinete);
		catalogo.setParent(null);
	}

	/* Busca si existe un molinete de acuerdo al codigo */
	@Listen("onChange = #txtCodigoMolinete")
	public void buscarPorCodigo() {
		Molinete molinete = servicioMolinete.buscar(txtCodigoMolinete.getValue());
		if (molinete != null)
			llenarCampos(molinete);
	}

	/* LLena los campos del formulario dado un molinete */
	private void llenarCampos(Molinete molinete) {
		txtCodigoMolinete.setValue(molinete.getId());
		txtDescripcionMolinete.setValue(molinete.getDescripcion());
		idMolinete = molinete.getId();
	}

}
