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
import org.primefaces.PrimeFaces;

/**
 *
 * @author Rolando Cachipuendo
 */
@ManagedBean
@ViewScoped
public class PagoBasico implements Serializable {

    private BigDecimal contadorCentavo;
    private BigDecimal contadorCincoCentavos;
    private BigDecimal contadorUnDolar;
    private BigDecimal contadorCincoDolares;
    private BigDecimal contadorDiezDolares;
    private BigDecimal valorReceptado;
    private BigDecimal cambio;

    private boolean esAgua;

    private String contraPartidaIngresada;

    private Cliente clienteEncontrado;

    private Pago nuevoPago;
    List<PFijo> listaBasico = new ArrayList();

    @EJB
    private ClienteFacade clienteFacade;

    @EJB
    private PagoFacade pagoFacade;

    public PagoBasico() {
        valorReceptado = BigDecimal.ZERO;
        cambio = BigDecimal.ZERO;
        esAgua = true;
    }

    public List<PFijo> getListaBasico() {
        return listaBasico;
    }

    public void setListaBasico(List<PFijo> listaBasico) {
        this.listaBasico = listaBasico;
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

    public void buscar() {
        try {
            if (contraPartidaIngresada != null && !contraPartidaIngresada.trim().equals("")) {
                clienteEncontrado = clienteFacade.obtenerClientePorContrapartida(contraPartidaIngresada);
                if (clienteEncontrado != null) {
                    nuevoPago = pagoFacade.obtenerPagoByCliente(clienteEncontrado.getCliIdentificacion(), true, esAgua);
                    //buscar pagos
                } else {
                    nuevoPago = null;
                    mostrarMensaje("no hay el cliente", false);
                    System.out.println("no hay el cliente");
                }
            } else {

            }
        } catch (Exception e) {
        }
    }

    public void registrarPago() {
        if (cambio.compareTo(BigDecimal.ZERO) >= 0) {
            nuevoPago.setPagEstado(new BigInteger("2"));
            pagoFacade.actualizarPago(nuevoPago);
            clienteEncontrado = null;
            contraPartidaIngresada = null;
            nuevoPago = null;
            valorReceptado = null;
            cambio = null;

            contadorCentavo = null;
            contadorCincoCentavos = null;
            contadorUnDolar = null;
            contadorCincoDolares = null;
            contadorDiezDolares = null;
            valorReceptado = null;
            cambio= null;
        } else {
            mostrarMensaje("debe ingresar un numero mayor", false);
            System.out.println("debe ingresar un numero mayor");
        }

    }
    
    public List<PFijo> reporteTBasico(){
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("ec.edu.espe.arqui_aw_syspago_war_1.0PU");
        EntityManager em1 = factory.createEntityManager();
        try {
            Query a = em1.createNativeQuery(
                    "SELECT cl.cli_identificacion,cl.cli_tipo, cl.cli_nombre, cl.cli_direccion, pg.pag_valor, pg.pag_estado, pg.pag_fecha FROM cliente cl, pago pg, servicio sv WHERE cl.cli_identificacion=pg.cli_identificacion AND pg.ser_id=sv.ser_id AND sv.ser_id BETWEEN 20 AND 21");
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
                listaBasico.add(pfijo);
            }            
            return listaBasico;
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

    public boolean isEsAgua() {
        return esAgua;
    }

    public void setEsAgua(boolean esAgua) {
        this.esAgua = esAgua;
    }

    public String getContraPartidaIngresada() {
        return contraPartidaIngresada;
    }

    public void setContraPartidaIngresada(String contraPartidaIngresada) {
        this.contraPartidaIngresada = contraPartidaIngresada;
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

    public ClienteFacade getClienteFacade() {
        return clienteFacade;
    }

    public void setClienteFacade(ClienteFacade clienteFacade) {
        this.clienteFacade = clienteFacade;
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

}
