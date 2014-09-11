package controlador.maestros;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import modelo.maestros.Turno;

import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Div;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Timebox;

import componentes.Botonera;
import componentes.Catalogo;
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
	private Div catalogoTurno;
	private String idTurno;

	Catalogo<Turno> catalogo;

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
		txtCodigoTurno.setFocus(true);

		Botonera botonera = new Botonera() {

			@Override
			public void salir() {
				cerrarVentana(divTurno, "Turno", tabs);

			}

			@Override
			public void limpiar() {
				txtCodigoTurno.setValue("");
				txtDescripcionTurno.setValue("");
				tmbHoraEntrada.setValue(new Date());
				tmbHoraSalida.setValue(new Date());
				itbMinutos.setValue(null);
				idTurno = "";
				txtCodigoTurno.setFocus(true);

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
				}

			}

			@Override
			public void eliminar() {
				if (txtCodigoTurno.getText().compareTo("") != 0) {
					Messagebox.show("¿Esta Seguro de Eliminar el Turno?",
							"Alerta", Messagebox.OK | Messagebox.CANCEL,
							Messagebox.QUESTION,
							new org.zkoss.zk.ui.event.EventListener<Event>() {
								public void onEvent(Event evt)
										throws InterruptedException {
									if (evt.getName().equals("onOK")) {

										Turno turno = servicioTurno
												.buscar(idTurno);
										servicioTurno.eliminar(turno);
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
		botoneraTurno.appendChild(botonera);
	}

	/* Permite validar que todos los campos esten completos */
	public boolean validar() {
		if (txtCodigoTurno.getText().compareTo("") == 0) {
			msj.mensajeError(Mensaje.camposVacios);
			return false;
		} else
			return true;
	}

	/* Muestra el catalogo de los turnos */
	@Listen("onClick = #btnBuscarTurno")
	public void mostrarCatalogo() {
		final List<Turno> turnos = servicioTurno.buscarTodos();
		catalogo = new Catalogo<Turno>(catalogoTurno, "Catalogo de Turnos",
				turnos, "Código", "Descripción", "Hora de Entrada",
				"Hora de Salida", "Minutos a Laborar") {

			@Override
			protected List<Turno> buscar(String valor, String combo) {
				switch (combo) {
				case "Código":
					return servicioTurno.filtroCodigo(valor);
				case "Descripción":
					return servicioTurno.filtroDescripcion(valor);
				case "Hora de Entrada":
					return servicioTurno.filtroHoraEntrada(valor);
				case "Hora de Salida":
					return servicioTurno.filtroHoraSalida(valor);
				case "Minutos a Laborar":
					return servicioTurno.filtroMinutos(valor);
				default:
					return turnos;
				}
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
		catalogo.setParent(catalogoTurno);
		catalogo.doModal();
	}

	/* Permite la seleccion de un item del catalogo */
	@Listen("onSeleccion = #catalogoTurno")
	public void seleccinar() {
		Turno turno = catalogo.objetoSeleccionadoDelCatalogo();
		llenarCampos(turno);
		catalogo.setParent(null);
	}

	/* Busca si existe un turno de acuerdo al codigo */
	@Listen("onChange = #txtCodigoTurno")
	public void buscarPorCodigo() {
		Turno turno = servicioTurno.buscar(txtCodigoTurno.getValue());
		if (turno != null)
			llenarCampos(turno);
	}

	/* LLena los campos del formulario dado un turno */
	private void llenarCampos(Turno turno) {
		txtCodigoTurno.setValue(turno.getId());
		txtDescripcionTurno.setValue(turno.getDescripcion());
		tmbHoraEntrada.setValue(turno.getHoraEntrada());
		tmbHoraSalida.setValue(turno.getHoraSalida());
		itbMinutos.setValue(turno.getMinutos());
		idTurno = turno.getId();
	}

}
