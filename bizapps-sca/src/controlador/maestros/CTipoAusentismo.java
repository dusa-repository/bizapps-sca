package controlador.maestros;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import modelo.maestros.TipoAusentismo;

import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Div;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Textbox;

import componentes.Botonera;
import componentes.CatalogoGenerico;
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
	private Div divCatalogoTipoAusentismo;
	@Wire
	private Groupbox gpxRegistro;
	@Wire
	private Groupbox gpxDatos;
	private String idTipoAusentismo;
	
	Botonera botonera;
	Mensaje msj = new Mensaje();
	CatalogoGenerico<TipoAusentismo> catalogo;
	protected List<TipoAusentismo> listaGeneral = new ArrayList<TipoAusentismo>();

	@Override
	public void inicializar() throws IOException {

		HashMap<String, Object> mapa = (HashMap<String, Object>) Sessions
				.getCurrent().getAttribute("mapaGeneral");
		if (mapa != null) {
			if (mapa.get("tabsGenerales") != null) {
				tabs = (List<Tab>) mapa.get("tabsGenerales");
				titulo = (String) mapa.get("titulo");
				mapa.clear();
				mapa = null;
			}
		}
		
		txtCodigoTipoAusentismo.setFocus(true);
		mostrarCatalogo();
		botonera = new Botonera() {

			@Override
			public void seleccionar() {
				// TODO Auto-generated method stub
				if (validarSeleccion()) {
					if (catalogo.obtenerSeleccionados().size() == 1) {
						mostrarBotones(false);
						abrirRegistro();
						TipoAusentismo tipoAusentismo = catalogo
								.objetoSeleccionadoDelCatalogo();
						idTipoAusentismo = tipoAusentismo.getId();
						txtCodigoTipoAusentismo.setValue(tipoAusentismo.getId());
						txtCodigoTipoAusentismo.setDisabled(true);
						txtDescripcionTipoAusentismo.setValue(tipoAusentismo
								.getDescripcion());
						txtDescripcionTipoAusentismo.setFocus(true);
					} else
						msj.mensajeAlerta(Mensaje.editarSoloUno);
				}

			}

			@Override
			public void salir() {
				cerrarVentana(divTipoAusentismo,"Tipo de Ausentismo", tabs);

			}

			@Override
			public void limpiar() {
				mostrarBotones(false);
				limpiarCampos();

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
					listaGeneral = servicioTipoAusentismo.buscarTodos();
					catalogo.actualizarLista(listaGeneral);
					abrirCatalogo();
				}

			}

			@Override
			public void eliminar() {
				// TODO Auto-generated method stub
				if (gpxDatos.isOpen()) {
					/* Elimina Varios Registros */
					if (validarSeleccion()) {
						final List<TipoAusentismo> eliminarLista = catalogo
								.obtenerSeleccionados();
						Messagebox
								.show("¿Desea Eliminar los "
										+ eliminarLista.size() + " Registros?",
										"Alerta",
										Messagebox.OK | Messagebox.CANCEL,
										Messagebox.QUESTION,
										new org.zkoss.zk.ui.event.EventListener<Event>() {
											public void onEvent(Event evt)
													throws InterruptedException {
												if (evt.getName()
														.equals("onOK")) {
													servicioTipoAusentismo
															.eliminarVarios(eliminarLista);
													msj.mensajeInformacion(Mensaje.eliminado);
													listaGeneral = servicioTipoAusentismo.buscarTodos();
													catalogo.actualizarLista(listaGeneral);
													
													
												}
											}
										});
					}
				} else {
					/* Elimina un solo registro */
					if (idTipoAusentismo != null) {
						Messagebox
								.show(Mensaje.deseaEliminar,
										"Alerta",
										Messagebox.OK | Messagebox.CANCEL,
										Messagebox.QUESTION,
										new org.zkoss.zk.ui.event.EventListener<Event>() {
											public void onEvent(Event evt)
													throws InterruptedException {
												if (evt.getName()
														.equals("onOK")) {
													servicioMolinete
															.eliminarUno(idTipoAusentismo);
													msj.mensajeInformacion(Mensaje.eliminado);
													limpiar();
													listaGeneral = servicioTipoAusentismo.buscarTodos();
													catalogo.actualizarLista(listaGeneral);
													abrirCatalogo();
												}
											}
										});
					} else
						msj.mensajeAlerta(Mensaje.noSeleccionoRegistro);
				}

			}


			@Override
			public void reporte() {
				// TODO Auto-generated method stub

			}

			@Override
			public void buscar() {
				// TODO Auto-generated method stub
				abrirCatalogo();

			}

			@Override
			public void annadir() {
				// TODO Auto-generated method stub
				abrirRegistro();
				mostrarBotones(false);

			}

			@Override
			public void ayuda() {
				// TODO Auto-generated method stub

			}
		};

		botonera.getChildren().get(1).setVisible(false);
		botonera.getChildren().get(3).setVisible(false);
		botonera.getChildren().get(5).setVisible(false);
		botonera.getChildren().get(6).setVisible(false);
		botonera.getChildren().get(8).setVisible(false);
		botoneraTipoAusentismo.appendChild(botonera);
	}

	@Listen("onOpen = #gpxDatos")
	public void abrirCatalogo() {
		gpxDatos.setOpen(false);
		if (camposEditando()) {
			Messagebox.show(Mensaje.estaEditando, "Alerta", Messagebox.YES
					| Messagebox.NO, Messagebox.QUESTION,
					new org.zkoss.zk.ui.event.EventListener<Event>() {
						public void onEvent(Event evt)
								throws InterruptedException {
							if (evt.getName().equals("onYes")) {
								gpxDatos.setOpen(false);
								gpxRegistro.setOpen(true);
							} else {
								if (evt.getName().equals("onNo")) {
									gpxDatos.setOpen(true);
									gpxRegistro.setOpen(false);
									limpiarCampos();
									mostrarBotones(true);
								}
							}
						}
					});
		} else {
			gpxDatos.setOpen(true);
			gpxRegistro.setOpen(false);
			mostrarBotones(true);
		}
	}
	
	public void limpiarCampos(){
		txtCodigoTipoAusentismo.setValue("");
		txtDescripcionTipoAusentismo.setValue("");
		idTipoAusentismo= null;
		txtCodigoTipoAusentismo.setDisabled(false);
		txtCodigoTipoAusentismo.setFocus(true);
		
	}
	
	
	@Listen("onClick = #gpxRegistro")
	public void abrirRegistro() {
		gpxDatos.setOpen(false);
		gpxRegistro.setOpen(true);
		mostrarBotones(false);

	}

	
	public boolean camposEditando() {
		if (txtCodigoTipoAusentismo.getText().compareTo("") != 0
				|| txtDescripcionTipoAusentismo.getText().compareTo("") != 0) {
			return true;
		} else
			return false;
	}

	public void mostrarBotones(boolean bol) {
		botonera.getChildren().get(0).setVisible(bol);
		botonera.getChildren().get(1).setVisible(!bol);
		botonera.getChildren().get(3).setVisible(!bol);
		botonera.getChildren().get(5).setVisible(!bol);
		botonera.getChildren().get(2).setVisible(bol);
		botonera.getChildren().get(4).setVisible(bol);
		botonera.getChildren().get(8).setVisible(false);

	}

	public boolean camposLLenos() {
		if (txtCodigoTipoAusentismo.getText().compareTo("") == 0) {
			return false;
		} else
			return true;
	}

	protected boolean validar() {

		if (!camposLLenos()) {
			msj.mensajeError(Mensaje.camposVacios);
			return false;
		} else
			return true;

	}

	/* Muestra el catalogo de los tipos de ausentismo */
	public void mostrarCatalogo() {

		listaGeneral = servicioTipoAusentismo.buscarTodos();
		catalogo = new CatalogoGenerico<TipoAusentismo>(divCatalogoTipoAusentismo,
				"Catalogo de Tipos de Ausentismos", listaGeneral, false, true, true,
				"Código", "Descripción") {

			@Override
			protected List<TipoAusentismo> buscar(List<String> valores) {
				List<TipoAusentismo> lista = new ArrayList<TipoAusentismo>();

				for (TipoAusentismo tipoAusentismo : listaGeneral) {
					if (tipoAusentismo.getId().toLowerCase()
							.contains(valores.get(0).toLowerCase())
							&& tipoAusentismo.getDescripcion().toLowerCase()
									.contains(valores.get(1).toLowerCase())) {
						lista.add(tipoAusentismo);
					}
				}
				return lista;

			}

			@Override
			protected String[] crearRegistros(TipoAusentismo tipoAusentismo) {
				String[] registros = new String[16];
				registros[0] = tipoAusentismo.getId();
				registros[1] = tipoAusentismo.getDescripcion();
				return registros;
			}
		};
		catalogo.setParent(divCatalogoTipoAusentismo);

	}
	

	/* Busca si existe un tipo de ausentismo de acuerdo al codigo */
	@Listen("onChange = #txtCodigoTipoAusentismo")
	public void buscarPorCodigo() {
		
		if(txtCodigoTipoAusentismo.getValue() != null){
			TipoAusentismo tipoAusentismo = servicioTipoAusentismo.buscar(txtCodigoTipoAusentismo
					.getValue());
			if (tipoAusentismo != null)
				msj.mensajeAlerta(Mensaje.codigoMolinete);
		}
	
	}


	public boolean validarSeleccion() {
		List<TipoAusentismo> seleccionados = catalogo.obtenerSeleccionados();
		if (seleccionados == null) {
			msj.mensajeAlerta(Mensaje.noHayRegistros);
			return false;
		} else {
			if (seleccionados.isEmpty()) {
				msj.mensajeAlerta(Mensaje.noSeleccionoItem);
				return false;
			} else {
				return true;
			}
		}
	}

}