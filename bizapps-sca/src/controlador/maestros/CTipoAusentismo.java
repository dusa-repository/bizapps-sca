package controlador.maestros;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

import modelo.maestros.TipoAusentismo;

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

public class CTipoAusentismo extends CGenerico {

	@Wire
	private Div divTipoAusentismo;
	@Wire
	private Textbox txtCodigoTipoAusentismo;
	@Wire
	private Textbox txtDescripcionTipoAusentismo;
	@Wire
	private Button btnBuscarTipoAusentismo;
	@Wire
	private Div botoneraTipoAusentismo;
	@Wire
	private Div catalogoTipoAusentismo;
	private String idTipoAusentismo;

	Catalogo<TipoAusentismo> catalogo;

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
		txtCodigoTipoAusentismo.setFocus(true);

		Botonera botonera = new Botonera() {

			@Override
			public void salir() {
				cerrarVentana(divTipoAusentismo, "Tipo de Ausentismo", tabs);

			}

			@Override
			public void limpiar() {
				txtCodigoTipoAusentismo.setValue("");
				txtDescripcionTipoAusentismo.setValue("");
				idTipoAusentismo = "";
				txtCodigoTipoAusentismo.setFocus(true);

			}

			@Override
			public void guardar() {
				if (validar()) {
					String id = txtCodigoTipoAusentismo.getValue();
					String descripcion = txtDescripcionTipoAusentismo.getValue();
					String usuario = nombreUsuarioSesion();
					TipoAusentismo tipoAusentismo = new TipoAusentismo(id, descripcion,
							fechaHora, horaAuditoria, usuario);
					servicioTipoAusentismo.guardar(tipoAusentismo);
					msj.mensajeInformacion(Mensaje.guardado);
					limpiar();
				}

			}

			@Override
			public void eliminar() {
				if (txtCodigoTipoAusentismo.getText().compareTo("") != 0) {
					Messagebox.show("¿Esta Seguro de Eliminar el Tipo de Ausentismo?",
							"Alerta", Messagebox.OK | Messagebox.CANCEL,
							Messagebox.QUESTION,
							new org.zkoss.zk.ui.event.EventListener<Event>() {
								public void onEvent(Event evt)
										throws InterruptedException {
									if (evt.getName().equals("onOK")) {

										TipoAusentismo tipoAusentismo = servicioTipoAusentismo
												.buscar(idTipoAusentismo);
										servicioTipoAusentismo.eliminar(tipoAusentismo);
										limpiar();
										msj.mensajeInformacion(Mensaje.eliminado);

									}
								}
							});
				} else {
					msj.mensajeAlerta(Mensaje.noSeleccionoRegistro);
				}

			}
		};

		botoneraTipoAusentismo.appendChild(botonera);
	}

	/* Permite validar que todos los campos esten completos */
	public boolean validar() {
		if (txtCodigoTipoAusentismo.getText().compareTo("") == 0) {
			msj.mensajeError(Mensaje.camposVacios);
			return false;
		} else
			return true;
	}

	/* Muestra el catalogo de los tipoAusentismos */
	@Listen("onClick = #btnBuscarTipoAusentismo")
	public void mostrarCatalogo() {
		final List<TipoAusentismo> tiposAusentismos = servicioTipoAusentismo.buscarTodos();
		catalogo = new Catalogo<TipoAusentismo>(catalogoTipoAusentismo, "Catalogo de Tipos de Ausentismos",
				tiposAusentismos, "Código", "Descripción") {

			@Override
			protected List<TipoAusentismo> buscar(String valor, String combo) {
				switch (combo) {
				case "Código":
					return servicioTipoAusentismo.filtroCodigo(valor);
				case "Descripción":
					return servicioTipoAusentismo.filtroDescripcion(valor);
				default:
					return tiposAusentismos;
				}
			}

			@Override
			protected String[] crearRegistros(TipoAusentismo tipoAusentismo) {
				String[] registros = new String[2];
				registros[0] = tipoAusentismo.getId();
				registros[1] = tipoAusentismo.getDescripcion();
				return registros;
			}

			
		};
		catalogo.setParent(catalogoTipoAusentismo);
		catalogo.doModal();
	}

	/* Permite la seleccion de un item del catalogo */
	@Listen("onSeleccion = #catalogoTipoAusentismo")
	public void seleccinar() {
		TipoAusentismo tipoAusentismo = catalogo.objetoSeleccionadoDelCatalogo();
		llenarCampos(tipoAusentismo);
		catalogo.setParent(null);
	}

	/* Busca si existe un tipoAusentismo de acuerdo al codigo */
	@Listen("onChange = #txtCodigoTipoAusentismo")
	public void buscarPorCodigo() {
		TipoAusentismo tipoAusentismo = servicioTipoAusentismo.buscar(txtCodigoTipoAusentismo.getValue());
		if (tipoAusentismo != null)
			llenarCampos(tipoAusentismo);
	}

	/* LLena los campos del formulario dado un tipoAusentismo */
	private void llenarCampos(TipoAusentismo tipoAusentismo) {
		txtCodigoTipoAusentismo.setValue(tipoAusentismo.getId());
		txtDescripcionTipoAusentismo.setValue(tipoAusentismo.getDescripcion());
		idTipoAusentismo = tipoAusentismo.getId();
	}

}
