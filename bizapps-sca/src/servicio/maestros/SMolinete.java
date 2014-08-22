package servicio.maestros;


import interfacedao.maestros.IMolineteDAO;

import java.util.List;



import modelo.maestros.Molinete;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SMolinete")
public class SMolinete {

	@Autowired
	private IMolineteDAO molineteDAO;


	public void guardar(Molinete molinete) {
		molineteDAO.save(molinete);
	}

	public Molinete buscar(String id) {
		return molineteDAO.findOne(id);
	}
	
	public Molinete buscarPorDescripcion(String value) {
		return molineteDAO.findByDescripcion(value);
	}

	public void eliminar(Molinete molinete) {
		molineteDAO.delete(molinete);
	}

	public List<Molinete> buscarTodos() {
		return molineteDAO.findAll();
	}
	
	public List<Molinete> filtroCodigo(String valor) {
		return molineteDAO.findByIdStartingWithAllIgnoreCase(valor);
	}

	public List<Molinete> filtroDescripcion(String valor) {
		return molineteDAO.findByDescripcionStartingWithAllIgnoreCase(valor);
	}

}
