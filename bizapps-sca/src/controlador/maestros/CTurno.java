package controlador.maestros;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import modelo.maestros.Molinete;
import modelo.maestros.Turno;

import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Div;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Timebox;

import componentes.Botonera;
import componentes.CatalogoGenerico;
import componentes.Mensaje;

public class CTurno extends CGenerico {

	@Wire
	private Div divTurno;
	@Wire
	private Textbox txtCodigoTurno;
	@Wire
	private Textbox txtDescripcionTurno;
	@Wire
	private Button btnBuscarTurno;
	@Wire
	private Timebox tmbHoraEntrada;
	@Wire
	private Timebox tmbHoraSalida;
	@Wire
	private Intbox itbMinutos;
	@Wire
	private Div botoneraTurno;
	@Wire
	private Div divCatalogoTurno;
	@Wire
	private Groupbox gpxRegistro;
	@Wire
	private Groupbox gpxDatos;
	private String idTurno;

	Botonera botonera;
	Mensaje msj = new Mensaje();
	CatalogoGenerico<Turno> catalogo;
	protected List<Turno> listaGeneral = new ArrayList<Turno>();

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

		txtCodigoTurno.setFocus(true);
		mostrarCatalogo();
		botonera = new Botonera() {

			@Override
			public void seleccionar() {
				// TODO Auto-generated method stub
				if (validarSeleccion()) {
					if (catalogo.obtenerSeleccionados().size() == 1) {
						mostrarBotones(false);
						abrirRegistro();
						Turno turno = catalogo.objetoSeleccionadoDelCatalogo();
						idTurno = turno.getId();
						txtCodigoTurno.setValue(turno.getId());
						txtCodigoTurno.setDisabled(true);
						tmbHoraEntrada.setValue(turno.getHoraEntrada());
						tmbHoraSalida.setValue(turno.getHoraSalida());
						itbMinutos.setValue(turno.getMinutos());
						txtDescripcionTurno.setValue(turno.getDescripcion());
						txtDescripcionTurno.setFocus(true);
					} else
						msj.mensajeAlerta(Mensaje.editarSoloUno);
				}

			}

			@Override
			public void salir() {
				cerrarVentana(divTurno, "Turno", tabs);

			}

			@Override
			public void limpiar() {
				mostrarBotones(false);
				limpiarCampos();

			}

			@Override
			public void guardar() {
				if (validar()) {
					String id = txtCodigoTurno.getValue();
					String descripcion = txtDescripcionTurno.getValue();
					Date horaEntrada = tmbHoraEntrada.getValue();
					Date horaSalida = tmbHoraSalida.getValue();
					int minutos = itbMinutos.getValue();
					String usuario = nombreUsuarioSesion();
					Turno turno = new Turno(id, descripcion, horaEntrada,
							horaSalida, minutos, fechaHora, horaAuditoria,
							usuario);
					servicioTurno.guardar(turno);
					msj.mensajeInformacion(Mensaje.guardado);
					limpiar();
					listaGeneral = servicioTurno.buscarTodos();
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
						final List<Turno> eliminarLista = catalogo
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
													servicioTurno
															.eliminarVarios(eliminarLista);
													msj.mensajeInformacion(Mensaje.eliminado);
													listaGeneral = servicioTurno
															.buscarTodos();
													catalogo.actualizarLista(listaGeneral);

												}
											}
										});
					}
				} else {
					/* Elimina un solo registro */
					if (idTurno != null) {
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
													servicioTurno
															.eliminarUno(idTurno);
													msj.mensajeInformacion(Mensaje.eliminado);
													limpiar();
													listaGeneral = servicioTurno
															.buscarTodos();
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
		botoneraTurno.appendChild(botonera);
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

	public void limpiarCampos() {
		txtCodigoTurno.setValue("");
		txtDescripcionTurno.setValue("");
		idTurno = null;
		tmbHoraEntrada.setValue(null);
		tmbHoraSalida.setValue(null);
		itbMinutos.setValue(null);
		txtCodigoTurno.setDisabled(false);
		txtCodigoTurno.setFocus(true);

	}

	@Listen("onClick = #gpxRegistro")
	public void abrirRegistro() {
		gpxDatos.setOpen(false);
		gpxRegistro.setOpen(true);
		mostrarBotones(false);

	}

	public boolean camposEditando() {
		if (txtCodigoTurno.getText().compareTo("") != 0
				|| txtDescripcionTurno.getText().compareTo("") != 0
				|| tmbHoraEntrada.getText().compareTo("") != 0
				|| tmbHoraSalida.getText().compareTo("") != 0
				|| itbMinutos.getText().compareTo("") != 0) {
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
		if (txtCodigoTurno.getText().compareTo("") == 0) {
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

		listaGeneral = servicioTurno.buscarTodos();
		catalogo = new CatalogoGenerico<Turno>(divCatalogoTurno,
				"Catalogo de Turnos", listaGeneral, false, true, true, "Código",
				"Descripción", "Hora de Entrada", "Hora de Salida",
				"Minutos a Laborar") {

			@Override
			protected List<Turno> buscar(List<String> valores) {
				List<Turno> lista = new ArrayList<Turno>();

				for (Turno turno : listaGeneral) {
					if (turno.getId().toLowerCase()
							.contains(valores.get(0).toLowerCase())
							&& turno.getDescripcion().toLowerCase()
									.contains(valores.get(1).toLowerCase())
							&& String.valueOf(turno.getHoraEntrada())
									.toLowerCase()
									.contains(valores.get(2).toLowerCase())
							&& String.valueOf(turno.getHoraSalida())
									.toLowerCase()
									.contains(valores.get(3).toLowerCase())
							&& String.valueOf(turno.getMinutos()).toLowerCase()
									.contains(valores.get(4).toLowerCase())) {
						lista.add(turno);
					}
				}
				return lista;

			}

			@Override
			protected String[] crearRegistros(Turno turno) {
				String[] registros = new String[5];
				registros[0] = turno.getId();
				registros[1] = turno.getDescripcion();
				registros[2] = String.valueOf(turno.getHoraEntrada());
				registros[3] = String.valueOf(turno.getHoraSalida());
				registros[4] = String.valueOf(turno.getMinutos());
				return registros;
			}
		};
		catalogo.setParent(divCatalogoTurno);

	}

	@Listen("onChange = #txtCodigoTurno")
	public void buscarPorCodigo() {

		if (txtCodigoTurno.getValue() != null) {
			Turno turno = servicioTurno.buscar(txtCodigoTurno
					.getValue());
			if (turno != null)
				msj.mensajeAlerta(Mensaje.codigoMolinete);
		}

	}

	public boolean validarSeleccion() {
		List<Turno> seleccionados = catalogo.obtenerSeleccionados();
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
