package controlador.transacciones;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import modelo.transacciones.PlanificacionSemanal;

import org.springframework.stereotype.Controller;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Div;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Tab;

import componentes.Botonera;
import componentes.Mensaje;

import controlador.maestros.CGenerico;

/**
 * Controlador que permite realizar las operaciones basicas (CRUD) sobre la
 * entidad Actividad
 */
@Controller
public class CDepuracion extends CGenerico {

	private static final long serialVersionUID = -7144250834458342918L;
	@Wire
	private Div divDepuracion;
	@Wire
	private Listbox lsbDepuracion;
	@Wire
	private Div botoneraDepuracion;
	List<PlanificacionSemanal> planificaciones = new ArrayList<PlanificacionSemanal>();
	List<PlanificacionSemanal> lotes = new ArrayList<PlanificacionSemanal>();

	@Override
	public void inicializar() throws IOException {
		// TODO Auto-generated method stub

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

		llenarDatos();
		Botonera botonera = new Botonera() {

			@Override
			public void guardar() {
				// TODO Auto-generated method stub

			}

			@Override
			public void limpiar() {
				// TODO Auto-generated method stub

			}

			@Override
			public void salir() {
				// TODO Auto-generated method stub

				cerrarVentana(divDepuracion, "Depuracion", tabs);

			}

			@Override
			public void eliminar() {
				// TODO Auto-generated method stub

				if (validarSeleccion()) {
					if (obtenerSeleccionados().size() == 1) {

						Messagebox
								.show("¿Esta Seguro de Eliminar los registros correspondientes al lote seleccionado?",
										"Alerta",
										Messagebox.OK | Messagebox.CANCEL,
										Messagebox.QUESTION,
										new org.zkoss.zk.ui.event.EventListener<Event>() {
											public void onEvent(Event evt)
													throws InterruptedException {
												if (evt.getName()
														.equals("onOK")) {

													PlanificacionSemanal planificacion = objetoSeleccionadoDelCatalogo();
													List<PlanificacionSemanal> planificaciones = servicioPlanificacionSemanal
															.buscarPorLoteUpload(planificacion
																	.getLoteUpload());

													for (int i = 0; i < planificaciones
															.size(); i++) {

														PlanificacionSemanal loteEliminado = planificaciones
																.get(i);
														servicioPlanificacionSemanal
																.eliminar(loteEliminado);

													}

													llenarDatos();
													msj.mensajeInformacion(Mensaje.eliminado);

												}
											}
										});

					} else
						msj.mensajeAlerta(Mensaje.eliminarSoloUno);
				}

			}

			@Override
			public void reporte() {
				// TODO Auto-generated method stub

			}

			@Override
			public void seleccionar() {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void buscar() {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void annadir() {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void ayuda() {
				// TODO Auto-generated method stub
				
			}

		};

		botonera.getChildren().get(0).setVisible(false);
		botonera.getChildren().get(1).setVisible(false);
		botonera.getChildren().get(2).setVisible(false);
		botonera.getChildren().get(3).setVisible(false);
		botonera.getChildren().get(8).setVisible(false);
		botoneraDepuracion.appendChild(botonera);

	}

	// Metodo que permite cargar los datos del listbox
	public void llenarDatos() {

		lotes = new ArrayList<PlanificacionSemanal>();
		planificaciones = servicioPlanificacionSemanal.buscarTodos();
		boolean encontro = false;

		for (int i = 0; i < planificaciones.size(); i++) {

			if (lotes.size() == 0) {

				lotes.add(planificaciones.get(i));

			} else {

				for (int j = 0; j < lotes.size(); j++) {

					if (planificaciones.get(i).getLoteUpload() != lotes.get(j)
							.getLoteUpload()) {

						encontro = false;

					} else {

						encontro = true;

					}

				}

				if (encontro == false) {

					lotes.add(planificaciones.get(i));

				}

			}

		}

		lsbDepuracion.setModel(new ListModelList<PlanificacionSemanal>(lotes));
		lsbDepuracion.setMultiple(false);
		lsbDepuracion.setCheckmark(false);
		lsbDepuracion.setMultiple(true);
		lsbDepuracion.setCheckmark(true);
	}

	public boolean validarSeleccion() {
		List<PlanificacionSemanal> seleccionados = obtenerSeleccionados();
		if (seleccionados == null) {
			msj.mensajeAlerta(Mensaje.noHayRegistros);
			return false;
		} else {
			if (seleccionados.isEmpty()) {
				msj.mensajeAlerta(Mensaje.noSeleccionoRegistro);
				return false;
			} else {
				return true;
			}
		}
	}

	public List<PlanificacionSemanal> obtenerSeleccionados() {
		List<PlanificacionSemanal> valores = new ArrayList<PlanificacionSemanal>();
		boolean entro = false;
		if (lsbDepuracion.getItemCount() != 0) {
			final List<Listitem> list1 = lsbDepuracion.getItems();
			for (int i = 0; i < list1.size(); i++) {
				if (list1.get(i).isSelected()) {
					PlanificacionSemanal planificacion = list1.get(i)
							.getValue();
					entro = true;
					valores.add(planificacion);
				}
			}
			if (!entro) {
				valores.clear();
				return valores;
			}
			return valores;
		} else
			return null;
	}

	public PlanificacionSemanal objetoSeleccionadoDelCatalogo() {
		return lsbDepuracion.getSelectedItem().getValue();
	}
	
	public void actualizarLista(List<PlanificacionSemanal> lista) {
		lsbDepuracion.setModel(new ListModelList<PlanificacionSemanal>(lista));
		lsbDepuracion.setMultiple(false);
		lsbDepuracion.setCheckmark(false);
		lsbDepuracion.setMultiple(true);
		lsbDepuracion.setCheckmark(true);
	}

}
