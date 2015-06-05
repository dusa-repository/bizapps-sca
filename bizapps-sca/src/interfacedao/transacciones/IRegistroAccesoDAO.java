package interfacedao.transacciones;


import modelo.transacciones.RegistroAcceso;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IRegistroAccesoDAO extends JpaRepository<RegistroAcceso, String> {

	
	
}
