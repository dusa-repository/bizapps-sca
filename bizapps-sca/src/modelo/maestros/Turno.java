package modelo.maestros;

import java.io.Serializable;
import javax.persistence.*;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the turno database table.
 * 
 */
@Entity
@Table(name="turno")
public class Turno implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name="id_turno")
	private long id;

	@Column(name="descripcion")
	private String descripcion;
	
	@Temporal(TemporalType.TIME)
	@Column(name = "hora_entrada")
	private Date horaEntrada;
	
	@Temporal(TemporalType.TIME)
	@Column(name = "hora_salida")
	private Date horaSalida;
	
	@Column(name="minutos")
	private int minutos;

	@Column(name="fecha_auditoria")
	private Timestamp fechaAuditoria;

	@Column(name="hora_auditoria")
	private String horaAuditoria;
	
	@Column(name="usuario")
	private String usuario;

	public Turno() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Turno(long id, String descripcion, Date horaEntrada, Date horaSalida,
			int minutos, Timestamp fechaAuditoria, String horaAuditoria,
			String usuario) {
		super();
		this.id = id;
		this.descripcion = descripcion;
		this.horaEntrada = horaEntrada;
		this.horaSalida = horaSalida;
		this.minutos = minutos;
		this.fechaAuditoria = fechaAuditoria;
		this.horaAuditoria = horaAuditoria;
		this.usuario = usuario;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Date getHoraEntrada() {
		return horaEntrada;
	}

	public void setHoraEntrada(Date horaEntrada) {
		this.horaEntrada = horaEntrada;
	}

	public Date getHoraSalida() {
		return horaSalida;
	}

	public void setHoraSalida(Date horaSalida) {
		this.horaSalida = horaSalida;
	}

	public int getMinutos() {
		return minutos;
	}

	public void setMinutos(int minutos) {
		this.minutos = minutos;
	}

	public Timestamp getFechaAuditoria() {
		return fechaAuditoria;
	}

	public void setFechaAuditoria(Timestamp fechaAuditoria) {
		this.fechaAuditoria = fechaAuditoria;
	}

	public String getHoraAuditoria() {
		return horaAuditoria;
	}

	public void setHoraAuditoria(String horaAuditoria) {
		this.horaAuditoria = horaAuditoria;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
}