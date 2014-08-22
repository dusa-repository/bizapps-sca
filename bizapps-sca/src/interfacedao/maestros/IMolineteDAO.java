package interfacedao.maestros;


import java.util.List;

import modelo.maestros.Molinete;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IMolineteDAO extends JpaRepository<Molinete, String> {

	Molinete findByDescripcion(String value);

	public List<Molinete> findByDescripcionStartingWithAllIgnoreCase(String valor);

	public List<Molinete> findByIdStartingWithAllIgnoreCase(String valor);



}
