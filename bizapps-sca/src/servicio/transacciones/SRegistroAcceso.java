package servicio.transacciones;


import interfacedao.transacciones.IRegistroAccesoDAO;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SRegistroAcceso")
public class SRegistroAcceso {

	@Autowired
	private IRegistroAccesoDAO registroAccesoDAO;


}
