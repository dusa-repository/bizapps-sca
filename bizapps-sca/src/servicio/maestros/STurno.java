package servicio.maestros;


import interfacedao.maestros.ITurnoDAO;

import java.util.List;



import modelo.maestros.Turno;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("STurno")
public class STurno {

	@Autowired
	private ITurnoDAO turnoDAO;


	public void guardar(Turno turno) {
		turnoDAO.save(turno);
	}

	public Turno buscar(long id) {
		return turnoDAO.findOne(id);
	}
	
	public Turno buscarPorDescripcion(String value) {
		return turnoDAO.findByDescripcion(value);
	}

	public void eliminar(Turno turno) {
		turnoDAO.delete(turno);
	}

	public List<Turno> buscarTodos() {
		return turnoDAO.findAll();
	}

	public List<Turno> filtroDescripcion(String valor) {
		return turnoDAO.findByDescripcionStartingWithAllIgnoreCase(valor);
	}

	public List<Turno> filtroHoraEntrada(String valor) {
		return turnoDAO.findByHoraEntradaStartingWithAllIgnoreCase(valor);
	}
	
	public List<Turno> filtroHoraSalida(String valor) {
		return turnoDAO.findByHoraSalidaStartingWithAllIgnoreCase(valor);
	}
	
	public List<Turno> filtroMinutos(String valor) {
		return turnoDAO.findByMinutosStartingWithAllIgnoreCase(valor);
	}
}