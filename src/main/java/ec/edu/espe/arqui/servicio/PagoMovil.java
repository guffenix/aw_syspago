/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.arqui.servicio;

import ec.edu.espe.arqui.cls.PFijo;
import ec.edu.espe.arqui.entidades.Cliente;
import ec.edu.espe.arqui.entidades.Pago;
import ec.edu.espe.arqui.facade.ClienteFacade;
import ec.edu.espe.arqui.facade.PagoFacade;
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
 * @author jorge
 */
@ManagedBean
@ViewScoped
public class PagoMovil {
    private Cliente clienteEncontrado;
    private String numFijo;
    private String nameService;
    private Pago nuevoPago;
    List<PFijo> listaM = new ArrayList();
    
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

    @EJB
    private ClienteFacade clienteFacade;

    @EJB
    private PagoFacade pagoFacade;
    
    public PagoMovil() {
        valorReceptado = BigDecimal.ZERO;
        cambio = BigDecimal.ZERO;
    }

    public List<PFijo> getListaM() {
        return listaM;
    }

    public void setListaM(List<PFijo> listaM) {
        this.listaM = listaM;
    }

    
    public String getNameService() {
        return nameService;
    }

    public void setNameService(String nameService) {
        this.nameService = nameService;
    }
    
    
    public Cliente getClienteEncontrado() {
        return clienteEncontrado;
    }

    public void setClienteEncontrado(Cliente clienteEncontrado) {
        this.clienteEncontrado = clienteEncontrado;
    }

    public String getNumFijo() {
        return numFijo;
    }

    public void setNumFijo(String numFijo) {
        this.numFijo = numFijo;
    }

    public Pago getNuevoPago() {
        return nuevoPago;
    }

    public void setNuevoPago(Pago nuevoPago) {
        this.nuevoPago = nuevoPago;
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
    
    public void actualizarEstado() {
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
    }
    
    public void buscar() {
        try {
            if (numFijo != null && !numFijo.trim().equals("")) {
                clienteEncontrado = clienteFacade.obtenerClientePorContrapartida(numFijo);
                if (clienteEncontrado != null) {
                    nuevoPago = pagoFacade.obtenerPagoByMovil(clienteEncontrado.getCliIdentificacion(),nameService);
                    //buscar pagos
                } else {
                    nuevoPago = null;
                    mostrarMensaje("no hay el cliente", false);
                    System.out.println("no hay el cliente");
                }
            } else {

            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
    
    public void registrarPago() {
        if (cambio.compareTo(BigDecimal.ZERO) >= 0) {
            nuevoPago.setPagEstado(new BigInteger("2"));
            pagoFacade.actualizarPago(nuevoPago);
            clienteEncontrado = null;
            numFijo = null;
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
            cambio= null;
        } else {
            mostrarMensaje("debe ingresar un numero mayor", false);
            System.out.println("debe ingresar un numero mayor");
        }

    }
    
    public List<PFijo> reporteTMovil(){
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("ec.edu.espe.arqui_aw_syspago_war_1.0PU");
        EntityManager em1 = factory.createEntityManager();
        try {
            Query a = em1.createNativeQuery(
                    "SELECT cl.cli_identificacion,cl.cli_tipo, cl.cli_nombre, cl.cli_direccion, pg.pag_valor, pg.pag_estado, pg.pag_fecha FROM cliente cl, pago pg, servicio sv WHERE cl.cli_identificacion=pg.cli_identificacion AND pg.ser_id=sv.ser_id AND sv.ser_id BETWEEN 31 AND 33");
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
                listaM.add(pfijo);
            }            
            return listaM;
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return null;
    }
    private void mostrarMensaje(String _mensaje, boolean _esInformativo)
    {
        FacesContext context =FacesContext.getCurrentInstance();
        context.addMessage(null,  new FacesMessage(_esInformativo ? FacesMessage.SEVERITY_INFO : FacesMessage.SEVERITY_ERROR, _mensaje, null));
    }
}
