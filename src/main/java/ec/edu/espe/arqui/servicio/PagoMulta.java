package ec.edu.espe.arqui.servicio;

import ec.edu.espe.arqui.cls.PFijo;
import ec.edu.espe.arqui.entidades.Cliente;
import ec.edu.espe.arqui.entidades.DetalleMoneda;
import ec.edu.espe.arqui.entidades.Pago;
import ec.edu.espe.arqui.entidades.Tipomoneda;
import ec.edu.espe.arqui.entidades.Vehiculo;
import ec.edu.espe.arqui.facade.ClienteFacade;
import ec.edu.espe.arqui.facade.DetalleMonedaFacade;
import ec.edu.espe.arqui.facade.PagoFacade;
import ec.edu.espe.arqui.facade.TipomonedaFacade;
import ec.edu.espe.arqui.facade.VehiculoFacade;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
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
    @EJB
    private DetalleMonedaFacade detalleMonedaFacade;
    @EJB
    private TipomonedaFacade tipoMonedaFacade;

    private DetalleMoneda detalleMoneda;
    private List<DetalleMoneda> listaDetalleMoneda;
    private Tipomoneda tipoMoneda;
    private List<Tipomoneda> listaMoneda;
    

    private Cliente cliente;
    private Vehiculo vehiculo;
    private String identificacionCliente;
    private String nameUsuario;
    private String idUsuario;
    List<PFijo> listaMulta = new ArrayList();
    // caja
    private Pago nuevoPago;
    private BigDecimal contadorCentavo;
    private BigDecimal contadorCincoCentavos;
    private BigDecimal contadorDiezCentavos;
    private BigDecimal contadorVcincoCentavos;
    private BigDecimal contadorCincuentaCentavos;
    private BigDecimal contadorDolarCentavos;

    private BigDecimal contadorUnDolar;
    private BigDecimal contadorCincoDolares;
    private BigDecimal contadorDiezDolares;
    private BigDecimal contadorVeinteDolares;

    private BigDecimal valorReceptado;
    private BigDecimal cambio;

    public PagoMulta() {
        valorReceptado = BigDecimal.ZERO;
        cambio = BigDecimal.ZERO;
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest origRequest = (HttpServletRequest) context.getExternalContext().getRequest();
        getUsuario(origRequest);
    }

