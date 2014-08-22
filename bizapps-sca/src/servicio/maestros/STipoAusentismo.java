package servicio.maestros;


import interfacedao.maestros.ITipoAusentismoDAO;

import java.util.List;



import modelo.maestros.TipoAusentismo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("STipoAusentismo")
public class STipoAusentismo {

	@Autowired
	private ITipoAusentismoDAO ausentismoDAO;


	public void guardar(TipoAusentismo ausentismo) {
		ausentismoDAO.save(ausentismo);
	}

	public TipoAusentismo buscar(String id) {
		return ausentismoDAO.findOne(id);
	}
	
	public TipoAusentismo buscarPorDescripcion(String value) {
		return ausentismoDAO.findByDescripcion(value);
	}

	public void eliminar(TipoAusentismo ausentismo) {
		ausentismoDAO.delete(ausentismo);
	}

	public List<TipoAusentismo> buscarTodos() {
		return ausentismoDAO.findAll();
	}

	
	public List<TipoAusentismo> filtroCodigo(String valor) {
		return ausentismoDAO.findByIdStartingWithAllIgnoreCase(valor);
	}
	
	public List<TipoAusentismo> filtroDescripcion(String valor) {
		return ausentismoDAO.findByDescripcionStartingWithAllIgnoreCase(valor);
	}

}
