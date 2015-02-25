package interfacedao.transacciones;


import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import modelo.transacciones.PlanificacionSemanal;
import modelo.transacciones.RegistroAcceso;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IPlanificacionSemanalDAO extends JpaRepository<PlanificacionSemanal, Integer> {

	public List<PlanificacionSemanal> findByLoteUpload(int lote);

	/*public List<PlanificacionSemanal> findByFechaTurnoAndIdTurno(Date fecha,
			String turno);*/

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
	
	@Query("select distinct a.ficha, a.nombre from PlanificacionSemanal a")
	List<Object> findAllDistinctDataEmpleado();
	
	@Query("select distinct a.fichaJefe from PlanificacionSemanal a")
	List<Object> findAllDistinctDataJefe();
	
	
	@Query("select c from PlanificacionSemanal c WHERE c.fechaTurno between ?1 and ?2  and c.idTurno in ?3 and c.fichaJefe in ?4 and  c.idPermiso<>'VAC'  ")
	List<PlanificacionSemanal> obtenerCantidadNomina(Date fechaDesde, Date fechaHasta,List<String> turno,List<String> fichaJefe);

	
	
}