//    @PostConstruct
    public void init() {
        listaMoneda = new ArrayList<>();
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest origRequest = (HttpServletRequest) context.getExternalContext().getRequest();
        getUsuario(origRequest);
    }

    public void buscarMulta() {
        try {
            this.vehiculo = vehiculoFacade.find(this.identificacionCliente);
        if (this.vehiculo != null) {
            this.cliente = this.clienteFacade.obtenerClienteByPlaca(this.vehiculo.getViPlaca());
            if (this.cliente != null) {
                this.nuevoPago = this.pagoFacade.obtenerPagoByCliente(this.cliente.getCliIdentificacion());
                if(this.nuevoPago != null){
                    mostrarMensaje("Cliente encontrado exitosamente!", true);
                }else{
                    this.nuevoPago = new Pago();
                    mostrarMensaje("El cliente no tiene deudas por Multas de Tránsito!", true);
                }
                
            }
        } else {
            mostrarMensaje("No se encuentra un vehículo con ese identificador", true);
            this.cliente = new Cliente();
        }
        } catch (Exception e) {
             mostrarMensaje("No se encuentra un vehículo con ese identificador2", true);
        }
        
    }

    public void actualizarEstado() {
//        this.listaDetalleMoneda = null;
        
        BigDecimal contadorCentavoAux = ((contadorCentavo != null) ? contadorCentavo : BigDecimal.ZERO).multiply(new BigDecimal("0.01"));
        BigDecimal contadorCincoCentavosAux = ((contadorCincoCentavos != null) ? contadorCincoCentavos : BigDecimal.ZERO).multiply(new BigDecimal("0.05"));;
        BigDecimal contadorDiezCentavosAux = ((contadorDiezCentavos != null) ? contadorDiezCentavos : BigDecimal.ZERO).multiply(new BigDecimal("0.10"));;
        BigDecimal contadorVcincoCentavosAux = ((contadorVcincoCentavos != null) ? contadorVcincoCentavos : BigDecimal.ZERO).multiply(new BigDecimal("0.25"));;
        BigDecimal contadorCincuentaCentavosAux = ((contadorCincuentaCentavos != null) ? contadorCincuentaCentavos : BigDecimal.ZERO).multiply(new BigDecimal("0.50"));;
        BigDecimal contadorDolarCentavosAux = ((contadorDolarCentavos != null) ? contadorDolarCentavos : BigDecimal.ZERO).multiply(new BigDecimal("1"));;
        BigDecimal contadorUnDolarAux = ((contadorUnDolar != null) ? contadorUnDolar : BigDecimal.ZERO).multiply(new BigDecimal("1"));;
        BigDecimal contadorCincoDolaresAux = ((contadorCincoDolares != null) ? contadorCincoDolares : BigDecimal.ZERO).multiply(new BigDecimal("5"));;
        BigDecimal contadorDiezDolaresAux = ((contadorDiezDolares != null) ? contadorDiezDolares : BigDecimal.ZERO).multiply(new BigDecimal("10"));;
        BigDecimal contadorVeinteDolaresAux = ((contadorVeinteDolares != null) ? contadorVeinteDolares : BigDecimal.ZERO).multiply(new BigDecimal("20"));;
        valorReceptado = contadorCentavoAux.add(contadorCincoCentavosAux).add(contadorDiezCentavosAux).add(contadorVcincoCentavosAux).add(contadorCincuentaCentavosAux).add(contadorDolarCentavosAux).add(contadorUnDolarAux).add(contadorCincoDolaresAux).add(contadorDiezDolaresAux).add(contadorVeinteDolaresAux).setScale(2, RoundingMode.HALF_UP);
        cambio = valorReceptado.subtract(nuevoPago.getPagValor());

//        
//            detalleMoneda = new DetalleMoneda();
//            tipoMoneda = tipoMonedaFacade.find(new BigDecimal(33));
//            detalleMoneda.setTipomoneda(tipoMoneda);
//            detalleMoneda.setDmonCantidad(contadorCentavo.toBigInteger());
//            detalleMoneda.setDmonTotal(contadorCentavoAux.doubleValue());
//            listaDetalleMoneda.add(detalleMoneda);
//        
//            detalleMoneda = new DetalleMoneda();
//            tipoMoneda = tipoMonedaFacade.find(new BigDecimal(34));
//            detalleMoneda.setTipomoneda(tipoMoneda);
//            detalleMoneda.setDmonCantidad(contadorCincoCentavos.toBigInteger());
//            detalleMoneda.setDmonTotal(contadorCincoCentavosAux.doubleValue());
//            listaDetalleMoneda.add(detalleMoneda);
//        
//            detalleMoneda = new DetalleMoneda();
//            tipoMoneda = tipoMonedaFacade.find(new BigDecimal(35));
//            detalleMoneda.setTipomoneda(tipoMoneda);
//            detalleMoneda.setDmonCantidad(contadorDiezCentavos.toBigInteger());
//            detalleMoneda.setDmonTotal(contadorDiezCentavosAux.doubleValue());
//            listaDetalleMoneda.add(detalleMoneda);
//        
//            
//            detalleMoneda = new DetalleMoneda();
//            tipoMoneda = tipoMonedaFacade.find(new BigDecimal(36));
//            detalleMoneda.setTipomoneda(tipoMoneda);
//            detalleMoneda.setDmonCantidad(contadorVcincoCentavos.toBigInteger());
//            detalleMoneda.setDmonTotal(contadorVcincoCentavosAux.doubleValue());
//            listaDetalleMoneda.add(detalleMoneda);
//        
//            detalleMoneda = new DetalleMoneda();
//            tipoMoneda = tipoMonedaFacade.find(new BigDecimal(37));
//            detalleMoneda.setTipomoneda(tipoMoneda);
//            detalleMoneda.setDmonCantidad(contadorCincuentaCentavos.toBigInteger());
//            detalleMoneda.setDmonTotal(contadorCincuentaCentavosAux.doubleValue());
//            listaDetalleMoneda.add(detalleMoneda);
//        
//            detalleMoneda = new DetalleMoneda();
//            tipoMoneda = tipoMonedaFacade.find(new BigDecimal(38));
//            detalleMoneda.setTipomoneda(tipoMoneda);
//            detalleMoneda.setDmonCantidad(contadorDolarCentavos.toBigInteger());
//            detalleMoneda.setDmonTotal(contadorDolarCentavosAux.doubleValue());
//            listaDetalleMoneda.add(detalleMoneda);
//        
//            detalleMoneda = new DetalleMoneda();
//            tipoMoneda = tipoMonedaFacade.find(new BigDecimal(29));
//            detalleMoneda.setTipomoneda(tipoMoneda);
//            detalleMoneda.setDmonCantidad(contadorUnDolar.toBigInteger());
//            detalleMoneda.setDmonTotal(contadorUnDolarAux.doubleValue());
//            listaDetalleMoneda.add(detalleMoneda);
//        
//            detalleMoneda = new DetalleMoneda();
//            tipoMoneda = tipoMonedaFacade.find(new BigDecimal(30));
//            detalleMoneda.setTipomoneda(tipoMoneda);
//            detalleMoneda.setDmonCantidad(contadorCincoDolares.toBigInteger());
//            detalleMoneda.setDmonTotal(contadorCincoDolaresAux.doubleValue());
//            listaDetalleMoneda.add(detalleMoneda);
//        
//            detalleMoneda = new DetalleMoneda();
//            tipoMoneda = tipoMonedaFacade.find(new BigDecimal(31));
//            detalleMoneda.setTipomoneda(tipoMoneda);
//            detalleMoneda.setDmonCantidad(contadorDiezDolares.toBigInteger());
//            detalleMoneda.setDmonTotal(contadorDiezDolaresAux.doubleValue());
//            listaDetalleMoneda.add(detalleMoneda);
//        
//            detalleMoneda = new DetalleMoneda();
//            tipoMoneda = tipoMonedaFacade.find(new BigDecimal(32));
//            detalleMoneda.setTipomoneda(tipoMoneda);
//            detalleMoneda.setDmonCantidad(contadorVeinteDolares.toBigInteger());
//            detalleMoneda.setDmonTotal(contadorVeinteDolaresAux.doubleValue());
//            listaDetalleMoneda.add(detalleMoneda);
        
    }
    
    public void guardarDetalle(){
        
    } 

    public void registrarPago() {
        if (cambio.compareTo(BigDecimal.ZERO) >= 0) {
            nuevoPago.setPagEstado(new BigInteger("2"));
            pagoFacade.actualizarPago(nuevoPago);
            if(listaDetalleMoneda != null){
                for (DetalleMoneda detalleMoney : listaDetalleMoneda) {
                    detalleMoney.setPago(nuevoPago);
                    detalleMonedaFacade.create(detalleMoney);
                }                
            }
            
            mostrarMensaje("Pago realizado correctamente", true);
            cliente = null;
            identificacionCliente = null;
            //contraPartidaIngresada = null;
            nuevoPago = null;
            valorReceptado = null;
            cambio = null;

            contadorCentavo = null;
            contadorCincoCentavos = null;
            contadorDiezCentavos = null;
            contadorVcincoCentavos = null;
            contadorCincuentaCentavos = null;
            contadorDolarCentavos = null;
            contadorUnDolar = null;
            contadorCincoDolares = null;
            contadorDiezDolares = null;
            contadorVeinteDolares = null;
            valorReceptado = null;
            cambio = null;
        } else {
            mostrarMensaje("Ingrese un número mayor o igual a su deuda", false);
            System.out.println("debe ingresar un numero mayor");
        }

    }
    
    public List<PFijo> reporteTMulta(){
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("ec.edu.espe.arqui_aw_syspago_war_1.0PU");
        EntityManager em1 = factory.createEntityManager();
        try {
            Query a = em1.createNativeQuery(
                    "SELECT cl.cli_identificacion,cl.cli_tipo, cl.cli_nombre, cl.cli_direccion, pg.pag_valor, pg.pag_estado, pg.pag_fecha FROM cliente cl, pago pg, servicio sv WHERE cl.cli_identificacion=pg.cli_identificacion AND pg.ser_id=sv.ser_id AND sv.ser_id=25");
            List<Object[]> listado = a.getResultList();
            for (Object[] objects : listado) {
                PFijo pfijo =new PFijo();
                pfijo.setIdent((String)objects[0]);
                pfijo.setTipo((String)objects[1]);
                pfijo.setNombre((String)objects[2]);
                pfijo.setDir((String)objects[3]);
                pfijo.setPago((String)objects[4]);
                pfijo.setEstado((String)objects[5]);
                pfijo.setFecha((String)objects[6]);
                listaMulta.add(pfijo);
            }            
            return listaMulta;
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return null;
    }
    private void mostrarMensaje(String _mensaje, boolean _esInformativo) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(_esInformativo ? FacesMessage.SEVERITY_INFO : FacesMessage.SEVERITY_ERROR, _mensaje, null));
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

    public VehiculoFacade getVehiculoFacade() {
        return vehiculoFacade;
    }

    public void setVehiculoFacade(VehiculoFacade vehiculoFacade) {
        this.vehiculoFacade = vehiculoFacade;
    }

    public ClienteFacade getClienteFacade() {
        return clienteFacade;
    }

    public void setClienteFacade(ClienteFacade clienteFacade) {
        this.clienteFacade = clienteFacade;
    }

    public PagoFacade getPagoFacade() {
        return pagoFacade;
    }

    public void setPagoFacade(PagoFacade pagoFacade) {
        this.pagoFacade = pagoFacade;
    }

    public BigDecimal getContadorCentavo() {
        return contadorCentavo;
    }

    public void setContadorCentavo(BigDecimal contadorCentavo) {
        this.contadorCentavo = contadorCentavo;
    }

    public BigDecimal getContadorCincoCentavos() {
        return contadorCincoCentavos;
    }

    public void setContadorCincoCentavos(BigDecimal contadorCincoCentavos) {
        this.contadorCincoCentavos = contadorCincoCentavos;
    }

    public BigDecimal getContadorDiezCentavos() {
        return contadorDiezCentavos;
    }

    public void setContadorDiezCentavos(BigDecimal contadorDiezCentavos) {
        this.contadorDiezCentavos = contadorDiezCentavos;
    }

    public BigDecimal getContadorVcincoCentavos() {
        return contadorVcincoCentavos;
    }

    public void setContadorVcincoCentavos(BigDecimal contadorVcincoCentavos) {
        this.contadorVcincoCentavos = contadorVcincoCentavos;
    }

    public BigDecimal getContadorCincuentaCentavos() {
        return contadorCincuentaCentavos;
    }

    public void setContadorCincuentaCentavos(BigDecimal contadorCincuentaCentavos) {
        this.contadorCincuentaCentavos = contadorCincuentaCentavos;
    }

    public BigDecimal getContadorDolarCentavos() {
        return contadorDolarCentavos;
    }

    public void setContadorDolarCentavos(BigDecimal contadorDolarCentavos) {
        this.contadorDolarCentavos = contadorDolarCentavos;
    }

    public BigDecimal getContadorUnDolar() {
        return contadorUnDolar;
    }

    public void setContadorUnDolar(BigDecimal contadorUnDolar) {
        this.contadorUnDolar = contadorUnDolar;
    }

    public BigDecimal getContadorCincoDolares() {
        return contadorCincoDolares;
    }

    public void setContadorCincoDolares(BigDecimal contadorCincoDolares) {
        this.contadorCincoDolares = contadorCincoDolares;
    }

    public BigDecimal getContadorDiezDolares() {
        return contadorDiezDolares;
    }

    public void setContadorDiezDolares(BigDecimal contadorDiezDolares) {
        this.contadorDiezDolares = contadorDiezDolares;
    }

    public BigDecimal getContadorVeinteDolares() {
        return contadorVeinteDolares;
    }

    public void setContadorVeinteDolares(BigDecimal contadorVeinteDolares) {
        this.contadorVeinteDolares = contadorVeinteDolares;
    }

    public BigDecimal getValorReceptado() {
        return valorReceptado;
    }

    public void setValorReceptado(BigDecimal valorReceptado) {
        this.valorReceptado = valorReceptado;
    }

    public BigDecimal getCambio() {
        return cambio;
    }

    public void setCambio(BigDecimal cambio) {
        this.cambio = cambio;
    }

    public Pago getNuevoPago() {
        return nuevoPago;
    }

    public void setNuevoPago(Pago nuevoPago) {
        this.nuevoPago = nuevoPago;
    }

    public List<PFijo> getListaMulta() {
        return listaMulta;
    }

    public void setListaMulta(List<PFijo> listaMulta) {
        this.listaMulta = listaMulta;
    }
    
    
    public DetalleMonedaFacade getDetalleMonedaFacade() {
        return detalleMonedaFacade;
    }

    public void setDetalleMonedaFacade(DetalleMonedaFacade detalleMonedaFacade) {
        this.detalleMonedaFacade = detalleMonedaFacade;
    }

    public DetalleMoneda getDetalleMoneda() {
        return detalleMoneda;
    }

    public void setDetalleMoneda(DetalleMoneda detalleMoneda) {
        this.detalleMoneda = detalleMoneda;
    }

    public List<DetalleMoneda> getListaDetalleMoneda() {
        return listaDetalleMoneda;
    }

    public void setListaDetalleMoneda(List<DetalleMoneda> listaDetalleMoneda) {
        this.listaDetalleMoneda = listaDetalleMoneda;
    }

    public TipomonedaFacade getTipoMonedaFacade() {
        return tipoMonedaFacade;
    }

    public void setTipoMonedaFacade(TipomonedaFacade tipoMonedaFacade) {
        this.tipoMonedaFacade = tipoMonedaFacade;
    }

}
