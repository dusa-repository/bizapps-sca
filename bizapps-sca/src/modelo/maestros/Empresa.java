package modelo.maestros;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * The persistent class for the empresa database table.
 * 
 */
@Entity
@Table(name="empresa")
public class Empresa implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name="id_empresa")
	private int id;

	@Column(name="direccion")
	private String direccion;

	@Column(name="fecha_auditoria")
	private Timestamp fechaAuditoria;

	@Column(name="hora_auditoria")
	private String horaAuditoria;

	@Column(name="id_empresa_auxiliar")
	private String idEmpresaAuxiliar;

	@Column(name="nombre")
	private String nombre;

	@Column(name="telefono1")
	private String telefono1;

	@Column(name="telefono2")
	private String telefono2;

	@Column(name="usuario")
	private String usuario;
	
	public Empresa() {
	}
	
	public Empresa(int idEmpresa, String direccion, Timestamp fechaAuditoria,
			String horaAuditoria, String idEmpresaAuxiliar, String nombre,
			String telefono1, String telefono2, String usuario) {
		super();
		this.id = idEmpresa;
		this.direccion = direccion;
		this.fechaAuditoria = fechaAuditoria;
		this.horaAuditoria = horaAuditoria;
		this.idEmpresaAuxiliar = idEmpresaAuxiliar;
		this.nombre = nombre;
		this.telefono1 = telefono1;
		this.telefono2 = telefono2;
		this.usuario = usuario;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int idEmpresa) {
		this.id = idEmpresa;
	}

	public String getDireccion() {
		return this.direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public Timestamp getFechaAuditoria() {
		return this.fechaAuditoria;
	}

	public void setFechaAuditoria(Timestamp fechaAuditoria) {
		this.fechaAuditoria = fechaAuditoria;
	}

	public String getHoraAuditoria() {
		return this.horaAuditoria;
	}

	public void setHoraAuditoria(String horaAuditoria) {
		this.horaAuditoria = horaAuditoria;
	}

	public String getIdEmpresaAuxiliar() {
		return this.idEmpresaAuxiliar;
	}

	public void setIdEmpresaAuxiliar(String idEmpresaAuxiliar) {
		this.idEmpresaAuxiliar = idEmpresaAuxiliar;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTelefono1() {
		return this.telefono1;
	}

	public void setTelefono1(String telefono1) {
		this.telefono1 = telefono1;
	}

	public String getTelefono2() {
		return this.telefono2;
	}

	public void setTelefono2(String telefono2) {
		this.telefono2 = telefono2;
	}
	
	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}


}