package servicio.transacciones;


import interfacedao.transacciones.IPlanificacionSemanalDAO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import modelo.maestros.Empleado;
import modelo.transacciones.PlanificacionSemanal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SPlanificacionSemanal")
public class SPlanificacionSemanal {

	@Autowired
	private IPlanificacionSemanalDAO planificacionSemanalDAO;
	
	
	public void guardar(PlanificacionSemanal planificacion) {
		planificacionSemanalDAO.save(planificacion);
	}
	
	/* Servicio que permite buscar los registros de una planificacion de acuerdo al lote_upload */
	public List<PlanificacionSemanal> buscarPorLoteUpload(int lote) {
		List<PlanificacionSemanal> planificaciones;
		planificaciones = planificacionSemanalDAO.findByLoteUpload(lote);
		return planificaciones;
	}
	
	
	/* Servicio que permite buscar los registros de una planificacion de acuerdo a la fecha y al id_turno*/
	public List<PlanificacionSemanal> buscarPorFechaYTurno(Date fechaDesde,Date fechaHasta, List<String> turno,List<String> fichaJefe) {
		List<PlanificacionSemanal> planificaciones;
		//planificaciones = planificacionSemanalDAO.findByFechaTurnoAndIdTurno(fecha,turno);
		planificaciones= planificacionSemanalDAO.obtenerCantidadNomina(fechaDesde,fechaHasta,turno,fichaJefe);
		return planificaciones;
	}
	
	
public List<PlanificacionSemanal> buscarTodos() {
		
		List<Object> cdataList=planificacionSemanalDAO.findAllDistinctDataEmpleado();
		List<PlanificacionSemanal> listaPlanificacionAuxiliar= new ArrayList<PlanificacionSemanal>();
		for (Object cdata:cdataList) {
		   Object[] obj= (Object[]) cdata;
		     String ficha = (String)obj[0];
		     String nombre = (String)obj[1];
		     PlanificacionSemanal planificacionAuxiliar= new PlanificacionSemanal();
		     planificacionAuxiliar.setFicha(ficha);
		     planificacionAuxiliar.setNombre(nombre);
		     listaPlanificacionAuxiliar.add(planificacionAuxiliar);
		  }

		return listaPlanificacionAuxiliar ;
	}

	public List<PlanificacionSemanal> buscarJefes() {
	
	List<Object> cdataList=planificacionSemanalDAO.findAllDistinctDataJefe();
	List<PlanificacionSemanal> listaPlanificacionAuxiliar= new ArrayList<PlanificacionSemanal>();
	for (Object cdata:cdataList) {
	   Object obj= (Object) cdata;
	     String ficha = (String)obj;
	     String nombre = "";
	     PlanificacionSemanal planificacionAuxiliar= new PlanificacionSemanal();
	     planificacionAuxiliar.setFicha(ficha);
	     planificacionAuxiliar.setNombre(nombre);
	     listaPlanificacionAuxiliar.add(planificacionAuxiliar);
	  }

	return listaPlanificacionAuxiliar ;
}
	
	public void eliminar(PlanificacionSemanal planificacion) {
		planificacionSemanalDAO.delete(planificacion);
	}
	
	/*
	 * Servicio que permite filtrar la planificacion de acuerdo a la semana
	 */
	public List<PlanificacionSemanal> filtroSemana(String valor) {
		return planificacionSemanalDAO.findBySemanaStartingWithAllIgnoreCase(valor);
	}
	
	/*
	 * Servicio que permite filtrar la planificacion de acuerdo al dia de la semana
	 */
	public List<PlanificacionSemanal> filtroDiaSemana(String valor) {
		return planificacionSemanalDAO.findByDiaSemanaStartingWithAllIgnoreCase(valor);
	}
	
	/*
	 * Servicio que permite filtrar la planificacion de acuerdo a la ficha del empleado
	 */
	public List<PlanificacionSemanal> filtroFicha(String valor) {
		return planificacionSemanalDAO.findByFichaStartingWithAllIgnoreCase(valor);
	}
	
	/*
	 * Servicio que permite filtrar la planificacion de acuerdo al nombre del empleado
	 */
	public List<PlanificacionSemanal> filtroNombre(String valor) {
		return planificacionSemanalDAO.findByNombreStartingWithAllIgnoreCase(valor);
	}
	
	/*
	 * Servicio que permite filtrar la planificacion de acuerdo al turno
	 */
	public List<PlanificacionSemanal> filtroTurno(String valor) {
		return planificacionSemanalDAO.findByIdTurnoStartingWithAllIgnoreCase(valor);
	}
	
	/*
	 * Servicio que permite filtrar la planificacion de acuerdo a la fecha del turno
	 */
	public List<PlanificacionSemanal> filtroFechaTurno(String valor) {
		return planificacionSemanalDAO.findByFechaTurnoStartingWithAllIgnoreCase(valor);
	}
	
	
	/*
	 * Servicio que permite filtrar la planificacion de acuerdo a la cuadrilla
	 */
	public List<PlanificacionSemanal> filtroCuadrilla(String valor) {
		return planificacionSemanalDAO.findByCuadrillaStartingWithAllIgnoreCase(valor);
	}
	
	
	
	
	
	


}