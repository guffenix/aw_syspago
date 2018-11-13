/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.arqui.servicio;

import ec.edu.espe.arqui.entidades.Cliente;
import ec.edu.espe.arqui.entidades.Pago;
import ec.edu.espe.arqui.facade.ClienteFacade;
import ec.edu.espe.arqui.facade.PagoFacade;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Rolando Cachipuendo
 */
@ManagedBean
@ViewScoped
public class PagoCredito {
    
    private BigDecimal contadorCentavo;
    private BigDecimal contadorCincoCentavos;
    private BigDecimal contadorUnDolar;
    private BigDecimal contadorCincoDolares;
    private BigDecimal contadorDiezDolares;
    private BigDecimal valorReceptado;
    private BigDecimal cambio;
    
    private String identificacionIngresada;

    private Cliente clienteEncontrado;

    private Pago nuevoPago;

    @EJB
    private ClienteFacade clienteFacade;

    

    @EJB
    private PagoFacade pagoFacade;

    public PagoCredito() {
        valorReceptado = BigDecimal.ZERO;
        cambio = BigDecimal.ZERO;
        
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
        BigDecimal contadorCentavoAux = ((contadorCentavo != null) ? contadorCentavo : BigDecimal.ZERO).multiply(new BigDecimal("0.01"));
        BigDecimal contadorCincoCentavosAux = ((contadorCincoCentavos != null) ? contadorCincoCentavos : BigDecimal.ZERO).multiply(new BigDecimal("0.05"));;
        BigDecimal contadorUnDolarAux = ((contadorUnDolar != null) ? contadorUnDolar : BigDecimal.ZERO).multiply(new BigDecimal("1"));;
        BigDecimal contadorCincoDolaresAux = ((contadorCincoDolares != null) ? contadorCincoDolares : BigDecimal.ZERO).multiply(new BigDecimal("5"));;
        BigDecimal contadorDiezDolaresAux = ((contadorDiezDolares != null) ? contadorDiezDolares : BigDecimal.ZERO).multiply(new BigDecimal("10"));;
        valorReceptado = contadorCentavoAux.add(contadorCincoCentavosAux).add(contadorUnDolarAux).add(contadorCincoDolaresAux).add(contadorDiezDolaresAux).setScale(2, RoundingMode.HALF_UP);
        cambio = valorReceptado.subtract(nuevoPago.getPagValor());
    }
    
    public void registrarPago() {
        if (cambio.compareTo(BigDecimal.ZERO) >= 0) {
            nuevoPago.setPagEstado(new BigInteger("2"));
            pagoFacade.actualizarPago(nuevoPago);
            clienteEncontrado = null;
            nuevoPago = null;
            valorReceptado = null;
            identificacionIngresada=null;
            cambio = null;

            contadorCentavo = null;
            contadorCincoCentavos = null;
            contadorUnDolar = null;
            contadorCincoDolares = null;
            contadorDiezDolares = null;;
            cambio= null;
        } else {
            mostrarMensaje("debe ingresar un numero mayor", false);
            System.out.println("debe ingresar un numero mayor");
        }

    }

    public void buscar() {
        try {
            if (identificacionIngresada != null && !identificacionIngresada.trim().equals("")) {
                clienteEncontrado = clienteFacade.obtenerClientePorIdentificacion(identificacionIngresada);
                if (clienteEncontrado != null) {
                    nuevoPago = pagoFacade.obtenerPago(clienteEncontrado.getCliIdentificacion());

                } else {
                    nuevoPago = null;
                    mostrarMensaje("no hay el cliente", false);

                }
            } else {
                mostrarMensaje("no hay el identificacion", false);
            }

        } catch (Exception e) {
        }

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

}
