package controlador.reporte;

import java.io.IOException;
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
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Div;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Timebox;

import componentes.Botonera;
import componentes.Catalogo;
import componentes.Mensaje;
import controlador.maestros.CGenerico;

public class CReportes extends CGenerico {

	@Wire
	private Div divReporte;
	@Wire
	private Datebox dtbFechaInicio;
	@Wire
	private Datebox dtbFechaFinal;
	@Wire
	private Timebox tmbHoraInicio;
	@Wire
	private Timebox tmbHoraFinal;
	@Wire
	private Combobox cmbMolineteEntrada;
	@Wire
	private Combobox cmbMolineteSalida;
	@Wire
	private Combobox cmbTurno;
	@Wire
	private Textbox txtFicha;
	@Wire
	private Combobox cmbReporte;
	@Wire
	private Div botoneraReporte;
	List<Turno> turnos = new ArrayList<Turno>();
	List<Molinete> molinetes = new ArrayList<Molinete>();
	
	
	
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
		
		//Cargar el combo de turnos
		Turno turno = new Turno("9999","TODOS", null, null, 0, null, "", "");
		turnos = servicioTurno.buscarTodos();
		turnos.add(turno);
		cmbTurno.setModel(new ListModelList<Turno>(turnos));
		
		
		//Cargar los combos de molinees
		molinetes = servicioMolinete.buscarTodos();
		cmbMolineteEntrada.setModel(new ListModelList<Molinete>(molinetes));
		cmbMolineteSalida.setModel(new ListModelList<Molinete>(molinetes));
		

		Botonera botonera = new Botonera() {

			@Override
			public void salir() {
				cerrarVentana(divReporte, "Reportes", tabs);

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

			@Override
			public void reporte() {
				// TODO Auto-generated method stub
				
			}
		};

		botonera.getChildren().get(0).setVisible(false);
		botonera.getChildren().get(1).setVisible(false);
		botoneraReporte.appendChild(botonera);
	}

	/* Permite validar que todos los campos esten completos */
	public boolean validar() {
		if (dtbFechaInicio.getText().compareTo("") == 0
				|| dtbFechaFinal.getText().compareTo("") == 0 
				|| tmbHoraInicio.getText().compareTo("") == 0 
				|| tmbHoraFinal.getText().compareTo("") == 0
				|| cmbMolineteEntrada.getText().compareTo("") == 0
				|| cmbMolineteSalida.getText().compareTo("") == 0
				|| cmbTurno.getText().compareTo("") == 0
				|| txtFicha.getText().compareTo("") == 0
				|| cmbReporte.getText().compareTo("") == 0) {
			msj.mensajeError(Mensaje.camposVacios);
			return false;
		} else
			return true;
	}
	
	
	public void limpiarCampos(){
		
		dtbFechaInicio.setValue(new Date());
		dtbFechaFinal.setValue(new Date());
		tmbHoraInicio.setValue(new Date());
		tmbHoraFinal.setValue(new Date());
		cmbMolineteEntrada.setValue("");
		cmbMolineteSalida.setValue("");
		cmbTurno.setValue("");
		txtFicha.setValue("");
		cmbReporte.setValue("");
			
	}

	

}
