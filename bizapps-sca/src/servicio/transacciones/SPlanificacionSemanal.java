package servicio.transacciones;


import interfacedao.transacciones.IPlanificacionSemanalDAO;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SPlanificacionSemanal")
public class SPlanificacionSemanal {

	@Autowired
	private IPlanificacionSemanalDAO planificacionSemanalDAO;


}