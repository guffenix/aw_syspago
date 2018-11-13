package ec.edu.espe.arqui.servicio;

import ec.edu.espe.arqui.entidades.Cliente;
import ec.edu.espe.arqui.entidades.Pago;
import ec.edu.espe.arqui.entidades.Tipomoneda;
import ec.edu.espe.arqui.entidades.Vehiculo;
import ec.edu.espe.arqui.facade.ClienteFacade;
import ec.edu.espe.arqui.facade.PagoFacade;
import ec.edu.espe.arqui.facade.VehiculoFacade;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author bigdata09
 */
@ManagedBean
@ViewScoped
public class PagoMulta implements Serializable {

    @EJB
    private VehiculoFacade vehiculoFacade;
    @EJB
    private ClienteFacade clienteFacade;
    @EJB
    private PagoFacade pagoFacade;

    private Tipomoneda tipoMoneda;
    private List<Tipomoneda> listaMoneda;

    private Cliente cliente;
    private Vehiculo vehiculo;
    private Pago pago;
    private String identificacionCliente;
    private String nameUsuario;
    private String idUsuario;

    public PagoMulta() {
    }

    @PostConstruct
    public void init() {
        listaMoneda = new ArrayList<>();
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest origRequest = (HttpServletRequest) context.getExternalContext().getRequest();
        getUsuario(origRequest);
    }

    public void buscarMulta() {
        this.vehiculo = vehiculoFacade.find(this.identificacionCliente);
        if (this.vehiculo != null) {
            this.cliente = this.clienteFacade.obtenerClienteByPlaca(this.vehiculo.getViPlaca());
            if (this.cliente != null) {
                this.pago = this.pagoFacade.obtenerPagoByCliente(this.cliente.getCliIdentificacion());
            }
        } else {
            this.cliente = new Cliente();
        }
    }

    private void getUsuario(HttpServletRequest request) {
        HttpSession session = request.getSession();
        nameUsuario = (String) session.getAttribute("usuario");
         idUsuario = (String) session.getAttribute("idUsuario");
    }

    public PagoMulta(Tipomoneda tipoMoneda, List<Tipomoneda> listaMoneda) {
        this.tipoMoneda = tipoMoneda;
        this.listaMoneda = listaMoneda;
    }

    public Tipomoneda getTipoMoneda() {
        return tipoMoneda;
    }

    public void setTipoMoneda(Tipomoneda tipoMoneda) {
        this.tipoMoneda = tipoMoneda;
    }

    public List<Tipomoneda> getListaMoneda() {
        return listaMoneda;
    }

    public void setListaMoneda(List<Tipomoneda> listaMoneda) {
        this.listaMoneda = listaMoneda;
    }

    public void onAddNew() {
        // Add one new car to the table:
        Tipomoneda nuevaMoneda = new Tipomoneda();
        listaMoneda.add(nuevaMoneda);
    }

    public void onRowEdit(RowEditEvent event) {
        System.out.println(((Tipomoneda) event.getObject()).getTmoId());
//        FacesMessage msg = new FacesMessage("Car Edited", ((Tipomoneda) event.getObject()).getTmoId());
//        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowCancel(RowEditEvent event) {
        System.out.println(((Tipomoneda) event.getObject()).getTmoId());
//        FacesMessage msg = new FacesMessage("Edit Cancelled", ((Tipomoneda) event.getObject()).getTmoId());
//        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public String getIdentificacionCliente() {
        return identificacionCliente;
    }

    public void setIdentificacionCliente(String identificacionCliente) {
        this.identificacionCliente = identificacionCliente;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

    public Pago getPago() {
        return pago;
    }

    public void setPago(Pago pago) {
        this.pago = pago;
    }

    public String getNameUsuario() {
        return nameUsuario;
    }

    public void setNameUsuario(String nameUsuario) {
        this.nameUsuario = nameUsuario;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

}
