package controlador.maestros;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import modelo.seguridad.Usuario;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Div;
import org.zkoss.zul.Include;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;

import servicio.maestros.SMolinete;
import servicio.maestros.STipoAusentismo;
import servicio.maestros.STurno;
import servicio.seguridad.SArbol;
import servicio.seguridad.SGrupo;
import servicio.seguridad.SUsuario;
import servicio.transacciones.SPlanificacionSemanal;
import servicio.transacciones.SRegistroAcceso;

import componentes.Mensaje;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public abstract class CGenerico extends SelectorComposer<Component> {

	private static final long serialVersionUID = -2264423023637489596L;
	@WireVariable("SGrupo")
	protected SGrupo servicioGrupo;
	@WireVariable("SUsuario")
	protected SUsuario servicioUsuario;
	@WireVariable("SArbol")
	protected SArbol servicioArbol;
	@WireVariable("STurno")
	protected STurno servicioTurno;
	@WireVariable("SMolinete")
	protected SMolinete servicioMolinete;
	@WireVariable("STipoAusentismo")
	protected STipoAusentismo servicioTipoAusentismo;
	@WireVariable("SRegistroAcceso")
	protected SRegistroAcceso servicioRegistroAcceso;
	@WireVariable("SPlanificacionSemanal")
	protected SPlanificacionSemanal servicioPlanificacionSemanal;
	

	protected static ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
			"/META-INF/ConfiguracionAplicacion.xml");
	public Mensaje msj = new Mensaje();
	public Tabbox tabBox;
	public Include contenido;
	public Tab tab;
	protected DateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
	// public static List<Tab> tabs = new ArrayList<Tab>();
	public List<Tab> tabs = new ArrayList<Tab>();
	protected DateFormat df = new SimpleDateFormat("HH:mm a");
	public final Calendar calendario = Calendar.getInstance();
	public String horaAuditoria = String.valueOf(calendario
			.get(Calendar.HOUR_OF_DAY))
			+ ":"
			+ String.valueOf(calendario.get(Calendar.MINUTE));
	public java.util.Date fecha = new Date();
	public Timestamp fechaHora = new Timestamp(fecha.getTime());

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		inicializar();
	}

	public abstract void inicializar() throws IOException;

	public void cerrarVentana(Div div, String id, List<Tab> tabs2) {
		div.setVisible(false);
		tabs = tabs2;
		System.out.println(tabs.size());
		for (int i = 0; i < tabs.size(); i++) {
			if (tabs.get(i).getLabel().equals(id)) {
				if (i == (tabs.size() - 1) && tabs.size() > 1) {
					tabs.get(i - 1).setSelected(true);
				}
				tabs.get(i).onClose();
				tabs.remove(i);
			}
		}
	}

	public String nombreUsuarioSesion() {
		Authentication sesion = SecurityContextHolder.getContext()
				.getAuthentication();
		return sesion.getName();
	}

	public Usuario usuarioSesion(String valor) {
		return servicioUsuario.buscarUsuarioPorNombre(valor);
	}

	/* Metodo que permite enviar un correo electronico a cualquier destinatario */
	public boolean enviarEmailNotificacion(String correo, String mensajes) {
		try {

			Properties props = new Properties();
			props.setProperty("mail.smtp.host", "smtp.gmail.com");
			props.setProperty("mail.smtp.starttls.enable", "true");
			props.setProperty("mail.smtp.port", "587");
			props.setProperty("mail.smtp.auth", "true");

			Session session = Session.getDefaultInstance(props);
			String asunto = "Notificacion de SITEG";
			String remitente = "siteg.ucla@gmail.com";
			String contrasena = "Equipo.2";
			String destino = correo;
			String mensaje = mensajes;

			String destinos[] = destino.split(",");

			MimeMessage message = new MimeMessage(session);

			message.setFrom(new InternetAddress(remitente));

			Address[] receptores = new Address[destinos.length];
			int j = 0;
			while (j < destinos.length) {
				receptores[j] = new InternetAddress(destinos[j]);
				j++;
			}

			message.addRecipients(Message.RecipientType.TO, receptores);
			message.setSubject(asunto);
			message.setText(mensaje);

			Transport t = session.getTransport("smtp");
			t.connect(remitente, contrasena);
			t.sendMessage(message,
					message.getRecipients(Message.RecipientType.TO));

			t.close();

			return true;
		}

		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static int calcularEdad(Date birthDate) {
		Calendar birth = new GregorianCalendar();
		Calendar today = new GregorianCalendar();
		int age = 0;
		int factor = 0;
		Date currentDate = new Date();
		birth.setTime(birthDate);
		today.setTime(currentDate);
		if (today.get(Calendar.DAY_OF_YEAR) < birth.get(Calendar.DAY_OF_YEAR)) {
			factor = -1;
		}
		age = (currentDate.getYear() - birthDate.getYear()) + factor;
		if (age == -1)
			age = 0;
		return age;
	}
	
	public String damePath() {
		return Executions.getCurrent().getContextPath() + "/";
	}

	public List<String> obtenerPropiedades() {
		List<String> arreglo = new ArrayList<String>();
		DriverManagerDataSource ds = (DriverManagerDataSource) applicationContext
				.getBean("dataSource");
		arreglo.add(ds.getUsername());
		arreglo.add(ds.getPassword());
		arreglo.add(ds.getUrl());
		return arreglo;
	}

	
}