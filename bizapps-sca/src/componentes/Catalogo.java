package componentes;

import java.util.List;

import modelo.seguridad.Usuario;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Separator;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

public abstract class Catalogo<Clase> extends Window {

	private static final long serialVersionUID = 1L;
	Listbox lsbCatalogo;

	public Catalogo(final Component cGenerico, String titulo,
			List<Clase> lista, String... campos) {
		super(titulo, "2", true);
		this.setId("cmpCatalogo");
		this.setStyle("background-header:#661313; background: #f4f2f2");
		setWidth("60%");
		crearLista(lista, campos);
		lsbCatalogo.addEventListener(Events.ON_SELECT,
				new EventListener<Event>() {

					@Override
					public void onEvent(Event arg0) throws Exception {
						Events.postEvent(cGenerico, new Event("onSeleccion"));
					}
				});
	}

	public void crearLista(List<Clase> lista, String[] campos) {
		Hbox hbxBusqueda = new Hbox();
		final Label lblBuscar = new Label();
		final Textbox txtBuscar = new Textbox();
		final Separator separador1 = new Separator();
		final Separator separador2 = new Separator();
		txtBuscar.setWidth("23em");
		txtBuscar.setPlaceholder("Introduzca el criterio de busqueda");
		final Combobox cmbBuscarPor = new Combobox();
		cmbBuscarPor.setReadonly(true);
		cmbBuscarPor.setPlaceholder("Seleccione el Campo");
		txtBuscar.addEventListener(Events.ON_CHANGING,
				new EventListener<InputEvent>() {
					@Override
					public void onEvent(InputEvent e) throws Exception {
						List<Clase> listaNueva = buscar(e.getValue(),
								cmbBuscarPor.getValue());
						lsbCatalogo.setModel(new ListModelList<Clase>(
								listaNueva));
					}
				});
		lsbCatalogo = new Listbox();
		lsbCatalogo.setMold("paging");
		lsbCatalogo.setPageSize(10);
		Listhead lhdEncabezado = new Listhead();
		for (int i = 0; i < campos.length; i++) {
			Listheader listHeader = new Listheader(campos[i]);
			listHeader.setHflex("min");
			lhdEncabezado.appendChild(listHeader);
		}
		lsbCatalogo.appendChild(lhdEncabezado);
		lhdEncabezado.setVisible(true);
		lsbCatalogo.setModel(new ListModelList<Clase>(lista));
		lsbCatalogo.setItemRenderer(new ListitemRenderer<Clase>() {

			@Override
			public void render(Listitem fila, Clase objeto, int arg2)
					throws Exception {
				fila.setValue(objeto);
				String[] registros = crearRegistros(objeto);
				for (int i = 0; i < registros.length; i++) {
					Listcell celda = new Listcell(registros[i]);
					celda.setParent(fila);
				}
			}
		});
		lsbCatalogo.setWidth("100%");
		lsbCatalogo.setSpan(true);
		this.appendChild(separador1);
		this.appendChild(hbxBusqueda);
		lblBuscar.setValue("   Buscar Por :  ");
		lblBuscar.setSclass("etiqueta");
		hbxBusqueda.appendChild(lblBuscar);
		cmbBuscarPor.setModel(new ListModelList<String>(campos));
		hbxBusqueda.appendChild(cmbBuscarPor);
		hbxBusqueda.appendChild(txtBuscar);
		this.appendChild(separador2);
		this.appendChild(lsbCatalogo);
	}

	/**
	 * Metodo que permite llamar un servicio dependiendo el controlador que
	 * busque, es decir que haga un filtro dentro del catalogo, ayudando asi al
	 * usuario a encontrar el registro buscado con mayor facilidad
	 */
	protected abstract List<Clase> buscar(String valor, String combo);

	/**
	 * Metodo que permite por cada controlador indicar cuales son los registros
	 * que quiere mostrar en su catalogo, formando una matriz de String
	 */
	protected abstract String[] crearRegistros(Clase objeto);

	public Clase objetoSeleccionadoDelCatalogo() {
		return lsbCatalogo.getSelectedItem().getValue();
	}

	public Listbox getListbox() {
		return lsbCatalogo;
	}

	public void actualizarLista(List<Clase> listaGeneral) {
		lsbCatalogo.setModel(new ListModelList<Clase>(listaGeneral));
		lsbCatalogo.setMultiple(false);
		lsbCatalogo.setCheckmark(false);
		lsbCatalogo.setMultiple(true);
		lsbCatalogo.setCheckmark(true);
	}
}