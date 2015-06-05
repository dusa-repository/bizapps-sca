package interfacedao.seguridad;

import java.util.List;

import modelo.seguridad.Usuario;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IUsuarioDAO extends JpaRepository<Usuario, Long> {

	Usuario findByLogin(String nombre);

	Usuario findByCedula(String value);

	List<Usuario> findByCedulaStartingWithAllIgnoreCase(String valor);

	List<Usuario> findByLoginStartingWithAllIgnoreCase(String valor);

	List<Usuario> findByEmailStartingWithAllIgnoreCase(String valor);

	List<Usuario> findByPrimerNombreStartingWithAllIgnoreCase(String valor);

	List<Usuario> findByPrimerApellidoStartingWithAllIgnoreCase(String valor);

	Usuario findByFicha(String ficha);

	Usuario findByCedulaAndEmail(String value, String value2);
}