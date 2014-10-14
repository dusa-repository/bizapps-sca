package modelo.maestros;

import java.io.Serializable;
import javax.persistence.*;

import modelo.seguridad.Usuario;

import java.sql.Timestamp;


/**
 * The persistent class for the unidad_organizativa database table.
 * 
 */
@Entity
@Table(name="unidad_organizativa")
public class UnidadOrganizativa implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name="id_unidad_organizativa")
	private int id;

	@Column(name="descripcion")
	private String descripcion;

	@Column(name="fecha_auditoria")
	private Timestamp fechaAuditoria;

	@Column(name="hora_auditoria")
	private String horaAuditoria;

	@Column(name="id_empresa_auxiliar")
	private String idEmpresaAuxiliar;

	@Column(name="id_unidad_organizativa_auxiliar")
	private String idUnidadOrganizativaAuxiliar;

	@Column(name="nivel")
	private int nivel;

	@Column(name="sub_nivel")
	private int subNivel;
	
	@Column(name="usuario")
	private String usuario;
	
	//bi-directional many-to-one association to Gerencia
	@ManyToOne
	@JoinColumn(name="id_gerencia")
	private Gerencia gerencia;

	public UnidadOrganizativa() {
	}

	public UnidadOrganizativa(int idUnidadOrganizativa, String descripcion,
			Timestamp fechaAuditoria, String horaAuditoria,
			String idEmpresaAuxiliar, String idUnidadOrganizativaAuxiliar,
			int nivel, int subNivel, String usuario, Gerencia gerencia) {
		super();
		this.id = idUnidadOrganizativa;
		this.descripcion = descripcion;
		this.fechaAuditoria = fechaAuditoria;
		this.horaAuditoria = horaAuditoria;
		this.idEmpresaAuxiliar = idEmpresaAuxiliar;
		this.idUnidadOrganizativaAuxiliar = idUnidadOrganizativaAuxiliar;
		this.nivel = nivel;
		this.subNivel = subNivel;
		this.usuario = usuario;
		this.gerencia = gerencia;
	}



	public int getId() {
		return this.id;
	}

	public void setId(int idUnidadOrganizativa) {
		this.id = idUnidadOrganizativa;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
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

	public String getIdUnidadOrganizativaAuxiliar() {
		return this.idUnidadOrganizativaAuxiliar;
	}

	public void setIdUnidadOrganizativaAuxiliar(String idUnidadOrganizativaAuxiliar) {
		this.idUnidadOrganizativaAuxiliar = idUnidadOrganizativaAuxiliar;
	}

	public int getNivel() {
		return this.nivel;
	}

	public void setNivel(int nivel) {
		this.nivel = nivel;
	}

	public int getSubNivel() {
		return this.subNivel;
	}

	public void setSubNivel(int subNivel) {
		this.subNivel = subNivel;
	}

	public Gerencia getGerencia() {
		return this.gerencia;
	}

	public void setGerencia(Gerencia gerencia) {
		this.gerencia = gerencia;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
	

}