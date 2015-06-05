package interfacedao.maestros;

import java.util.List;

import modelo.maestros.Turno;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ITurnoDAO extends JpaRepository<Turno, String> {

	Turno findByDescripcion(String value);

	public List<Turno> findByDescripcionStartingWithAllIgnoreCase(String valor);

	public List<Turno> findByHoraEntradaStartingWithAllIgnoreCase(String valor);

	public List<Turno> findByHoraSalidaStartingWithAllIgnoreCase(String valor);

	public List<Turno> findByMinutosStartingWithAllIgnoreCase(String valor);

	public List<Turno> findByIdStartingWithAllIgnoreCase(String valor);

}