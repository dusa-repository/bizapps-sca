package componentes;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;
import org.zkoss.zul.Hbox;

public abstract class Botonera extends Hbox {

	public Botonera() {
		super();
		Button btnGuardar = new Button();
		Button btnEliminar = new Button();
		Button btnLimpiar = new Button();
		Button btnSalir = new Button();
		this.appendChild(btnGuardar);
		this.appendChild(btnEliminar);
		this.appendChild(btnLimpiar);
		this.appendChild(btnSalir);
		
		btnGuardar
				.setSrc("/public/imagenes/botones/guardar.png");
		btnEliminar
				.setSrc("/public/imagenes/botones/eliminar.png");
		btnLimpiar
				.setSrc("/public/imagenes/botones/limpiar.png");
		btnSalir.setSrc("/public/imagenes/botones/salir.png");
		
		btnGuardar.setLabel("Guardar");
		btnEliminar.setLabel("Eliminar");
		btnLimpiar.setLabel("Limpiar");
		btnSalir.setLabel("Salir");
		
		btnGuardar.setTooltiptext("Guardar");
		btnEliminar.setTooltiptext("Eliminar");
		btnLimpiar.setTooltiptext("Limpiar");
		btnSalir.setTooltiptext("Salir");
		
		btnEliminar.setSclass("btn");
		btnLimpiar.setSclass("btn");
		btnSalir.setSclass("btn");
		btnGuardar.setSclass("btn");

		btnGuardar.addEventListener(Events.ON_CLICK,
				new EventListener<Event>() {
					@Override
					public void onEvent(Event arg0) throws Exception {
						guardar();
					}
				});
		btnEliminar.addEventListener(Events.ON_CLICK,
				new EventListener<Event>() {
					@Override
					public void onEvent(Event arg0) throws Exception {
						eliminar();
					}
				});
		btnLimpiar.addEventListener(Events.ON_CLICK,
				new EventListener<Event>() {
					@Override
					public void onEvent(Event arg0) throws Exception {
						limpiar();
					}
				});
		btnSalir.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				salir();
			}
		});
	}

	/**
	 * Metodo que guarda un registro nuevo si no a sido guardado con
	 * anterioridad, de ser así se modifica aquellos datos que el usuario cambie
	 */
	public abstract void guardar();

	/**
	 * Metodo que limpia todos los campos para darle la libertad al usuario de
	 * añadir un nuevo registro o seleccionar uno ya existente
	 */
	public abstract void limpiar();

	/**
	 * Metodo que permite cerrar el div que se genera al entrar a la vista
	 */
	public abstract void salir();

	/**
	 * Metodo que permite la eliminacion de un registro siempre y cuando
	 * no este asociado a otra entidad
	 */
	public abstract void eliminar();
}