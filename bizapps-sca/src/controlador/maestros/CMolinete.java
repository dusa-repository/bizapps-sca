package controlador.maestros;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import modelo.maestros.Molinete;

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
	private Div divCatalogoMolinete;
	@Wire
	private Groupbox gpxRegistro;
	@Wire
	private Groupbox gpxDatos;
	private String idMolinete;

	Botonera botonera;
	Mensaje msj = new Mensaje();
	CatalogoGenerico<Molinete> catalogo;
	protected List<Molinete> listaGeneral = new ArrayList<Molinete>();

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
		
		txtCodigoMolinete.setFocus(true);
		mostrarCatalogo();
		botonera = new Botonera() {

			@Override
			public void seleccionar() {
				// TODO Auto-generated method stub
				if (validarSeleccion()) {
					if (catalogo.obtenerSeleccionados().size() == 1) {
						mostrarBotones(false);
						abrirRegistro();
						Molinete molinete = catalogo
								.objetoSeleccionadoDelCatalogo();
						idMolinete = molinete.getId();
						txtCodigoMolinete.setValue(molinete.getId());
						txtCodigoMolinete.setDisabled(true);
						txtDescripcionMolinete.setValue(molinete
								.getDescripcion());
						txtDescripcionMolinete.setFocus(true);
					} else
						msj.mensajeAlerta(Mensaje.editarSoloUno);
				}

			}

			@Override
			public void salir() {
				cerrarVentana(divMolinete,"Molinete", tabs);

			}

			@Override
			public void limpiar() {
				mostrarBotones(false);
				limpiarCampos();

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
					listaGeneral = servicioMolinete.buscarTodos();
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
						final List<Molinete> eliminarLista = catalogo
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
													servicioMolinete
															.eliminarVarios(eliminarLista);
													msj.mensajeInformacion(Mensaje.eliminado);
													listaGeneral = servicioMolinete.buscarTodos();
													catalogo.actualizarLista(listaGeneral);
													
													
												}
											}
										});
					}
				} else {
					/* Elimina un solo registro */
					if (idMolinete != null) {
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
															.eliminarUno(idMolinete);
													msj.mensajeInformacion(Mensaje.eliminado);
													limpiar();
													listaGeneral = servicioMolinete.buscarTodos();
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
		botoneraMolinete.appendChild(botonera);
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
		txtCodigoMolinete.setValue("");
		txtDescripcionMolinete.setValue("");
		idMolinete = null;
		txtCodigoMolinete.setDisabled(false);
		txtCodigoMolinete.setFocus(true);
		
	}
	
	
	@Listen("onClick = #gpxRegistro")
	public void abrirRegistro() {
		gpxDatos.setOpen(false);
		gpxRegistro.setOpen(true);
		mostrarBotones(false);

	}

	
	public boolean camposEditando() {
		if (txtCodigoMolinete.getText().compareTo("") != 0
				|| txtDescripcionMolinete.getText().compareTo("") != 0) {
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
		if (txtCodigoMolinete.getText().compareTo("") == 0) {
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

	/* Muestra el catalogo de los molinetes */
	public void mostrarCatalogo() {

		listaGeneral = servicioMolinete.buscarTodos();
		catalogo = new CatalogoGenerico<Molinete>(divCatalogoMolinete,
				"Catalogo de Molinetes", listaGeneral, false, true, true,
				"Código", "Descripción") {

			@Override
			protected List<Molinete> buscar(List<String> valores) {
				List<Molinete> lista = new ArrayList<Molinete>();

				for (Molinete molinete : listaGeneral) {
					if (molinete.getId().toLowerCase()
							.contains(valores.get(0).toLowerCase())
							&& molinete.getDescripcion().toLowerCase()
									.contains(valores.get(1).toLowerCase())) {
						lista.add(molinete);
					}
				}
				return lista;

			}

			@Override
			protected String[] crearRegistros(Molinete molinete) {
				String[] registros = new String[16];
				registros[0] = molinete.getId();
				registros[1] = molinete.getDescripcion();
				return registros;
			}
		};
		catalogo.setParent(divCatalogoMolinete);

	}

	
	/* Busca si existe un molinete de acuerdo al codigo */
	@Listen("onChange = #txtCodigoMolinete")
	public void buscarPorCodigo() {
		
		if(txtCodigoMolinete.getValue() != null){
			Molinete molinete = servicioMolinete.buscar(txtCodigoMolinete
					.getValue());
			if (molinete != null)
				msj.mensajeAlerta(Mensaje.codigoMolinete);
		}
	
	}


	public boolean validarSeleccion() {
		List<Molinete> seleccionados = catalogo.obtenerSeleccionados();
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
