package interfacedao.transacciones;


import java.util.Date;
import java.util.List;

import modelo.transacciones.PlanificacionSemanal;
import modelo.transacciones.RegistroAcceso;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IPlanificacionSemanalDAO extends JpaRepository<PlanificacionSemanal, Integer> {

	public List<PlanificacionSemanal> findByLoteUpload(int lote);

	public List<PlanificacionSemanal> findByFechaTurnoAndIdTurno(Date fecha,
			String turno);

	public List<PlanificacionSemanal> findBySemanaStartingWithAllIgnoreCase(
			String valor);

	public List<PlanificacionSemanal> findByFichaStartingWithAllIgnoreCase(
			String valor);

	public List<PlanificacionSemanal> findByNombreStartingWithAllIgnoreCase(
			String valor);

	public List<PlanificacionSemanal> findByIdTurnoStartingWithAllIgnoreCase(
			String valor);

	public List<PlanificacionSemanal> findByDiaSemanaStartingWithAllIgnoreCase(
			String valor);

	public List<PlanificacionSemanal> findByFechaTurnoStartingWithAllIgnoreCase(
			String valor);

	public List<PlanificacionSemanal> findByCuadrillaStartingWithAllIgnoreCase(
			String valor);

	
	
}