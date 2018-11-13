
package ec.edu.espe.arqui.servicio;

import ec.edu.espe.arqui.cls.PFijo;
import ec.edu.espe.arqui.entidades.Cliente;
import ec.edu.espe.arqui.entidades.DetalleMoneda;
import ec.edu.espe.arqui.entidades.Pago;
import ec.edu.espe.arqui.entidades.Tipomoneda;
import ec.edu.espe.arqui.facade.ClienteFacade;
import ec.edu.espe.arqui.facade.DetalleMonedaFacade;
import ec.edu.espe.arqui.facade.PagoFacade;
import ec.edu.espe.arqui.facade.TipomonedaFacade;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author bigdata09
 */
@ManagedBean
@ViewScoped
public class PagoInternet implements Serializable{
    
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
    
    private String identificacionIngresada;

    private Cliente clienteEncontrado;
    List<PFijo> listaInternet = new ArrayList();

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

    public PagoInternet() {
        valorReceptado = BigDecimal.ZERO;
        cambio = BigDecimal.ZERO;
        
    }

    public List<PFijo> getListaInternet() {
        return listaInternet;
    }

    public void setListaInternet(List<PFijo> listaInternet) {
        this.listaInternet = listaInternet;
    }

    
    public BigDecimal getValorReceptado() {
        return valorReceptado;
    }

