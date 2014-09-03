package modelo.transacciones;

import java.io.Serializable;
import javax.persistence.*;

import modelo.maestros.Molinete;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the registro_acceso database table.
 * 
 */
@Entity
@Table(name="registro_acceso")
public class RegistroAcceso implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_registro_acceso", nullable = false, length = 50)
	private String id;

	// bi-directional many-to-one association to Area
	@ManyToOne
	@JoinColumn(name = "id_molinete")
	private Molinete molinete;


	@Column(name="fecha")
	private Timestamp fecha;
	
	@Temporal(TemporalType.TIME)
	@Column(name="hora")
	private Date hora;

	@Column(name="tipo_movimiento", length = 50)
	private String tipoMovimiento;
	
	@Column(name="ficha", length = 50)
	private String ficha;

	public RegistroAcceso() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RegistroAcceso(String id, Molinete molinete, Timestamp fecha,
			Date hora, String tipoMovimiento, String ficha) {
		super();
		this.id = id;
		this.molinete = molinete;
		this.fecha = fecha;
		this.hora = hora;
		this.tipoMovimiento = tipoMovimiento;
		this.ficha = ficha;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Molinete getMolinete() {
		return molinete;
	}

	public void setMolinete(Molinete molinete) {
		this.molinete = molinete;
	}

	public Timestamp getFecha() {
		return fecha;
	}

	public void setFecha(Timestamp fecha) {
		this.fecha = fecha;
	}

	public Date getHora() {
		return hora;
	}

	public void setHora(Date hora) {
		this.hora = hora;
	}

	public String getTipoMovimiento() {
		return tipoMovimiento;
	}

	public void setTipoMovimiento(String tipoMovimiento) {
		this.tipoMovimiento = tipoMovimiento;
	}

	public String getFicha() {
		return ficha;
	}

	public void setFicha(String ficha) {
		this.ficha = ficha;
	}
	
	
}