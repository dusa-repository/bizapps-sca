package interfacedao.transacciones;


import java.util.List;

import modelo.transacciones.RegistroAcceso;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IRegistroAccesoDAO extends JpaRepository<RegistroAcceso, String> {

	
	
}
