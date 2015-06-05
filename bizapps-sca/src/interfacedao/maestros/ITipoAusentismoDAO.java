package interfacedao.maestros;

import java.util.List;

import modelo.maestros.TipoAusentismo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ITipoAusentismoDAO extends
		JpaRepository<TipoAusentismo, String> {

	TipoAusentismo findByDescripcion(String value);

	public List<TipoAusentismo> findByDescripcionStartingWithAllIgnoreCase(
			String valor);

	public List<TipoAusentismo> findByIdStartingWithAllIgnoreCase(String valor);

}