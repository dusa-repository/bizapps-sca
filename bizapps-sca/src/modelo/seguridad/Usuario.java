package modelo.seguridad;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import security.modelo.Grupo;

/**
 * The persistent class for the usuario database table.
 * 
 */
@Entity
@Table(name = "usuario")
public class Usuario implements Serializable {

	private static final long serialVersionUID = -7826333453902731478L;

	@Id
	@Column(name = "id_usuario", length = 12, unique = true, nullable = false)
	private String cedula;

	@Column(length = 50)
	private String email;

	@Column(length = 50)
	private String login;

	@Column(length = 256)
	private String password;

	@Lob
	private byte[] imagen;

	@Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean estado;
	
	@Column(name="ficha")
	private String ficha;

	@Column(name = "usuario_auditoria", length = 50)
	private String usuarioAuditoria;
	
	@Column(name="primer_apellido", length=100)
	private String primerApellido;

	@Column(name="primer_nombre", length=100)
	private String primerNombre;
	
	@Column(name="segundo_apellido", length=100)
	private String segundoApellido;

	@Column(name="segundo_nombre", length=100)
	private String segundoNombre;
	
	@Column(length=1)
	private String sexo;

	@Column(length=50)
	private String telefono;
	
	@Column(length=500)
	private String direccion;

	public Usuario() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Usuario(String cedula, String email, String login, String password,
			byte[] imagen, boolean estado, String ficha,
			String usuarioAuditoria, String primerApellido,
			String primerNombre, String segundoApellido, String segundoNombre,
			String sexo, String telefono, String direccion) {
		super();
		this.cedula = cedula;
		this.email = email;
		this.login = login;
		this.password = password;
		this.imagen = imagen;
		this.estado = estado;
		this.ficha = ficha;
		this.usuarioAuditoria = usuarioAuditoria;
		this.primerApellido = primerApellido;
		this.primerNombre = primerNombre;
		this.segundoApellido = segundoApellido;
		this.segundoNombre = segundoNombre;
		this.sexo = sexo;
		this.telefono = telefono;
		this.direccion = direccion;
	}

	public String getCedula() {
		return cedula;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public byte[] getImagen() {
		return imagen;
	}

	public void setImagen(byte[] imagen) {
		this.imagen = imagen;
	}

	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	public String getFicha() {
		return ficha;
	}

	public void setFicha(String ficha) {
		this.ficha = ficha;
	}

	public String getUsuarioAuditoria() {
		return usuarioAuditoria;
	}

	public void setUsuarioAuditoria(String usuarioAuditoria) {
		this.usuarioAuditoria = usuarioAuditoria;
	}

	public String getPrimerApellido() {
		return primerApellido;
	}

	public void setPrimerApellido(String primerApellido) {
		this.primerApellido = primerApellido;
	}

	public String getPrimerNombre() {
		return primerNombre;
	}

	public void setPrimerNombre(String primerNombre) {
		this.primerNombre = primerNombre;
	}

	public String getSegundoApellido() {
		return segundoApellido;
	}

	public void setSegundoApellido(String segundoApellido) {
		this.segundoApellido = segundoApellido;
	}

	public String getSegundoNombre() {
		return segundoNombre;
	}

	public void setSegundoNombre(String segundoNombre) {
		this.segundoNombre = segundoNombre;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

}