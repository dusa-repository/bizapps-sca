package controlador.seguridad;

import java.io.IOException;

import modelo.seguridad.Usuario;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Div;
import org.zkoss.zul.Label;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import componentes.Botonera;
import componentes.Mensaje;
import componentes.Validador;

import controlador.maestros.CGenerico;

public class CReinicioPassword extends CGenerico {

	@Wire
	private Textbox txtCorreoUsuario;
	@Wire
	private Textbox txtCedulaUsuario;
	@Wire
	private Label lblNombreUsuario;
	@Wire
	private Div botoneraReinicio;
	@Wire
	private Window wdwRecordar;
	PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	private static final long serialVersionUID = 6988038390488496987L;

	@Override
	public void inicializar() throws IOException {

		Botonera botonera = new Botonera() {

			@Override
			public void salir() {
				wdwRecordar.onClose();
			}

			@Override
			public void limpiar() {
				txtCorreoUsuario.setValue("");
				txtCedulaUsuario.setValue("");
			}

			@Override
			public void guardar() {
				String password = KeyGenerators.string().generateKey();
				String correo;
				if (validar()) {
					Usuario usuario = servicioUsuario
							.buscarPorCedula(txtCedulaUsuario.getValue());
					if (usuario != null) {
						if (usuario.getEmail() != null) {
							correo = usuario.getEmail();
						} else {
							correo = txtCorreoUsuario.getValue();
						}
						usuario.setPassword(password);
						servicioUsuario.guardar(usuario);
						enviarEmailNotificacion(
								correo,
								"Ha Solicitado Reiniciar su Password, sus nuevos datos para el inicio de sesion son: "
										+ " Usuario: "
										+ usuario.getLogin()
										+ "  " + " Password: " + password);
						limpiar();
						msj.mensajeInformacion(Mensaje.reinicioContrasenna);
						salir();
					} else {
						msj.mensajeError(Mensaje.cedulaNoExiste);
					}
				}
			}

			@Override
			public void eliminar() {
			}
		};
		botonera.getChildren().get(1).setVisible(false);
		botoneraReinicio.appendChild(botonera);
	}

	protected boolean validar() {
		if (txtCedulaUsuario.getText().compareTo("") == 0
				|| txtCorreoUsuario.getText().compareTo("") == 0) {
			msj.mensajeError(Mensaje.camposVacios);
			return false;
		} else
			return true;
	}

	/*
	 * Metodo que permite validar el correo electronico
	 */
	@Listen("onChange = #txtCorreoUsuario")
	public void validarCorreo() throws IOException {
		if (Validador.validarCorreo(txtCorreoUsuario.getValue()) == false) {
			msj.mensajeAlerta(Mensaje.correoInvalido);
		}
	}

	/* Valida la cedula */
	@Listen("onChange = #txtCedulaUsuario")
	public void validarCedula() {
		if (!Validador.validarNumero(txtCedulaUsuario.getValue())) {
			msj.mensajeAlerta(Mensaje.cedulaInvalida);
		}
	}
}
