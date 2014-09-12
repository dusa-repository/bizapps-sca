package controlador.transacciones;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import modelo.maestros.Turno;
import modelo.transacciones.PlanificacionSemanal;

import org.springframework.stereotype.Controller;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Div;
import org.zkoss.zul.Include;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.West;
import org.zkoss.zul.Window;

import componentes.Botonera;

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

			}

			@Override
			public void reporte() {
				// TODO Auto-generated method stub

			}

		};

		botonera.getChildren().get(0).setVisible(false);
		botonera.getChildren().get(2).setVisible(false);
		botonera.getChildren().get(3).setVisible(false);
		botoneraDepuracion.appendChild(botonera);

	}

	// Metodo que permite cargar los datos del listbox
	public void llenarDatos() {

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
	}

}
