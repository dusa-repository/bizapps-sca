package modelo.maestros;

import java.io.Serializable;
import javax.persistence.*;

import modelo.seguridad.Usuario;

import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the gerencia database table.
 * 
 */
@Entity
@Table(name="gerencia")
public class Gerencia implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name="id_gerencia")
	private int id;

	@Column(name="descripcion")
	private String descripcion;

	@Column(name="fecha_auditoria")
	private Timestamp fechaAuditoria;

	@Column(name="hora_auditoria")
	private String horaAuditoria;
	
	@Column(name="usuario")
	private String usuario;
	
	//bi-directional many-to-one association to UnidadOrganizativa
	@OneToMany(mappedBy="gerencia")
	private List<UnidadOrganizativa> unidadOrganizativas;

	public Gerencia() {
	}
	
	public Gerencia(int idGerencia, String descripcion,
			Timestamp fechaAuditoria, String horaAuditoria, String usuario) {
		super();
		this.id = idGerencia;
		this.descripcion = descripcion;
		this.fechaAuditoria = fechaAuditoria;
		this.horaAuditoria = horaAuditoria;
		this.usuario = usuario;
	}



	public int getId() {
		return this.id;
	}

	public void setId(int idGerencia) {
		this.id = idGerencia;
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

	public List<UnidadOrganizativa> getUnidadOrganizativas() {
		return this.unidadOrganizativas;
	}

	public void setUnidadOrganizativas(List<UnidadOrganizativa> unidadOrganizativas) {
		this.unidadOrganizativas = unidadOrganizativas;
	}

	public UnidadOrganizativa addUnidadOrganizativa(UnidadOrganizativa unidadOrganizativa) {
		getUnidadOrganizativas().add(unidadOrganizativa);
		unidadOrganizativa.setGerencia(this);

		return unidadOrganizativa;
	}

	public UnidadOrganizativa removeUnidadOrganizativa(UnidadOrganizativa unidadOrganizativa) {
		getUnidadOrganizativas().remove(unidadOrganizativa);
		unidadOrganizativa.setGerencia(null);

		return unidadOrganizativa;
	}
	
	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

}