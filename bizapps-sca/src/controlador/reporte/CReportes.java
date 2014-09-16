package controlador.reporte;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import modelo.maestros.Molinete;
import modelo.maestros.Turno;
import modelo.transacciones.PlanificacionSemanal;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
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
	protected Connection conexion;
	List<Turno> turnos = new ArrayList<Turno>();
	List<Molinete> molinetes = new ArrayList<Molinete>();
	List<PlanificacionSemanal> planificaciones = new ArrayList<PlanificacionSemanal>();

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

		// Cargar el combo de turnos
		Turno turno = new Turno("9999", "TODOS", null, null, 0, null, "", "");
		turnos = servicioTurno.buscarTodos();
		turnos.add(turno);
		cmbTurno.setModel(new ListModelList<Turno>(turnos));

		// Cargar los combos de molinees
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

				if (validar()) {
					
					DateFormat fecha = new SimpleDateFormat("dd/MM/yyyy");
					DateFormat hora = new SimpleDateFormat("hh:mm:ss");
					String hora1 = "1";
					String hora2 = "2";
					String fecha1 = fecha.format(dtbFechaInicio.getValue());
					String fecha2 = fecha.format(dtbFechaFinal.getValue());
					
					System.out.println(fecha1);
					
					String molineteEntrada = servicioMolinete
							.buscarPorDescripcion(cmbMolineteEntrada.getValue())
							.getId();
					String molineteSalida = servicioMolinete
							.buscarPorDescripcion(cmbMolineteSalida.getValue())
							.getId();
					String turno = servicioTurno.buscarPorDescripcion(
							cmbTurno.getValue()).getId();
					String ficha = txtFicha.getValue();
					int totalNomina = 2;
					String reporte = "";
					System.out.println(cmbReporte.getSelectedItem().getId());

					if (cmbReporte.getSelectedItem().getId().equals("R00001")) {
						reporte = "R00001";
					} else {

						if (cmbReporte.getSelectedItem().getId()
								.equals("R00002")) {
							reporte = "R00002";
						} else {

							if (cmbReporte.getSelectedItem().getId()
									.equals("R00003")) {
								reporte = "R00003";
							} else {

								if (cmbReporte.getSelectedItem().getId()
										.equals("R00004")) {
									reporte = "R00004";
								} else {

									if (cmbReporte.getSelectedItem().getId()
											.equals("R00005")) {
										reporte = "R00005";
									} else {

										if (cmbReporte.getSelectedItem()
												.getId().equals("R00006")) {
											reporte = "R00006";
										} else {

											if (cmbReporte.getSelectedItem()
													.getId().equals("R00007")) {
												reporte = "R00007";
											}
										}
									}
								}
							}

						}

					}

					System.out.println(reporte);

					Clients.evalJavaScript("window.open('/bizapps-sca/Generador?valor=1&valor2="
							+ reporte
							+ "&valor3="
							+ fecha1
							+ "&valor4="
							+ fecha2
							+ "&valor5="
							+ hora1
							+ "&valor6="
							+ hora2
							+ "&valor7="
							+ molineteEntrada
							+ "&valor8="
							+ molineteSalida
							+ "&valor9="
							+ turno
							+ "&valor10="
							+ ficha
							+ "&valor11="
							+ totalNomina
							+ "','','top=100,left=200,height=600,width=800,scrollbars=1,resizable=1')");

				}

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

	public void limpiarCampos() {

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

	public byte[] reporte(String part2, String part3, String part4,
			String part5, String part6, String part7, String part8,
			String part9, String part10, String part11) {

		byte[] fichero = null;

		conexion = null;
		try {

			ClassLoader cl = this.getClass().getClassLoader();
			InputStream fis = null;
			
			SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
			
			Date fechaInicio = null;
			Timestamp fechaInicioHora = null;
			try {
				fechaInicio = formato.parse(part3);
				fechaInicioHora = new Timestamp(fechaInicio.getTime());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			Map parameters = new HashMap();

			if(part2.equals("R00003")){
				
				parameters.put("fecha_desde", fechaInicioHora);
				parameters.put("molinete_entrada", part7);
				parameters.put("total_nomina_planificada", part11);
				parameters.put("molinete_entrada_mostrar", part7);
				parameters.put("turno", part9);

				fis = (cl.getResourceAsStream("/reporte/R00003.jasper"));
				
			}
			
			

			String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
			String url = "jdbc:sqlserver://localhost:1433;DatabaseName=dusa_sca";
			String user = "client";
			String password = "123";

			try {

				try {
					conexion = java.sql.DriverManager.getConnection(url, user,
							password);

				} catch (Exception ex) {
					ex.printStackTrace();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}

			JasperPrint jasperPrint = null;

			try {

				if (fichero == null) {
					fichero = JasperRunManager.runReportToPdf(fis, parameters,
							conexion);
				}

			} catch (JRException ex) {
				ex.printStackTrace();
				System.out.println(ex.toString());
			}

			if (conexion != null) {
				conexion.close();
			}

		} catch (SQLException e) {
			System.out.println("Error de conexión: " + e.getMessage());
			System.exit(4);
		}

		return fichero;

	}

}
