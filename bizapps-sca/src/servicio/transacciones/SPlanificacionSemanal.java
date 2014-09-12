package servicio.transacciones;


import interfacedao.transacciones.IPlanificacionSemanalDAO;

import java.util.List;

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
	
	
	public List<PlanificacionSemanal> buscarTodos() {
		return planificacionSemanalDAO.findAll();
	}
	
	public void eliminar(PlanificacionSemanal planificacion) {
		planificacionSemanalDAO.delete(planificacion);
	}


}