    public void setValorReceptado(BigDecimal valorReceptado) {
        this.valorReceptado = valorReceptado;
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

    public BigDecimal getCambio() {
        return cambio;
    }

    public void setCambio(BigDecimal cambio) {
        this.cambio = cambio;
    }
    
    
    

    public String getIdentificacionIngresada() {
        return identificacionIngresada;
    }

    public void setIdentificacionIngresada(String identificacionIngresada) {
        this.identificacionIngresada = identificacionIngresada;
    }

    public Cliente getClienteEncontrado() {
        return clienteEncontrado;
    }

    public void setClienteEncontrado(Cliente clienteEncontrado) {
        this.clienteEncontrado = clienteEncontrado;
    }

    public Pago getNuevoPago() {
        return nuevoPago;
    }

    public void setNuevoPago(Pago nuevoPago) {
        this.nuevoPago = nuevoPago;
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
//        if(contadorCentavoAux != null){
//            tipoMoneda = tipoMonedaFacade.find(new BigDecimal(33));
//            detalleMoneda.setTipomoneda(tipoMoneda);
//            detalleMoneda.setDmonCantidad(contadorCentavo.toBigInteger());
//            detalleMoneda.setDmonTotal(contadorCentavoAux.doubleValue());
//            listaDetalleMoneda.add(detalleMoneda);
//        }
//        if(contadorCincoCentavosAux != null){
//            tipoMoneda = tipoMonedaFacade.find(new BigDecimal(34));
//            detalleMoneda.setTipomoneda(tipoMoneda);
//            detalleMoneda.setDmonCantidad(contadorCincoCentavos.toBigInteger());
//            detalleMoneda.setDmonTotal(contadorCincoCentavosAux.doubleValue());
//            listaDetalleMoneda.add(detalleMoneda);
//        }
//        if(contadorDiezCentavosAux != null){
//            tipoMoneda = tipoMonedaFacade.find(new BigDecimal(35));
//            detalleMoneda.setTipomoneda(tipoMoneda);
//            detalleMoneda.setDmonCantidad(contadorDiezCentavos.toBigInteger());
//            detalleMoneda.setDmonTotal(contadorDiezCentavosAux.doubleValue());
//            listaDetalleMoneda.add(detalleMoneda);
//        }
//        if(contadorVcincoCentavosAux != null){
//            tipoMoneda = tipoMonedaFacade.find(new BigDecimal(36));
//            detalleMoneda.setTipomoneda(tipoMoneda);
//            detalleMoneda.setDmonCantidad(contadorVcincoCentavos.toBigInteger());
//            detalleMoneda.setDmonTotal(contadorVcincoCentavosAux.doubleValue());
//            listaDetalleMoneda.add(detalleMoneda);
//        }
//        if(contadorCincuentaCentavosAux != null){
//            tipoMoneda = tipoMonedaFacade.find(new BigDecimal(37));
//            detalleMoneda.setTipomoneda(tipoMoneda);
//            detalleMoneda.setDmonCantidad(contadorCincuentaCentavos.toBigInteger());
//            detalleMoneda.setDmonTotal(contadorCincuentaCentavosAux.doubleValue());
//            listaDetalleMoneda.add(detalleMoneda);
//        }
//        if(contadorDolarCentavosAux != null){
//            tipoMoneda = tipoMonedaFacade.find(new BigDecimal(38));
//            detalleMoneda.setTipomoneda(tipoMoneda);
//            detalleMoneda.setDmonCantidad(contadorDolarCentavos.toBigInteger());
//            detalleMoneda.setDmonTotal(contadorDolarCentavosAux.doubleValue());
//            listaDetalleMoneda.add(detalleMoneda);
//        }
//        if(contadorUnDolarAux != null){
//            tipoMoneda = tipoMonedaFacade.find(new BigDecimal(29));
//            detalleMoneda.setTipomoneda(tipoMoneda);
//            detalleMoneda.setDmonCantidad(contadorUnDolar.toBigInteger());
//            detalleMoneda.setDmonTotal(contadorUnDolarAux.doubleValue());
//            listaDetalleMoneda.add(detalleMoneda);
//        }
//        if(contadorCincoDolaresAux != null){
//            tipoMoneda = tipoMonedaFacade.find(new BigDecimal(30));
//            detalleMoneda.setTipomoneda(tipoMoneda);
//            detalleMoneda.setDmonCantidad(contadorCincoDolares.toBigInteger());
//            detalleMoneda.setDmonTotal(contadorCincoDolaresAux.doubleValue());
//            listaDetalleMoneda.add(detalleMoneda);
//        }
//        if(contadorDiezDolaresAux != null){
//            tipoMoneda = tipoMonedaFacade.find(new BigDecimal(31));
//            detalleMoneda.setTipomoneda(tipoMoneda);
//            detalleMoneda.setDmonCantidad(contadorDiezDolares.toBigInteger());
//            detalleMoneda.setDmonTotal(contadorDiezDolaresAux.doubleValue());
//            listaDetalleMoneda.add(detalleMoneda);
//        }
//        if(contadorVeinteDolaresAux != null){
//            tipoMoneda = tipoMonedaFacade.find(new BigDecimal(32));
//            detalleMoneda.setTipomoneda(tipoMoneda);
//            detalleMoneda.setDmonCantidad(contadorVeinteDolares.toBigInteger());
//            detalleMoneda.setDmonTotal(contadorVeinteDolaresAux.doubleValue());
//            listaDetalleMoneda.add(detalleMoneda);
//        }
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
            
            mostrarMensaje("Pago realizado correctamente", false);
            clienteEncontrado = null;
            identificacionIngresada = null;
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

    public void buscar() {
        try {
            if (identificacionIngresada != null && !identificacionIngresada.trim().equals("")) {
                clienteEncontrado = clienteFacade.obtenerClientePorIdentificacion(identificacionIngresada);
                if (clienteEncontrado != null) {
                    nuevoPago = pagoFacade.obtenerPagoInternet(clienteEncontrado.getCliIdentificacion());

                } else {
                    nuevoPago = null;
                    mostrarMensaje("no hay el cliente", false);

                }
            } else {
                mostrarMensaje("no hay el identificacion", false);
            }

        } catch (Exception e) {
            System.out.println("no encontrado");
        }

    }
    
    public List<PFijo> reporteTInternet(){
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("ec.edu.espe.arqui_aw_syspago_war_1.0PU");
        EntityManager em1 = factory.createEntityManager();
        try {
            Query a = em1.createNativeQuery(
                    "SELECT cl.cli_identificacion,cl.cli_tipo, cl.cli_nombre, cl.cli_direccion, pg.pag_valor, pg.pag_estado, pg.pag_fecha FROM cliente cl, pago pg, servicio sv WHERE cl.cli_identificacion=pg.cli_identificacion AND pg.ser_id=sv.ser_id AND sv.ser_id=26");
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
                listaInternet.add(pfijo);
            }            
            return listaInternet;
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return null;
    }

    private void mostrarMensaje(String _mensaje, boolean _esInformativo) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(_esInformativo ? FacesMessage.SEVERITY_INFO : FacesMessage.SEVERITY_ERROR, _mensaje, null));
    }

    public void setClienteFacade(ClienteFacade clienteFacade) {
        this.clienteFacade = clienteFacade;
    }

    public void setPagoFacade(PagoFacade pagoFacade) {
        this.pagoFacade = pagoFacade;
    }
    
    public ClienteFacade getClienteFacade() {
        return clienteFacade;
    }

    public PagoFacade getPagoFacade() {
        return pagoFacade;
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

    public BigDecimal getContadorVeinteDolares() {
        return contadorVeinteDolares;
    }

    public void setContadorVeinteDolares(BigDecimal contadorVeinteDolares) {
        this.contadorVeinteDolares = contadorVeinteDolares;
    }

    public TipomonedaFacade getTipoMonedaFacade() {
        return tipoMonedaFacade;
    }

    public void setTipoMonedaFacade(TipomonedaFacade tipoMonedaFacade) {
        this.tipoMonedaFacade = tipoMonedaFacade;
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

    public Tipomoneda getTipoMoneda() {
        return tipoMoneda;
    }

    public void setTipoMoneda(Tipomoneda tipoMoneda) {
        this.tipoMoneda = tipoMoneda;
    }

}
