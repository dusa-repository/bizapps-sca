package modelo.transacciones;

import java.io.Serializable;
import javax.persistence.*;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the planificacion_semanal database table.
 * 
 */
@Entity
@Table(name="planificacion_semanal")
public class PlanificacionSemanal implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_row", nullable = false, length = 11)
	private int id;

	@Column(name="lote_upload", length = 50)
	private String loteUpload;
	
	@Column(name="ficha", nullable = false, length = 50)
	private String ficha;
	
	@Column(name="nombre", length = 500)
	private String nombre;
	
	@Temporal(TemporalType.DATE)
	@Column(name="fecha_turno", nullable = false)
	private Date fechaTurno;
	
	@Column(name="semana", length = 11)
	private int semana;
	
	@Column(name="id_turno", nullable = false, length = 50)
	private String idTurno;
	
	@Column(name="dia_semana", length = 50)
	private String diaSemana;
	
	@Column(name="tipo_turno", length = 50)
	private String tipoTurno;
	
	@Column(name="cuadrilla", length = 50)
	private String cuadrilla;
	
	@Column(name="id_permiso", length = 50)
	private String idPermiso;
	
	@Column(name="ficha_jefe", length = 50)
	private String fichaJefe;
	
	@Column(name="id_usuario", length = 50)
	private String idUsuario;
	
	@Column(name="fecha_registro")
	private Timestamp fechaRegistro;

	@Column(name="hora_registro")
	private String horaRegistro;
	
	@Column(name="ficha_usuario",  nullable = false, length = 50)
	private String fichaUsuario;

	public PlanificacionSemanal() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PlanificacionSemanal(int id, String loteUpload, String ficha,
			String nombre, Date fechaTurno, int semana, String idTurno,
			String diaSemana, String tipoTurno, String cuadrilla,
			String idPermiso, String fichaJefe, String idUsuario,
			Timestamp fechaRegistro, String horaRegistro, String fichaUsuario) {
		super();
		this.id = id;
		this.loteUpload = loteUpload;
		this.ficha = ficha;
		this.nombre = nombre;
		this.fechaTurno = fechaTurno;
		this.semana = semana;
		this.idTurno = idTurno;
		this.diaSemana = diaSemana;
		this.tipoTurno = tipoTurno;
		this.cuadrilla = cuadrilla;
		this.idPermiso = idPermiso;
		this.fichaJefe = fichaJefe;
		this.idUsuario = idUsuario;
		this.fechaRegistro = fechaRegistro;
		this.horaRegistro = horaRegistro;
		this.fichaUsuario = fichaUsuario;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLoteUpload() {
		return loteUpload;
	}

	public void setLoteUpload(String loteUpload) {
		this.loteUpload = loteUpload;
	}

	public String getFicha() {
		return ficha;
	}

	public void setFicha(String ficha) {
		this.ficha = ficha;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Date getFechaTurno() {
		return fechaTurno;
	}

	public void setFechaTurno(Date fechaTurno) {
		this.fechaTurno = fechaTurno;
	}

	public int getSemana() {
		return semana;
	}

	public void setSemana(int semana) {
		this.semana = semana;
	}

	public String getIdTurno() {
		return idTurno;
	}

	public void setIdTurno(String idTurno) {
		this.idTurno = idTurno;
	}

	public String getDiaSemana() {
		return diaSemana;
	}

	public void setDiaSemana(String diaSemana) {
		this.diaSemana = diaSemana;
	}

	public String getTipoTurno() {
		return tipoTurno;
	}

	public void setTipoTurno(String tipoTurno) {
		this.tipoTurno = tipoTurno;
	}

	public String getCuadrilla() {
		return cuadrilla;
	}

	public void setCuadrilla(String cuadrilla) {
		this.cuadrilla = cuadrilla;
	}

	public String getIdPermiso() {
		return idPermiso;
	}

	public void setIdPermiso(String idPermiso) {
		this.idPermiso = idPermiso;
	}

	public String getFichaJefe() {
		return fichaJefe;
	}

	public void setFichaJefe(String fichaJefe) {
		this.fichaJefe = fichaJefe;
	}

	public String getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Timestamp getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Timestamp fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public String getHoraRegistro() {
		return horaRegistro;
	}

	public void setHoraRegistro(String horaRegistro) {
		this.horaRegistro = horaRegistro;
	}

	public String getFichaUsuario() {
		return fichaUsuario;
	}

	public void setFichaUsuario(String fichaUsuario) {
		this.fichaUsuario = fichaUsuario;
	}
		
}